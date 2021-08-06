package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.bankapp.adapter.AccountListAdapter;

public class AccountListActivity extends AppCompatActivity {

    ListView listViewAccounts;

    Account []accounts = {
            new Account(1,100),
            new Account(2, 1200),
            new Account(3, 2000),
            new Account(4, 4000)
    };

    AccountListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        listViewAccounts = findViewById(R.id.listViewAccounts);

        listAdapter = new AccountListAdapter(this, accounts);

        listViewAccounts.setAdapter(listAdapter);


    }


}