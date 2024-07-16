package com.eteration.simplebanking.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private double amount;

    public TransactionRequest() {
    }

    public TransactionRequest(double amount) {
        this.amount = amount;
    }
}
