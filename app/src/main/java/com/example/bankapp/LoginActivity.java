package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView username, password;
    List<Client> allClients = DataManager.getInstance().getClients();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
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

    private Client validLogin(String user, String pass) {
        for(Client cln:allClients)
            if(cln.getClientUserName().equalsIgnoreCase(user) && cln.getPassword().equals(pass))
                return cln;
        return null;
    }
}