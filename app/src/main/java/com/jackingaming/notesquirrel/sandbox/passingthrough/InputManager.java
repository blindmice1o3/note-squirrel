package com.jackingaming.notesquirrel.sandbox.passingthrough;

import android.view.MotionEvent;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.buttonpad.ButtonPadFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.directionpad.DirectionPadFragment;

import java.util.HashMap;
import java.util.Map;

public class InputManager
        implements DirectionPadFragment.DirectionPadTouchListener,
        ButtonPadFragment.ButtonPadTouchListener {
    public interface MenuButtonEventListener {
        void onMenuButtonJustPressed();
    }
    private MenuButtonEventListener menuButtonEventListener;
    public void setMenuButtonEventListener(MenuButtonEventListener menuButtonEventListener) {
        this.menuButtonEventListener = menuButtonEventListener;
    }

    public enum Button { UP, DOWN, LEFT, RIGHT, CENTER, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT,
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
        for (Button button : InputManager.Button.values()) {
            pressing.put(button, false);
        }
    }

    private void initJustPressed() {
        justPressed = new HashMap<Button, Boolean>();
        for (Button button : InputManager.Button.values()) {
            justPressed.put(button, false);
        }
    }

    private void initCantPress() {
        cantPress = new HashMap<Button, Boolean>();
        for (Button button : InputManager.Button.values()) {
            cantPress.put(button, false);
        }
    }

    public void update(long elapsed) {
        // TO LIMIT TO KEY-JUST-PRESSED
        for (Button button : InputManager.Button.values()) {
            if (cantPress.get(button) && !pressing.get(button)) {
                cantPress.put(button, false);
            } else if (justPressed.get(button)) {
                cantPress.put(button, true);
                justPressed.put(button, false);
            }
            if (!cantPress.get(button) && pressing.get(button)) {
                justPressed.put(button, true);

                if (button == Button.MENU) {
                    menuButtonEventListener.onMenuButtonJustPressed();
                }
            }
        }
    }

    public boolean isJustPressed(Button button) {
        return justPressed.get(button);
    }

    public boolean isPressing(Button button) {
        return pressing.get(button);
    }

    @Override
    public void onButtonPadTouched(ButtonPadFragment.Button buttonButtonPad, MotionEvent event) {
        // RESET ALL TO FALSE (ONLY do this for buttons from ButtonPadFragment)
        for (Button button : InputManager.Button.values()) {
            if ((button == Button.MENU) || (button == Button.A) || (button == Button.B)) {
                this.pressing.put(button, false);
            }
        }

        boolean pressing = true;

        // ACTION_UP means a "button" was released, and is NOT a "button" press.
        if (event.getAction() == MotionEvent.ACTION_UP) {
            pressing = false;
        }

        switch (buttonButtonPad) {
            case BUTTON_MENU:
                this.pressing.put(InputManager.Button.MENU, pressing);
                break;
            case BUTTON_A:
                this.pressing.put(InputManager.Button.A, pressing);
                break;
            case BUTTON_B:
                this.pressing.put(InputManager.Button.B, pressing);
                break;
        }
    }

    private boolean pressingDirectionPad;
    public boolean isPressingDirectionPad() {
        return pressingDirectionPad;
    }
    @Override
    public void onDirectionPadTouched(DirectionPadFragment.Button buttonDirectionPad, MotionEvent event) {
        // RESET ALL TO FALSE (ONLY do this for buttons from DirectionPadFragment)
        for (Button button : InputManager.Button.values()) {
            if ((button == Button.UP) || (button == Button.DOWN) ||
                    (button == Button.LEFT) || (button == Button.RIGHT) ||
                    (button == Button.CENTER) ||
                    (button == Button.UPLEFT) || (button == Button.UPRIGHT) ||
                    (button == Button.DOWNLEFT) || (button == Button.DOWNRIGHT)) {
                this.pressing.put(button, false);
            }
        }

        boolean pressing = true;

        // ACTION_UP means a "button" was released, and is NOT a "button" press.
        if (event.getAction() == MotionEvent.ACTION_UP) {
            pressing = false;
        }

        pressingDirectionPad = pressing;

        switch (buttonDirectionPad) {
            case UP:
                this.pressing.put(InputManager.Button.UP, pressing);
                break;
            case DOWN:
                this.pressing.put(InputManager.Button.DOWN, pressing);
                break;
            case LEFT:
                this.pressing.put(InputManager.Button.LEFT, pressing);
                break;
            case RIGHT:
                this.pressing.put(InputManager.Button.RIGHT, pressing);
                break;
            case CENTER:
                this.pressing.put(InputManager.Button.CENTER, pressing);
                break;
            case UP_LEFT:
                this.pressing.put(InputManager.Button.UPLEFT, pressing);
                break;
            case UP_RIGHT:
                this.pressing.put(InputManager.Button.UPRIGHT, pressing);
                break;
            case DOWN_LEFT:
                this.pressing.put(InputManager.Button.DOWNLEFT, pressing);
                break;
            case DOWN_RIGHT:
                this.pressing.put(InputManager.Button.DOWNRIGHT, pressing);
                break;
        }
    }
}