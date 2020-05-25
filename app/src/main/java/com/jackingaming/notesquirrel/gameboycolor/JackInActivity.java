package com.jackingaming.notesquirrel.gameboycolor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.FroggerCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.PongCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.PoohFarmerCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.input.ViewportFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class JackInActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    private InputManager inputManager;
    private GameCartridge.Id cartridgeID;
    private GameCartridge gameCartridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jack_in);
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreate(Bundle)");


        //final GameView gameView = (GameView) findViewById(R.id.gameView);
        final ViewportFragment viewportFragment = (ViewportFragment) getSupportFragmentManager().findFragmentById(R.id.viewportfragment);
        DirectionalPadFragment directionalPadFragment = (DirectionalPadFragment) getSupportFragmentManager().findFragmentById(R.id.directionalPadFragment);
        ButtonPadFragment buttonPadFragment = (ButtonPadFragment) getSupportFragmentManager().findFragmentById(R.id.buttonPadFragment);
        Button swapGameButton = (Button) findViewById(R.id.swap_game);
        Button launchDvdActivityButton = (Button) findViewById(R.id.launch_dvd_activity_button);


        //////////////////////////////////
        inputManager = new InputManager();
        //////////////////////////////////
        GameView gameView = viewportFragment.getView().findViewById(R.id.viewportfragment_gameview);
        gameView.setOnTouchListener(inputManager);
        directionalPadFragment.setOnDirectionalPadTouchListener(inputManager);
        buttonPadFragment.setOnButtonPadTouchListener(inputManager);


        //////////////////////////////////////////////////////
        cartridgeID = GameCartridge.Id.POCKET_CRITTERS;
        gameCartridge = new PocketCrittersCartridge(this, cartridgeID);
        //////////////////////////////////////////////////////
        swapGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapGame();
            }
        });


        // Click event will launch ListFragmentDvdParentActivity.
        launchDvdActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fragmentParentDvdIntent = new Intent(JackInActivity.this, ListFragmentDvdParentActivity.class);
                startActivity(fragmentParentDvdIntent);
            }
        });


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

        //@@@@@@@CONTEXT_MENU@@@@@@@
        if (cartridgeID == GameCartridge.Id.POCKET_CRITTERS) {
            registerForContextMenu(gameView);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreateContextMenu(ContextMenu, View, ContextMenu.ContextMenuInfo)");
        MenuInflater inflater = getMenuInflater();
        //@@@@@@@CONTEXT_MENU@@@@@@@
        inflater.inflate(R.menu.context_menu_pocket_critters, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //@@@@@@@CONTEXT_MENU@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onContextItemSelected(MenuItem)");
        switch (item.getItemId()) {
            case R.id.critter_dex:
                Toast.makeText(this, "Critterdex", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.belt_list:
                Toast.makeText(this, "Belt List", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.backpack_list:
                Toast.makeText(this, "Backpack List", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.load:
                Toast.makeText(this, "Load", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.option:
                Toast.makeText(this, "Option", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.exit:
                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void swapGame() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.swapGame()");

        //GameView gameView = (GameView) findViewById(R.id.gameView);
        GameView gameView = (GameView) findViewById(R.id.viewportfragment_gameview);

        gameView.shutDownRunner();

        int index = cartridgeID.ordinal();
        index++;
        if (index >= GameCartridge.Id.values().length) {
            index = 0;
        }

        ///////////////////////////////////////////////
        cartridgeID = GameCartridge.Id.values()[index];
        ///////////////////////////////////////////////

        switch (cartridgeID) {
            case POCKET_CRITTERS:
                gameCartridge = new PocketCrittersCartridge(this, GameCartridge.Id.POCKET_CRITTERS);
                break;
            case POOH_FARMER:
                gameCartridge = new PoohFarmerCartridge(this, GameCartridge.Id.POOH_FARMER);
                break;
            case PONG:
                gameCartridge = new PongCartridge(this, GameCartridge.Id.PONG);
                break;
            case FROGGER:
                gameCartridge = new FroggerCartridge(this, GameCartridge.Id.FROGGER);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "JackInActivity.swapGame() switch's default block.");
                gameCartridge = new PoohFarmerCartridge(this, GameCartridge.Id.POOH_FARMER);
                break;
        }

        ///////////////////////////////////////////////////////////////
        gameView.runGameCartridge(gameCartridge, inputManager,
                gameView.getWidthScreen(), gameView.getHeightScreen());
        ///////////////////////////////////////////////////////////////
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onSaveInstanceState(Bundle) calling gameCartridge.savePresentState()");

        /////////////////////////////////
        gameCartridge.savePresentState();
        /////////////////////////////////
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onRestoreInstanceState(Bundle)");

        /////////////////////////////////
        if (savedInstanceState != null) {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onRestoreInstanceState(Bundle) calling gameCartridge.loadSavedState()");
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onRestoreInstanceState(Bundle) gameCartridge NOT init() YET! gameCartridge.loadSavedState() is called again in GameView.runGameCartridge()!!!!!");
            //gameCartridge.loadSavedState();
        }
        /////////////////////////////////

        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onRestoreInstanceState(Bundle) " +
                "WILL TAKE CARE OF IN GameView.runGameCartridge(GameCartridge, InputManager, int, int)");
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

    public GameCartridge getGameCartridge() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.getGameCartridge()");
        return gameCartridge;
    }

    public InputManager getInputManager() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.getInputManager()");
        return inputManager;
    }

    public Bundle getSavedInstanceState() {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.getSavedInstanceState()");
        return savedInstanceState;
    }

}