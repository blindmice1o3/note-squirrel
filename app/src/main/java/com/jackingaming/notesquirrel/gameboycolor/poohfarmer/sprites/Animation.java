package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites;

import android.graphics.Bitmap;

public class Animation {

    private int index;
    private int speed;
    private Bitmap[] frames;

    private long lastTime;
    private long timer;

    public Animation(int speed, Bitmap[] frames) {
        index = 0;
        this.speed = speed;
        this.frames = frames;

        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    public void update() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;

            if (index >= frames.length) {
                index = 0;
            }
        }
    }

    public Bitmap getCurrentFrame() {
        return frames[index];
    }

}