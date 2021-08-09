package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView helloMessage, accountsList, payTheBills, transferTheMoney;
    static Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloMessage = findViewById(R.id.helloMessage);
        accountsList = findViewById(R.id.accountsList);
        payTheBills = findViewById(R.id.payTheBill);
        transferTheMoney = findViewById(R.id.moneyTransfer);

        //set Hello message
        client = (Client) getIntent().getSerializableExtra("Client");
        helloMessage.setText("Hello " + client.getClientName() + "!");

    }

    public void getAction(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.accountsList:
                intent = new Intent(this, AccountListActivity.class);
                //get updated data from clients list
                client = DataManager.getClientByName(client.getClientName());
                intent.putExtra("Client", client);
                startActivity(intent);
                break;
            case R.id.payTheBill:
                intent = new Intent(this, PayTheBillActivity.class);
                client = DataManager.getClientByName(client.getClientName());
                intent.putExtra("Client", client);
                startActivity(intent);
                break;
            case R.id.moneyTransfer:
                intent = new Intent(this, MoneyTransferActivity.class);
                client = DataManager.getClientByName(client.getClientName());
                intent.putExtra("Client", client);
                startActivity(intent);
                break;
            case R.id.logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}