package com.jackingaming.notesquirrel.game.sprites;

import android.graphics.Bitmap;

import java.util.Random;

public class Bat extends Sprite {

    public enum Position {
        LEFT, RIGHT;
    }

    private Position position;
    private static final int margin = 20;

    private int dirY;
    private float speed = 0.4f;
    private Random random = new Random();

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

        //set dirY to either 1 or -1 (opponent's simulated intelligence).
        dirY = random.nextInt(2)*2-1;

        if (position == Position.LEFT) {
            setX(margin);
        } else if (position == Position.RIGHT) {
            setX( ((getScreenWidth() - margin) - getSpriteWidth()) );
        }
    }

    /**
     * Triggered by player's touch events.
     *
     * Set the center of the bat to where the player touched.
     *
     * @param y - The y-position of the MotionEvent.
     */
    public void setBatPosition(float y) {
        setY( (y - (getSpriteHeight()/2)) );
    }

    /**
     * The opponent's simulated response.
     *
     * @param elapsed - milliseconds elapsed since the previous game loop iteration.
     */
    public void update(long elapsed, Ball ball) {
        int decision = random.nextInt(20);

        if (decision == 0) {
            //Stop movement: 1 out of every 20 times.
            dirY = 0;
        } else if (decision == 1) {
            //Randomly set dirY to either 1 or -1: 1 out of every 20 times.
            dirY = random.nextInt(2)*2-1;
        } else if (decision < 4) {
            //Move towards the ball: 2 out of every 20 times.
            //if vertical center of ball is less than vertical center of opponent, move opponent upward.
            if (ball.getScreenRect().centerY() < getScreenRect().centerY()) {
                dirY = -1;
            }
            //the ball is lower on the screen than the bat, move opponent downward.
            else {
                dirY = 1;
            }
        }

        //A check to prevent opponent from moving off-screen.
        if (getScreenRect().top <= 0) {
            dirY = 1;
        } else if (getScreenRect().bottom >= getScreenHeight()) {
            dirY = -1;
        }

        float y = getY();

        ////////////////////////////
        y += dirY * speed * elapsed;
        ////////////////////////////

        setY(y);
    }

}