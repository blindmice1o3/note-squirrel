package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game;

import android.util.Log;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.buttonpad.ButtonPadFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.directionpad.DirectionPadFragment;

import java.util.HashMap;
import java.util.Map;

public class InputManager
        implements DirectionPadFragment.TouchListener,
        ButtonPadFragment.TouchListener {
    public enum Button { UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT,
        MENU, A, B, BUTTONHOLDER_A, BUTTONHOLDER_B;}

    private Map<Button, Boolean> pressing;
    private Map<Button, Boolean> justPressed;
    private Map<Button, Boolean> cantPress;

    public InputManager() {
        initPressing();
        initJustPressed();
        initCantPress();
    }

    private void initPressing() {
        pressing = new HashMap<Button, Boolean>();
        for (Button button : Button.values()) {
            pressing.put(button, false);
        }
    }

    private void initJustPressed() {
        justPressed = new HashMap<Button, Boolean>();
        for (Button button : Button.values()) {
            justPressed.put(button, false);
        }
    }

    private void initCantPress() {
        cantPress = new HashMap<Button, Boolean>();
        for (Button button : Button.values()) {
            cantPress.put(button, false);
        }
    }

    public void update(long elapsed) {
        // TO LIMIT TO KEY-JUST-PRESSED
        for (Button button : Button.values()) {
            if (cantPress.get(button) && !pressing.get(button)) {
                cantPress.put(button, false);
            } else if (justPressed.get(button)) {
                cantPress.put(button, true);
                justPressed.put(button, false);
            }
            if (!cantPress.get(button) && pressing.get(button)) {
                justPressed.put(button, true);
            }
        }
    }

    public boolean isJustPressed(Button button) {
        return justPressed.get(button);
    }

    public boolean isPressing(Button button) {
        return pressing.get(button);
    }

    // TODO: fix to interpret ALL presses... not just pressed.
    @Override
    public void onButtonPadJustPressed(ButtonPadFragment.Button button) {
        switch (button) {
            case BUTTON_MENU:
                pressing.put(Button.MENU, true);
                break;
            case BUTTON_A:
                pressing.put(Button.A, true);
                break;
            case BUTTON_B:
                pressing.put(Button.B, true);
                break;
        }
    }

    @Override
    public void onDirectionPadTouch(DirectionPadFragment.Direction direction, boolean pressing) {
        for (Button button : Button.values()) {
            this.pressing.put(button, false);
        }

        switch (direction) {
            case UP:
                this.pressing.put(Button.UP, pressing);
                break;
            case DOWN:
                this.pressing.put(Button.DOWN, pressing);
                break;
            case LEFT:
                this.pressing.put(Button.LEFT, pressing);
                break;
            case RIGHT:
                this.pressing.put(Button.RIGHT, pressing);
                break;
            case UP_LEFT:
                this.pressing.put(Button.UPLEFT, pressing);
                break;
            case UP_RIGHT:
                this.pressing.put(Button.UPRIGHT, pressing);
                break;
            case DOWN_LEFT:
                this.pressing.put(Button.DOWNLEFT, pressing);
                break;
            case DOWN_RIGHT:
                this.pressing.put(Button.DOWNRIGHT, pressing);
                break;
        }
    }
}