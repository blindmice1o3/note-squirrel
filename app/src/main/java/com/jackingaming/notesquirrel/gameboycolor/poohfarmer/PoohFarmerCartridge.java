package com.jackingaming.notesquirrel.gameboycolor.poohfarmer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites.Assets;

public class PoohFarmerCartridge
        implements GameCartridge {

    private Context context;
    private SurfaceHolder holder;   //used to get Canvas
    private Resources resources;


    private int sideSquareScreen;
    private int sideSquareGameCameraInPixel;
    public float pixelToScreenRatio;


    private Player player;
    private GameCamera gameCamera;
    private Scene sceneCurrent;


    //CURRENTLY, USED TO MOVE PLAYER!!!
    private int xCenterScreen;
    private int yCenterScreen;
    private boolean cantPress = false;
    private boolean justPressed = false;
    private boolean pressing = false;

    public PoohFarmerCartridge(Context context, SurfaceHolder holder, Resources resources, int sideSquareScreen) {
        this.context = context;
        this.holder = holder;
        this.resources = resources;
        this.sideSquareScreen = sideSquareScreen;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareScreen: " + sideSquareScreen);


        sideSquareGameCameraInPixel = GameCamera.CLIP_NUMBER_OF_TILES * Scene.TILE_SIZE;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareGameCameraInPixel: " + sideSquareGameCameraInPixel);
        pixelToScreenRatio = ((float)sideSquareScreen) / sideSquareGameCameraInPixel;
        Log.d(MainActivity.DEBUG_TAG, "pixelToScreenRatio: " + pixelToScreenRatio);


        xCenterScreen = sideSquareScreen / 2;
        yCenterScreen = sideSquareScreen / 2;


        player = new Player(sideSquareScreen, pixelToScreenRatio);
        gameCamera = new GameCamera();
        sceneCurrent = new Scene(sideSquareScreen);
    }

    @Override
    public void init() {
        Assets.init(resources);

        sceneCurrent.init(player, gameCamera);
    }

    @Override
    public void getInput(MotionEvent event) {
        //////////////////////////////////////////////////////////
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressing = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressing = false;
        }
        //////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////////
        if (cantPress && !pressing) {
            cantPress = false;
        } else if (justPressed) {
            cantPress = true;
            justPressed = false;
        }
        if (!cantPress && pressing) {
            justPressed = true;
        }
        //////////////////////////////////////////////////////////






        float xPlayerBefore = player.getxCurrent();
        float yPlayerBefore = player.getyCurrent();
        float xGameCameraBefore = gameCamera.getX();
        float yGameCameraBefore = gameCamera.getY();
        if (justPressed) {
            //HORIZONTAL
            //left
            if (event.getX() < xCenterScreen && event.getY() < yCenterScreen) {
                int xFuture = (int) (player.getxCurrent() - player.getMoveSpeed());
                int yFutureTop = (int) (player.getyCurrent());
                int yFutureBottom = (int) (player.getyCurrent() + player.getHeight() - 1);

                if (!sceneCurrent.isSolid(xFuture, yFutureTop) && !sceneCurrent.isSolid(xFuture, yFutureBottom)) {
                    player.moveLeft();
                    gameCamera.moveLeft();
                }
            }
            //right
            else if (event.getX() > xCenterScreen && event.getY() > yCenterScreen) {
                int xFuture = (int) ((player.getxCurrent() + player.getWidth()) + player.getMoveSpeed() - 1);
                int yFutureTop = (int) (player.getyCurrent());
                int yFutureBottom = (int) (player.getyCurrent() + player.getHeight() - 1);

                if (!sceneCurrent.isSolid(xFuture, yFutureTop) && !sceneCurrent.isSolid(xFuture, yFutureBottom)) {
                    player.moveRight();
                    gameCamera.moveRight();
                }
            }

            //VERTICAL
            //up
            if (event.getY() < yCenterScreen && event.getX() > xCenterScreen) {
                int yFuture = (int) (player.getyCurrent() - player.getMoveSpeed());
                int xFutureLeft = (int) (player.getxCurrent());
                int xFutureRight = (int) (player.getxCurrent() + player.getWidth() - 1);

                if (!sceneCurrent.isSolid(xFutureLeft, yFuture) && !sceneCurrent.isSolid(xFutureRight, yFuture)) {
                    player.moveUp();
                    gameCamera.moveUp();
                }
            }
            //down
            else if (event.getY() > yCenterScreen && event.getX() < xCenterScreen) {
                int yFuture = (int) ((player.getyCurrent() + player.getHeight()) + player.getMoveSpeed() - 1);
                int xFutureLeft = (int) (player.getxCurrent());
                int xFutureRight = (int) (player.getxCurrent() + player.getWidth() - 1);

                if (!sceneCurrent.isSolid(xFutureLeft, yFuture) && !sceneCurrent.isSolid(xFutureRight, yFuture)) {
                    player.moveDown();
                    gameCamera.moveDown();
                }
            }
            float xPlayerAfter = player.getxCurrent();
            float yPlayerAfter = player.getyCurrent();
            float xGameCameraAfter = gameCamera.getX();
            float yGameCameraAfter = gameCamera.getY();

            //TODO:
            Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            Log.d(MainActivity.DEBUG_TAG, "xPlayerBefore: " + xPlayerBefore);
            Log.d(MainActivity.DEBUG_TAG, "yPlayerBefore: " + yPlayerBefore);
            Log.d(MainActivity.DEBUG_TAG, "xGameCameraBefore: " + xGameCameraBefore);
            Log.d(MainActivity.DEBUG_TAG, "yGameCameraBefore: " + yGameCameraBefore);
            Log.d(MainActivity.DEBUG_TAG, "==================================");
            Log.d(MainActivity.DEBUG_TAG, "xPlayerAfter: " + xPlayerAfter);
            Log.d(MainActivity.DEBUG_TAG, "yPlayerAfter: " + yPlayerAfter);
            Log.d(MainActivity.DEBUG_TAG, "xGameCameraAfter: " + xGameCameraAfter);
            Log.d(MainActivity.DEBUG_TAG, "yGameCameraAfter: " + yGameCameraAfter);
            Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }




        /*  CAN USE IN ANIMATION CLASS
        //CHANGE currentFrame (animation) of corgi sprites USED IN render().
        if (justPressed) {
            /////////
            xIndex++;
            /////////
            if (xIndex == Assets.corgiCrusade[0].length) {
                xIndex = 0;
                /////////
                yIndex++;
                /////////
                if (yIndex == Assets.corgiCrusade.length) {
                    yIndex = 0;
                }
            }
        }
        */
    }

    @Override
    public void update(long elapsed) {
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


            sceneCurrent.render(canvas);

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}