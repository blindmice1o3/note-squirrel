package com.jackingaming.notesquirrel.gameboycolor;

import android.view.MotionEvent;

public interface GameCartridge {
    public void init();
    public void getInput(MotionEvent event);
    public void update(long elapsed);
    public void render();
}
