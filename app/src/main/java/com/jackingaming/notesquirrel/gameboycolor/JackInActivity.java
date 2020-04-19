package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.PongCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.PoohFarmerCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.sandbox.learnfragment.FragmentParentDvdActivity;

public class JackInActivity extends AppCompatActivity {

    public enum CartridgeID { POOH, PONG; }

    private Bundle savedInstanceState;
    private CartridgeID cartridgeID;

    private GameCartridge gameCartridge;

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
                gameCartridge.onDirectionalPadInput(direction);
            }
        });

        buttonPadFragment.setOnButtonPadTouchListener(new ButtonPadFragment.OnButtonPadTouchListener() {
            @Override
            public void onButtonPadTouched(ButtonPadFragment.InputButton inputButton) {
                gameCartridge.onButtonPadInput(inputButton);
            }
        });

        cartridgeID = CartridgeID.POOH;
        swapGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapGame();
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



        /////////////////////////////////////////////////////////////////////
        gameCartridge = new PoohFarmerCartridge(this, getResources());
        /////////////////////////////////////////////////////////////////////



        /////////////////////////////////////////////
        this.savedInstanceState = savedInstanceState;
        /////////////////////////////////////////////

        //TODO: this is the solution to a new GameCartridge being instantiated from configuration (orientation or keyboard) changes.
        //Will probably have to move GameCartridge and GameRunner here.
        //https://stackoverflow.com/questions/456211/activity-restart-on-rotation-android?rq=1
        if (savedInstanceState == null) {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreate(Bundle) Bundle savedInstanceState is null: " + savedInstanceState);
        } else {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreate(Bundle) Bundle savedInstanceState is NOT null: " + savedInstanceState);
        }

    }

    public void swapGame() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.switchGame()");

        GameView gameView = (GameView) findViewById(R.id.gameView);

        gameView.shutDownRunner();

        int index = cartridgeID.ordinal();
        index++;
        if (index >= CartridgeID.values().length) {
            index = 0;
        }

        ////////////////////////////////////////////
        cartridgeID = CartridgeID.values()[index];
        ////////////////////////////////////////////

        switch (cartridgeID) {
            case POOH:
                gameCartridge = new PoohFarmerCartridge(this, getResources());
                break;
            case PONG:
                gameCartridge = new PongCartridge(this, getResources());
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "JackInActivity.switchGame() switch's default block.");
                gameCartridge = new PoohFarmerCartridge(this, getResources());
                break;
        }

        gameView.runGameCartridge(gameCartridge);
    }

    public GameCartridge getGameCartridge() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.getGameCartridge()");
        return gameCartridge;
    }

    public Bundle getSavedInstanceState() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.getSavedInstanceState()");
        return savedInstanceState;
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

        ///////////////////////////////////////////////////////////
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onPause() calling gameCartridge.savePresentState()");
        gameCartridge.savePresentState();
        ///////////////////////////////////////////////////////////
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