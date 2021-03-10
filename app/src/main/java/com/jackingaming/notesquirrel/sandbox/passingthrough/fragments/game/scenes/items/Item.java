package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.io.Serializable;

public class Item
        implements Serializable {
    transient protected Game game;

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    transient protected Rect bounds;
    transient protected Bitmap image;

    protected String name;

    public Item() {
        x = 0;
        y = 0;
        width = Tile.WIDTH;
        height = Tile.HEIGHT;
    }

    public void init(Game game) {
        this.game = game;
        bounds = new Rect(0, 0, width, height);
    }

    public void draw(Canvas canvas) {
        if (image != null) {
            Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
            Rect rectOnScreen = GameCamera.getInstance().convertInGameRectToScreenRect(getCollisionBounds(0, 0));

            canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Rect getCollisionBounds(float xOffset, float yOffset) {
        return new Rect(
                (int)(x + bounds.left + xOffset),
                (int)(y + bounds.top + yOffset),
                (int)(x + bounds.left + xOffset) + bounds.right,
                (int)(y + bounds.top + yOffset) + bounds.bottom);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }
}