package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class FroggerCartridge
        implements GameCartridge {

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Id idGameCartridge;
    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;

    public FroggerCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.holder = holder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        //Assets.init(context);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Handler handler = new Handler(this);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        gameCamera = new GameCamera(handler);
        player = new Player(handler);
        sceneManager = new SceneManager(handler);
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.savePresentState()");
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.loadSavedState()");
    }

    @Override
    public void getInputViewport() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getInputViewport()");
    }

    @Override
    public void getInputDirectionalPad() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getInputDirectionalPad()");
    }

    @Override
    public void getInputButtonPad() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getInputButtonPad()");
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
    public Player getPlayer() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getPlayer()");
        return player;
    }

    @Override
    public GameCamera getGameCamera() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getGameCamera()");
        return gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getSceneManager()");
        return sceneManager;
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
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            sceneManager.getCurrentScene().render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}