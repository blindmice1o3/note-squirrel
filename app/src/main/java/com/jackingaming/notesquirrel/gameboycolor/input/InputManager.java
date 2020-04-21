package com.jackingaming.notesquirrel.gameboycolor.input;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jackingaming.notesquirrel.MainActivity;

public class InputManager
        implements View.OnTouchListener,
        DirectionalPadFragment.OnDirectionalPadTouchListener,
        ButtonPadFragment.OnButtonPadTouchListener {

    private boolean pressingScreen;
    private boolean justPressedScreen;
    private boolean cantPressScreen;

    private boolean pressingDirectionalPad;
    public boolean upDirectionalPad;
    public boolean downDirectionalPad;
    public boolean leftDirectionalPad;
    public boolean rightDirectionalPad;

    private boolean pressingButtonPad;
    private boolean menuButton;
    private boolean aButton;
    private boolean bButton;

    private MotionEvent event;
    //private DirectionalPadFragment.Direction inputDirection;
    private ButtonPadFragment.InputButton inputButton;

    public InputManager() {

    }

    public void update() {
        //////////////////////////////////////////////////////////
        if (cantPressScreen && !pressingScreen) {
            cantPressScreen = false;
        } else if (justPressedScreen) {
            cantPressScreen = true;
            justPressedScreen = false;
        }
        if (!cantPressScreen && pressingScreen) {
            justPressedScreen = true;
        }
        //////////////////////////////////////////////////////////
    }

    /**
     * System's callback method when the user triggers a touch event
     * from the viewport.
     *
     * @param event The touch event's meta-data (e.g. x and y position
     *              of the user triggered touch event)
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(MainActivity.DEBUG_TAG, getClass().toString() + ".onTouch(View, MotionEvent)");
        Log.d(MainActivity.DEBUG_TAG, getClass().toString() + ".onTouch(View, MotionEvent)... View v == " + v.toString());

        //////////////////////////////////////////////////////////
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressingScreen = true;
            this.event = event;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingScreen = false;
            this.event = null;
        }
        //////////////////////////////////////////////////////////

        // Should return true if you've handled the touch event. If false gets returned,
        // will NOT check for drag event (can't drag without touch).
        return true;
    }

    @Override
    public void onDirectionalPadTouched(DirectionalPadFragment.Direction inputDirection, boolean isPressed) {
        Log.d(MainActivity.DEBUG_TAG, getClass().toString() + ".onDirectionalPadTouched(DirectionalPadFragment.Direction, MotionEvent)");

        ///////////////////////////////////
        pressingDirectionalPad = isPressed;
        ///////////////////////////////////

        ////////////////////////////
        upDirectionalPad = false;
        downDirectionalPad = false;
        leftDirectionalPad = false;
        rightDirectionalPad = false;
        ////////////////////////////

        if (inputDirection == DirectionalPadFragment.Direction.UP) {
            upDirectionalPad = isPressed;
        } else if (inputDirection == DirectionalPadFragment.Direction.DOWN) {
            downDirectionalPad = isPressed;
        } else if (inputDirection == DirectionalPadFragment.Direction.LEFT) {
            leftDirectionalPad = isPressed;
        } else if (inputDirection == DirectionalPadFragment.Direction.RIGHT) {
            rightDirectionalPad = isPressed;
        }

        //TODO:
    }

    @Override
    public void onButtonPadTouched(ButtonPadFragment.InputButton inputButton, MotionEvent event) {
        Log.d(MainActivity.DEBUG_TAG, getClass().toString() + ".onButtonPadTouched(ButtonPadFragment.InputButton, MotionEvent)");
        //////////////////////////////////////////////////////////
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressingButtonPad = true;
            this.inputButton = inputButton;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingButtonPad = false;
            this.inputButton = null;
        }
        //////////////////////////////////////////////////////////

        //TODO:
    }

    public boolean isPressingScreen() {
        return pressingScreen;
    }

    public boolean isJustPressedScreen() {
        return justPressedScreen;
    }

    public boolean isCantPressScreen() {
        return cantPressScreen;
    }

    public boolean isPressingButtonPad() {
        return pressingButtonPad;
    }

    public MotionEvent getEvent() {
        return event;
    }

    public boolean isPressingDirectionalPad() {
        return pressingDirectionalPad;
    }

    public ButtonPadFragment.InputButton getInputButton() {
        return inputButton;
    }

}
