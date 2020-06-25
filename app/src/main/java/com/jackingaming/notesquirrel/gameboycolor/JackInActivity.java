package com.jackingaming.notesquirrel.gameboycolor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.IGameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.SerializationDoer;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.FroggerCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors.SceneHome01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors.SceneHome02;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.PongCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.BackpackActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.input.ViewportFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

import java.util.ArrayList;

public class JackInActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_BACKPACK_ACTIVITY = 17;
    public static final String INVENTORY = "INVENTORY";

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



        /////////////////////////////////////////////
        this.savedInstanceState = savedInstanceState;
        /////////////////////////////////////////////
        //TODO: this is the solution to a new GameCartridge being instantiated from configuration (orientation or keyboard) changes.
        //Will probably have to move GameCartridge and GameRunner here.
        //https://stackoverflow.com/questions/456211/activity-restart-on-rotation-android?rq=1
        if (savedInstanceState == null) {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreate(Bundle) Bundle savedInstanceState is null: " + savedInstanceState);
            ///////////////////////////////////////////////////////////////////////
            cartridgeID = GameCartridge.Id.POCKET_CRITTERS;
            gameCartridge = new GameCartridge(this, cartridgeID);
            ///////////////////////////////////////////////////////////////////////
        } else {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreate(Bundle) Bundle savedInstanceState is NOT null: " + savedInstanceState);

            /////////////////////////////////////////////////////////////////////////////////////
            //retrieving PERSISTENT data (values stored between "runs").
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            //checking if the key-value pair exists,
            //if does NOT exist (haven't done a put() and commit())...
            //it uses the default value (the second argument).
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //Load integer (its index value) as enum.
            int idGameCartridgeAsInt = prefs.getInt("idGameCartridge", GameCartridge.Id.values()[0].ordinal());
            cartridgeID = GameCartridge.Id.values()[idGameCartridgeAsInt];
            //Clear the key-value pair.
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("idGameCartridge");
            editor.commit();
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            /////////////////////////////////////////////////////////////////////////////////////

            switch (cartridgeID) {
                case POCKET_CRITTERS:
                    ///////////////////////////////////////////////////////////////////////
                    gameCartridge = new GameCartridge(this, cartridgeID);
                    ///////////////////////////////////////////////////////////////////////
                    break;
                case POOH_FARMER:
                    ///////////////////////////////////////////////////////////////////////
                    gameCartridge = new GameCartridge(this, cartridgeID);
                    ///////////////////////////////////////////////////////////////////////
                    break;
                case PONG:
                    ///////////////////////////////////////////////////////////////////////
                    gameCartridge = new PongCartridge(this, cartridgeID);
                    ///////////////////////////////////////////////////////////////////////
                    break;
                case FROGGER:
                    ///////////////////////////////////////////////////////////////////////
                    gameCartridge = new FroggerCartridge(this, cartridgeID);
                    ///////////////////////////////////////////////////////////////////////
                    break;
                default:
                    Log.d(MainActivity.DEBUG_TAG, "@@@@@JackInActivity.onCreate(Bundle) switch (cartridgeID) construct's default block.@@@@@");
                    ///////////////////////////////////////////////////////////////////////
                    gameCartridge = new GameCartridge(this, cartridgeID);
                    ///////////////////////////////////////////////////////////////////////
                    break;
            }
        }




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



        //@@@@@@@CONTEXT_MENU@@@@@@@
        if (cartridgeID == GameCartridge.Id.POCKET_CRITTERS) {
            registerForContextMenu(gameView);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        /*
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreateContextMenu(ContextMenu, View, ContextMenu.ContextMenuInfo)");
        MenuInflater inflater = getMenuInflater();
        //@@@@@@@CONTEXT_MENU@@@@@@@
        inflater.inflate(R.menu.context_menu_pocket_critters, menu);
        */

        if (cartridgeID == GameCartridge.Id.POCKET_CRITTERS) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.start_menu);

            LinearLayout startMenuLinearLayout = (LinearLayout) dialog.findViewById(R.id.start_menu_linearlayout);

            TextView critterDex = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_critter_dex);
            critterDex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Critterdex", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            TextView beltList = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_belt_list);
            beltList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Belt List", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            TextView backpackList = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_backpack_list);
            backpackList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Backpack List", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    ////////////////////////////////////////////////////////////////////////////////////
                    Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreateContextMenu.OnClickListener.onClick(View) saved present state");
                    if (gameCartridge.getIdGameCartridge() == IGameCartridge.Id.POCKET_CRITTERS) {
                        gameCartridge.savePresentState();
                    }
                    Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreateContextMenu.OnClickListener.onClick(View) starting BackpackActivity for result...)");
                    Intent backpackIntent = new Intent(JackInActivity.this, BackpackActivity.class);
                    if (gameCartridge.getIdGameCartridge() == IGameCartridge.Id.POCKET_CRITTERS) {
                        ArrayList<Item> inventory = gameCartridge.getPlayer().getInventory();
                        backpackIntent.putExtra(INVENTORY, inventory);
                        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreateContextMenu.OnClickListener.onClick(View) passing ArrayList<Item> into BackpackActivity");
                    }
                    startActivityForResult(backpackIntent, REQUEST_CODE_BACKPACK_ACTIVITY);
                    ////////////////////////////////////////////////////////////////////////////////////

                    //TODO: remove following code used to practice BUILT-IN SCENE TRANSITION
