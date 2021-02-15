package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.io.Serializable;

public class GameCamera
        implements Serializable {
    public static final int CLIP_WIDTH_IN_TILE_DEFAULT = 8;
    public static final int CLIP_HEIGHT_IN_TILE_DEFAULT = 8;

    private static GameCamera uniqueInstance;

    private Entity entity;
    private float x;
    private float y;

    private int widthScene;
    private int heightScene;

    private int clipWidthInTile;
    private int clipHeightInTile;
    private int clipWidthInPixel;
    private int clipHeightInPixel;
    transient private int widthViewport;
    transient private int heightViewport;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private GameCamera() {
        x = 0f;
        y = 0f;
        clipWidthInTile = CLIP_WIDTH_IN_TILE_DEFAULT;
        clipHeightInTile = CLIP_HEIGHT_IN_TILE_DEFAULT;
    }

    public static GameCamera getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameCamera();
        }
        return uniqueInstance;
    }

    public void init(Entity entity, int widthViewport, int heightViewport, int widthScene, int heightScene) {
        this.entity = entity;
        // Want to use the values-newly-assigned-by-OS for widthViewport and heightViewport when loading.
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
        this.widthScene = widthScene;
        this.heightScene = heightScene;

        updateWidthPixelToViewportRatio();
        updateHeightPixelToViewportRatio();

        update(0L);
    }

    public void update(long elapsed) {
        centerOnEntity();
        doNotMoveOffScreen();
    }

    private void centerOnEntity() {
        //get entity's xCenter, subtract half of clipWidthInPixel.
        x = (entity.getX() + (entity.getWidth() / 2f)) - (clipWidthInPixel / 2f);
        //get entity's yCenter, subtract half of clipHeightInPixel.
        y = (entity.getY() + (entity.getHeight() / 2f)) - (clipHeightInPixel / 2f);
    }

    private void doNotMoveOffScreen() {
        //LEFT
        if (x < 0) {
            x = 0;
        }
        //RIGHT
        else if ((x + clipWidthInPixel) > widthScene) {
            x = (widthScene - clipWidthInPixel);
        }

        //TOP
        if (y < 0) {
            y = 0;
        }
        //BOTTOM
        else if ((y + clipHeightInPixel) > heightScene) {
            y = (heightScene - clipHeightInPixel);
        }
    }

    public void updateClipWidthInTile(int clipWidthInTile) {
        this.clipWidthInTile = clipWidthInTile;
        updateWidthPixelToViewportRatio();
    }

    public void updateClipHeightInTile(int clipHeightInTile) {
        this.clipHeightInTile = clipHeightInTile;
        updateHeightPixelToViewportRatio();
    }

    public void updateWidthPixelToViewportRatio() {
        clipWidthInPixel = clipWidthInTile * Tile.WIDTH;
        widthPixelToViewportRatio = widthViewport / (float) clipWidthInPixel;
    }

    public void updateHeightPixelToViewportRatio() {
        clipHeightInPixel = clipHeightInTile * Tile.HEIGHT;
        heightPixelToViewportRatio = heightViewport / (float) clipHeightInPixel;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getClipWidthInTile() {
        return clipWidthInTile;
    }

    public int getClipHeightInTile() {
        return clipHeightInTile;
    }

    public int getClipWidthInPixel() {
        return clipWidthInPixel;
    }

    public int getClipHeightInPixel() {
        return clipHeightInPixel;
    }

    public float getWidthPixelToViewportRatio() {
        return widthPixelToViewportRatio;
    }

    public float getHeightPixelToViewportRatio() {
        return heightPixelToViewportRatio;
    }

    public void setWidthScene(int widthScene) {
        this.widthScene = widthScene;
    }

    public void setHeightScene(int heightScene) {
        this.heightScene = heightScene;
    }
}
