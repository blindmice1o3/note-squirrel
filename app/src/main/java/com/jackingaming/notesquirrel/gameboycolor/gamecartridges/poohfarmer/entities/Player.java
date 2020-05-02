package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public enum Direction { UP, DOWN, LEFT, RIGHT; }

    private GameCartridge gameCartridge;
    private GameCamera gameCamera;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Map<Direction, Animation> animation;

    private Direction direction;

    private float moveSpeed;
    private float xMove;
    private float yMove;
    private TileMap tileMap;

    public Player(GameCartridge gameCartridge, GameCamera gameCamera, int widthViewport, int heightViewport) {
        super(0f, 0f);

        this.gameCartridge = gameCartridge;
        this.gameCamera = gameCamera;

        int widthClipInPixel = gameCamera.getWidthClipInPixel();
        int heightClipInPixel = gameCamera.getHeightClipInPixel();
        widthPixelToViewportRatio = ((float)widthViewport) / widthClipInPixel;
        heightPixelToViewportRatio = ((float)heightViewport) / heightClipInPixel;

        direction = Direction.DOWN;

        moveSpeed = 4f;
        xMove = 0f;
        yMove = 0f;
    }

    @Override
    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "Player.init()");

        initAnimation();
    }

    private void initAnimation() {
        Log.d(MainActivity.DEBUG_TAG, "Player.initAnimation()");

        animation = new HashMap<Direction, Animation>();

        animation.put(Direction.DOWN, new Animation(420, Assets.corgiCrusade[0]));
        animation.put(Direction.UP, new Animation(420, Assets.corgiCrusade[1]));
        animation.put(Direction.LEFT, new Animation(420, Assets.corgiCrusade[2]));
        animation.put(Direction.RIGHT, new Animation(420, Assets.corgiCrusade[3]));
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

        //TODO: check entity collision with if-statement
        moveX();    //currently checking tile collisions/transfer points
        //TODO: check entity collision with if-statement
        moveY();    //currently checking tile collisions/transfer points
    }

    private void moveX() {
        //LEFT
        if (xMove < 0) {
            int xFutureLeft = (int) (xCurrent + xMove);
            int xFutureRight = (int) ((xCurrent + width) + xMove - 1);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureTop) && !tileMap.isSolid(xFutureLeft, yFutureBottom)) {

                xCurrent += xMove;
                gameCamera.update(0L);

                //CHECKING TransferPoints
                if (tileMap.getSceneID() != Scene.Id.FARM) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Player.moveY() LEFT, @@@@@transfer point collision@@@@@");
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

                xCurrent += xMove;
                gameCamera.update(0L);

                //CHECKING TransferPoints
                if (tileMap.getSceneID() != Scene.Id.FARM) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Player.moveY() RIGHT, @@@@@transfer point collision@@@@@");
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
        //UP
        if (yMove < 0) {
            int yFutureTop = (int) (yCurrent + yMove);
            int yFutureBottom = (int) ((yCurrent + height) + yMove - 1);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureTop) && !tileMap.isSolid(xFutureRight, yFutureTop)) {

                yCurrent += yMove;
                gameCamera.update(0L);

                //CHECKING TransferPoints
                if (tileMap.getSceneID() != Scene.Id.FARM) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Player.moveY() UP, @@@@@transfer point collision@@@@@");
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

                yCurrent += yMove;
                gameCamera.update(0L);

                //CHECKING TransferPoints
                if (tileMap.getSceneID() != Scene.Id.FARM) {
                    Rect collisionBoundsFuture = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                    if (tileMap.checkTransferPointsCollision(collisionBoundsFuture) != null) {
                        Log.d(MainActivity.DEBUG_TAG, "Player.moveY() DOWN, @@@@@transfer point collision@@@@@");
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

    @Override
    public void update(long elapsed) {
        xMove = 0f;
        yMove = 0f;

        for (Animation anim : animation.values()) {
            anim.update();
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = currentAnimationFrame();

        Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());

//        Rect screenRect = new Rect(
//                (int)(64 * pixelToScreenRatio),
//                (int)(64 * pixelToScreenRatio),
//                (int)((64 + width)* pixelToScreenRatio),
//                (int)((64 + height) * pixelToScreenRatio)
//        );

        Rect screenRect = new Rect(
                (int)( (xCurrent - gameCamera.getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCamera.getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCamera.getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCamera.getY()) + height) * heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    private Bitmap currentAnimationFrame() {
        Bitmap currentFrame = null;

        switch (direction) {
            case UP:
                currentFrame = animation.get(Direction.UP).getCurrentFrame();
                break;
            case DOWN:
                currentFrame = animation.get(Direction.DOWN).getCurrentFrame();
                break;
            case LEFT:
                currentFrame = animation.get(Direction.LEFT).getCurrentFrame();
                break;
            case RIGHT:
                currentFrame = animation.get(Direction.RIGHT).getCurrentFrame();
                break;
            default:
                currentFrame = Assets.corgiCrusade[0][0];
                break;
        }

        return currentFrame;
    }

    public void setTileMap(TileMap tileMap) {
        Log.d(MainActivity.DEBUG_TAG, "Player.setTileMap(TileMap)");

        this.tileMap = tileMap;
    }

    public void checkTileFacing() {
        Log.d(MainActivity.DEBUG_TAG, "Player.checkTileFacing()");

        /////////////////////////////////////////////////////
        if (gameCartridge instanceof PocketCrittersCartridge) {
        /////////////////////////////////////////////////////
            float xPlayerCenter = xCurrent + (width / 2);
            float yPlayerCenter = yCurrent + (height / 2);

            int xInspect = 0;
            int yInspect = 0;
            switch (direction) {
                case UP:
                    xInspect = (int) (xPlayerCenter);
                    yInspect = (int) (yPlayerCenter - ((height / 2) + 1));
                    break;
                case DOWN:
                    xInspect = (int) (xPlayerCenter);
                    yInspect = (int) (yPlayerCenter + ((height / 2) + 1));
                    break;
                case LEFT:
                    xInspect = (int) (xPlayerCenter - ((width / 2) + 1));
                    yInspect = (int) (yPlayerCenter);
                    break;
                case RIGHT:
                    xInspect = (int) (xPlayerCenter + ((width / 2) + 1));
                    yInspect = (int) (yPlayerCenter);
                    break;
                default:
                    Log.d(MainActivity.DEBUG_TAG, "Player.checkTileFacing() switch construct's default block.");
                    break;
            }

            Log.d(MainActivity.DEBUG_TAG, "checkTileFacing at (currently in pixel values): (" + xInspect + ", " + yInspect + ").");
            ////////////////////////////////////////////////////////////////////
            TileMap.TileType tileFacing = tileMap.checkTile(xInspect, yInspect);
            ////////////////////////////////////////////////////////////////////
            if (tileFacing != null) {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing: " + tileFacing.name());

                final String message = tileFacing.name();
                final Context context = ((PocketCrittersCartridge)gameCartridge).getContext();
                /////////////////////////////////////////////////////////////////////////////////
                ((JackInActivity)context).runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                });
                /////////////////////////////////////////////////////////////////////////////////
                
                /*
                if ( (tileFacing == TileMap.TileType.COMPUTER) ||
                        (tileFacing == TileMap.TileType.GAME_CONSOLE) ||
                        (tileFacing == TileMap.TileType.TELEVISION) ) {

                    final String message = tileFacing.name();
                    final Context context = ((PocketCrittersCartridge)gameCartridge).getContext();
                    /////////////////////////////////////////////////////////////////////////////////
                    ((JackInActivity)context).runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    });
                    /////////////////////////////////////////////////////////////////////////////////
                }
                */
            } else {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing is null");
            }
        }
    }

    public Direction getDirection() {
        Log.d(MainActivity.DEBUG_TAG, "Player.getDirection()");

        return direction;
    }

    public void setDirection(Direction direction) {
        Log.d(MainActivity.DEBUG_TAG, "Player.setDirection(Direction)");

        this.direction = direction;
    }

}