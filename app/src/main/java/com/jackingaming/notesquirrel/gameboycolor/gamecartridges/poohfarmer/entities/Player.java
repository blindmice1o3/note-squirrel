package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    private enum Direction { UP, DOWN, LEFT, RIGHT; }

    private int sideSquareScreen;
    private float pixelToScreenRatio;

    private Map<Direction, Animation> animation;
    private Direction direction;
    private float moveSpeed = 4f;

    public Player(int sideSquareScreen, float pixelToScreenRatio) {
        super(0f, 0f);

        this.sideSquareScreen = sideSquareScreen;
        this.pixelToScreenRatio = pixelToScreenRatio;
        direction = Direction.DOWN;
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

    public void moveLeft() {
        xCurrent -= moveSpeed;
        direction = Direction.LEFT;
    }

    public void moveRight() {
        xCurrent += moveSpeed;
        direction = Direction.RIGHT;
    }

    public void moveUp() {
        yCurrent -= moveSpeed;
        direction = Direction.UP;
    }

    public void moveDown() {
        yCurrent += moveSpeed;
        direction = Direction.DOWN;
    }

    @Override
    public void update(long elapsed) {
        for (Animation anim : animation.values()) {
            anim.update();
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = currentAnimationFrame();

        Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
        Rect screenRect = new Rect(
                (int)(64 * pixelToScreenRatio),
                (int)(64 * pixelToScreenRatio),
                (int)((64 + width)* pixelToScreenRatio),
                (int)((64 + height) * pixelToScreenRatio)
        );

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

    public float getMoveSpeed() {
        return moveSpeed;
    }

}