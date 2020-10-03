package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jackingaming.notesquirrel.R;

public class TelevisionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_television);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintlayout_television);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoViewActivityIntent = new Intent(TelevisionActivity.this, VideoViewActivity.class);
                startActivity(videoViewActivityIntent);
            }
        });
    }

}