package com.eteration.simplebanking.model;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private String accountNumber;
    private double balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {}

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public void deposit(double amount) {
        DepositTransaction transaction = new DepositTransaction(amount);
        post(transaction);
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        WithdrawalTransaction transaction = new WithdrawalTransaction(amount);
        post(transaction);
    }

    public void post(Transaction transaction) throws InsufficientBalanceException {
        if (transaction instanceof DepositTransaction) {
            balance += transaction.getAmount();
        } else if (transaction instanceof WithdrawalTransaction) {
            if (balance >= transaction.getAmount()) {
                balance -= transaction.getAmount();
            } else {
                throw new InsufficientBalanceException("Insufficient balance");
            }
        }
        transactions.add(transaction);
    }
}
