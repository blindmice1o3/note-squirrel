package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface GameCartridge {
    public void init(SurfaceHolder holder, int sideSquareScreen, InputManager inputManager);
    public void savePresentState();
    public void loadSavedState();
    public void getInputViewport();
    public void getInputDirectionalPad();
    public void getInputButtonPad();
    public void update(long elapsed);
    public void render();
}
