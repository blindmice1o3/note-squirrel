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
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites.Assets;

public class PoohFarmerCartridge
        implements GameCartridge {

    private Context context;
    private SurfaceHolder holder;
    private Resources resources;

    private int widthScreen;
    private int heightScreen;

    private boolean cantPress = false;
    private boolean justPressed = false;
    private boolean pressing = false;

    public PoohFarmerCartridge(Context context, SurfaceHolder holder, Resources resources, int widthScreen, int heightScreen) {
        this.context = context;
        this.holder = holder;
        this.resources = resources;

        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
    }

    @Override
    public void init() {
        Assets.init(resources);
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

    @Override
    public void update(long elapsed) {

    }

    private float x = 100;
    private float y = 50;
    private int spriteWidth = 500;
    private int spriteHeight = 500;
    private int yIndex = 0;
    private int xIndex = 0;
    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //BACKGROUND (clear the canvas by painting the background white).
            canvas.drawColor(Color.WHITE);

            Bitmap currentFrame = Assets.corgiCrusade[yIndex][xIndex];
            Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
            Rect screenRect = new Rect((int)x, (int)y, (int)(x + spriteWidth), (int)(y + spriteHeight));
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@ DRAWING-RELATED-CODE @@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            canvas.drawBitmap(currentFrame, bounds, screenRect, null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}