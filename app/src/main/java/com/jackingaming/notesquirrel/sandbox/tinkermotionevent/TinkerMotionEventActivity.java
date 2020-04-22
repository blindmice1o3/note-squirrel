package com.jackingaming.notesquirrel.sandbox.tinkermotionevent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
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

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1MotionEvent);
        imageView1.setOnTouchListener(new View.OnTouchListener() {

            private Rect rectBoundsOfView1;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity.imageView1.OnTouchListener... 111");

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_DOWN 111");

                    rectBoundsOfView1 = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_MOVE 111");

                    if( !rectBoundsOfView1.contains(
                            v.getLeft() + (int)event.getX(),
                            v.getTop() + (int)event.getY()) ) {
                        Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_MOVE 111 NOT inside rectBoundsOfView1");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_OUTSIDE 111");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_UP 111");
                }

                return true;
            }
        });

        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2MotionEvent);
        imageView2.setOnTouchListener(new View.OnTouchListener() {

            private Rect rectBoundsOfView2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity.imageView2.OnTouchListener... 222");

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_DOWN 222");

                    rectBoundsOfView2 = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_MOVE 222");

                    if( !rectBoundsOfView2.contains(
                            v.getLeft() + (int)event.getX(),
                            v.getTop() + (int)event.getY()) ) {
                        Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_MOVE 222 NOT inside rectBoundsOfView2");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_OUTSIDE 222");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d(MainActivity.DEBUG_TAG, "TinkerMotionEventActivity MotionEvent.ACTION_UP 222");
                }

                return true;
            }
        });

    }

}