package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItemManager
        implements Serializable {
    transient private Game game;
    private List<MenuStateImplEvo.MenuItem> menuItemStack;

    public MenuItemManager() {
        menuItemStack = new ArrayList<MenuStateImplEvo.MenuItem>();
        menuItemStack.add(MenuItemInitial.getInstance());
    }

    public void init(Game game) {
        this.game = game;

        MenuItemInitial.getInstance().init(game);
        MenuItemEvolution.getInstance().init(game);
        MenuItemConfirmation.getInstance().init(game);
        MenuItemCapability.getInstance().init(game);
        MenuItemRecordOfEvolution.getInstance().init(game);
    }

    public void update(long elapsed) {
        getCurrentMenuItem().update(elapsed);
    }

    public void render(Canvas canvas) {
        getCurrentMenuItem().render(canvas);
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
    public void pushMenuItemConfirmation() {
        menuItemStack.add(MenuItemConfirmation.getInstance());
    }
    public void pushMenuItemCapability() {
        menuItemStack.add(MenuItemCapability.getInstance());
    }
    public void pushMenuItemRecordOfEvolution() {
        menuItemStack.add(MenuItemRecordOfEvolution.getInstance());
    }
    public MenuStateImplEvo.MenuItem getCurrentMenuItem() {
        int indexOfTop = menuItemStack.size() - 1;
        return menuItemStack.get(indexOfTop);
    }
}