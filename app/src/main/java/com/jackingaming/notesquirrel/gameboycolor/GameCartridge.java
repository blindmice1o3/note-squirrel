package com.jackingaming.notesquirrel.gameboycolor;

import android.view.MotionEvent;

import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

public interface GameCartridge {
    public void init();
    public void getScreenInput(MotionEvent event);
    public void getDirectionalPadInput(DirectionalPadFragment.Direction direction);
    public void update(long elapsed);
    public void render();
}
