package com.jackingaming.notesquirrel.learn_fragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
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
        /*
        //Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
        imageView.setBackgroundResource(R.drawable.gbc_hm2_spritesheet_items);
        */

        Log.d(MainActivity.DEBUG_TAG,
                "imageView.getWidth(), imageView.getHeight(): " +
                        imageView.getWidth() + ", " + imageView.getHeight());

        /*
        Log.d(MainActivity.DEBUG_TAG,
                "imageView.getBackground().getBounds().right, imageView.getBackground().getBounds().bottom: " +
                        imageView.getBackground().getBounds().right + ", " + imageView.getBackground().getBounds().bottom);
         */

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