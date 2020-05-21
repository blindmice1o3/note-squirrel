package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.SerializationDoer;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class PocketCrittersCartridge
        implements GameCartridge {

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Handler handler;
    private Id idGameCartridge;
    private StateManager stateManager;
    //private State state;

    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;

    public PocketCrittersCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
        //state = State.BASE_GAME;
    }

    @Override
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.holder = holder;
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

        gameCamera = new GameCamera(handler);
        player = new Player(handler);
        sceneManager = new SceneManager(handler);
        /////////////////////////////////////////
        stateManager = new StateManager(handler);
        /////////////////////////////////////////
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.savePresentState()");

        /*
        gameCamera.savePresentState();
        player.savePresentState();
        sceneManager.savePresentState();
        stateManager.savePresentState();
        */

        //TODO:
        SerializationDoer.saveWriteToFile(handler);
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.loadSavedState()");

        /*
        gameCamera.loadSavedState();
        player.loadSavedState();
        sceneManager.loadSavedState();
        stateManager.loadSavedState();
        */

        //TODO:
        SerializationDoer.loadReadFromFile(handler);
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

    public SurfaceHolder getHolder() {
        return holder;
    }

    @Override
    public Id getIdGameCartridge() {
        return idGameCartridge;
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

    public InputManager getInputManager() { return inputManager; }

    public StateManager getStateManager() { return stateManager; }

    @Override
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }

}