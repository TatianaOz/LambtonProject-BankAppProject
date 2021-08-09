package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PayTheBillActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Spinner spinnerAccount;
    TextView txtSubscriptionNumber;
    TextView txtAmount;
    Button btnPay;

    RadioButton rdHydro;
    RadioButton rdWater;
    RadioButton rdGas;
    RadioButton rdPhone;

    Client client;
    ArrayList<Account> clientAccounts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_the_bill);

        radioGroup = findViewById(R.id.billTypeGroup);
        spinnerAccount = findViewById(R.id.spinner);
        txtSubscriptionNumber = findViewById(R.id.txtSubscriptionNumber);
        txtAmount = findViewById(R.id.txtAmount);
        btnPay = findViewById(R.id.btnPay);
        rdHydro = findViewById(R.id.rdHydro);
        rdWater = findViewById(R.id.rdWater);
        rdGas = findViewById(R.id.rdGas);
        rdPhone = findViewById(R.id.rdPhone);

        rdHydro.setChecked(true);

        client = (Client) getIntent().getSerializableExtra("Client");
        clientAccounts = new ArrayList<>(client.getClientAccounts());

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,clientAccounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerAccount.setAdapter(adapter);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Account selectedAccount = (Account) spinnerAccount.getSelectedItem();
                String strAmount = txtAmount.getText().toString();

                if (strAmount.equals("")) {
                    Toast.makeText(PayTheBillActivity.this, "Please enter an amount", Toast.LENGTH_LONG).show();
                    return;
                }

                if (selectedAccount.getAmount() < Double.parseDouble(strAmount)) {
                    Toast.makeText(PayTheBillActivity.this, "Selected account doesn't has enough amount.", Toast.LENGTH_LONG).show();
                    return;
                }

                RadioButton selectedRadio = findViewById(radioGroup.getCheckedRadioButtonId());
                //Subtract the money from account
                Account accFrom = DataManager.findAccountByNumber(client, selectedAccount.getAccountId());
                accFrom.setAmount(accFrom.getAmount() - Double.parseDouble(strAmount));
                clientAccounts.set(DataManager.getIndexByNum(accFrom.getAccountId(), clientAccounts),accFrom);
                client.setClientAccounts(clientAccounts);
                DataManager.mClients.set(DataManager.getClientIndexByName(client.getClientName()), client);

                Toast.makeText(PayTheBillActivity.this, "Successfully paid " + selectedRadio.getText() + " Bill", Toast.LENGTH_LONG).show();

                PayTheBillActivity.this.finish();
            }
        });

    }

    //go back to previous activity
    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Client", client);
        startActivity(intent);
    }
}