package com.jackingaming.notesquirrel.game.sprites;

import android.graphics.Rect;

public class Corgi extends Sprite {

    private final float speedX = 0.5f;
    private final float speedY = 0.5f;

    private int dirX = 1;
    private int dirY = 1;

    public Corgi(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
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