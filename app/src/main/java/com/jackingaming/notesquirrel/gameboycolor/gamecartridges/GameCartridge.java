package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.view.MotionEvent;

import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;

public interface GameCartridge {
    public void init();
    public void onScreenInput(MotionEvent event);
    public void onDirectionalPadInput(DirectionalPadFragment.Direction direction);
    public void onButtonPadInput(ButtonPadFragment.InputButton inputButton);
    public void update(long elapsed);
    public void render();
}
