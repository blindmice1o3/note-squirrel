package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

public abstract class Entity {

    protected float xCurrent;
    protected float yCurrent;
    protected int width;
    protected int height;

    public Entity(float xCurrent, float yCurrent) {
        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
        width = TileMap.TILE_SIZE;
        height = TileMap.TILE_SIZE;
    }

    public abstract void init();
    public abstract void update(long elapsed);
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