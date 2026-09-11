package az.orient.starkbank.runner;

import az.orient.starkbank.model.Account;
import az.orient.starkbank.model.City;
import az.orient.starkbank.model.Customer;
import az.orient.starkbank.repository.jpa.AccountRepository;
import az.orient.starkbank.repository.jpa.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeederRunner implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Customer c1 = Customer.builder().name("Yusif").city(City.BAKU).address("Ev").dateOfBirth(2000).build();
        Customer c2 = Customer.builder().name("Elsad").city(City.LERIK).address("Ev").dateOfBirth(2001).build();
        Customer c3 = Customer.builder().name("Elbey").city(City.QAZAX).address("Qazax").dateOfBirth(2001).build();

        List<Customer> savedCustomers = customerRepository.saveAll(Arrays.asList(c1, c2, c3));

        Account a1 = Account.builder()
                .city(City.BAKU)
                .balance(1300.0)
                .customerId(savedCustomers.get(0).getId()) // c1 (Yusif) üçün ID
                .build();

        Account a2 = Account.builder()
                .city(City.LERIK)
                .balance(2000.0)
                .customerId(savedCustomers.get(1).getId()) // c2 (Elşad) üçün ID
                .build();

        Account a3 = Account.builder()
                .city(City.QAZAX)
                .balance(2.0)
                .customerId(savedCustomers.get(2).getId()) // c3 (Elbəy) üçün ID
                .build();

        //Hesabları bazaya yazırıq
        accountRepository.saveAll(Arrays.asList(a1, a2, a3));
    }
}
