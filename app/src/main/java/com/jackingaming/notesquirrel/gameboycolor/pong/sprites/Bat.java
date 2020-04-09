package com.jackingaming.notesquirrel.gameboycolor.pong.sprites;

import android.graphics.Bitmap;

import java.util.Random;

public class Bat extends Sprite {

    public enum Position {
        LEFT, RIGHT;
    }

    private Position position;
    private static final int margin = 20;

    private int dirY;
    private float speed = 0.8f;
    private Random random = new Random();

    public Bat(int screenWidth, int screenHeight, Position position) {
        super(screenWidth, screenHeight);

        this.position = position;
    }

    @Override
    public void init(Bitmap image) {
        super.init(image);

        setWidthSprite(100);
        setHeightSprite(600);

        initPosition();

        //set dirY to either 1 or -1 (opponent's simulated intelligence).
        dirY = random.nextInt(2)*2-1;

        if (position == Position.LEFT) {
            setX(margin);
        } else if (position == Position.RIGHT) {
            setX( (getWidthScreen() - margin - getWidthSprite()) );
        }
    }

    public void initPosition() {
        setY( ((getHeightScreen()/2) - (getHeightSprite()/2)) );
    }

    /**
     * Set the (vertical) center of the bat to where the user touched.
     *
     * Handle touch events triggered by GameView (custom SurfaceView).
     * Invoked in PongCartridge.getInput(MotionEvent).
     *
     * @param y - The y-position of the MotionEvent (a.k.a. touch event).
     */
    public void setBatPosition(float y) {
        ////UPDATE PLAYER'S POSITION
        setY( (y - (getHeightSprite()/2)) );
    }

    /**
     * The opponent's simulated response.
     *
     * @param elapsed - milliseconds elapsed since the previous game loop iteration.
     */
    public void update(long elapsed, Ball ball) {
        //SIMULATED INTELLIGENCE
        int decision = random.nextInt(20);
        //OPPONENT'S POSSIBLE MOVES (no movement, random movement, towards ball movement)
        if (decision == 0) {
            //Stop movement: 1 out of every 20 times.
            dirY = 0;
        } else if (decision == 1) {
            //Randomly set dirY to either 1 or -1: 1 out of every 20 times.
            dirY = random.nextInt(2)*2-1;
        } else if (decision < 6) {
            //Move towards the ball: 4 out of every 20 times.
            //if vertical center of ball is less than vertical center of opponent, move opponent upward.
            if (ball.getScreenRect().centerY() < getScreenRect().centerY()) {
                dirY = -1;
            }
            //the ball is lower on the screen than the bat, move opponent downward.
            else {
                dirY = 1;
            }
        }

        //PREVENT MOVING OFF-SCREEN (vertically)
        if (getScreenRect().top <= 0) {
            dirY = 1;
        } else if (getScreenRect().bottom >= getHeightScreen()) {
            dirY = -1;
        }

        //UPDATE OPPONENT'S POSITION
        float y = getY();
        ////////////////////////////
        y += dirY * speed * elapsed;
        ////////////////////////////
        setY(y);
    }

}