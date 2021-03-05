package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MenuStateImplEvoCapability
        implements MenuStateImplEvo.MenuItem {
    private static MenuStateImplEvoCapability uniqueInstance;

    private Game game;

    private MenuStateImplEvoCapability() {
    }

    public static MenuStateImplEvoCapability getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuStateImplEvoCapability();
        }
        return uniqueInstance;
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