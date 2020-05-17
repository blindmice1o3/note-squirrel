package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

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

    private void moveX() {
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

                //CHECKING TransferPoints
                if ((this instanceof Player) && (tileMap.getSceneID() != Scene.Id.FARM)) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Creature.moveY() LEFT, @@@@@transfer point collision@@@@@");
                        //TODO: recordLocationPriorToTranfer() and loadLocationPriorToTransfer().
                        //TODO: !!!!!POP() IS NEVER CALLED!!!!!
                        Scene.Id id = tileMap.checkTransferPointsCollision(collisionBoundsFuture);
                        //TODO: Instead of handling transfering here, do it in TileMap.checkTransferPointsCollision(Rect).
                        SceneManager sceneManager = ((PocketCrittersCartridge)gameCartridge).getSceneManager();
                        if ( (id == Scene.Id.PART_01) ||
                                ((id == Scene.Id.HOME_01) && (sceneManager.getCurrentScene().getSceneID() == Scene.Id.HOME_02)) ) {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.pop(directionFacing);
                        } else {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.push(id, directionFacing);
                        }
                    }
                }
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

                //CHECKING TransferPoints
                if ((this instanceof Player) && (tileMap.getSceneID() != Scene.Id.FARM)) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Creature.moveY() RIGHT, @@@@@transfer point collision@@@@@");
                        //TODO: recordLocationPriorToTranfer() and loadLocationPriorToTransfer()
                        //TODO: !!!!!POP() IS NEVER CALLED!!!!!
                        Scene.Id id = tileMap.checkTransferPointsCollision(collisionBoundsFuture);
                        //TODO: Instead of handling transfering here, do it in TileMap.checkTransferPointsCollision(Rect).
                        SceneManager sceneManager = ((PocketCrittersCartridge)gameCartridge).getSceneManager();
                        if ( (id == Scene.Id.PART_01) ||
                                ((id == Scene.Id.HOME_01) && (sceneManager.getCurrentScene().getSceneID() == Scene.Id.HOME_02)) ) {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.pop(directionFacing);

                        } else {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.push(id, directionFacing);
                        }
                    }
                }
            }
        }
    }

    private void moveY() {
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

                //CHECKING TransferPoints
                if ((this instanceof Player) && (tileMap.getSceneID() != Scene.Id.FARM)) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Creature.moveY() UP, @@@@@transfer point collision@@@@@");
                        //TODO: recordLocationPriorToTranfer() and loadLocationPriorToTransfer()
                        //TODO: !!!!!POP() IS NEVER CALLED!!!!!
                        Scene.Id id = tileMap.checkTransferPointsCollision(collisionBoundsFuture);
                        //TODO: Instead of handling transfering here, do it in TileMap.checkTransferPointsCollision(Rect).
                        SceneManager sceneManager = ((PocketCrittersCartridge)gameCartridge).getSceneManager();
                        if ( (id == Scene.Id.PART_01) ||
                                ((id == Scene.Id.HOME_01) && (sceneManager.getCurrentScene().getSceneID() == Scene.Id.HOME_02)) ) {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.pop(directionFacing);

                        } else {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.push(id, directionFacing);
                        }
                    }
                }
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

                //CHECKING TransferPoints
                if ((this instanceof Player) && (tileMap.getSceneID() != Scene.Id.FARM)) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Creature.moveY() DOWN, @@@@@transfer point collision@@@@@");
                        //TODO: recordLocationPriorToTranfer() and loadLocationPriorToTransfer()
                        //TODO: !!!!!POP() IS NEVER CALLED!!!!!
                        Scene.Id id = tileMap.checkTransferPointsCollision(collisionBoundsFuture);
                        //TODO: Instead of handling transfering here, do it in TileMap.checkTransferPointsCollision(Rect).
                        SceneManager sceneManager = ((PocketCrittersCartridge)gameCartridge).getSceneManager();
                        if ( (id == Scene.Id.PART_01) ||
                                ((id == Scene.Id.HOME_01) && (sceneManager.getCurrentScene().getSceneID() == Scene.Id.HOME_02)) ) {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.pop(directionFacing);

                        } else {
                            Object[] directionFacing = { direction, moveSpeed };
                            sceneManager.push(id, directionFacing);
                        }
                    }
                }
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

}