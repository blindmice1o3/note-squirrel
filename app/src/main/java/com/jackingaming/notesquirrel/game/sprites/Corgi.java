package com.jackingaming.notesquirrel.game.sprites;

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

        x += (dirX * speedX * elapsed);
        y += (dirY * speedY * elapsed);

        setX(x);
        setY(y);
    }

}