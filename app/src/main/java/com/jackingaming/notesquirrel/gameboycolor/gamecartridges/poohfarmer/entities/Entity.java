package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

public abstract class Entity {

    protected Handler handler;

    protected float xCurrent;
    protected float yCurrent;
    protected int width;
    protected int height;
    protected Rect bounds;

    public Entity(Handler handler, float xCurrent, float yCurrent) {
        this.handler = handler;

        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
        width = TileMap.TILE_SIZE;
        height = TileMap.TILE_SIZE;

        bounds = new Rect(0, 0, width, height);
    }

    public abstract void init();
    public abstract void update(long elapsed);
    public abstract void render(Canvas canvas);

    public boolean checkEntityCollision(float xOffset, float yOffset) {
        for (Entity e : handler.getGameCartridge().getSceneManager().getCurrentScene().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (e.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public Rect getCollisionBounds(float xOffset, float yOffset) {
        return new Rect(
                (int)(xCurrent + bounds.left + xOffset),
                (int)(yCurrent + bounds.top + yOffset),
                (int)(xCurrent + bounds.left + xOffset) + bounds.right,
                (int)(yCurrent + bounds.top + yOffset) + bounds.bottom);
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}