package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    Spinner from, to;
    EditText clientName, etAmount;
    TextView clientNameLabel;
    Button transfer, findClient;
    Client client;
    Client otherClient;
    ArrayList<Account> accountList;
    ArrayList<Account> otherClientAccList;
    ArrayList<Integer> accountNumbers = new ArrayList<>();
    ArrayList<Integer> accNumbers = new ArrayList<>();
    ArrayAdapter<Integer> adapterTo;
    Context context;
    int selectedAccFrom, selectedAccTo = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);
        context = this;
        toMyAcc = findViewById(R.id.toMyAcc);
        toOtherAcc = findViewById(R.id.toOtherClient);
        from = findViewById(R.id.spFromAcc);
        to = findViewById(R.id.spToAcc);
        clientName = findViewById(R.id.etClientName);
        etAmount = findViewById(R.id.etAmount);
        transfer = findViewById(R.id.buttonTransfer);
        clientNameLabel = findViewById(R.id.clientNameLable);
        findClient = findViewById(R.id.findClient);

        client = (Client) getIntent().getSerializableExtra("Client");
        otherClient = client;
        accountList = new ArrayList<>(client.getClientAccounts());
        for (int i = 0; i < accountList.size(); i++){
            accountNumbers.add(accountList.get(i).getAccountId());
        }
        accNumbers.addAll(accountNumbers);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,accountNumbers);
        adapterTo = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,accountNumbers);
        from.setAdapter(adapter);
        to.setAdapter(adapterTo);
        toMyAcc.toggle();
        toMyAcc.setOnClickListener(new RadioButtonListener());
        toOtherAcc.setOnClickListener(new RadioButtonListener());

        clientName.setEnabled(false);
        clientName.setVisibility(View.INVISIBLE);
        clientNameLabel.setVisibility(View.INVISIBLE);
        findClient.setVisibility(View.INVISIBLE);

        findClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,new ArrayList<>());
                to.setAdapter(adapterTo);
                String name = clientName.getText().toString();
                otherClient = findClientByName(name);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(clientName.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                if (client != null){
                    accNumbers = new ArrayList<>();
                    otherClientAccList = new ArrayList<>(client.getClientAccounts());
                    for (int i = 0; i < otherClientAccList.size(); i++){
                        accNumbers.add(otherClientAccList.get(i).getAccountId());
                    }
                    adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,accNumbers);
                    to.setAdapter(adapterTo);

                } else {
                    Toast.makeText(MoneyTransferActivity.this, "This client does not exist, please check the name.", Toast.LENGTH_LONG).show();
                }

            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etAmount.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                //transfer the money and sent the email
                double am = Double.parseDouble(etAmount.getText().toString());
                Account accFrom = findAccountByNumber(client, selectedAccFrom);
                Account accTo = findAccountByNumber(otherClient, selectedAccTo);
                makeTransfer(accFrom, accTo, am);
            }
        });
        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAccFrom = accountNumbers.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAccTo = accNumbers.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private Client findClientByName(String name){
        List<Client> allClients = DataManager.getInstance().getClients();
        for (Client c: allClients){
            if (c.getClientName().equals(name))
                return c;
        }
        return null;
    }

    private Account findAccountByNumber(Client c, int number){
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

    private void makeTransfer(Account from, Account to, double amount){
        if (from.getAmount() < amount)
            Toast.makeText(MoneyTransferActivity.this, "You don't have enough money on this account.", Toast.LENGTH_LONG).show();
        else {
            //Set account "from" values
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);

            //update values in main list
            if (toMyAcc.isChecked()){
                accountList.set(getIndexByNum(to.getAccountId(), accountList),to);
                accountList.set(getIndexByNum(from.getAccountId(), accountList),from);
                client.setClientAccounts(accountList);
                otherClient = client;

            }else {
                otherClientAccList.set(getIndexByNum(to.getAccountId(), otherClientAccList),to);
                accountList.set(getIndexByNum(from.getAccountId(), accountList),from);
                client.setClientAccounts(accountList);
                otherClient.setClientAccounts(otherClientAccList);
            }
            Toast.makeText(MoneyTransferActivity.this, "Your transaction succeed!", Toast.LENGTH_LONG).show();
            etAmount.setText("");
//            //send the mail
//            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + otherClient.getEmail()));
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Money Transfer");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "You got " + amount + " to your account number " + to.getAccountId());
//            startActivity(Intent.createChooser(emailIntent, "Bank"));
        }

    }

    private int getIndexByNum(int id, ArrayList<Account> list){
        int index = 0;
        for (int i = 0; i < list.size(); i++){
            if (id == list.get(i).getAccountId()){
                index = i;
            }
        }
        return index;
    }

    private class RadioButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.toMyAcc:
                    clientName.setEnabled(false);
                    clientName.setVisibility(View.INVISIBLE);
                    clientNameLabel.setVisibility(View.INVISIBLE);
                    findClient.setVisibility(View.INVISIBLE);
                    adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,accountNumbers);
                    to.setAdapter(adapterTo);
                    otherClient  = client;
                    break;
                case R.id.toOtherClient:
                    clientName.setEnabled(true);
                    clientName.setVisibility(View.VISIBLE);
                    clientNameLabel.setVisibility(View.VISIBLE);
                    findClient.setVisibility(View.VISIBLE);
                    adapterTo = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,new ArrayList<>());
                    to.setAdapter(adapterTo);
                    clientName.setText("");
                    otherClient = null;
                    break;
            }
        }
    }
}