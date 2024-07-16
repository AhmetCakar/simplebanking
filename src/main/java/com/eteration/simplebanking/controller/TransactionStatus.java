package com.eteration.simplebanking.controller;

public class TransactionStatus {
    private String status;

    public TransactionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
