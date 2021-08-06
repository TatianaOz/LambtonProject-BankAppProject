package com.example.bankapp;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;
    private List<Client> mClients = new ArrayList<>();

    public static DataManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DataManager();
            ourInstance.initializeClients();
        }
        return ourInstance;
    }

    public List<Client> getClients() {
        return mClients;
    }

    private void initializeClients() {
        mClients.add(initializeClient1());
        mClients.add(initializeClient2());
        mClients.add(initializeClient3());
    }

    private Client initializeClient1() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account(789654321, 2505.0));
        accounts.add(new Account(789654321, 2505.0));
        accounts.add(new Account(789654321, 2505.0));

        return new Client(456654, "Rick Sanchez", accounts, 1234);
    }

    private Client initializeClient2() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account(789654321, 2505.0));
        accounts.add(new Account(789654321, 2505.0));
        accounts.add(new Account(789654321, 2505.0));

        return new Client(789987, "Morty Smith", accounts, 1234);
    }

    private Client initializeClient3() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account(789654321, 2505.0));
        accounts.add(new Account(789654321, 2505.0));
        accounts.add(new Account(789654321, 2505.0));

        return new Client(321123, "Summer Smith", accounts, 1234);
    }
}
