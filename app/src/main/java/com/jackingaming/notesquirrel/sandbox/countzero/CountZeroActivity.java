package com.jackingaming.notesquirrel.sandbox.countzero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;
import java.util.List;

public class CountZeroActivity extends AppCompatActivity {

    private static final int START_OF_MONTH = 11;
    private static final int END_OF_MONTH = 30;

    private List<Entry> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_zero);

        initializeDates();
        inflateListViewUsingDates();

        loadIsActiveColumn();
    }

    private void initializeDates() {
        dates = new ArrayList<Entry>();

        //SEPTEMBER
        for (int i = START_OF_MONTH; i <= END_OF_MONTH; i++) {
            String date = String.format("2020_09_%02d", i);
            Entry entry = new Entry(date, false);

            dates.add(entry);
        }
    }

    private void inflateListViewUsingDates() {
        ListView listView = (ListView) findViewById(R.id.listview_count_zero_viewport);

        EntryToListItemAdapter entryToListItemAdapter = new EntryToListItemAdapter(this, dates);
        listView.setAdapter(entryToListItemAdapter);
    }

    //Called before the activity is paused.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        saveIsActiveColumn();
    }

    private void saveIsActiveColumn() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        for (Entry entry : dates) {
            String date = entry.getDate();
            boolean isActive = entry.isActive();

            editor.putBoolean(date, isActive);
        }

        ////////////////
        editor.commit();
        ////////////////
    }

    private void loadIsActiveColumn() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        for (Entry entry : dates) {
            String date = entry.getDate();
            boolean isActive = pref.getBoolean(date, false);

            entry.setActive(isActive);
        }
    }

}