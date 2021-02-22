package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states;

import android.graphics.Canvas;

public interface State {
    void enter();
    void exit();

    void update(long elapsed);
    void render(Canvas canvas);
}