package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.bankapp.adapter.AccountListAdapter;

import java.util.List;

public class AccountListActivity extends AppCompatActivity {
    ListView listViewAccounts;
    List<Account> accountList;
    static Client client;


    AccountListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        client = (Client) getIntent().getSerializableExtra("Client");
        accountList = client.getClientAccounts();
        listViewAccounts = findViewById(R.id.listViewAccounts);

        listAdapter = new AccountListAdapter(this, accountList);

        listViewAccounts.setAdapter(listAdapter);


    }


}