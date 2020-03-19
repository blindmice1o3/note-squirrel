package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        Log.d(MainActivity.DEBUG_TAG, "PhotoViewerActivity.onStart() BEFORE extraction of passed in image address.");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageFilePath = extras.getString("imageAddress");
            if (imageFilePath != null) {
                Log.d(MainActivity.DEBUG_TAG, "PhotoViewerActivity.onStart() AFTER extraction of passed in image address: " + imageFilePath);
                Bitmap photoCapturedByCamera = BitmapFactory.decodeFile(imageFilePath);
                imageView.setImageBitmap(photoCapturedByCamera);
            }
        }

        /*
        if (photoCapturedByCamera != null) {
            Log.d(MainActivity.DEBUG_TAG, "PhotoViewerActivity.onStart() END (photoCaptured != null).");
            imageView.setImageBitmap(photoCapturedByCamera);
        }

        */
    }

}