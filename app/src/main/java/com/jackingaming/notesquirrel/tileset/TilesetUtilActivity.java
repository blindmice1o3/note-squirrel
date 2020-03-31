package com.jackingaming.notesquirrel.tileset;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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

        leftPanel = (ImageView) findViewById(R.id.tileset_view);
        rightPanel = (ImageView) findViewById(R.id.tileset_palatte);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "TilesetUtilActivity.onStart().");

        Bitmap fromRightPanel = ((TilesetPaletteView)rightPanel).tile00x00;
        leftPanel.setImageBitmap(fromRightPanel);
    }

}
