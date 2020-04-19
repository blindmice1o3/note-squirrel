package com.jackingaming.notesquirrel.sandbox.learncommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class CommunicationActivity extends AppCompatActivity {

    TextView textView;
    Button buttonLeft;
    Button buttonDisplayWebsite;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.onCreate(Bundle)");

        textView = (TextView) findViewById(R.id.textView_communication);
        buttonLeft = (Button) findViewById(R.id.button_left_communication);
        buttonDisplayWebsite = (Button) findViewById(R.id.button_display_website_communication);
        sb = new StringBuilder();

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.buttonLeft.onClick(View)");
            }
        });

        buttonDisplayWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.buttonDisplayWebsite.onClick(View)");

                ////////////////////////////////
                textView.setText(sb.toString());
                ////////////////////////////////
            }
        });

        //Don't want to access the internet on the main thread (could lock up the main thread/UI)
        //Android actually prevent you from doing that... so have to do this on a separate thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    ///////////////
                    downloadHTML();
                    ///////////////
                } catch (Exception e) {
                    Log.d(MainActivity.DEBUG_TAG, e.toString());
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    private void downloadHTML() throws Exception {
        URL url = new URL("https://objectionable.net/");

        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        while ((line = br.readLine()) != null) {
            //Log.d(MainActivity.DEBUG_TAG, line);

            ////////////////
            sb.append(line);
            ////////////////
        }
    }

}