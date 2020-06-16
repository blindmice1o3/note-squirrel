package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.sprites;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

public class Ball extends Sprite {

    private final float speedX = 0.5f;
    private final float speedY = 0.5f;

    private int dirX;
    private int dirY;

    public Ball(int widthViewport, int heightViewport) {
        super(widthViewport, heightViewport);
    }

    @Override
    public void init(Bitmap image) {
        super.init(image);

        setWidth(100);
        setHeight(100);

        initPosition();

        Random random = new Random();

        //Randomly choose 1 or -1.
        dirX = random.nextInt(2)*2 - 1;
        dirY = random.nextInt(2)*2 - 1;
    }

    public void initPosition() {
        setxCurrent( ((getWidthViewport()/2) - (getWidth()/2)) );
        setyCurrent( ((getHeightViewport()/2) - (getHeight()/2)) );
    }

    public void update(long elapsed) {
        Rect screenRect = getRectOnScreen();
        //PREVENT MOVING OFF-SCREEN (horizontally)
        if (screenRect.left <= 0) {
            dirX = 1;
        } else if (screenRect.right >= getWidthViewport()) {
            dirX = -1;
        }
        //PREVENT MOVING OFF-SCREEN (vertically)
        if (screenRect.top <= 0) {
            dirY = 1;
        } else if (screenRect.bottom >= getHeightViewport()) {
            dirY = -1;
        }

        //UPDATE BALL'S POSITION
        float x = getxCurrent();
        float y = getyCurrent();
        ///////////////////////////////
        x += (dirX * speedX * elapsed);
        y += (dirY * speedY * elapsed);
        ///////////////////////////////
        setxCurrent(x);
        setyCurrent(y);
    }

    public void moveRight() {
        dirX = 1;
    }

    public void moveLeft() {
        dirX = -1;
    }

}