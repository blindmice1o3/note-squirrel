package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

public interface GameCartridge {
    public void init(SurfaceHolder holder, int sideSquareScreen);
    public void savePresentState();
    public void loadSavedState();
    public void onScreenInput(MotionEvent event);
    public void onDirectionalPadInput(DirectionalPadFragment.Direction direction);
    public void onButtonPadInput(ButtonPadFragment.InputButton inputButton);
    public void update(long elapsed);
    public void render();
}
