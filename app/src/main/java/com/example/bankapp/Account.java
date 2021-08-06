package com.example.bankapp;

public class Account {

    // Fields
    private int accountId;
    private double amount;

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Constructor
    public Account(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public Account(int accountId) {
        this.accountId = accountId;
        this.amount = 0;
    }

}
