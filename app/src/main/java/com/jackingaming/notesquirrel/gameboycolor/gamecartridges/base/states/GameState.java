package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.HeadUpDisplay;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import java.io.Serializable;

public class GameState
        implements State, Serializable {

    transient private GameCartridge gameCartridge;
    private Id id;

    transient private Context context;
    transient private InputManager inputManager;
    transient private SurfaceHolder surfaceHolder;   //used to get Canvas
    private int widthViewport;
    private int heightViewport;

    private Player player;
    transient private SceneManager sceneManager;
    private HeadUpDisplay headUpDisplay;

    public GameState(GameCartridge gameCartridge) {
        /////////////
        id = Id.GAME;
        /////////////

        widthViewport = gameCartridge.getWidthViewport();
        heightViewport = gameCartridge.getHeightViewport();

        init(gameCartridge);
    }

    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;

        context = gameCartridge.getContext();
        inputManager = gameCartridge.getInputManager();
        surfaceHolder = gameCartridge.getSurfaceHolder();

        player = gameCartridge.getPlayer();
        sceneManager = new SceneManager(gameCartridge);
        headUpDisplay = new HeadUpDisplay(gameCartridge);
    }

    @Override
    public void getInputViewport() {
        //////////////////////////////////////////////////
        sceneManager.getCurrentScene().getInputViewport();
        //////////////////////////////////////////////////
    }

    @Override
    public void getInputDirectionalPad() {
        ////////////////////////////////////////////////////////
        sceneManager.getCurrentScene().getInputDirectionalPad();
        ////////////////////////////////////////////////////////
    }

    @Override
    public void getInputButtonPad() {
        ///////////////////////////////////////////////////
        sceneManager.getCurrentScene().getInputButtonPad();
        ///////////////////////////////////////////////////
    }

    @Override
    public void update(long elapsed) {
        sceneManager.getCurrentScene().update(elapsed);
        headUpDisplay.update(elapsed);
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
            headUpDisplay.render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            surfaceHolder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    @Override
    public void enter(Object[] args) {
        Log.d(MainActivity.DEBUG_TAG, "GameState.enter(Object[]) State.Id: " + id);
    }

    @Override
    public void exit() {

    }

    @Override
    public Id getId() {
        return id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

}