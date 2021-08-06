package com.example.bankapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankapp.Account;
import com.example.bankapp.R;

import java.util.List;

public class AccountListAdapter extends ArrayAdapter<Account> {
    private List<Account> accounts;
    private Activity context;

    public AccountListAdapter(Activity context, List<Account> accounts) {
        super(context, R.layout.list_item_account, accounts);
        this.context = context;
        this.accounts = accounts;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_account, null, true);


        TextView tvAccountName = (TextView) listViewItem.findViewById(R.id.textViewAccountId);
        TextView tvBalance = (TextView) listViewItem.findViewById(R.id.textViewBalance);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);



        tvAccountName.setText(accounts.get(position).getAccountId() + "");
        tvBalance.setText(Double.toString(accounts.get(position).getAmount()));
        return  listViewItem;
    }
}