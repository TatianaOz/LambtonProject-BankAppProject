package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView acn, pin;
    List<Client> allClients = DataManager.getInstance().getClients();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        acn = findViewById(R.id.txtAcn);
        pin = findViewById(R.id.txtPin);
        login = findViewById(R.id.btnLogin);


        //acn.setText("321123");
        //pin.setText("1234");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int user = Integer.parseInt(acn.getText().toString());
                int pass = Integer.parseInt(pin.getText().toString());
                Client client = validLogin(user,pass);
                if(null != client){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Client", client);
                    startActivity(intent);

                }else{
                    Toast.makeText(getBaseContext(),"Invalid username or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Client validLogin(int acnum, int pinum) {
        for(Client cln:allClients)
            if(cln.getAccessCardNumber()==acnum && cln.getPin()==pinum)
                return cln;
        return null;
    }
}