package com.jackingaming.notesquirrel.sandbox.tinkermotionevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class TinkerMotionEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker_motion_event);

        ImageView imageView = (ImageView) findViewById(R.id.tinker_motion_event);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity.imageView.OnTouchListener...");
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_DOWN");
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_MOVE");
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_OUTSIDE");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_UP");
                }

                return true;
            }
        });
    }

}