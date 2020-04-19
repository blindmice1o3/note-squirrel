package com.jackingaming.notesquirrel.sandbox.learncommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class CommunicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        TextView textView = (TextView) findViewById(R.id.textView_communication);
        Button buttonLeft = (Button) findViewById(R.id.button_left_communication);
        Button buttonRight = (Button) findViewById(R.id.button_right_communication);

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.buttonLeft.onClick(View)");
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "CommunicationActivity.buttonRight.onClick(View)");
            }
        });
    }

}