package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //MUST come after setContentView(int).
        addTouchListener();
    }

    private void addTouchListener() {
        ImageView image = (ImageView) findViewById(R.id.touch_image);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                String message = String.format("Coordinates: (%.2f, %.2f)", x, y);

                //display message to Logcat monitor.
                Log.d(MainActivity.DEBUG_TAG, message);

                //display message to device's screen via a Toast pop-up.
                /* (discovered that Toast is slow, and it continues to show back-logged Toasts even after app shut down.)
                Toast toastCantSave = Toast.makeText(ImageActivity.this, message, Toast.LENGTH_SHORT);
                toastCantSave.show();
                */


                return false;
            }
        });
    }

}
