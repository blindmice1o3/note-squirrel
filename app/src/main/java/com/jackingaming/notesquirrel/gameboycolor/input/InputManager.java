package com.jackingaming.notesquirrel.gameboycolor.input;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jackingaming.notesquirrel.MainActivity;

public class InputManager
        implements View.OnTouchListener,
        DirectionalPadFragment.OnDirectionalPadTouchListener,
        ButtonPadFragment.OnButtonPadTouchListener {

    //VIEWPORT
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;
    private int xScreenFirstThird;
    private int xScreenSecondThird;
    private int yScreenFirstThird;
    private int yScreenSecondThird;

    private MotionEvent event;
    private boolean pressingViewport;
    private boolean justPressedViewport;
    private boolean cantPressViewport;
    private boolean upViewport;
    private boolean downViewport;
    private boolean leftViewport;
    private boolean rightViewport;



    //DIRECTIONAL_PAD
    private boolean pressingDirectionalPad;
    private boolean justPressedDirectionalPad;
    private boolean cantPressDirectionalPad;
    private boolean upDirectionalPad;
    private boolean downDirectionalPad;
    private boolean leftDirectionalPad;
    private boolean rightDirectionalPad;



    //BUTTON_PAD
    private boolean pressingButtonPad;
    private boolean justPressedButtonPad;
    private boolean cantPressButtonPad;
    private boolean menuButtonPad;
    private boolean aButtonPad;
    private boolean bButtonPad;



    public InputManager() {

    }

    public void init(int widthScreen, int heightScreen) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        xScreenFirstThird = (int)((float)widthViewport / 3);
        xScreenSecondThird = (int)(2 * ((float)widthViewport / 3));
        yScreenFirstThird = (int)((float)heightViewport / 3);
        yScreenSecondThird = (int)(2 * ((float)heightViewport / 3));
    }

    public void update() {
        //VIEWPORT
        //////////////////////////////////////////////////////////
        if (cantPressViewport && !pressingViewport) {
            cantPressViewport = false;
        } else if (justPressedViewport) {
            cantPressViewport = true;
            justPressedViewport = false;
        }
        if (!cantPressViewport && pressingViewport) {
            justPressedViewport = true;
        }
        //////////////////////////////////////////////////////////

        //DIRECTIONAL_PAD
        //////////////////////////////////////////////////////////
        if (cantPressDirectionalPad && !pressingDirectionalPad) {
            cantPressDirectionalPad = false;
        } else if (justPressedDirectionalPad) {
            cantPressDirectionalPad = true;
            justPressedDirectionalPad = false;
        }
        if (!cantPressDirectionalPad && pressingDirectionalPad) {
            justPressedDirectionalPad = true;
        }
        //////////////////////////////////////////////////////////

        //BUTTON_PAD
        //////////////////////////////////////////////////////////
        if (cantPressButtonPad && !pressingButtonPad) {
            cantPressButtonPad = false;
        } else if (justPressedButtonPad) {
            cantPressButtonPad = true;
            justPressedButtonPad = false;
        }
        if (!cantPressButtonPad && pressingButtonPad) {
            justPressedButtonPad = true;
        }
        //////////////////////////////////////////////////////////

        //RESET VIEWPORT
        if (!pressingViewport) {
            upViewport = false;
            downViewport = false;
            leftViewport = false;
            rightViewport = false;
        }

        //RESET DIRECTIONAL_PAD
        if (!pressingDirectionalPad) {
            upDirectionalPad = false;
            downDirectionalPad = false;
            leftDirectionalPad = false;
            rightDirectionalPad = false;
        }

        //RESET BUTTON_PAD
        if (!pressingButtonPad) {
            menuButtonPad = false;
            aButtonPad = false;
            bButtonPad = false;
        }
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
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onTouch(View, MotionEvent)");

        //////////////////////////////////////////////////////////
        if ( (event.getAction() == MotionEvent.ACTION_DOWN) ||
                (event.getAction() == MotionEvent.ACTION_MOVE) ) {
            pressingViewport = true;
            this.event = event;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressingViewport = false;
            this.event = null;
        }
        //////////////////////////////////////////////////////////

        //left
        if (event.getX() < xScreenFirstThird && event.getY() > yScreenFirstThird && event.getY() < yScreenSecondThird) {
            leftViewport = true;
            return true;
        }
        //right
        else if (event.getX() > xScreenSecondThird && event.getY() > yScreenFirstThird && event.getY() < yScreenSecondThird) {
            rightViewport = true;
            return true;
        }
        //up
        else if (event.getY() < yScreenFirstThird && event.getX() > xScreenFirstThird && event.getX() < xScreenSecondThird) {
            upViewport = true;
            return true;
        }
        //down
        else if (event.getY() > yScreenSecondThird && event.getX() > xScreenFirstThird && event.getX() < xScreenSecondThird) {
           downViewport = true;
            return true;
        }

//        // Should return true if you've handled the touch event. If false gets returned,
//        // will NOT check for drag event (can't drag without touch).
//        return true;

        //RETURNING FALSE so something else (ancestor) can handle the touch event (in this case,
        // JackInActivity's viewport fragment's SurfaceView's context menu).
        return false;
    }

    /*
    GOTO: DirectionalPadFragment.onCreateView()
    https://stackoverflow.com/questions/6410200/android-detect-if-user-touches-and-drags-out-of-button-region/8069887
     */
    @Override
    public void onDirectionalPadTouched(DirectionalPadFragment.Direction inputDirection, boolean isPressed) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onDirectionalPadTouched(DirectionalPadFragment.Direction, MotionEvent)");

        ///////////////////////////////////
        pressingDirectionalPad = isPressed;
        ///////////////////////////////////

        //////////////////////////////////////////////////////////////////////////
        if (inputDirection != null) {
            if (inputDirection == DirectionalPadFragment.Direction.UP) {
                upDirectionalPad = isPressed;
            } else if (inputDirection == DirectionalPadFragment.Direction.DOWN) {
                downDirectionalPad = isPressed;
            } else if (inputDirection == DirectionalPadFragment.Direction.LEFT) {
                leftDirectionalPad = isPressed;
            } else if (inputDirection == DirectionalPadFragment.Direction.RIGHT) {
                rightDirectionalPad = isPressed;
            }
        }
        //////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onButtonPadTouched(ButtonPadFragment.InputButton inputButton, boolean isPressed) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onButtonPadTouched(ButtonPadFragment.InputButton, MotionEvent)");

        //////////////////////////////
        pressingButtonPad = isPressed;
        //////////////////////////////

        ////////////////////////////////////////////////////////////////////////
        if (inputButton != null) {
            if (inputButton == ButtonPadFragment.InputButton.MENU_BUTTON) {
                menuButtonPad = isPressed;
            } else if (inputButton == ButtonPadFragment.InputButton.A_BUTTON) {
                aButtonPad = isPressed;
            } else if (inputButton == ButtonPadFragment.InputButton.B_BUTTON) {
                bButtonPad = isPressed;
            }
        }
        ////////////////////////////////////////////////////////////////////////
    }

    public MotionEvent getEvent() {
        return event;
    }

    public boolean isJustPressedViewport() {
        return justPressedViewport;
    }

    public boolean isJustPressedDirectionalPad() { return justPressedDirectionalPad; }

    public boolean isJustPressedButtonPad() { return justPressedButtonPad; }

    public boolean isUpViewport() {
        return upViewport;
    }

    public boolean isDownViewport() {
        return downViewport;
    }

    public boolean isLeftViewport() {
        return leftViewport;
    }

    public boolean isRightViewport() {
        return rightViewport;
    }

    public boolean isPressingDirectionalPad() {
        return pressingDirectionalPad;
    }

    public boolean isUpDirectionalPad() {
        return upDirectionalPad;
    }

    public boolean isDownDirectionalPad() {
        return downDirectionalPad;
    }

    public boolean isLeftDirectionalPad() {
        return leftDirectionalPad;
    }

    public boolean isRightDirectionalPad() {
        return rightDirectionalPad;
    }

    public boolean isPressingButtonPad() {
        return pressingButtonPad;
    }

    public boolean isMenuButtonPad() {
        return menuButtonPad;
    }

    public boolean isaButtonPad() {
        return aButtonPad;
    }

    public boolean isbButtonPad() {
        return bButtonPad;
    }

}