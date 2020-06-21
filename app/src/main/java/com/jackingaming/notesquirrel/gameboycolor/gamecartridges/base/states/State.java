package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;

public interface State {

    public enum Id { GAME, START_MENU, TEXTBOX; }

    public void getInputViewport();

    public void getInputDirectionalPad();

    public void getInputButtonPad();

    public void update(long elapsed);

    public void render();

    public void enter(Object[] args);

    public void exit();

    public Id getId();

    public void setHandler(Handler handler);

}