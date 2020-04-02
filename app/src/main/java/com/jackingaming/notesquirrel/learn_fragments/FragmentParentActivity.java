package com.jackingaming.notesquirrel.learn_fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class FragmentParentActivity extends AppCompatActivity {

    private Bitmap imageSource;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_parent);

        imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
        imageView = (ImageView) findViewById(R.id.imageview_fragment);
        imageView.setImageBitmap(imageSource);


        Log.d(MainActivity.DEBUG_TAG,
                "imageView.getWidth(), imageView.getHeight(): " +
                        imageView.getWidth() + ", " + imageView.getHeight());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG,
                        "event.getX(), event.getY(): " +
                                event.getX() + ", " + event.getY());

                return true;
            }
        });



        button = (Button) findViewById(R.id.button_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG,
                        "imageView.getWidth(), imageView.getHeight(): " +
                                imageView.getWidth() + ", " + imageView.getHeight());

                Log.d(MainActivity.DEBUG_TAG,
                        "imageView.getDrawable().getIntrinsicWidth(), imageView.getDrawable().getIntrinsicHeight(): " +
                                imageView.getDrawable().getIntrinsicWidth() + ", " + imageView.getDrawable().getIntrinsicHeight());
            }
        });
    }

}