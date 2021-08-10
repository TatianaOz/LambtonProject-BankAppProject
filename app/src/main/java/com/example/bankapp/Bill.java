package com.example.bankapp;

import java.io.Serializable;
import java.util.Locale;

public class Bill implements Serializable {
    private String type;
    private String suscriptionNumber;
    private double amount;

    public Bill(String type, String suscriptionNumber, double amount) {
        this.type = type;
        this.suscriptionNumber = suscriptionNumber;
        this.amount = amount;
    }

    public String getType() { return type; }

    public String getSuscriptionNumber() { return suscriptionNumber; }

    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return type+ ": " + suscriptionNumber;
    }
}
