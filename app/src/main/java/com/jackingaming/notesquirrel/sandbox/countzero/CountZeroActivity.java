package com.jackingaming.notesquirrel.sandbox.countzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;
import java.util.List;

public class CountZeroActivity extends AppCompatActivity {

    private List<Entry> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_zero);

        initDates();
        initListView();

        loadIsActiveColumn();
    }

    private void initDates() {
        dates = new ArrayList<Entry>();

        dates.add(new Entry("2020_07_26", false));
        dates.add(new Entry("2020_07_27", false));
        dates.add(new Entry("2020_07_28", false));
        dates.add(new Entry("2020_07_29", false));
        dates.add(new Entry("2020_07_30", false));
        dates.add(new Entry("2020_07_31", false));

        //AUGUST
        for (int i = 1; i <= 31; i++) {
            String day = String.format("2020_08_%02d", i);
            dates.add(new Entry(day, false));
        }
    }

    private void initListView() {
        ListView listView = (ListView) findViewById(R.id.listview_count_zero_viewport);

        EntryToListItemAdapter entryToListItemAdapter = new EntryToListItemAdapter(this, dates);
        listView.setAdapter(entryToListItemAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveIsActiveColumn();
    }

    private void saveIsActiveColumn() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        for (int index = 0; index < dates.size(); index++) {
            String date = dates.get(index).getDate();

            editor.putBoolean(date, dates.get(index).isActive());
        }

        ////////////////
        editor.commit();
        ////////////////
    }

    private void loadIsActiveColumn() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        for (int index = 0; index < dates.size(); index++) {
            String date = dates.get(index).getDate();

            boolean isActive = pref.getBoolean(date, false);

            dates.get(index).setActive(isActive);
        }
    }

}