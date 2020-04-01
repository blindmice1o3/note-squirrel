package com.jackingaming.notesquirrel.learn_fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.R;

public class FragmentParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_parent);

        ImageView imageView = (ImageView) findViewById(R.id.imageview_fragment);
        //Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
        imageView.setBackgroundResource(R.drawable.gbc_hm2_spritesheet_items);
    }

}