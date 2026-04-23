package com.example.demo.model;

import java.time.LocalDateTime;

public class Transaction {

    private String type;
    private double amount;
    private LocalDateTime time;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
