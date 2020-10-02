package com.jackingaming.notesquirrel.sandbox.countzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.jackingaming.notesquirrel.R;

public class CountZeroActivity extends AppCompatActivity {

    private static final int START_OF_MONTH = 1;
    private static final int END_OF_MONTH = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_zero_scrollview_with_switches);

        initializeSwitchViews();
    }

    private void initializeSwitchViews() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout_for_switches);
        LinearLayout.LayoutParams layoutParamsOfSwitchViews = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int day = START_OF_MONTH; day <= END_OF_MONTH; day++) {
            String textOfLabel = String.format("2020_10_%02d", day);

            Switch switchView = new Switch(this);
            switchView.setChecked(false);
            switchView.setLayoutParams(layoutParamsOfSwitchViews);
            switchView.setPadding(20, 10, 0, 10);
            switchView.setGravity(Gravity.START);

            int textColor = 0;
            int backgroundColor = 0;
            if (day % 2 == 0) {
                textColor = Color.WHITE;
                backgroundColor = Color.CYAN;
            } else {
                textColor = Color.GRAY;
                backgroundColor = Color.WHITE;
            }

            switchView.setTextColor(textColor);
            switchView.setBackgroundColor(backgroundColor);
            switchView.setTextSize(30);
            switchView.setText(textOfLabel);

            linearLayout.addView(switchView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadFromSharedPreferences();
    }

    private void loadFromSharedPreferences() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout_for_switches);
        for (int day = START_OF_MONTH; day <= END_OF_MONTH; day++) {
            int index = day - 1;
            Switch switchView = (Switch) linearLayout.getChildAt(index);

            String key = Integer.toString(day);
            boolean isChecked = pref.getBoolean(key, false);

            switchView.setChecked(isChecked);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveToSharedPreferences();
    }

    private void saveToSharedPreferences() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout_for_switches);
        for (int day = START_OF_MONTH; day <= END_OF_MONTH; day++) {
            int index = day - 1;
            Switch switchView = (Switch) linearLayout.getChildAt(index);

            String key = Integer.toString(day);
            boolean isChecked = switchView.isChecked();

            editor.putBoolean(key, isChecked);
        }

        editor.commit();
    }

}