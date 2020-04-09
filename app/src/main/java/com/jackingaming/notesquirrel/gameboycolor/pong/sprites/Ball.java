package com.jackingaming.notesquirrel.gameboycolor.pong.sprites;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

public class Ball extends Sprite {

    private final float speedX = 0.5f;
    private final float speedY = 0.5f;

    private int dirX;
    private int dirY;

    public Ball(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
    }

    @Override
    public void init(Bitmap image) {
        super.init(image);

        setSpriteWidth(100);
        setSpriteHeight(100);

        initPosition();

        Random random = new Random();

        //Randomly choose 1 or -1.
        dirX = random.nextInt(2)*2 - 1;
        dirY = random.nextInt(2)*2 - 1;
    }

    public void initPosition() {
        setX( ((getScreenWidth()/2) - (getSpriteWidth()/2)) );
        setY( ((getScreenHeight()/2) - (getSpriteHeight()/2)) );
    }

    public void update(long elapsed) {
        Rect screenRect = getScreenRect();
        //PREVENT MOVING OFF-SCREEN (horizontally)
        if (screenRect.left <= 0) {
            dirX = 1;
        } else if (screenRect.right >= getScreenWidth()) {
            dirX = -1;
        }
        //PREVENT MOVING OFF-SCREEN (vertically)
        if (screenRect.top <= 0) {
            dirY = 1;
        } else if (screenRect.bottom >= getScreenHeight()) {
            dirY = -1;
        }

        //UPDATE BALL'S POSITION
        float x = getX();
        float y = getY();
        ///////////////////////////////
        x += (dirX * speedX * elapsed);
        y += (dirY * speedY * elapsed);
        ///////////////////////////////
        setX(x);
        setY(y);
    }

    public void moveRight() {
        dirX = 1;
    }

    public void moveLeft() {
        dirX = -1;
    }

}