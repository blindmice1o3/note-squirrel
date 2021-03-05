package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.State;

public class MenuStateImplEvoEvolution
        implements State {
    private static MenuStateImplEvoEvolution uniqueInstance;

    private Game game;

    private Bitmap imageBackground;

    private MenuStateImplEvoEvolution() {
    }

    public static MenuStateImplEvoEvolution getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuStateImplEvoEvolution();
        }
        return uniqueInstance;
    }

    public void init(Game game) {
        this.game = game;

        initImage(game.getContext().getResources());
    }

    private void initImage(Resources resources) {

    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {

    }
}
