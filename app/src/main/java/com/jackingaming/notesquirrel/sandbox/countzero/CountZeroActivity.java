package com.jackingaming.notesquirrel.sandbox.countzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;

public class CountZeroActivity extends AppCompatActivity {

    public static final String KEY_OF_COUNTER = "counter";

    private TextView textViewDisplayer;
    private Button buttonIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_zero);

        textViewDisplayer = (TextView) findViewById(R.id.textview_count_zero);
        buttonIncrement = (Button) findViewById(R.id.button_count_zero);

        init();
    }

    private void init() {
        //LOAD textViewDisplayer's content.
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        int counter = pref.getInt(KEY_OF_COUNTER, 0);
        textViewDisplayer.setText(String.valueOf(counter));
    }

    public void onIncrementButtonClick(View view) {
        int counter = Integer.parseInt(textViewDisplayer.getText().toString());
        counter++;
        textViewDisplayer.setText(String.valueOf(counter));

        //SAVE textViewDisplayer's content.
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY_OF_COUNTER, counter);
        editor.commit();
    }

}