package com.jackingaming.notesquirrel.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private float x;
    private float y;
    private int spriteWidth;
    private int spriteHeight;

    private int screenWidth;
    private int screenHeight;

    private Bitmap image;
    private int imageWidth;
    private int imageHeight;

    public Sprite(int screenWidth, int screenHeight) {
        x = 30;
        y = 30;
        spriteWidth = 600;
        spriteHeight = 680;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void init(Bitmap image) {
        this.image = image;

        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    public void update(long elapsed) {
        
    }

    public void draw(Canvas canvas) {
        Rect imageRectSrc = new Rect(0, 0, imageWidth, imageHeight);
        //Rect imageRectDst = new Rect((int)(0+x), (int)(0+y), (int)(imageWidth+x), (int)(imageHeight+y));
        Rect imageRectDst = new Rect((int)(x), (int)(y), (int)(x+spriteWidth), (int)(y+spriteHeight));

        //canvas.drawBitmap(image, x, y, null);
        canvas.drawBitmap(image, imageRectSrc, imageRectDst, null);
    }

}