package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private float x;
    private float y;
    private int widthSprite;
    private int heightSprite;

    private int widthViewport;
    private int heightViewport;

    private Bitmap image;

    private Rect bounds;

    public Sprite(int widthViewport, int heightViewport) {
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
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
        return new Rect((int)x, (int)y, (int)(x + widthSprite), (int)(y + heightSprite));
    }

    public int getWidthSprite() {
        return widthSprite;
    }

    public int getHeightSprite() {
        return heightSprite;
    }

    public int getWidthViewport() {
        return widthViewport;
    }

    public int getHeightViewport() {
        return heightViewport;
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

    public void setWidthSprite(int widthSprite) {
        this.widthSprite = widthSprite;
    }

    public void setHeightSprite(int heightSprite) {
        this.heightSprite = heightSprite;
    }

}