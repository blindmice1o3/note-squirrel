package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes.Scene;

public abstract class Entity {

    protected float xCurrent;
    protected float yCurrent;
    protected int width;
    protected int height;

    public Entity(float xCurrent, float yCurrent) {
        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
        width = Scene.TILE_SIZE;
        height = Scene.TILE_SIZE;
    }

    public abstract void render(Canvas canvas);

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}