package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game;

import android.graphics.Bitmap;

public class Animation {

    private int index;
    private Bitmap[] frames;

    private int speed;
    private long timer;

    public Animation(Bitmap[] frames, int speed) {
        this.frames = frames;
        this.speed = speed;
        index = 0;
        timer = 0;
    }

    public void update(long elapsed) {
        timer += elapsed;

        if (timer > speed) {
            index++;
            if (index >= frames.length) {
                index = 0;
            }

            timer = 0;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Bitmap getCurrentFrame() {
        return frames[index];
    }

}