//                // Create the scene root for the scenes in this app
//                ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.relativeLayout);
//                Scene aScene = Scene.getSceneForLayout(sceneRoot, R.layout.activity_jack_in, JackInActivity.this);
//                Scene anotherScene = Scene.getSceneForLayout(sceneRoot, R.layout.activity_recycler_view, JackInActivity.this);
//                Transition fadeTransition = new Fade();
//                TransitionManager.go(anotherScene, fadeTransition);
//                dialog.dismiss();
                }
            });

            TextView load = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_load);
            load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Load", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    /////////////////////////////////////////////////////
                    SerializationDoer.loadViaPlayerChoice(gameCartridge);
                    /////////////////////////////////////////////////////
                }
            });

            TextView save = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Save", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    /////////////////////////////////////////////////////
                    SerializationDoer.saveViaPlayerChoice(gameCartridge);
                    /////////////////////////////////////////////////////
                }
            });

            TextView option = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_option);
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Option", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            TextView exit = (TextView) startMenuLinearLayout.findViewById(R.id.dialog_exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(JackInActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                    /////////////////
                    dialog.dismiss();
                    /////////////////
                }
            });

            dialog.show();
        }
    }

    private boolean isReturningFromActivity = false;
    public void setReturningFromActivity(boolean returningFromActivity) {
        isReturningFromActivity = returningFromActivity;
    }
    public boolean isReturningFromActivity() {
        return isReturningFromActivity;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onActivityResult(int, int, Intent)");

        //TODO: this LOADING is happening before everything is instantiated (so everything loaded
        // is getting overwritten back to the initial state).
        //&& resultCode == RESULT_OK
        if (requestCode == REQUEST_CODE_BACKPACK_ACTIVITY) {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onActivityResult(int, int, Intent) RETURNING FROM BackpackActivity (LOADING SAVED STATE)");
//            if (gameCartridge.getIdGameCartridge() == GameCartridge.Id.POCKET_CRITTERS) {
//                ((PocketCrittersCartridge)gameCartridge).loadSavedState();
//            }

            //Prevents crashing if BACK was pressed instead of clicking an item.
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra(BackpackActivity.SELECTED_ITEM, -1);
                Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onActivityResult(int, int, Intent) BackpackActivity... position (-1 is defaultValue): " + position);
            } else {
                Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onActivityResult(int, int, Intent) BackpackActivity... resultCode is NOT \"RESULT_OK\"... BACK was pressed instead of clicking an item!!!");
            }

            //TODO: set a boolean isReturningFromActivity = true and use this at end of PocketCritterCartridge.init().
            isReturningFromActivity = true;
        } else if (requestCode == SceneHome01.REQUEST_CODE_TELEVISION_ACTIVITY) {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onActivityResult(int, int, Intent) RETURNING FROM TelevisionActivity (LOADING SAVED STATE)");

            isReturningFromActivity = true;
        } else if (requestCode == SceneHome02.REQUEST_CODE_COMPUTER_ACTIVITY) {
            Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onActivityResult(int, int, Intent) RETURNING FROM ComputerActivity (LOADING SAVED STATE)");

            isReturningFromActivity = true;
        }
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
                Log.d(MainActivity.DEBUG_TAG, "@@@@@JackInActivity.onContextItemSelected() switch (item) construct's default block.@@@@@");
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
                gameCartridge = new GameCartridge(this, IGameCartridge.Id.POCKET_CRITTERS);
                break;
            case POOH_FARMER:
                gameCartridge = new GameCartridge(this, IGameCartridge.Id.POOH_FARMER);
                break;
            case PONG:
                gameCartridge = new PongCartridge(this, IGameCartridge.Id.PONG);
                break;
            case FROGGER:
                gameCartridge = new FroggerCartridge(this, IGameCartridge.Id.FROGGER);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "@@@@@JackInActivity.swapGame() switch (cartridgeID) construct's default block.@@@@@");
                gameCartridge = new GameCartridge(this, cartridgeID);
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