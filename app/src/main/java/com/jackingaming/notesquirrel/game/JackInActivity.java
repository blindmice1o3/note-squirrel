package com.jackingaming.notesquirrel.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.R;

public class JackInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jack_in);

        /*
        GameView gameView = (GameView) findViewById(R.id.game);
        SurfaceHolder holder = gameView.getHolder();
        holder.addCallback(gameView);
         */
    }

}