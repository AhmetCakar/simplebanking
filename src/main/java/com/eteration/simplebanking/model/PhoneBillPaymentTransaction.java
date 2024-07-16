package com.eteration.simplebanking.model;
import lombok.Data;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("PHONE_BILL_PAYMENT")
public class PhoneBillPaymentTransaction extends Transaction {
    private String phoneNumber;
    private String provider;

    public PhoneBillPaymentTransaction() {}

    public PhoneBillPaymentTransaction(String provider, String phoneNumber, double amount) {
        super(amount);
        this.provider = provider;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void apply(Account account) {
        account.setBalance(account.getBalance() - getAmount());
    }
}
