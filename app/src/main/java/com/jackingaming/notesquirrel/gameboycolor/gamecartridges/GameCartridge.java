package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface GameCartridge {

    enum Id { POOH_FARMER, PONG, POCKET_CRITTERS; }
    
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen);
    public void savePresentState();
    public void loadSavedState();
    public void getInputViewport();
    public void getInputDirectionalPad();
    public void getInputButtonPad();
    public void update(long elapsed);
    public void render();
}
