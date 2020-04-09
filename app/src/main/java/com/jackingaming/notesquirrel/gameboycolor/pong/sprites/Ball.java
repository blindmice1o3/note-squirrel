package com.jackingaming.notesquirrel.gameboycolor.pong.sprites;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

public class Ball extends Sprite {

    private final float speedX = 0.5f;
    private final float speedY = 0.5f;

    private int dirX;
    private int dirY;

    public Ball(int widthScreen, int heightScreen) {
        super(widthScreen, heightScreen);
    }

    @Override
    public void init(Bitmap image) {
        super.init(image);

        setWidthSprite(100);
        setHeightSprite(100);

        initPosition();

        Random random = new Random();

        //Randomly choose 1 or -1.
        dirX = random.nextInt(2)*2 - 1;
        dirY = random.nextInt(2)*2 - 1;
    }

    public void initPosition() {
        setX( ((getWidthScreen()/2) - (getWidthSprite()/2)) );
        setY( ((getHeightScreen()/2) - (getHeightSprite()/2)) );
    }

    public void update(long elapsed) {
        Rect screenRect = getScreenRect();
        //PREVENT MOVING OFF-SCREEN (horizontally)
        if (screenRect.left <= 0) {
            dirX = 1;
        } else if (screenRect.right >= getWidthScreen()) {
            dirX = -1;
        }
        //PREVENT MOVING OFF-SCREEN (vertically)
        if (screenRect.top <= 0) {
            dirY = 1;
        } else if (screenRect.bottom >= getHeightScreen()) {
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