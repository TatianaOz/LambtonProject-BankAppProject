package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MoneyTransferActivity extends AppCompatActivity {
    RadioButton toMyAcc, toOtherAcc;
    Spinner spFrom, spTo;
    EditText etClientName, etAmount;
    TextView clientNameLabel;
    Button transfer, findClient;
    Client client;
    Client otherClient;
    ArrayList<Account> currentClientAccList, otherClientAccList;
    ArrayList<Integer> fromAccNumbers = new ArrayList<>();
    ArrayList<Integer> toAccNumbers = new ArrayList<>();

    ArrayAdapter<Integer> adapterTo;
    Context context;
    int selectedAccFrom, selectedAccTo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        //set views
        context = this;
        toMyAcc = findViewById(R.id.toMyAcc);
        toOtherAcc = findViewById(R.id.toOtherClient);
        spFrom = findViewById(R.id.spFromAcc);
        spTo = findViewById(R.id.spToAcc);
        etClientName = findViewById(R.id.etClientName);
        etAmount = findViewById(R.id.etAmount);
        transfer = findViewById(R.id.buttonTransfer);
        clientNameLabel = findViewById(R.id.clientNameLable);
        findClient = findViewById(R.id.findClient);

        //get current client instance. for init state other client (who gets the money) is the same client
        client = (Client) getIntent().getSerializableExtra("Client");
        otherClient = client;
        //get the accounts id list
        currentClientAccList = new ArrayList<>(client.getClientAccounts());
        for (int i = 0; i < currentClientAccList.size(); i++){
            fromAccNumbers.add(currentClientAccList.get(i).getAccountId());
        }
        toAccNumbers.addAll(fromAccNumbers);

        //set adapters with ID's for both spinners (from and to)
        ArrayAdapter<Integer> adapterFrom = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, fromAccNumbers);
        adapterTo = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, fromAccNumbers);
        spFrom.setAdapter(adapterFrom);
        spTo.setAdapter(adapterTo);
        //set radioButton toMyAccount as checked; set radioButtons listener
        toMyAcc.toggle();
        toMyAcc.setOnClickListener(new RadioButtonListener());
        toOtherAcc.setOnClickListener(new RadioButtonListener());

        //Set clientName invisible (for default option - transfer money to my account)
        etClientName.setEnabled(false);
        etClientName.setVisibility(View.INVISIBLE);
        clientNameLabel.setVisibility(View.INVISIBLE);
        findClient.setVisibility(View.INVISIBLE);

        //Find client by Name and show list of his accounts
        findClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etClientName.getText().toString();
                //find the other client (who will get the money)
                otherClient = DataManager.getClientByName(name);
                //Close virtual keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etClientName.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                //get Accounts list for other client, if client exist and set adapter
                if (otherClient != null){
                    toAccNumbers = new ArrayList<>();
                    otherClientAccList = new ArrayList<>(otherClient.getClientAccounts());
                    for (int i = 0; i < otherClientAccList.size(); i++){
                        toAccNumbers.add(otherClientAccList.get(i).getAccountId());
                    }
                    adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, toAccNumbers);
                    spTo.setAdapter(adapterTo);

                } else {
                    selectedAccTo = 0;
                    Toast.makeText(MoneyTransferActivity.this, "This client does not exist, please check the name.", Toast.LENGTH_LONG).show();
                }

            }
        });

        //transfer the money
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etAmount.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                //transfer the money and sent the email
                if (etAmount.getText().toString().equals("")){
                    Toast.makeText(MoneyTransferActivity.this, "Enter the amount!", Toast.LENGTH_LONG).show();
                } else if (etClientName.getText().toString().equals("") && toOtherAcc.isChecked()){
                    Toast.makeText(MoneyTransferActivity.this, "Enter the client name!", Toast.LENGTH_LONG).show();
                }else if (selectedAccTo == 0){
                    Toast.makeText(MoneyTransferActivity.this, "This client doesn't exists or has no accounts!", Toast.LENGTH_LONG).show();
                }else{
                    double am = Double.parseDouble(etAmount.getText().toString());
                    Account accFrom = DataManager.findAccountByNumber(client, selectedAccFrom);
                    Account accTo = DataManager.findAccountByNumber(otherClient, selectedAccTo);
                    makeTransfer(accFrom, accTo, am);
                }
            }
        });

        //set spinners listener - get account number
        spFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAccFrom = fromAccNumbers.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAccTo = toAccNumbers.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //Transfer the money and update the account
    private void makeTransfer(Account from, Account to, double amount){
        if (from.getAmount() < amount)
            Toast.makeText(MoneyTransferActivity.this, "You don't have enough money on this account.", Toast.LENGTH_LONG).show();
        else {
            //Set account "from" values
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);

            //update values in main list
            if (toMyAcc.isChecked()){
                currentClientAccList.set(DataManager.getIndexByNum(to.getAccountId(), currentClientAccList),to);
                currentClientAccList.set(DataManager.getIndexByNum(from.getAccountId(), currentClientAccList),from);
                client.setClientAccounts(currentClientAccList);
                otherClient = client;
                DataManager.mClients.set(DataManager.getClientIndexByName(client.getClientName()), client);

            }else {
                otherClientAccList.set(DataManager.getIndexByNum(to.getAccountId(), otherClientAccList),to);
                currentClientAccList.set(DataManager.getIndexByNum(from.getAccountId(), currentClientAccList),from);
                client.setClientAccounts(currentClientAccList);
                otherClient.setClientAccounts(otherClientAccList);
                DataManager.mClients.set(DataManager.getClientIndexByName(client.getClientName()), client);
                DataManager.mClients.set(DataManager.getClientIndexByName(otherClient.getClientName()), otherClient);
            }
            etAmount.setText("");

            //go to final activity
            Intent goToFinalActivity = new Intent(this, TransactionFinishedActivity.class);
            goToFinalActivity.putExtra("title", "Your transaction succeed!");
            goToFinalActivity.putExtra("message", "$" + amount + " was transfered from account " + from.getAccountId() + " to account " + to.getAccountId());
            goToFinalActivity.putExtra("Client", client);
            startActivity(goToFinalActivity);

//            //send the mail
//            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + otherClient.getEmail()));
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Money Transfer");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "You got " + amount + " to your account number " + to.getAccountId());
//            startActivity(Intent.createChooser(emailIntent, "Bank"));

        }

    }

    //go back to previous activity
    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Client", client);
        startActivity(intent);
    }

    //Radio buttons event listener
    private class RadioButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.toMyAcc:
                    etClientName.setEnabled(false);
                    etClientName.setVisibility(View.INVISIBLE);
                    clientNameLabel.setVisibility(View.INVISIBLE);
                    findClient.setVisibility(View.INVISIBLE);
                    adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, fromAccNumbers);
                    spTo.setAdapter(adapterTo);
                    otherClient  = client;
                    break;
                case R.id.toOtherClient:
                    etClientName.setEnabled(true);
                    etClientName.setVisibility(View.VISIBLE);
                    clientNameLabel.setVisibility(View.VISIBLE);
                    findClient.setVisibility(View.VISIBLE);
                    adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,new ArrayList<>());
                    spTo.setAdapter(adapterTo);
                    etClientName.setText("");
                    otherClient = null;
                    break;
            }
        }
    }
}