package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public enum Direction { UP, DOWN, LEFT, RIGHT; }

    private GameCamera gameCamera;
    private int sideSquareScreen;
    private float pixelToScreenRatio;

    private Map<Direction, Animation> animation;

    private Direction direction;

    private float moveSpeed;
    private float xMove;
    private float yMove;
    private TileMap tileMap;

    public Player(GameCamera gameCamera, int sideSquareScreen, float pixelToScreenRatio) {
        super(0f, 0f);

        this.gameCamera = gameCamera;
        this.sideSquareScreen = sideSquareScreen;
        this.pixelToScreenRatio = pixelToScreenRatio;

        direction = Direction.DOWN;

        moveSpeed = 4f;
        xMove = 0f;
        yMove = 0f;
    }

    @Override
    public void init() {
        initAnimation();
    }

    private void initAnimation() {
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
        moveX();    //currently checking tile collisions
        //TODO: check entity collision with if-statement
        moveY();    //currently checking tile collisions
    }

    private void moveX() {
        //LEFT
        if (xMove < 0) {
            int xFuture = (int) (xCurrent + xMove);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            if (!tileMap.isSolid(xFuture, yFutureTop) && !tileMap.isSolid(xFuture, yFutureBottom)) {
                xCurrent += xMove;
                gameCamera.update(0L);
            }
        }
        //RIGHT
        else if (xMove > 0) {
            int xFuture = (int) ((xCurrent + width) + xMove - 1);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            if (!tileMap.isSolid(xFuture, yFutureTop) && !tileMap.isSolid(xFuture, yFutureBottom)) {
                xCurrent += xMove;
                gameCamera.update(0L);
            }
        }
    }

    private void moveY() {
        //UP
        if (yMove < 0) {
            int yFuture = (int) (yCurrent + yMove);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            if (!tileMap.isSolid(xFutureLeft, yFuture) && !tileMap.isSolid(xFutureRight, yFuture)) {
                yCurrent += yMove;
                gameCamera.update(0L);
            }
        }
        //DOWN
        else if (yMove > 0) {
            int yFuture = (int) ((yCurrent + height) + yMove - 1);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            if (!tileMap.isSolid(xFutureLeft, yFuture) && !tileMap.isSolid(xFutureRight, yFuture)) {
                yCurrent += yMove;
                gameCamera.update(0L);
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
                (int)( (xCurrent - gameCamera.getX()) * pixelToScreenRatio ),
                (int)( (yCurrent - gameCamera.getY()) * pixelToScreenRatio ),
                (int)( ((xCurrent - gameCamera.getX()) + width) * pixelToScreenRatio ),
                (int)( ((yCurrent - gameCamera.getY()) + height) * pixelToScreenRatio ) );

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
        this.tileMap = tileMap;
    }

    public Direction getDirection() { return direction; }

    public void setDirection(Direction direction) { this.direction = direction; }

}