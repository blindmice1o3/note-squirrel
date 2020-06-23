package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.SerializationDoer;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

import static android.content.Context.MODE_PRIVATE;

public class PocketCrittersCartridge
        implements GameCartridge {

    private Context context;
    private Id idGameCartridge;

    private SurfaceHolder surfaceHolder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Player player;
    private GameCamera gameCamera;
    private StateManager stateManager;

    public PocketCrittersCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        Assets.init(context);

        gameCamera = new GameCamera(widthViewport, heightViewport);
        player = new Player(this);
        ///////////////////////////////////////////////////
        stateManager = new StateManager(this);
        ///////////////////////////////////////////////////

        //TODO: if isReturningFromActivity... then load
        if ( ((JackInActivity)context).isReturningFromActivity() ) {
            loadSavedState();

            ((JackInActivity)context).setReturningFromActivity(false);
        }
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.savePresentState()");

        //HANDLES JackInActivity.gameCartridge/////////////////////////////////////////
        //only THIS activity can get access to THIS preference file.
        SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
        //Editor is an inner-class of the SharedPreferences class.
        SharedPreferences.Editor editor = prefs.edit();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //Save enum as integer (its index value).
        //!!!Used in JackInActivity's constructor (orientation change)!!!
        editor.putInt("idGameCartridge", idGameCartridge.ordinal());
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //HAVE TO tell editor to actually save the values we'd put into it.
        editor.commit();
        /////////////////////////////////////////////////////////////////////////////////

        SerializationDoer.saveWriteToFile(this, false);
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.loadSavedState()");

        // !!!THIS CHECKING FOR NULL IS NECESSARY!!!
        //if (handler != null) {
            SerializationDoer.loadReadFromFile(this, false);
        //}
    }

    @Override
    public void getInputViewport() {
        if (inputManager.isJustPressedViewport()) {
            stateManager.getCurrentState().getInputViewport();
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            stateManager.getCurrentState().getInputDirectionalPad();
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isJustPressedButtonPad()) {
            stateManager.getCurrentState().getInputButtonPad();
        }
    }

    @Override
    public void update(long elapsed) {
        //////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        //////////////////////////

        stateManager.getCurrentState().update(elapsed);
    }

    @Override
    public void render() {
        stateManager.getCurrentState().render();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        return idGameCartridge;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public GameCamera getGameCamera() {
        return gameCamera;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        return ((GameState)stateManager.getState(State.Id.GAME)).getSceneManager();
    }

    @Override
    public StateManager getStateManager() {
        return stateManager;
    }

}