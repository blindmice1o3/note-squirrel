package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(MainActivity.DEBUG_TAG, "CameraResultActivity.onStart() BEFORE extraction of passed in image address.");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageFilePath = extras.getString("imageAddress");

            if (imageFilePath != null) {
                Log.d(MainActivity.DEBUG_TAG, "CameraResultActivity.onStart() AFTER extraction of passed in image address: " + imageFilePath);

                /*
                In order to READ (e.g. when we invoke "decodeFile(String)") from the
                device's external storage, we need to request PERMISSION (this is done
                within the "AndroidManifest.xml" file).
                */
                Bitmap photoCapturedByCamera = BitmapFactory.decodeFile(imageFilePath);

                if (photoCapturedByCamera != null) {
                    imageView.setImageBitmap(photoCapturedByCamera);
                } else {
                    Toast.makeText(this, "Unable to save photo file: CameraResultActivity.onStart()... photoCapturedByCamera == null.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}