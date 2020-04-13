package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites.Assets;

public class Player extends Entity {

    private float pixelToScreenRatio;

    private Bitmap[][] texture;
    private float moveSpeed = 4f;

    public Player(float pixelToScreenRatio) {
        super(0f, 0f);

        this.pixelToScreenRatio = pixelToScreenRatio;
    }

    @Override
    public void init() {
        initTexture();
    }

    private void initTexture() {
        texture = Assets.corgiCrusade;
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

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = texture[0][0];

        Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
        Rect screenRect = new Rect(
                (int)(xCurrent * pixelToScreenRatio),
                (int)(yCurrent * pixelToScreenRatio),
                (int)((xCurrent + width) * pixelToScreenRatio),
                (int)((yCurrent + height) * pixelToScreenRatio)
        );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

}