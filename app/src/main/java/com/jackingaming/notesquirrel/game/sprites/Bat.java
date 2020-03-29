package com.jackingaming.notesquirrel.game.sprites;

import android.graphics.Bitmap;

public class Bat extends Sprite {

    public enum Position {
        LEFT, RIGHT;
    }

    private static final int margin = 20;
    private Position position;

    public Bat(int screenWidth, int screenHeight, Position position) {
        super(screenWidth, screenHeight);

        this.position = position;
    }

    @Override
    public void init(Bitmap image) {
        super.init(image);

        setSpriteWidth(242);
        setSpriteHeight(834);
        setY( ((getScreenHeight()/2) - (getSpriteHeight()/2)) );

        if (position == Position.LEFT) {
            setX(margin);
        } else if (position == Position.RIGHT) {
            setX( ((getScreenWidth() - margin) - getSpriteWidth()) );
        }
    }

    /**
     * Set the center of the bat to where the player touched.
     *
     * @param y - The y-position of the MotionEvent.
     */
    public void setPosition(float y) {
        setY( (y - (getSpriteHeight()/2)) );
    }

}