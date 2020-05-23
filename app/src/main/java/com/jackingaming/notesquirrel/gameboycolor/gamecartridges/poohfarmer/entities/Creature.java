package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.io.Serializable;

public abstract class Creature extends Entity {

    public enum Direction { UP, DOWN, LEFT, RIGHT; }

    protected Direction direction;

    protected float moveSpeed;
    protected float xMove;
    protected float yMove;

    public Creature(Handler handler, float xCurrent, float yCurrent) {
        super(handler, xCurrent, yCurrent);

        direction = Direction.DOWN;

        moveSpeed = 4f;
        xMove = 0f;
        yMove = 0f;
    }

    public void move(Direction direction) {
        this.direction = direction;

        switch (this.direction) {
            case LEFT:
                xMove = -moveSpeed;
                break;
            case RIGHT:
                xMove = moveSpeed;
                break;
            case UP:
                yMove = -moveSpeed;
                break;
            case DOWN:
                yMove = moveSpeed;
                break;
            default:
                xMove = 0f;
                yMove = 0f;
                break;
        }

        //check entity collision.
        if (!checkEntityCollision(xMove, 0f)) {
            moveX();    //check tile and transfer point collisions.
        }
        //check entity collision.
        if (!checkEntityCollision(0f, yMove)) {
            moveY();    //check tile and transfer point collisions.
        }
    }

    protected void moveX() {
        GameCartridge gameCartridge = handler.getGameCartridge();
        TileMap tileMap = gameCartridge.getSceneManager().getCurrentScene().getTileMap();

        //LEFT
        if (xMove < 0) {
            int xFutureLeft = (int) (xCurrent + xMove);
            int xFutureRight = (int) ((xCurrent + width) + xMove - 1);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureTop) && !tileMap.isSolid(xFutureLeft, yFutureBottom)) {
                //////////////////
                xCurrent += xMove;
                //////////////////
            }
        }
        //RIGHT
        else if (xMove > 0) {
            int xFutureLeft = (int) (xCurrent + xMove);
            int xFutureRight = (int) ((xCurrent + width) + xMove - 1);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureRight, yFutureTop) && !tileMap.isSolid(xFutureRight, yFutureBottom)) {
                //////////////////
                xCurrent += xMove;
                //////////////////
            }
        }
    }

    protected void moveY() {
        GameCartridge gameCartridge = handler.getGameCartridge();
        TileMap tileMap = gameCartridge.getSceneManager().getCurrentScene().getTileMap();

        //UP
        if (yMove < 0) {
            int yFutureTop = (int) (yCurrent + yMove);
            int yFutureBottom = (int) ((yCurrent + height) + yMove - 1);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureTop) && !tileMap.isSolid(xFutureRight, yFutureTop)) {
                //////////////////
                yCurrent += yMove;
                //////////////////
            }
        }
        //DOWN
        else if (yMove > 0) {
            int yFutureTop = (int) (yCurrent + yMove);
            int yFutureBottom = (int) ((yCurrent + height) + yMove - 1);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureBottom) && !tileMap.isSolid(xFutureRight, yFutureBottom)) {
                //////////////////
                yCurrent += yMove;
                //////////////////
            }
        }
    }

    public Direction getDirection() {
        Log.d(MainActivity.DEBUG_TAG, "Creature.getDirection()");

        return direction;
    }

    public void setDirection(Direction direction) {
        Log.d(MainActivity.DEBUG_TAG, "Creature.setDirection(Direction)");

        this.direction = direction;
    }

    public float getMoveSpeed() {
        Log.d(MainActivity.DEBUG_TAG, "Creature.getMoveSpeed()");

        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

}