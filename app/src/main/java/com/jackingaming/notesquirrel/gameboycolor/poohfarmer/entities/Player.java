package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites.Assets;

public class Player extends Entity {

    private float moveSpeed = 3f;
    private float conversionFactorPixelToScreen;

    public Player(float xCurrent, float yCurrent, float conversionFactorPixelToScreen) {
        super(xCurrent, yCurrent);
        this.conversionFactorPixelToScreen = conversionFactorPixelToScreen;
    }

    public void moveLeft() {
        xCurrent -= moveSpeed;
    }

    public void moveRight() {
        xCurrent += moveSpeed;
    }

    public void moveUp() {
        yCurrent -= moveSpeed;
    }

    public void moveDown() {
        yCurrent += moveSpeed;
    }

    public void render(Canvas canvas) {
        //ENTITIES
        Bitmap currentFrame = Assets.corgiCrusade[0][0];
        Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
        Rect screenRect = new Rect(
                (int)(xCurrent * conversionFactorPixelToScreen),
                (int)(yCurrent * conversionFactorPixelToScreen),
                (int)((xCurrent + width) * conversionFactorPixelToScreen),
                (int)((yCurrent + height) * conversionFactorPixelToScreen)
        );
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@ DRAWING-RELATED-CODE @@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}