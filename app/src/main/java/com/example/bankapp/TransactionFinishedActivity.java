package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TransactionFinishedActivity extends AppCompatActivity {

    TextView header, text;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_finished);
        header = findViewById(R.id.titleDoneMessage);
        text = findViewById(R.id.textDoneMessage);
        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");
        client = (Client) getIntent().getSerializableExtra("Client");
        header.setText(title);
        text.setText(message);
    }

    public void getToMainScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Client", client);
        startActivity(intent);
    }
}