package com.example.bankapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

    //Fields
    private int accessCardNumber;
    private String clientName;
    private List<Account> clientAccounts;
    private int pin;
    private String email;

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

    public List<Account> getClientAccounts() {
        return clientAccounts;
    }

    public void setClientAccounts(List<Account> clientAccounts) {
        this.clientAccounts = clientAccounts;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


    //Constructors
    public Client(int accessCardNumber, String clientName, List<Account> clientAccounts, int pin, String email) {
        this.accessCardNumber = accessCardNumber;
        this.clientName = clientName;
        this.clientAccounts = clientAccounts;
        this.pin = pin;
        this.email = email;
    }

    public Client(int accessCardNumber, String clientName, List<Account> clientAccounts, int pin) {
        this.accessCardNumber = accessCardNumber;
        this.clientName = clientName;
        this.clientAccounts = clientAccounts;
        this.pin = pin;
        this.email = "";
    }

    public Client(int accessCardNumber, String clientName, int pin, String email) {
        this.accessCardNumber = accessCardNumber;
        this.clientName = clientName;
        this.clientAccounts = new ArrayList<>();
        this.pin = pin;
        this.email = email;
    }

    public Client(int accessCardNumber, String clientName, int pin) {
        this.accessCardNumber = accessCardNumber;
        this.clientName = clientName;
        this.clientAccounts = new ArrayList<>();
        this.pin = pin;
        this.email = "";
    }
}
