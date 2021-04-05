package com.jackingaming.notesquirrel.sandbox.autopilotoff.hellomoon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jackingaming.notesquirrel.R;

public class HelloMoonActivity extends AppCompatActivity {
    private static final String TAG = "HelloMoonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_moon);
        Log.i(TAG, "onCreate(Bundle)");
    }
}