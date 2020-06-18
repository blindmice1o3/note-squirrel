package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapHothouse;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

import static android.content.Context.MODE_PRIVATE;

public class PoohFarmerCartridge
        implements GameCartridge {

    private Context context;
    private Id idGameCartridge;

    private SurfaceHolder surfaceHolder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Handler handler;
    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;
    private StateManager stateManager;

    public PoohFarmerCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        Assets.init(context);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        handler = new Handler(this);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        gameCamera = new GameCamera(widthViewport, heightViewport);
        player = new Player(handler);
        sceneManager = new SceneManager(handler);
        /////////////////////////////////////////
        stateManager = new StateManager(handler);
        /////////////////////////////////////////

        //TODO: if isReturningFromActivity... then load
        if ( ((JackInActivity)context).isReturningFromActivity() ) {
            loadSavedState();

            ((JackInActivity)context).setReturningFromActivity(false);
        }
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.savePresentState()");

        if (idGameCartridge == Id.POOH_FARMER) {
            /////////////////////////////////////////////////////////////////////////////////
            //only THIS activity can get access to THIS preference file.
            SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
            //Editor is an inner-class of the SharedPreferences class.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("xCurrentPlayer", player.getxCurrent());
            editor.putFloat("yCurrentPlayer", player.getyCurrent());
            editor.putInt("directionOrdinalPlayer", player.getDirection().ordinal());
            editor.putFloat("xGameCamera", gameCamera.getX());
            editor.putFloat("yGameCamera", gameCamera.getY());

            //HAVE TO tell editor to actually save the values we'd put into it.
            editor.commit();
            /////////////////////////////////////////////////////////////////////////////////
        }
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.loadSavedState()");

        if (idGameCartridge == Id.POOH_FARMER) {
            /////////////////////////////////////////////////////////////////////////////////////
            //retrieving PERSISTENT data (values stored between "runs").
            SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
            //checking if the key-value pair exists,
            //if does NOT exist (haven't done a put() and commit())...
            //it uses the default value (the second argument).
            float xCurrentPlayer = prefs.getFloat("xCurrentPlayer", 3f * 16f);
            float yCurrentPlayer = prefs.getFloat("yCurrentPlayer", 10f * 16f);
            int directionOrdinalPlayer = prefs.getInt("directionOrdinalPlayer", 1);
            float xGameCamera = prefs.getFloat("xGameCamera", 2f * 16f);
            float yGameCamera = prefs.getFloat("yGameCamera", 8f * 16f);
            /////////////////////////////////////////////////////////////////////////////////////

            ////////////////////maybe possible to remove if-checks////////////////////
            if (player != null) {
                player.setxCurrent(xCurrentPlayer);
                player.setyCurrent(yCurrentPlayer);
                player.setDirection(Player.Direction.values()[directionOrdinalPlayer]);
            }
            if (gameCamera != null) {
                gameCamera.setX(xGameCamera);
                gameCamera.setY(yGameCamera);
            }
            //////////////////////////////////////////////////////////////////////////
        }
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
        ////////////////////////////////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        ////////////////////////////////////////////////////

        sceneManager.getCurrentScene().update(elapsed);
    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = surfaceHolder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            sceneManager.getCurrentScene().render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            surfaceHolder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
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
    public Handler getHandler() {
        return handler;
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
        return sceneManager;
    }

    @Override
    public StateManager getStateManager() {
        return stateManager;
    }

}