package com.jackingaming.notesquirrel.sandbox.learncommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class CommunicationActivity extends AppCompatActivity {

    Fragment viewFragment;
    TextView textView;

    Fragment controllerFragment;
    Button buttonFragmentSwapper;
    Button buttonWebsiteDisplayer;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.onCreate(Bundle)");

        viewFragment = (ViewFragment) getSupportFragmentManager().findFragmentById(R.id.viewFragment);
        textView = (TextView) findViewById(R.id.textView_communication);

        controllerFragment = (ControllerFragment) getSupportFragmentManager().findFragmentById(R.id.controllerFragment);
        buttonFragmentSwapper = (Button) findViewById(R.id.button_fragment_swapper_communication);
        buttonWebsiteDisplayer = (Button) findViewById(R.id.button_website_displayer_communication);

        sb = new StringBuilder();

        buttonFragmentSwapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.buttonFragmentSwapper.onClick(View)");

                DirectionalPadFragment directionalPadFragment = new DirectionalPadFragment();

                ////////////////////////////////////////////////////////////////////////////////////
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.viewFragment, directionalPadFragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
                ////////////////////////////////////////////////////////////////////////////////////
            }
        });

        buttonWebsiteDisplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.buttonWebsiteDisplayer.onClick(View)");

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