package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import android.graphics.Canvas;

public interface State {

    public enum Id { GAME, START_MENU, TEXTBOX; }

    public void getInputViewport();

    public void getInputDirectionalPad();

    public void getInputButtonPad();

    public void getInputSelectButton();

    public void getInputStartButton();

    public void update(long elapsed);

    public void render(Canvas canvas);

    public void enter(Object[] args);

    public void exit();

    public Id getId();

}