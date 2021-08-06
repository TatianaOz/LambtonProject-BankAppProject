package com.example.bankapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {

    //Fields
    private String clientUserName;
    private String clientName;
    private ArrayList<Account> clientAccounts;
    private String password;

    //Getters and Setters
    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
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
    public Client(String clientUserName, String clientName, ArrayList<Account> clientAccounts, String password) {
        this.clientUserName = clientUserName;
        this.clientName = clientName;
        this.clientAccounts = clientAccounts;
        this.password = password;
    }

    public Client(String clientUserName, String clientName, String password) {
        this.clientUserName = clientUserName;
        this.clientName = clientName;
        this.clientAccounts = new ArrayList<Account>();
        this.password = password;
    }
}
