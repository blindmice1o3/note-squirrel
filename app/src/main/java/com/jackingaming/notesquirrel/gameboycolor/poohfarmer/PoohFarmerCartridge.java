package com.jackingaming.notesquirrel.gameboycolor.poohfarmer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
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



    private int widthScreen;
    private int heightScreen;
    private int sideSquareScreen;
    //CURRENTLY, USED TO MOVE PLAYER!!!
    private int xCenterScreen;
    private int yCenterScreen;

    private Player player;
    private GameCamera gameCamera;
    private Scene sceneCurrent;



    private int sizeTileInPixel;
    private int numberOfTilesGameCamera;
    private int sideSquareGameCameraInPixel;

    public float pixelToScreenRatio;

    //SPRITE
    private int spriteWidth;
    private int spriteHeight;
    private float x;
    private float y;
    private int yIndex = 0;
    private int xIndex = 0;

    private boolean cantPress = false;
    private boolean justPressed = false;
    private boolean pressing = false;

    public PoohFarmerCartridge(Context context, SurfaceHolder holder, Resources resources, int widthScreen, int heightScreen) {
        this.context = context;
        this.holder = holder;
        this.resources = resources;

        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        Log.d(MainActivity.DEBUG_TAG, "@@@@@ POOH FARMER CARTRIDGE @@@@@");
        Log.d(MainActivity.DEBUG_TAG, "widthScreen: " + widthScreen);
        Log.d(MainActivity.DEBUG_TAG, "heightScreen: " + heightScreen);



        sideSquareScreen = Math.min(widthScreen, heightScreen);
        Log.d(MainActivity.DEBUG_TAG, "sideSquareScreen: " + sideSquareScreen);
        xCenterScreen = sideSquareScreen / 2;
        yCenterScreen = sideSquareScreen / 2;

        //had a null pointer exception... MOVE TO init().
        //TODO: SEE init() (AFTER Assets.init())



        sizeTileInPixel = 16;
        numberOfTilesGameCamera = 9;
        sideSquareGameCameraInPixel = numberOfTilesGameCamera * sizeTileInPixel;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareGameCameraInPixel: " + sideSquareGameCameraInPixel);

        pixelToScreenRatio = ((float)sideSquareScreen) / sideSquareGameCameraInPixel;
        Log.d(MainActivity.DEBUG_TAG, "pixelToScreenRatio: " + pixelToScreenRatio);
        player = new Player(pixelToScreenRatio);
        gameCamera = new GameCamera();
        sceneCurrent = new Scene(sideSquareScreen);

        spriteWidth = (int) ((1 * sizeTileInPixel) * pixelToScreenRatio);
        Log.d(MainActivity.DEBUG_TAG, "spriteWidth = (int) ((1*sizeTileInPixel) * pixelToScreenRatio): " + spriteWidth);
        spriteHeight = (int) ((1 * sizeTileInPixel) * pixelToScreenRatio);
        Log.d(MainActivity.DEBUG_TAG, "spriteHeight = (int) ((1*sizeTileInPixel) * pixelToScreenRatio): " + spriteHeight);
        x = (2 * sizeTileInPixel) * pixelToScreenRatio;
        y = (6 * sizeTileInPixel) * pixelToScreenRatio;
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
            if (event.getX() < xCenterScreen) {
                player.moveLeft();
            }
            //right
            else if (event.getX() > xCenterScreen) {
                player.moveRight();
            }

            //VERTICAL
            //up
            if (event.getY() < yCenterScreen) {
                player.moveUp();
            }
            //down
            else if (event.getY() > yCenterScreen) {
                player.moveDown();
            }
        }




        /*
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



            //ENTITIES
            Bitmap currentFrame = Assets.corgiCrusade[yIndex][xIndex];
            Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
            Rect screenRect = new Rect((int)x, (int)y, (int)(x + spriteWidth), (int)(y + spriteHeight));
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@ DRAWING-RELATED-CODE @@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            canvas.drawBitmap(currentFrame, bounds, screenRect, null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


            //////////////////////////////
            ///////////TESTING////////////
            //////////////////////////////
            //SECOND CORGI (@@@TESTING@@@)
            int xSecondCorgi = (int)((3 * sizeTileInPixel) * pixelToScreenRatio);
            int ySecondCorgi = (int)((5 * sizeTileInPixel) * pixelToScreenRatio);
            Rect screenSecondCorgi = new Rect(xSecondCorgi, ySecondCorgi, (xSecondCorgi + spriteWidth), (ySecondCorgi + spriteHeight));
            canvas.drawBitmap(currentFrame, bounds, screenSecondCorgi, null);
            //////////////////////////////
            //////////////////////////////
            //////////////////////////////



            sceneCurrent.render(canvas);



            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}