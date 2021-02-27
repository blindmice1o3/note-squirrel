package com.jackingaming.notesquirrel.sandbox.passingthrough;

import android.util.Log;
import android.view.MotionEvent;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.buttonpad.ButtonPadFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.directionpad.DirectionPadFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.views.MySurfaceView;

import java.util.HashMap;
import java.util.Map;

public class InputManager
        implements MySurfaceView.MySurfaceViewTouchListener,
        DirectionPadFragment.DirectionPadListener,
        ButtonPadFragment.ButtonPadListener {

    public enum Button { UP, DOWN, LEFT, RIGHT, CENTER, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT,
        MENU, A, B, BUTTONHOLDER_A, BUTTONHOLDER_B;}

    private Map<Button, Boolean> pressing;
    private Map<Button, Boolean> justPressed;
    private Map<Button, Boolean> cantPress;
    private boolean pressingViewport, justPressedViewport, cantPressViewport;

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
        // TO LIMIT TO KEY-JUST-PRESSED (DirectionPad, ButtonPad, and ButtonHolders)
        for (Button button : InputManager.Button.values()) {
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

        // TO LIMIT TO KEY-JUST-PRESSED (MySurfaceView)
        if (cantPressViewport && !pressingViewport) {
            cantPressViewport = false;
        } else if (justPressedViewport) {
            cantPressViewport = true;
            justPressedViewport = false;
        }
        if (!cantPressViewport && pressingViewport) {
            justPressedViewport = true;
        }
    }

    public boolean isJustPressed(Button button) {
        return justPressed.get(button);
    }

    public boolean isPressing(Button button) {
        return pressing.get(button);
    }

    public boolean isJustPressedViewport() {
        return justPressedViewport;
    }

    public boolean isPressingViewport() {
        return pressingViewport;
    }

    @Override
    public boolean onMySurfaceViewTouched(MySurfaceView mySurfaceView, MotionEvent event) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onMySurfaceViewTouched(MySurfaceView mySurfaceView, MotionEvent event)");

        pressingViewport = true;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingViewport = false;
        }

//        float xLowerBound = mySurfaceView.getWidth() / 3f;
//        float xUpperBound = 2 * xLowerBound;
//        float yLowerBound = mySurfaceView.getHeight() / 3f;
//        float yUpperBound = 2 * yLowerBound;
//
//        // horizontal first-third
//        if (0 <= event.getX() && (event.getX() < xLowerBound)) {
//            // vertical (center-third)
//            if ((yLowerBound <= event.getY()) && (event.getY() < yUpperBound)) {
//                this.pressing.put(InputManager.Button.LEFT, pressing);
//                return true;
//            }
//        }
//        // horizontal center-third
//        else if ((xLowerBound <= event.getX()) && (event.getX() < xUpperBound)) {
//            // vertical (first-third)
//            if ((0 <= event.getY()) && (event.getY() < yLowerBound)) {
//                this.pressing.put(InputManager.Button.UP, pressing);
//                return true;
//            }
//            // vertical (last-third)
//            else if (yUpperBound <= event.getY()) {
//                this.pressing.put(InputManager.Button.DOWN, pressing);
//                return true;
//            }
//        }
//        // horizontal last-third
//        else if (xUpperBound <= event.getX()) {
//            // vertical (center-third)
//            if ((yLowerBound <= event.getY()) && (event.getY() < yUpperBound)) {
//                this.pressing.put(InputManager.Button.RIGHT, pressing);
//                return true;
//            }
//        }
//
        return pressingViewport;
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