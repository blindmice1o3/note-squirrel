package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.sandbox.learnfragment.FragmentParentDvdActivity;

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

        buttonPadFragment.setOnButtonPadTouchListener(new ButtonPadFragment.OnButtonPadTouchListener() {
            @Override
            public void onButtonPadTouched(ButtonPadFragment.InputButton inputButton) {
                gameView.onButtonPadTouched(inputButton);
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

        //click event will launch FragmentParentDvdActivity
        Button launchDvdActivityButton = (Button) findViewById(R.id.launch_dvd_activity_button);
        launchDvdActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fragmentParentDvdIntent = new Intent(JackInActivity.this, FragmentParentDvdActivity.class);
                startActivity(fragmentParentDvdIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onDestroy()");
    }

}