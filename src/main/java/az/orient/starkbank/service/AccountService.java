package az.orient.starkbank.service;

import az.orient.starkbank.dto.*;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDto createAccount(CreateAccountRequest createAccountRequest);

    AccountDto updateAccount(UUID id, UpdateAccountRequest updateAccountRequest);

    List<AccountDto> getAllAccounts();

    AccountDto getAccountById(UUID id);

    void deleteAccount(UUID id);

    AccountDto withdrawMoney(UUID id, BalanceRequest request);

    AccountDto addMoney(UUID id, BalanceRequest request);

    void transferMoney(MoneyTransferRequest transferRequest);
}
