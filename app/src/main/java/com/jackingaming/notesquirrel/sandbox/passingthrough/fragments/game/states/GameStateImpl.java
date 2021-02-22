package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;

public class GameStateImpl
        implements State {

    private Game game;

    public GameStateImpl() {

    }

    public void init(Game game) {
        this.game = game;
    }

    @Override
    public void enter() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(long elapsed) {
        game.getTimeManager().update(elapsed);
        game.getSceneManager().update(elapsed);
        GameCamera.getInstance().update(elapsed);
    }

    @Override
    public void render(Canvas canvas) {
        game.getSceneManager().drawCurrentFrame(canvas);
    }
}