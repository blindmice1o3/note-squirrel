package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

import java.io.Serializable;

public class Tile
        implements Serializable {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    transient private Game game;

    private String id;
    private boolean walkable = true;
    private int xIndex;
    private int yIndex;
    transient private Bitmap image;

    public Tile(String id) {
        this.id = id;
    }

    public void init(Game game, int xIndex, int yIndex, Bitmap image) {
        this.game = game;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.image = image;
    }

    public void draw(Canvas canvas) {
        if (image != null) {
            Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
            Rect rectOnScreen = new Rect(
                    (int) (((xIndex * Tile.WIDTH) - game.getGameCamera().getX()) * game.getGameCamera().getWidthPixelToViewportRatio()),
                    (int) (((yIndex * Tile.HEIGHT) - game.getGameCamera().getY()) * game.getGameCamera().getHeightPixelToViewportRatio()),
                    (int) ((((xIndex * Tile.WIDTH) + Tile.WIDTH) - game.getGameCamera().getX()) * game.getGameCamera().getWidthPixelToViewportRatio()),
                    (int) ((((yIndex * Tile.HEIGHT) + Tile.HEIGHT) - game.getGameCamera().getY()) * game.getGameCamera().getHeightPixelToViewportRatio()));
            canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
        }
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public String getId() {
        return id;
    }
}