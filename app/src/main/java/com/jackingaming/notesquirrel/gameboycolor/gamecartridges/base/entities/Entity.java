package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;

import java.io.Serializable;

public abstract class Entity
        implements Serializable {

    transient protected GameCartridge gameCartridge;

    protected float xCurrent;
    protected float yCurrent;
    protected int width;
    protected int height;

    transient protected Rect bounds; //COLLISION-BOX (NOT same as rectOfImage or rectOnScreen).

    protected boolean active;

    public Entity(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
        this.gameCartridge = gameCartridge;

        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
        width = TileMap.TILE_WIDTH;
        height = TileMap.TILE_HEIGHT;

        bounds = new Rect(0, 0, width, height);

        active = true;
    }

    public abstract void init(GameCartridge gameCartridge);
    public abstract void initBounds();
    public abstract void update(long elapsed);
    public abstract void render(Canvas canvas);

    public boolean checkEntityCollision(float xOffset, float yOffset) {
        for (Entity e : gameCartridge.getSceneManager().getCurrentScene().getEntityManager().getEntities()) {
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

    public void setBounds(Rect bounds) {
        this.bounds = bounds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setGameCartridge(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
    }

}