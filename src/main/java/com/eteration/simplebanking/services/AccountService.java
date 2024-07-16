package com.eteration.simplebanking.services;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eteration.simplebanking.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccount(String accountNumber) {
        return accountRepository.findById(accountNumber).orElse(null);
    }

    public void credit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            DepositTransaction transaction = new DepositTransaction(amount);
            try {
                account.post(transaction);
                accountRepository.save(account);
            } catch (InsufficientBalanceException e) {
                // Handle exception
            }
        }
    }

    public void debit(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = findAccount(accountNumber);
        if (account != null) {
            WithdrawalTransaction transaction = new WithdrawalTransaction(amount);
            account.post(transaction);
            accountRepository.save(account);
        }
    }
}
