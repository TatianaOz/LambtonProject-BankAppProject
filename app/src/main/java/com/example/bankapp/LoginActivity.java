package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Button login;
    static ArrayList<Client> database = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fillData();

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo check validation and get Client from the ArrayList of clients

                //only for test - added some client. Remove this row
                Client client = new Client("User1", "AA BB", "123R");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Client",client);
                startActivity(intent);
            }
        });
    }

    // create a database - add clients with accounts
    private void fillData(){

        database.add(new Client("user1", "Jhon Smith", new ArrayList<Account>() {{add(new Account(123, 259.09)); add (new Account(345, 67.8));}},"u765"));
    }
}