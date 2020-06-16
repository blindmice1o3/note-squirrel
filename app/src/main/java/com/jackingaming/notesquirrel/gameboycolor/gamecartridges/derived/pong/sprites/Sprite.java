package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private float xCurrent;
    private float yCurrent;
    private int width;
    private int height;

    private int widthViewport;
    private int heightViewport;

    private Bitmap image;

    private Rect rectOfImage;

    public Sprite(int widthViewport, int heightViewport) {
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
    }

    public void init(Bitmap image) {
        this.image = image;

        rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(image, xCurrent, yCurrent, null);
        canvas.drawBitmap(image, getRectOfImage(), getRectOnScreen(), null);
    }

    public Rect getRectOfImage() {
        return rectOfImage;
    }

    public Rect getRectOnScreen() {
        return new Rect((int) xCurrent, (int) yCurrent, (int)(xCurrent + width), (int)(yCurrent + height));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidthViewport() {
        return widthViewport;
    }

    public int getHeightViewport() {
        return heightViewport;
    }

    public float getxCurrent() {
        return xCurrent;
    }

    public void setxCurrent(float xCurrent) {
        this.xCurrent = xCurrent;
    }

    public float getyCurrent() {
        return yCurrent;
    }

    public void setyCurrent(float yCurrent) {
        this.yCurrent = yCurrent;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}