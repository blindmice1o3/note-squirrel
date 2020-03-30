package com.jackingaming.notesquirrel.tileset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class TilesetUtilActivity extends AppCompatActivity {

    private ImageView leftPanel;
    private ImageView rightPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "TilesetUtilActivity.onCreate() PRE-setContentView(int).");
        setContentView(R.layout.activity_tileset_util);
        Log.d(MainActivity.DEBUG_TAG, "TilesetUtilActivity.onCreate() POST-setContentView(int).");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "TilesetUtilActivity.onStart().");

        initLeftPanel();
        Log.d(MainActivity.DEBUG_TAG, "TilesetUtilActivity.onStart() PRE-initRightPanel().");
        initRightPanel();
        Log.d(MainActivity.DEBUG_TAG, "TilesetUtilActivity.onStart() POST-initRightPanel().");
    }

    private void initLeftPanel() {
        //TODO:
        leftPanel = (ImageView) findViewById(R.id.tileset_view);
    }
    private void initRightPanel() {
        //TODO:
        rightPanel = (ImageView) findViewById(R.id.tileset_palatte);
    }

}
