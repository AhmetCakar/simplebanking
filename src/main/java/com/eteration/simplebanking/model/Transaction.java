package com.eteration.simplebanking.model;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private Account bankAccount;

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    public Transaction(double amount) {
        this();
        this.amount = amount;
    }

    public abstract void apply(Account account);
}

