package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MenuItemEvolution
        implements MenuStateImplEvo.MenuItem {
    private static MenuItemEvolution uniqueInstance;
    transient private Game game;
    private String name;

    transient private Bitmap imageBackground;

    private MenuItemEvolution() {
        name = "Evolution";
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

    @Override
    public String getName() {
        return name;
    }
}