package com.jackingaming.notesquirrel.sandbox.restclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.restclient.tutorial.DataRepresentation;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestClientActivity extends AppCompatActivity {

    private static final String KEY_COUNTER_SAVED_INSTANCE_STATE = "counter";

    private int counterSavedInstanceState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);

        Log.d(MainActivity.DEBUG_TAG, "RestClientActivity.onCreate(Bundle)");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO: change url from HttpRequestTask.
//        new HttpRequestTask().execute();
    }

    //Called after onStart(). It is only called if there is a saved state to restore
    // (do NOT need to check if savedInstanceState is-not null).
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(MainActivity.DEBUG_TAG, "RestClientActivity.onRestoreInstanceState(Bundle)");
        int counterSavedInstanceState = savedInstanceState.getInt(KEY_COUNTER_SAVED_INSTANCE_STATE);
        this.counterSavedInstanceState = counterSavedInstanceState;
        Toast.makeText(this, String.valueOf(counterSavedInstanceState), Toast.LENGTH_LONG).show();
    }

    //Called before the activity is paused.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(MainActivity.DEBUG_TAG, "RestClientActivity.onSavedInstanceState(Bundle)");
        counterSavedInstanceState++;
        outState.putInt(KEY_COUNTER_SAVED_INSTANCE_STATE, counterSavedInstanceState);

        super.onSaveInstanceState(outState);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, DataRepresentation> {

        @Override
        protected DataRepresentation doInBackground(Void... voids) {
            try {
                ////////////////////////////////////////////////////////////////////////////////////////
                //https://projects.spring.io/spring-android/
                // The connection URL (from where to fetch data [json])
                final String url = "www.example.com";
                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate();
                // Add the Jackson message converter
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                // Make the HTTP GET request, marshaling the response to a DataRepresentation
                DataRepresentation dataRepresentation = restTemplate.getForObject(url, DataRepresentation.class);
                ////////////////////////////////////////////////////////////////////////////////////////

                return dataRepresentation;
            } catch (Exception e) {
                Log.d(MainActivity.DEBUG_TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(DataRepresentation dataRepresentation) {
            TextView idLabel = (TextView) findViewById(R.id.id_label);
            TextView contentLabel = (TextView) findViewById(R.id.content_label);
            TextView nameLabel = (TextView) findViewById(R.id.name_label);
            TextView phoneNumberLabel = (TextView) findViewById(R.id.phone_number_label);
            TextView emailAddressLabel = (TextView) findViewById(R.id.email_address_label);

            idLabel.setText(dataRepresentation.getId());
            contentLabel.setText(dataRepresentation.getContent());
            nameLabel.setText(dataRepresentation.getName());
            phoneNumberLabel.setText(dataRepresentation.getPhoneNumber());
            emailAddressLabel.setText(dataRepresentation.getEmailAddress());
        }

    }

}