package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.io.Serializable;

public abstract class Entity
        implements Serializable {
    transient protected Game game;

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    transient protected Rect bounds;
    transient protected Bitmap image;
    protected boolean active;

    public Entity(int xSpawn, int ySpawn) {
        x = xSpawn;
        y = ySpawn;
        width = Tile.WIDTH;
        height = Tile.HEIGHT;
        active = true;
    }

    public void init(Game game) {
        this.game = game;
        bounds = new Rect(0, 0, width, height);
    }

    public abstract void update(long elapsed);
    public abstract void respondToEntityCollision(Entity e);
    public abstract void respondToItemCollisionViaClick(Item item);
    public abstract void respondToItemCollisionViaMove(Item item);
    public abstract void respondToTransferPointCollision(String key);

    public void draw(Canvas canvas) {
        if (image != null) {
            Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
            Rect rectOnScreen = new Rect(
                    (int) ((x - game.getGameCamera().getX()) * game.getGameCamera().getWidthPixelToViewportRatio()),
                    (int) ((y - game.getGameCamera().getY()) * game.getGameCamera().getHeightPixelToViewportRatio()),
                    (int) ((x + width - game.getGameCamera().getX()) * game.getGameCamera().getWidthPixelToViewportRatio()),
                    (int) ((y + height - game.getGameCamera().getY()) * game.getGameCamera().getHeightPixelToViewportRatio()));
            canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
        }
    }

    public boolean checkItemCollision(float xOffset, float yOffset, boolean viaClick) {
        for (Item item : game.getSceneManager().getCurrentScene().getItemManager().getItems()) {
            if (item.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xOffset, yOffset))) {
                if (viaClick) {
                    respondToItemCollisionViaClick(item);
                } else {
                    respondToItemCollisionViaMove(item);
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkTransferPointCollision(float xOffset, float yOffset) {
        for (String key : game.getSceneManager().getCurrentScene().getTileManager().getTransferPoints().keySet()) {
            Rect transferPoint = game.getSceneManager().getCurrentScene().getTileManager().getTransferPoints().get(key);

            if (transferPoint.intersect(getCollisionBounds(xOffset, yOffset))) {
                respondToTransferPointCollision(key);
                return true;
            }
        }
        return false;
    }

    public boolean checkEntityCollision(float xOffset, float yOffset) {
        for (Entity e : game.getSceneManager().getCurrentScene().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (e.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xOffset, yOffset))) {
                respondToEntityCollision(e);
                return true;
            }
        }
        return false;
    }

    public Rect getCollisionBounds(float xOffset, float yOffset) {
        return new Rect(
                (int)(x + bounds.left + xOffset),
                (int)(y + bounds.top + yOffset),
                (int)(x + bounds.left + xOffset) + bounds.right,
                (int)(y + bounds.top + yOffset) + bounds.bottom);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}