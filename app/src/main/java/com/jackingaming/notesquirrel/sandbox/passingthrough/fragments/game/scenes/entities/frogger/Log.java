package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.frogger;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class Log extends Creature {
    public enum Size { LARGE, MEDIUM, SMALL; }

    private Size size;

    public Log(int xSpawn, int ySpawn, Size size, Direction direction, Bitmap image) {
        super(xSpawn, ySpawn);

        this.size = size;
        switch (size) {
            case LARGE:
                width = 3 * Tile.WIDTH;
                break;
            case MEDIUM:
                width = 2 * Tile.WIDTH;
                break;
            case SMALL:
                width = 1 * Tile.WIDTH;
                break;
        }
        height = 1 * Tile.HEIGHT;
        bounds = new Rect(0, 0, width, height);

        //Do NOT allow UP, DOWN, or any of the diagonals (default is RIGHT).
        if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
            this.direction = direction;
        } else {
            this.direction = Direction.RIGHT;
        }

        this.image = image;
    }

    @Override
    public void update(long elapsed) {
        xMove = 0;
        yMove = 0;

        switch (direction) {
            case LEFT:
                if ( (x - moveSpeed) > 0 ) {
                    xMove = -moveSpeed;
                } else {
                    active = false;
                }

                break;
            case RIGHT:
                int widthSceneMax = game.getSceneManager().getCurrentScene().getTileManager().getWidthScene();
                if ( (x + width + moveSpeed) < widthSceneMax ) {
                    xMove = moveSpeed;
                } else {
                    active = false;
                }

                break;
            default:
                android.util.Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".update(long elapsed) switch (direction)'s default.");
                break;
        }

        move();
    }

    @Override
    public boolean respondToEntityCollision(Entity e) {
        if (e instanceof Player) {
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@
            e.setX( (e.getX() + xMove) );
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@

            //@@@@@@@@@@%
            return false;
            //@@@@@@@@@@@
        } else {
            return true;
        }
    }

    @Override
    public void respondToItemCollisionViaClick(Item item) {

    }

    @Override
    public void respondToItemCollisionViaMove(Item item) {

    }

    @Override
    public void respondToTransferPointCollision(String key) {

    }
}