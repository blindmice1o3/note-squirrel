package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_viewer);

        setListListener();
    }

    private void setListListener() {
        //dynamically populating the ListView object
        String[] values = getResources().getStringArray(R.array.list_book_title);

        //demonstrating that it's dynamically loading the list item
        //values[0] = "Virtual Light";

        //transform a bunch of String objects (from the array) into list items
            //the "R" is not the one we've used before (relative to this current project), it's
            //actually "android.R" which is a predefined constant from android (xml layout files).
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);

        ListView list = (ListView) findViewById(R.id.list);

        //no longer "hard-coded" into the layout xml file; now the list items are loaded dynamically
        /////////////////////////
        list.setAdapter(adapter);
        /////////////////////////

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Toast.makeText(ListViewerActivity.this, "Pos: " + position + "; value: " + adapter.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}