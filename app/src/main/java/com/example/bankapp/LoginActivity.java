package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo chech validation and get Client from the ArrayList of clients
                //only for test - added some client. Remove this row
                Client client = new Client("User1", "AA BB", "123R");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Client",client);
                startActivity(intent);
            }
        });
    }
}