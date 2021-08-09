package com.example.bankapp;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;
    public static ArrayList<Client> mClients = new ArrayList<>();

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
    private DataManager() {
    }

    private void initializeClients() {
        mClients.add(initializeClient1());
        mClients.add(initializeClient2());
        mClients.add(initializeClient3());
    }

    private Client initializeClient1() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(789654399, 1505.0));
        accounts.add(new Account(789654398, 2005.0));
        accounts.add(new Account(789654397, 800.0));

        return new Client(456654, "Rick Sanchez", accounts, 1234, "rick@gmail1.com");
    }

    private Client initializeClient2() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(789654301, 105.0));
        accounts.add(new Account(789654302, 100.0));
        accounts.add(new Account(789654303, 25.0));

        return new Client(789987, "Morty Smith", accounts, 1234, "morty@gmail1.com");
    }

    private Client initializeClient3() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(789654310, 1500.0));
        accounts.add(new Account(789654311, 200.0));
        accounts.add(new Account(789654313, 5205.0));

        return new Client(321123, "Summer Smith", accounts, 1234);
    }

    //Find Client by name
    public static Client getClientByName(String name){
        Client client = null;
        for (Client c: mClients){
            if (c.getClientName().equals(name)){
                client = c;
            }
        }
        return client;
    }

    //Find client index (in Array) by name
    public static int getClientIndexByName(String name){
        int index = 0;
        for (int i = 0; i < mClients.size(); i++){
            if (mClients.get(i).getClientName().equals(name)){
                index = i;
            }
        }
        return index;
    }

    //Find account by its number
    public static Account findAccountByNumber(Client c, int number){
        if(c == null)
            return null;
        List<Account> allAccs = c.getClientAccounts();
        Account acc = null;
        for (Account a: allAccs){
            if (a.getAccountId() == number)
                acc = a;
        }
        return acc;
    }

    //Get account index by account number
    public static int getIndexByNum(int id, ArrayList<Account> list){
        int index = 0;
        for (int i = 0; i < list.size(); i++){
            if (id == list.get(i).getAccountId()){
                index = i;
            }
        }
        return index;
    }
}
