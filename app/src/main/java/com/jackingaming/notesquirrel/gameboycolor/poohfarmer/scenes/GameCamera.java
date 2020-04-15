package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.tiles.TileMap;

public class GameCamera {

    public static final int CLIP_NUMBER_OF_TILES = 9;

    private float x;
    private float y;
    private int widthClip;
    private int heightClip;

    private Entity entity;
    private int widthSceneMax;
    private int heightSceneMax;

    public GameCamera() {
        x = 0f;
        y = 0f;
        widthClip = CLIP_NUMBER_OF_TILES * TileMap.TILE_SIZE;
        heightClip = CLIP_NUMBER_OF_TILES * TileMap.TILE_SIZE;
    }

    public void init(Entity entity, int widthSceneMax, int heightSceneMax) {
        this.entity = entity;
        this.widthSceneMax = widthSceneMax;
        this.heightSceneMax = heightSceneMax;

        update(0L);
    }

    public void update(long elapsed) {
        centerOnEntity();
        doNotMoveOffScreen();
    }

    public void moveLeft() {
        x -= 4f;
    }
    public void moveRight() {
        x += 4f;
    }
    public void moveUp() {
        y -= 4f;
    }
    public void moveDown() {
        y += 4f;
    }

    private void centerOnEntity() {
        //get entity's xCenter, subtract half of widthClip.
        x = (entity.getxCurrent() + (entity.getWidth() / 2)) - (widthClip / 2);
        //get entity's yCenter, subtract half of heightClip.
        y = (entity.getyCurrent() + (entity.getHeight() / 2)) - (heightClip / 2);
    }

    private void doNotMoveOffScreen() {
        //LEFT
        if (x < 0) { x = 0; }
        //TOP
        if (y < 0) { y = 0; }
        //RIGHT
        if ((x + widthClip) > widthSceneMax) {
            x = (widthSceneMax - widthClip);
        }
        //BOTTOM
        if ((y + heightClip) > heightSceneMax) {
            y = (heightSceneMax - heightClip);
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidthClip() {
        return widthClip;
    }

    public int getHeightClip() {
        return heightClip;
    }

}