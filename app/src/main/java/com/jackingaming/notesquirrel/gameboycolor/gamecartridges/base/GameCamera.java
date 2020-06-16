package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;

import java.io.Serializable;

public class GameCamera
        implements Serializable {

    public static final int CLIP_WIDTH_IN_TILE = 8;
    public static final int CLIP_HEIGHT_IN_TILE = 8;

    private float x;
    private float y;
    private int widthClipInPixel;
    private int heightClipInPixel;

    private int widthViewport;
    private int heightViewport;

    private Entity entity;
    private int widthSceneMax;
    private int heightSceneMax;

    public GameCamera(int widthViewport, int heightViewport) {
        Log.d(MainActivity.DEBUG_TAG, "GameCamera(Handler) constructor");

        x = 0f;
        y = 0f;
        widthClipInPixel = CLIP_WIDTH_IN_TILE * TileMap.TILE_WIDTH;
        heightClipInPixel = CLIP_HEIGHT_IN_TILE * TileMap.TILE_HEIGHT;

        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
    }

    public void init(Entity entity, int widthSceneMax, int heightSceneMax) {
        Log.d(MainActivity.DEBUG_TAG, "GameCamera.init(Entity, int, int)");

        this.entity = entity;
        this.widthSceneMax = widthSceneMax;
        this.heightSceneMax = heightSceneMax;

        update(0L);
    }

    public void update(long elapsed) {
        centerOnEntity();
        doNotMoveOffScreen();
    }

    private void centerOnEntity() {
        //get entity's xCenter, subtract half of widthClipInPixel.
        x = (entity.getxCurrent() + (entity.getWidth() / 2)) - (widthClipInPixel / 2);
        //get entity's yCenter, subtract half of heightClipInPixel.
        y = (entity.getyCurrent() + (entity.getHeight() / 2)) - (heightClipInPixel / 2);
    }

    private void doNotMoveOffScreen() {
        //LEFT
        if (x < 0) { x = 0; }
        //TOP
        if (y < 0) { y = 0; }
        //RIGHT
        if ((x + widthClipInPixel) > widthSceneMax) {
            x = (widthSceneMax - widthClipInPixel);
        }
        //BOTTOM
        if ((y + heightClipInPixel) > heightSceneMax) {
            y = (heightSceneMax - heightClipInPixel);
        }
    }

    public Rect getRectOfClip() {
        return new Rect((int)x, (int)y, (int)(x + widthClipInPixel), (int)(y + heightClipInPixel));
    }

    public Rect getRectOfViewport() {
        return new Rect(0, 0, widthViewport, heightViewport);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidthClipInPixel() {
        return widthClipInPixel;
    }

    public void setWidthClipInPixel(int widthClipInPixel) {
        this.widthClipInPixel = widthClipInPixel;
    }

    public int getHeightClipInPixel() {
        return heightClipInPixel;
    }

    public void setHeightClipInPixel(int heightClipInPixel) {
        this.heightClipInPixel = heightClipInPixel;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}