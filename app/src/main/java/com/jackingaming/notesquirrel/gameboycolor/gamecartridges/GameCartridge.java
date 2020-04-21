package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface GameCartridge {
    public void init(SurfaceHolder holder, int sideSquareScreen, InputManager inputManager);
    public void savePresentState();
    public void loadSavedState();
    public void getInputScreen(MotionEvent event);
    public void getInputDirectionalPad();
    public void getInputButtonPad(ButtonPadFragment.InputButton inputButton);
    public void update(long elapsed);
    public void render();
}
