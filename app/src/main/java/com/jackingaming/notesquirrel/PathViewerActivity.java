package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PathViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_viewer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView textView = (TextView) findViewById(R.id.text_view_file_path);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imagePath = extras.getString("pathAddress");

            if (imagePath != null) {
                textView.setText(imagePath);
            } else {
                Log.d(MainActivity.DEBUG_TAG, "imagePath is null");
            }
        } else {
            Log.d(MainActivity.DEBUG_TAG, "extras is null");
        }
    }

}