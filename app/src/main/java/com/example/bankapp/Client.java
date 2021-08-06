package com.example.bankapp;

import java.util.ArrayList;

public class Client {

    //Fields
    private int clientId;
    private String clientName;
    private ArrayList<Account> clientAccounts;
    private String password;

    //Getters and Setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Constructors
    public Client(int clientId, String clientName, ArrayList<Account> clientAccounts, String password) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAccounts = clientAccounts;
        this.password = password;
    }

    public Client(int clientId, String clientName, String password) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAccounts = new ArrayList<Account>();
        this.password = password;
    }
}
