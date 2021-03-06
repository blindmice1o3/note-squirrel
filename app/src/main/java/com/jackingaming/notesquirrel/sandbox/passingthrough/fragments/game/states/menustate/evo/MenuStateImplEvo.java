package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.MenuStateImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuStateImplEvo extends MenuStateImpl {
    interface MenuItem extends Serializable {
        void init(Game game);
        void enter(Object[] args);
        void exit();
        void update(long elapsed);
        void render(Canvas canvas);
        String getName();
    }
    private static MenuStateImplEvo uniqueInstance;
    transient private Game game;

    private List<MenuItem> menuItemStack;

    private MenuStateImplEvo() {
        menuItemStack = new ArrayList<MenuItem>();
        menuItemStack.add(MenuItemInitial.getInstance());
    }
    public void popMenuItemStack() {
        if (menuItemStack.size() > 1) {
            int indexOfTop = menuItemStack.size() - 1;
            menuItemStack.remove(indexOfTop);
        }
    }
    public void pushMenuItemEvolution() {
        menuItemStack.add(MenuItemEvolution.getInstance());
    }
    public void pushMenuItemCapability() {
        menuItemStack.add(MenuItemCapability.getInstance());
    }
    public void pushMenuItemRecordOfEvolution() {
        menuItemStack.add(MenuItemRecordOfEvolution.getInstance());
    }
    public MenuItem getCurrentMenuItem() {
        int indexOfTop = menuItemStack.size() - 1;
        return menuItemStack.get(indexOfTop);
    }

    public static MenuStateImplEvo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuStateImplEvo();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        for (MenuItem menuItem : menuItemStack) {
            menuItem.init(game);
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(long elapsed) {
        getCurrentMenuItem().update(elapsed);
    }

    @Override
    public void render(Canvas canvas) {
        getCurrentMenuItem().render(canvas);
    }
}