package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

public class JackInActivity extends AppCompatActivity {

    private boolean isPoohFarmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jack_in);
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreate(Bundle)");

        final GameView gameView = (GameView) findViewById(R.id.gameView);
        DirectionalPadFragment directionalPadFragment = (DirectionalPadFragment) getSupportFragmentManager().findFragmentById(R.id.directionalPadFragment);
        ButtonPadFragment buttonPadFragment = (ButtonPadFragment) getSupportFragmentManager().findFragmentById(R.id.buttonPadFragment);
        Button swapGameButton = (Button) findViewById(R.id.swap_game);

        directionalPadFragment.setOnDirectionalPadTouchListener(new DirectionalPadFragment.OnDirectionalPadTouchListener() {
            @Override
            public void onDirectionalPadTouched(DirectionalPadFragment.Direction direction) {
                gameView.onDirectionalPadTouched(direction);
            }
        });

        isPoohFarmer = true;
        swapGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPoohFarmer = !isPoohFarmer;
                gameView.switchGame(isPoohFarmer);
            }
        });
    }

}