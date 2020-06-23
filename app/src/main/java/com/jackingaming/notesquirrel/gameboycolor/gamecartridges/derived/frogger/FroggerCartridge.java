package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.SerializationDoer;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import static android.content.Context.MODE_PRIVATE;

public class FroggerCartridge
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

    public FroggerCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        //Assets.init(context);

        gameCamera = new GameCamera(widthViewport, heightViewport);
        int clipWidthInTile = 20;
        int clipHeightInTile = 15;
        int tileWidthFrogger = (int)((48f) * (15f/20f));
        int tileHeightFrogger = 48;
        gameCamera.setWidthClipInPixel(clipWidthInTile * tileWidthFrogger);
        gameCamera.setHeightClipInPixel(clipHeightInTile * tileHeightFrogger);

        player = new Player(this);
        ///////////////////////////////////////////////////
        stateManager = new StateManager(this);
        ///////////////////////////////////////////////////
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.savePresentState()");

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

        SerializationDoer.saveViaOS(this);
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.loadSavedState()");

        // !!!THIS CHECKING FOR NULL IS NECESSARY!!!
        //if (handler != null) {
            SerializationDoer.loadViaOS(this);
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
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getContext()");
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getIdGameCartridge()");
        return idGameCartridge;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getSurfaceHolder()");
        return surfaceHolder;
    }

    @Override
    public InputManager getInputManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getInputManager()");
        return inputManager;
    }

    @Override
    public int getWidthViewport() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getWidthViewport()");
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getHeightViewport()");
        return heightViewport;
    }

    @Override
    public Player getPlayer() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getPlayer()");
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.setPlayer(Player)");
        this.player = player;
    }

    @Override
    public GameCamera getGameCamera() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getGameCamera()");
        return gameCamera;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.setGameCamera(GameCamera)");
        this.gameCamera = gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getSceneManager()");
        return ((GameState)stateManager.getState(State.Id.GAME)).getSceneManager();
    }

    @Override
    public StateManager getStateManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getStateManager()");
        return stateManager;
    }

}