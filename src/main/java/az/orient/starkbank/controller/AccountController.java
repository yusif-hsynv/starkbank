package az.orient.starkbank.controller;

import az.orient.starkbank.dto.*;
import az.orient.starkbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable UUID id, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(id, updateAccountRequest));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable UUID id, @RequestBody BalanceRequest request) {
        return ResponseEntity.ok(accountService.withdrawMoney(id, request));
    }

    @PutMapping(path = "/{id}/add")
    public ResponseEntity<AccountDto> addMoney(@PathVariable UUID id, @RequestBody BalanceRequest request) {
        return ResponseEntity.ok(accountService.addMoney(id, request));
    }

    @PutMapping(path = "/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody MoneyTransferRequest transferRequest){
        accountService.transferMoney(transferRequest);
        return ResponseEntity.ok("Your operation was successful!");
    }

}
