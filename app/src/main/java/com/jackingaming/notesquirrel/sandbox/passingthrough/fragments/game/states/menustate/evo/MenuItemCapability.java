package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MenuItemCapability
        implements MenuStateImplEvo.MenuItem {
    private static MenuItemCapability uniqueInstance;

    private Game game;

    private MenuItemCapability() {
    }

    public static MenuItemCapability getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuItemCapability();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;
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