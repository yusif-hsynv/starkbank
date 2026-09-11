package az.orient.starkbank.service;

import az.orient.starkbank.dto.*;
import az.orient.starkbank.exception.AccountNotFoundException;
import az.orient.starkbank.exception.InsufficientFundsException;
import az.orient.starkbank.model.Account;
import az.orient.starkbank.repository.jpa.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;
    private final DirectExchange exchange;

    private final AmqpTemplate rabbitTemplate;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Value("${sample.rabbitmq.queue}")
    String queueName;


    @Override
    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        CustomerDto customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        Account account = Account.builder()
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .customerId(customer.getId())
                .city(createAccountRequest.getCity())
                .build();
        return accountDtoConverter.convert(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountDto updateAccount(UUID id, UpdateAccountRequest updateAccountRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("No account found to update! ID: " + id));

        account.setCity(updateAccountRequest.getCity());
        return accountDtoConverter.convert(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        return accountList.stream().map(accountDtoConverter::convert).toList();
    }

    @Override
    public AccountDto getAccountById(UUID id) {
        return accountRepository.findById(id)
                .map(accountDtoConverter::convert)
                .orElseThrow(() -> new AccountNotFoundException("Account not found! ID: " + id));
    }

    @Override
    @Transactional
    public void deleteAccount(UUID id) {
        if (!accountRepository.existsById(id)) {
            throw new AccountNotFoundException("No account found to delete! ID: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AccountDto withdrawMoney(UUID id, BalanceRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found! ID: " + id));
        if (account.getBalance() < request.getAmount()) {
            throw new InsufficientFundsException("Not enough funds! Current balance: " + account.getBalance() + " AZN");
        }
        account.setBalance(account.getBalance() - request.getAmount());
        return accountDtoConverter.convert(account);
    }

    @Override
    @Transactional
    public AccountDto addMoney(UUID id, BalanceRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found! ID: " + id));

        account.setBalance(account.getBalance() + request.getAmount());
        return accountDtoConverter.convert(account);
    }

    @Override
    public void transferMoney(MoneyTransferRequest transferRequest) {
        log.info("Starting money transfer from {} to {}, amount: {}",
                transferRequest.getFromId(), transferRequest.getToId(), transferRequest.getAmount());
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, transferRequest); //producer
    }

    //transferMoneyMessage: Göndərənin balansı yoxlanılır, pul çıxılır və növbəti addıma (RabbitMQ-yə) mesaj atılır.
    @RabbitListener(queues = "${sample.rabbitmq.queue}")  //consumer - Queue-dan mesaj oxuyan hissədir.
    public void transferMoneyMessage(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
        accountOptional.ifPresentOrElse(account -> {
                    if (account.getBalance() > transferRequest.getAmount()) {
                        account.setBalance(account.getBalance() - transferRequest.getAmount());
                        accountRepository.save(account);
                        log.info("Sender balance updated successfully. Moving to second step.");
                        rabbitTemplate.convertAndSend(exchange.getName(), "secondRoute", transferRequest);
                    } else {
                        log.warn("Insufficient funds -> accountId: {} balance: {}",
                                transferRequest.getFromId(), account.getBalance());
                    }
                },
                () -> log.error("Sender account not found! ID: {}", transferRequest.getFromId()));
    }

    //updateReceiverAccount: Alan şəxsin hesabı tapılır və pul əlavə edilir.
    // Əgər alan şəxsin hesabı tapılmazsa Kompensasiya edərək pulu göndərənə geri qaytarır.
    @RabbitListener(queues = "secondStepQueue")
    public void updateReceiverAccount(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getToId());
        accountOptional.ifPresentOrElse(account -> {
                    account.setBalance(account.getBalance() + transferRequest.getAmount());
                    accountRepository.save(account);
                    log.info("Receiver balance updated successfully. Moving to finalization step.");
                    rabbitTemplate.convertAndSend(exchange.getName(), "thirdRoute", transferRequest);
                },
                () -> {
                    log.error("Receiver Account not found! ID: {}", transferRequest.getToId());
                    Optional<Account> senderAccount = accountRepository.findById(transferRequest.getFromId());
                    senderAccount.ifPresent(sender -> {
                        log.info("Initiating charge back to sender...");
                        sender.setBalance(sender.getBalance() + transferRequest.getAmount());
                        accountRepository.save(sender);
                        log.info("Charge back completed for account: {}", sender.getId());
                    });
                }
        );
    }

    //finalizeTransfer: Prosesin yekunlaşdığını və yekun balansları göstərir.
    @RabbitListener(queues = "thirdStepQueue")
    public void finalizeTransfer(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
        accountOptional.ifPresentOrElse(account ->
                        log.info("Sender({}) new account balance: {}", account.getId(), account.getBalance()),
                () -> log.error("Sender account not found!"));

        Optional<Account> accountToOptional = accountRepository.findById(transferRequest.getToId());
        accountToOptional.ifPresentOrElse(account ->
                        log.info("Receiver({}) new account balance: {}", account.getId(), account.getBalance()),
                () -> log.error("Receiver account not found!"));
    }
}
