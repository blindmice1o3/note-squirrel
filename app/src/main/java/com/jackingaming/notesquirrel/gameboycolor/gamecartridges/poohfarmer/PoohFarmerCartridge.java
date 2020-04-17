package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.sandbox.learnfragment.FragmentParentDvdActivity;

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
    private int xScreenFirstThird;
    private int xScreenSecondThird;
    private int yScreenFirstThird;
    private int yScreenSecondThird;
    //private int xCenterScreen;
    //private int yCenterScreen;


    private boolean cantPress = false;
    private boolean justPressed = false;
    private boolean pressing = false;

    public PoohFarmerCartridge(Context context, SurfaceHolder holder, Resources resources, int sideSquareScreen) {
        this.context = context;
        this.holder = holder;
        this.resources = resources;
        this.sideSquareScreen = sideSquareScreen;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareScreen: " + sideSquareScreen);


        sideSquareGameCameraInPixel = GameCamera.CLIP_NUMBER_OF_TILES * TileMap.TILE_SIZE;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareGameCameraInPixel: " + sideSquareGameCameraInPixel);
        pixelToScreenRatio = ((float)sideSquareScreen) / sideSquareGameCameraInPixel;
        Log.d(MainActivity.DEBUG_TAG, "pixelToScreenRatio: " + pixelToScreenRatio);


        xScreenFirstThird = (int)((float)sideSquareScreen / 3);
        xScreenSecondThird = (int)(2 * ((float)sideSquareScreen / 3));
        yScreenFirstThird = (int)((float)sideSquareScreen / 3);
        yScreenSecondThird = (int)(2 * ((float)sideSquareScreen / 3));
        //xCenterScreen = sideSquareScreen / 2;
        //yCenterScreen = sideSquareScreen / 2;


        player = new Player(sideSquareScreen, pixelToScreenRatio);
        gameCamera = new GameCamera();
        sceneCurrent = new Scene(sideSquareScreen);
    }

    @Override
    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.init()");
        Assets.init(resources);

        sceneCurrent.init(player, gameCamera);
    }

    @Override
    public void onScreenInput(MotionEvent event) {
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

        if (justPressed) {
            //HORIZONTAL
            //left
            if (event.getX() < xScreenFirstThird && event.getY() > yScreenFirstThird && event.getY() < yScreenSecondThird) {
                int xFuture = (int) (player.getxCurrent() - player.getMoveSpeed());
                int yFutureTop = (int) (player.getyCurrent());
                int yFutureBottom = (int) (player.getyCurrent() + player.getHeight() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFuture, yFutureTop) && !sceneCurrent.getTileMap().isSolid(xFuture, yFutureBottom)) {
                    player.moveLeft();
                    gameCamera.moveLeft();
                }
            }
            //right
            else if (event.getX() > xScreenSecondThird && event.getY() > yScreenFirstThird && event.getY() < yScreenSecondThird) {
                int xFuture = (int) ((player.getxCurrent() + player.getWidth()) + player.getMoveSpeed() - 1);
                int yFutureTop = (int) (player.getyCurrent());
                int yFutureBottom = (int) (player.getyCurrent() + player.getHeight() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFuture, yFutureTop) && !sceneCurrent.getTileMap().isSolid(xFuture, yFutureBottom)) {
                    player.moveRight();
                    gameCamera.moveRight();
                }
            }

            //VERTICAL
            //up
            if (event.getY() < yScreenFirstThird && event.getX() > xScreenFirstThird && event.getX() < xScreenSecondThird) {
                int yFuture = (int) (player.getyCurrent() - player.getMoveSpeed());
                int xFutureLeft = (int) (player.getxCurrent());
                int xFutureRight = (int) (player.getxCurrent() + player.getWidth() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFutureLeft, yFuture) && !sceneCurrent.getTileMap().isSolid(xFutureRight, yFuture)) {
                    player.moveUp();
                    gameCamera.moveUp();
                }
            }
            //down
            else if (event.getY() > yScreenSecondThird && event.getX() > xScreenFirstThird && event.getX() < xScreenSecondThird) {
                int yFuture = (int) ((player.getyCurrent() + player.getHeight()) + player.getMoveSpeed() - 1);
                int xFutureLeft = (int) (player.getxCurrent());
                int xFutureRight = (int) (player.getxCurrent() + player.getWidth() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFutureLeft, yFuture) && !sceneCurrent.getTileMap().isSolid(xFutureRight, yFuture)) {
                    player.moveDown();
                    gameCamera.moveDown();
                }
            }

            //bottom-right quadrant on-screen touch event will launch FragmentParentDvdActivity
            if ((event.getX() > xScreenSecondThird) && (event.getY() > yScreenSecondThird)) {
                Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.onScreenInput(MotionEvent) launch FragmentParentDvdActivity");
                Intent fragmentParentDvdIntent = new Intent(context, FragmentParentDvdActivity.class);
                context.startActivity(fragmentParentDvdIntent);
            }
        }
    }


    @Override
    public void onDirectionalPadInput(DirectionalPadFragment.Direction direction) {
        //TODO: refactor to player.move(direction) and gameCamera.move(direction).
        switch (direction) {
            case UP:
                player.moveUp();
                gameCamera.moveUp();
                break;
            case LEFT:
                player.moveLeft();
                gameCamera.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                gameCamera.moveRight();
                break;
            case DOWN:
                player.moveDown();
                gameCamera.moveDown();
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.onDirectionalPadInput(Direction) switch's default block.");
                break;
        }
    }

    @Override
    public void onButtonPadInput(ButtonPadFragment.InputButton inputButton) {
        switch(inputButton) {
            //menu button will launch FragmentParentDvdActivity
            case MENU_BUTTON:
                Log.d(MainActivity.DEBUG_TAG, "menu-button");
                Intent fragmentParentDvdIntent = new Intent(context, FragmentParentDvdActivity.class);
                context.startActivity(fragmentParentDvdIntent);
                break;
            case A_BUTTON:
                Log.d(MainActivity.DEBUG_TAG, "a-button");
                break;
            case B_BUTTON:
                Log.d(MainActivity.DEBUG_TAG, "b-button");
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.onButtonPadInput(InputButton) switch's default block.");
        }
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