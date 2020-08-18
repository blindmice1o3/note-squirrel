package com.jackingaming.notesquirrel.sandbox.restclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class RestClientActivity extends AppCompatActivity {

    private static final String KEY_COUNTER_SAVED_INSTANCE_STATE = "counter";

    private int counterSavedInstanceState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);

        Log.d(MainActivity.DEBUG_TAG, "RestClientActivity.onCreate(Bundle)");
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

//    //As oppose to the application being started for the first time.
//    private boolean isBeingReloaded(Bundle savedInstanceState) {
//        return (savedInstanceState != null) ? true : false;
//    }

}