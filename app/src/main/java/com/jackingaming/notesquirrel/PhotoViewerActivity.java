package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class PhotoViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(MainActivity.DEBUG_TAG, "PhotoViewerActivity.onStart() BEFORE extraction of passed in photo.");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Bitmap photoCapturedByCamera = null;
        /*
        Bundle extrasOfPreviousActivity = getIntent().getExtras();
        photoCapturedByCamera = (Bitmap) extrasOfPreviousActivity.get("photoTaken");
        */

        Log.d(MainActivity.DEBUG_TAG, "PhotoViewerActivity.onStart() AFTER extraction of passed in photo.");

        if (photoCapturedByCamera != null) {
            Log.d(MainActivity.DEBUG_TAG, "PhotoViewerActivity.onStart() END (photoCaptured != null).");
            imageView.setImageBitmap(photoCapturedByCamera);
        }
    }

}