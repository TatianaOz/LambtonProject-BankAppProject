package com.example.bankapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {

    //Fields
    private int accessCardNumber;
    private String clientName;
    private ArrayList<Account> clientAccounts;
    private int pin;

    //Getters and Setters
    public int getAccessCardNumber() {
        return accessCardNumber;
    }

    public void setAccessCardNumber(int accessCardNumber) {
        this.accessCardNumber = accessCardNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ArrayList<Account> getClientAccounts() {
        return clientAccounts;
    }

    public void setClientAccounts(ArrayList<Account> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    //Constructors
    public Client(int accessCardNumber, String clientName, ArrayList<Account> clientAccounts, int pin) {
        this.accessCardNumber = accessCardNumber;
        this.clientName = clientName;
        this.clientAccounts = clientAccounts;
        this.pin = pin;
    }

    public Client(int accessCardNumber, String clientName, int pin) {
        this.accessCardNumber = accessCardNumber;
        this.clientName = clientName;
        this.clientAccounts = new ArrayList<Account>();
        this.pin = pin;
    }
}
