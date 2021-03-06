package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MenuItemEvolution
        implements MenuStateImplEvo.MenuItem {
    private static MenuItemEvolution uniqueInstance;

    private Game game;

    private Bitmap imageBackground;

    private MenuItemEvolution() {
    }

    public static MenuItemEvolution getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuItemEvolution();
        }
        return uniqueInstance;
    }

    @Override
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
