package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;
import com.jackingaming.notesquirrel.sandbox.learnfragment.FragmentParentDvdActivity;

public class PocketCrittersCartridge
        implements GameCartridge {

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;

    private int sideSquareScreen;
    private int sideSquareGameCameraInPixel;
    public float pixelToScreenRatio;

    private Id idGameCartridge;
    private Player player;
    private GameCamera gameCamera;
    private Scene sceneCurrent;

    public PocketCrittersCartridge(Context context, Id idGameCartridge) {
        this.context = context;
        this.idGameCartridge = idGameCartridge;

    }

    @Override
    public void init(SurfaceHolder holder, int sideSquareScreen, InputManager inputManager) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.init(SurfaceHolder, int, InputManager)");

        this.holder = holder;
        this.sideSquareScreen = sideSquareScreen;
        this.inputManager = inputManager;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareScreen: " + sideSquareScreen);


        sideSquareGameCameraInPixel = GameCamera.CLIP_NUMBER_OF_TILES * TileMap.TILE_SIZE;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareGameCameraInPixel: " + sideSquareGameCameraInPixel);
        pixelToScreenRatio = ((float)sideSquareScreen) / sideSquareGameCameraInPixel;
        Log.d(MainActivity.DEBUG_TAG, "pixelToScreenRatio: " + pixelToScreenRatio);


        Assets.init(context);


        gameCamera = new GameCamera();
        player = new Player(gameCamera, sideSquareScreen, pixelToScreenRatio);
        sceneCurrent = new Scene(context, sideSquareScreen, idGameCartridge);
        sceneCurrent.init(player, gameCamera);
    }

    @Override
    public void savePresentState() {

    }

    @Override
    public void loadSavedState() {

    }

    @Override
    public void getInputViewport() {
        if (inputManager.isJustPressedViewport()) {
            //left
            if (inputManager.isLeftViewport()) {
                player.move(Player.Direction.LEFT);
            }
            //right
            else if (inputManager.isRightViewport()) {
                player.move(Player.Direction.RIGHT);
            }
            //up
            else if (inputManager.isUpViewport()) {
                player.move(Player.Direction.UP);
            }
            //down
            else if (inputManager.isDownViewport()) {
                player.move(Player.Direction.DOWN);
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            //up
            if (inputManager.isUpDirectionalPad()) {
                player.move(Player.Direction.UP);
            }
            //down
            else if (inputManager.isDownDirectionalPad()) {
                player.move(Player.Direction.DOWN);
            }
            //left
            else if (inputManager.isLeftDirectionalPad()) {
                player.move(Player.Direction.LEFT);
            }
            //right
            else if (inputManager.isRightDirectionalPad()) {
                player.move(Player.Direction.RIGHT);
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isPressingButtonPad()) {
            //menu button (will launch FragmentParentDvdActivity)
            if (inputManager.isMenuButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "menu-button");
                Intent fragmentParentDvdIntent = new Intent(context, FragmentParentDvdActivity.class);
                context.startActivity(fragmentParentDvdIntent);
            }
            //a button
            else if (inputManager.isaButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "a-button");
            }
            //b button
            else if (inputManager.isbButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "b-button");
            }
        }
    }

    @Override
    public void update(long elapsed) {
        ////////////////////////////////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        ////////////////////////////////////////////////////

        sceneCurrent.update(elapsed);
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
            sceneCurrent.render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}