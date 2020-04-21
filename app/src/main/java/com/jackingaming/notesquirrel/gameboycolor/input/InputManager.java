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



    private boolean pressingButtonPad;



    private MotionEvent event;
    private DirectionalPadFragment.Direction inputDirection;
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
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingScreen = false;
        }
        //////////////////////////////////////////////////////////

        //TODO: on press set to what's below... on de-press set to null.
        if (pressingScreen) {
            ///////////////////
            this.event = event;
            ///////////////////
        } else {
            this.event = null;
        }

        // Should return true if you've handled the touch event. If false gets returned,
        // will NOT check for drag event (can't drag without touch).
        return true;
    }

    @Override
    public void onDirectionalPadTouched(DirectionalPadFragment.Direction inputDirection, MotionEvent event) {
        Log.d(MainActivity.DEBUG_TAG, getClass().toString() + ".onDirectionalPadTouched(DirectionalPadFragment.Direction, MotionEvent)");
        //////////////////////////////////////////////////////////
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressingDirectionalPad = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingDirectionalPad = false;
        }
        //////////////////////////////////////////////////////////

        //TODO: on press set to what's below... on de-press set to null.
        if (pressingDirectionalPad) {
            ///////////////////////////
            this.inputDirection = inputDirection;
            ///////////////////////////
        } else {
            this.inputDirection = null;
        }

        //TODO:
    }

    @Override
    public void onButtonPadTouched(ButtonPadFragment.InputButton inputButton, MotionEvent event) {
        Log.d(MainActivity.DEBUG_TAG, getClass().toString() + ".onButtonPadTouched(ButtonPadFragment.InputButton, MotionEvent)");
        //////////////////////////////////////////////////////////
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressingButtonPad = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingButtonPad = false;
        }
        //////////////////////////////////////////////////////////

        //TODO: on press set to what's below... on de-press set to null.
        if (pressingButtonPad) {
            ///////////////////////////////
            this.inputButton = inputButton;
            ///////////////////////////////
        } else {
            this.inputButton = null;
        }

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

    public MotionEvent getEvent() {
        return event;
    }

    public DirectionalPadFragment.Direction getInputDirection() {
        return inputDirection;
    }

    public ButtonPadFragment.InputButton getInputButton() {
        return inputButton;
    }

}
