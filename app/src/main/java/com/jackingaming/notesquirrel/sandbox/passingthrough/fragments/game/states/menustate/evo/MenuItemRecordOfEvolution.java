package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.State;

public class MenuItemRecordOfEvolution implements State {
    private static MenuItemRecordOfEvolution uniqueInstance;

    private MenuItemRecordOfEvolution() {
    }

    public static MenuItemRecordOfEvolution getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuItemRecordOfEvolution();
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