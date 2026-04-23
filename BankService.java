package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;

@Service
public class BankService {

    private Map<Integer, Account> accounts = new HashMap<>();
    private int nextAccountNumber = 1001;

    public Account createAccount(String name, int pin, double balance) {
        Account acc = new Account(nextAccountNumber, name, pin, balance);
        accounts.put(nextAccountNumber, acc);
        nextAccountNumber++;
        return acc;
    }

    public Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public Account login(int accountNumber, int pin) {

        Account acc = accounts.get(accountNumber);

        if (acc == null) {
            throw new RuntimeException("Account not found");
        }

        if (!acc.validatePin(pin)) {
            throw new RuntimeException("Invalid PIN");
        }

        return acc;
    }

    public void deposit(int accountNumber, double amount) {

        Account acc = accounts.get(accountNumber);

        if (acc == null) {
            throw new RuntimeException("Account not found");
        }

        acc.deposit(amount); 
    }


    public void withdraw(int accountNumber, double amount) {

        Account acc = accounts.get(accountNumber);

        if (acc == null) {
            throw new RuntimeException("Account not found");
        }

        acc.withdraw(amount); 
    }

    public List<Transaction> getTransactions(int accountNumber) {

        Account acc = accounts.get(accountNumber);

        if (acc == null) {
            throw new RuntimeException("Account not found");
        }

        return acc.getTransactions();
    }
}
