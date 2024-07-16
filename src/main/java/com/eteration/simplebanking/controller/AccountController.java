package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        Account account = accountService.findAccount(accountNumber);
        DepositTransaction transaction = new DepositTransaction(request.getAmount());
        account.post(transaction);
        return ResponseEntity.ok(new TransactionStatus("OK"));
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        Account account = accountService.findAccount(accountNumber);
        WithdrawalTransaction transaction = new WithdrawalTransaction(request.getAmount());
        if (account.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.post(transaction);
        return ResponseEntity.ok(new TransactionStatus("OK"));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.findAccount(accountNumber);
        return ResponseEntity.ok(account);
    }
}
