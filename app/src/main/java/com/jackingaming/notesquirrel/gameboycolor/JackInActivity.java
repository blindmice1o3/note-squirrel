package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jackingaming.notesquirrel.R;

public class JackInActivity extends AppCompatActivity {

    private boolean isPoohFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jack_in);

        final GameView gameView = (GameView) findViewById(R.id.gameView);
        Button swapGameButton = (Button) findViewById(R.id.swap_game);
        isPoohFarmer = true;

        swapGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPoohFarmer = !isPoohFarmer;
                gameView.switchGame(isPoohFarmer);
            }
        });

        /*
        GameView gameView = (GameView) findViewById(R.id.game);
        SurfaceHolder holder = gameView.getHolder();
        holder.addCallback(gameView);
         */
    }

}