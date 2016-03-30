package com.example.aaron.moneyexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        TextView txtCoinFrom = (TextView) findViewById(R.id.textCoinFrom);
        TextView txtCoinTo = (TextView) findViewById(R.id.textCoinTo);
        Bundle extras = getIntent().getExtras();
        if(getIntent().getStringExtra("coinFrom") != null){
            txtCoinFrom.setText(extras.getString("coinFrom"));
            txtCoinTo.setText(extras.getString("coinTo"));
        }



    }
    public void clickChangeCoin(View view){
        TextView txtCoinFrom = (TextView) findViewById(R.id.textCoinFrom);
        TextView txtCoinTo = (TextView) findViewById(R.id.textCoinTo);
        Intent i = new Intent(this,ExchangeCoinActivity.class);
        i.putExtra("coinFrom",txtCoinFrom.getText().toString());
        i.putExtra("coinTo",txtCoinTo.getText().toString());
        startActivity(i);
    }
    public void clickConvert(View view) {

        TextView txtCoinFrom = (TextView) findViewById(R.id.textCoinFrom);
        TextView txtCoinTo = (TextView) findViewById(R.id.textCoinTo);
        TextView inputMoney = (TextView) findViewById(R.id.inputMoney);
        double money = Double.parseDouble(inputMoney.getText().toString());
        double usdXmxn = 18.81;
        double eurXmxn = 21.26;
        double eurXusd = 1.13;

        if (txtCoinFrom.getText().toString().matches(txtCoinTo.getText().toString())) {
            money = money * 1;
        } else {
            if (txtCoinFrom.getText().toString().matches("EUR")) {
                if (txtCoinTo.getText().toString().matches("USD")) {
                    money = money * 1.13;
                } else {
                    money = money * 21.26;
                }
            }
            if (txtCoinFrom.getText().toString().matches("USD")) {
                if (txtCoinTo.getText().toString().matches("MXN")) {
                    money = money * 18.81;
                } else {
                    money = money / 1.13;
                }
            }
            if (txtCoinFrom.getText().toString().matches("MXN")) {
                if (txtCoinTo.getText().toString().matches("EUR")) {
                    money = money / 21.26;
                } else {
                    money = money / 18.81;
                }
            }
        }
        String conversion = String.valueOf(money);
        Toast toast = Toast.makeText(getApplicationContext(), "The conversion is " + conversion + txtCoinTo.getText().toString(), Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
