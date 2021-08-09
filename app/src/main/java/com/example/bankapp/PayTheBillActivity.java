package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PayTheBillActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    CheckBox saveBill;
    Spinner spinnerAccount, spinnerBill;
    TextView txtSubscriptionNumber;
    TextView txtSaved;
    TextView txtAmount;
    Button btnPay;
    RadioButton rdHydro;
    RadioButton rdWater;
    RadioButton rdGas;
    RadioButton rdPhone;

    Client client;
    ArrayList<Account> clientAccounts;
    ArrayList<Bill> clientBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_the_bill);

        radioGroup = findViewById(R.id.billTypeGroup);

        saveBill = findViewById(R.id.btnSave);
        spinnerAccount = findViewById(R.id.spinner);
        spinnerBill = findViewById(R.id.spBillsSaved);
        txtSubscriptionNumber = findViewById(R.id.txtSubscriptionNumber);
        txtSaved = findViewById(R.id.txtSaved);
        txtAmount = findViewById(R.id.txtAmount);
        btnPay = findViewById(R.id.btnPay);
        rdHydro = findViewById(R.id.rdHydro);
        rdWater = findViewById(R.id.rdWater);
        rdGas = findViewById(R.id.rdGas);
        rdPhone = findViewById(R.id.rdPhone);

        rdHydro.setChecked(true);

        client = (Client) getIntent().getSerializableExtra("Client");
        clientAccounts = new ArrayList<>(client.getClientAccounts());
        clientBills = new ArrayList<>(client.getClientBills());

        if (clientBills.size() == 0){
            txtSaved.setVisibility(View.INVISIBLE);
            spinnerBill.setVisibility(View.INVISIBLE);
        }

        ArrayAdapter adapterBill = new ArrayAdapter(this,android.R.layout.simple_spinner_item,clientBills);
        adapterBill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBill.setAdapter(adapterBill);

        ArrayAdapter adapterAccount = new ArrayAdapter(this,android.R.layout.simple_spinner_item,clientAccounts);
        adapterAccount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(adapterAccount);

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


                // go to final activity
                Intent goToFinalActivity = new Intent(getApplicationContext(), TransactionFinishedActivity.class);
                goToFinalActivity.putExtra("title", "Your transaction succeed!");
                goToFinalActivity.putExtra("message", "The "+ selectedRadio.getText().toString().toLowerCase() + " bill was successfully paid!");
                goToFinalActivity.putExtra("Client", client);
                startActivity(goToFinalActivity);
            }
        });

        saveBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton selectedRadio = findViewById(radioGroup.getCheckedRadioButtonId());
                String valType = selectedRadio.getText().toString().toLowerCase();
                String valSubscriptionNumber = txtSubscriptionNumber.getText().toString();
                double  valAmount = Double.parseDouble(txtAmount.getText().toString());
                client.getClientBills().add(new Bill(valType, valSubscriptionNumber,valAmount));
            }
        });
    }

    //go back to previous activity
    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Client", client);
        startActivity(intent);
    }

    //close th virtual keyboard when click on emty place
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}