package com.jackingaming.notesquirrel.game.sprites;

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

        setSpriteWidth(600);
        setSpriteHeight(680);
        setX( ((getScreenWidth()/2) - (getSpriteWidth()/2)) );
        setY( ((getScreenHeight()/2) - (getSpriteHeight()/2)) );

        Random random = new Random();

        //Randomly choose 1 or -1.
        dirX = random.nextInt(2)*2 - 1;
        dirY = random.nextInt(2)*2 - 1;
    }

    public void update(long elapsed) {
        float x = getX();
        float y = getY();

        Rect screenRect = getScreenRect();

        if (screenRect.left <= 0) {
            dirX = 1;
        } else if (screenRect.right >= getScreenWidth()) {
            dirX = -1;
        }

        if (screenRect.top <= 0) {
            dirY = 1;
        } else if (screenRect.bottom >= getScreenHeight()) {
            dirY = -1;
        }

        x += (dirX * speedX * elapsed);
        y += (dirY * speedY * elapsed);

        setX(x);
        setY(y);
    }

}