package com.jackingaming.notesquirrel.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {

    private float x;
    private float y;

    private int screenWidth;
    private int screenHeight;

    private Bitmap image;

    public Sprite(int screenWidth, int screenHeight) {
        this.x = 30;
        this.y = 30;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void init(Bitmap image) {
        this.image = image;
    }

    public void update(long elapsed) {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

}