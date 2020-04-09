package com.jackingaming.notesquirrel.gameboycolor.pong.sprites;

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

    private Rect bounds;

    public Sprite(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void init(Bitmap image) {
        this.image = image;

        bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(image, x, y, null);
        canvas.drawBitmap(image, getBounds(), getScreenRect(), null);
    }

    public Rect getBounds() {
        return bounds;
    }

    public Rect getScreenRect() {
        return new Rect((int)x, (int)y, (int)(x + spriteWidth), (int)(y + spriteHeight));
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

}