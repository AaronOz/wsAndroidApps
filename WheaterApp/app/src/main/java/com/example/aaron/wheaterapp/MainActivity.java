package com.example.aaron.wheaterapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            HttpURLConnection connection = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("OnPostExecute", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray weatherArray = jsonObject.getJSONArray("weather");

                TextView weatherResult = (TextView) findViewById(R.id.txtResult);

                if(jsonObject.getInt("cod") == 200){
                    
                    for(int i = 0; i < weatherArray.length(); i++){
                        JSONObject weather = weatherArray.getJSONObject(i);

                        weatherResult.setText(weather.getString("description"));
                    }
                }
            } catch (JSONException e) {
                TextView weatherResult = (TextView) findViewById(R.id.txtResult);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Woops seems that isnt a country/city", Toast.LENGTH_SHORT).show();
                weatherResult.setText("Error");
            }
        }
    }
    public void CheckWeather(View view){
        EditText input = (EditText) findViewById(R.id.inputPlace);

        String weather = input.getText().toString();
        DownloadTask task = new DownloadTask();
        weather = weather.replaceAll(" ", "%20"); //change all spaces into %20 for the URL
        //weather = "http://api.openweathermap.org/data/2.5/weather?q=" + weather + "&appid=b1b15e88fa797225412429c1c50c122a";

        if(input != null || !input.getText().equals("")) {
            task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + weather + "&appid=b1b15e88fa797225412429c1c50c122a");
        }else{
            Toast.makeText(getApplicationContext(), "Please type a country or city", Toast.LENGTH_SHORT).show();
        }
        //These lines are for hide the keyboard phone
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
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
