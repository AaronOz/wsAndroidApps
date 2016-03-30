package com.example.aaron.moneyexchange;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by aaron on 2/9/16.
 */
public class ExchangeCoinActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        //Get the coin values from the mainActivity
        Bundle extras = getIntent().getExtras();
        String coinFrom = extras.getString("coinFrom");
        String coinTo = extras.getString("coinTo");

        Spinner spinnerCoinFrom = (Spinner) findViewById(R.id.spinnerFrom);
        Spinner spinnerCoinTo = (Spinner) findViewById(R.id.spinnerTo);

        String[]  coins = getResources().getStringArray(R.array.coins);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,coins);
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerCoinFrom.setAdapter(myAdapter);
        spinnerCoinTo.setAdapter(myAdapter);
        //Set spinner with the coin values from the mainActivity
        int spinnerPos = myAdapter.getPosition(coinFrom);
        spinnerCoinFrom.setSelection(spinnerPos);
        spinnerPos = myAdapter.getPosition(coinTo);
        spinnerCoinTo.setSelection(spinnerPos);


    }
    public void clickReturn(View view){
        Intent i = new Intent(this,MainActivity.class);
        boolean newValue = true;
        /* Change these 2 lines into 1
        Spinner spinnerCoinTo = (Spinner) findViewById(R.id.spinnerTo);
        spinnerCoinTo.getSelectedItem().toString();*/
        i.putExtra("coinFrom", ((Spinner) findViewById(R.id.spinnerFrom)).getSelectedItem().toString());
        i.putExtra("coinTo", ((Spinner) findViewById(R.id.spinnerTo)).getSelectedItem().toString());
        startActivity(i);

    }
}
