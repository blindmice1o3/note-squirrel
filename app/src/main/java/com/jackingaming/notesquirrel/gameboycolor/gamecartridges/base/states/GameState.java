package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
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
    }

    @Override
    public void getInputViewport() {
        //left
        if (inputManager.isLeftViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputViewport() left-button-justPressed");
            player.move(Player.Direction.LEFT);
        }
        //right
        else if (inputManager.isRightViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputViewport() right-button-justPressed");
            player.move(Player.Direction.RIGHT);
        }
        //up
        else if (inputManager.isUpViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputViewport() up-button-justPressed");
            player.move(Player.Direction.UP);
        }
        //down
        else if (inputManager.isDownViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputViewport() down-button-justPressed");
            player.move(Player.Direction.DOWN);
        }
    }

    @Override
    public void getInputDirectionalPad() {
        //up
        if (inputManager.isUpDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputDirectionalPad() up-button-pressing");
            player.move(Player.Direction.UP);
        }
        //down
        else if (inputManager.isDownDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputDirectionalPad() down-button-pressing");
            player.move(Player.Direction.DOWN);
        }
        //left
        else if (inputManager.isLeftDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputDirectionalPad() left-button-pressing");
            player.move(Player.Direction.LEFT);
        }
        //right
        else if (inputManager.isRightDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputDirectionalPad() right-button-pressing");
            player.move(Player.Direction.RIGHT);
        }
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