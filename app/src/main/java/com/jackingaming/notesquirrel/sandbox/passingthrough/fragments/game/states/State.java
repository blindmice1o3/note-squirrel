package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public interface State {
    void init(Game game);
    void enter(Object[] args);
    void exit();

    void update(long elapsed);
    void render(Canvas canvas);
}