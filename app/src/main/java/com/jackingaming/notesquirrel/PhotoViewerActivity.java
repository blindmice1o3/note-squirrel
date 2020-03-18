package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
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

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bundle extrasOfPreviousActivity = getIntent().getExtras();
        Bitmap photoCapturedByCamera = (Bitmap) extrasOfPreviousActivity.get("photoTaken");

        imageView.setImageBitmap(photoCapturedByCamera);
    }

}