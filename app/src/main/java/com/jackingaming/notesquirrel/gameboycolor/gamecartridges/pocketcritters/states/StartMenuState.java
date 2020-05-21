package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

public class StartMenuState
        implements State {

    public enum MenuItem { CRITTER_DEX, BELT_LIST, BACKPACK_LIST, LOAD, SAVE, OPTION, EXIT; }

    private Handler handler;

    private Context context;
    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthViewport;
    private int heightViewport;

    private SceneManager sceneManager;

    private int indexMenu;

    public StartMenuState(Handler handler) {
        this.handler = handler;
        indexMenu = 0;

        context = handler.getGameCartridge().getContext();
        holder = ((PocketCrittersCartridge)handler.getGameCartridge()).getHolder();
        inputManager = ((PocketCrittersCartridge)handler.getGameCartridge()).getInputManager();
        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();
        sceneManager = handler.getGameCartridge().getSceneManager();
    }

    @Override
    public void getInputViewport() {
        //left
        if (inputManager.isLeftViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU left-button-justPressed");
        }
        //right
        else if (inputManager.isRightViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU right-button-justPressed");
        }
        //up
        else if (inputManager.isUpViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU up-button-justPressed");
            indexMenu--;
            if (indexMenu < 0) {
                indexMenu = (MenuItem.values().length-1);
            }
        }
        //down
        else if (inputManager.isDownViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU down-button-justPressed");
            indexMenu++;
            if (indexMenu > (MenuItem.values().length-1)) {
                indexMenu = 0;
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isJustPressedDirectionalPad()) {
            //up
            if (inputManager.isUpDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU up-button-pressing");
                indexMenu--;
                if (indexMenu < 0) {
                    indexMenu = (MenuItem.values().length - 1);
                }
            }
            //down
            else if (inputManager.isDownDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU down-button-pressing");
                indexMenu++;
                if (indexMenu > (MenuItem.values().length - 1)) {
                    indexMenu = 0;
                }
            }
            //left
            else if (inputManager.isLeftDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU left-button-pressing");
            }
            //right
            else if (inputManager.isRightDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU right-button-pressing");
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        //menu button (will toggle between State.GAME and State.START_MENU)
        if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "menu-button");

            ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().pop();
        }
        //a button
        else if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "a-button");

            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed");

            //TODO: handle menu item selected by indexMenu.
            MenuItem selectedMenuItem = MenuItem.values()[indexMenu];
            switch (selectedMenuItem) {
                case CRITTER_DEX:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed CRITTER_DEX");
                    break;
                case BELT_LIST:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed BELT_LIST");
                    break;
                case BACKPACK_LIST:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed BACKPACK_LIST");
                    break;
                case LOAD:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed LOAD");
                    break;
                case SAVE:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed SAVE");
                    break;
                case OPTION:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed OPTION");
                    break;
                case EXIT:
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed EXIT");
                    ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().pop();
                    break;
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "b-button");

            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU b-button-justPressed");
            ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().pop();
        }
    }

    @Override
    public void update(long elapsed) {
        //TODO:
    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            //TODO: 2020_05_19 PocketCrittersCartridge.render(Canvas) state == State.START_MENU

            //RE-DRAW state.GAME as background (otherwise white background because we'd cleared earlier)
            sceneManager.getCurrentScene().render(canvas);

            //DRAW BACKGROUND
            Bitmap startMenuState = Assets.cropStartMenuState(context.getResources());
            int scaleFactor = 6;

            Rect srcBoundsBackground = new Rect(0,
                    0,
                    startMenuState.getWidth(),
                    startMenuState.getHeight());
            Rect dstBoundsBackground = new Rect((widthViewport - (scaleFactor * startMenuState.getWidth())),
                    ((heightViewport / 2) - (scaleFactor * (startMenuState.getHeight() / 2))),
                    widthViewport,
                    (heightViewport / 2) + (scaleFactor * (startMenuState.getHeight() / 2)));
            canvas.drawBitmap(startMenuState, srcBoundsBackground, dstBoundsBackground, null);

            //DRAW CURSOR
            Bitmap startMenuStateCursor = Assets.cropStartMenuStateCursor(context.getResources());

            Rect srcBoundsCursor = new Rect(0,
                    0,
                    startMenuStateCursor.getWidth(),
                    startMenuStateCursor.getHeight());
            int xOffset = 5 * scaleFactor;
            int yOffsetInitial = 13 * scaleFactor;
            ////////////////////////////////////////////////////////////////
            int yOffset = yOffsetInitial + (indexMenu * (16 * scaleFactor));
            ////////////////////////////////////////////////////////////////
            Rect dstBoundsCursor = new Rect((widthViewport - (scaleFactor * startMenuState.getWidth()) + xOffset),
                    ((heightViewport / 2) - (scaleFactor * (startMenuState.getHeight() / 2)) + yOffset),
                    (widthViewport - (scaleFactor * startMenuState.getWidth()) + xOffset) + (scaleFactor * startMenuStateCursor.getWidth()),
                    ((heightViewport / 2) - (scaleFactor * (startMenuState.getHeight() / 2)) + yOffset) + (scaleFactor * startMenuStateCursor.getHeight()));
            canvas.drawBitmap(startMenuStateCursor, srcBoundsCursor, dstBoundsCursor, null);


            //FILL IN BLANK OF BACKGROUND IMAGE
            Paint textPaint = new Paint();
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(64);
            int xOffsetLoad = xOffset + (startMenuStateCursor.getWidth() * scaleFactor) + (1 * scaleFactor);
            int yOffsetLoad = yOffsetInitial + (startMenuStateCursor.getHeight() * scaleFactor) + (MenuItem.LOAD.ordinal() * (16 * scaleFactor));
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            canvas.drawText("LOAD",
                    (widthViewport - (scaleFactor * startMenuState.getWidth()) + xOffsetLoad),
                    ((heightViewport / 2) - (scaleFactor * (startMenuState.getHeight() / 2)) + yOffsetLoad),
                    textPaint);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////


            //TODO: DRAW SECOND CURSOR (temporary to figure out yOffset).
            /*
            //////////////////////////////
            yOffset += (16 * scaleFactor);
            //////////////////////////////
            Rect dstBoundsCursor2 = new Rect( (widthViewport-(scaleFactor*startMenuState.getWidth()) + xOffset),
                    ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2)) + yOffset),
                    (widthViewport-(scaleFactor*startMenuState.getWidth()) + xOffset) + (scaleFactor*startMenuStateCursor.getWidth()),
                    ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2)) + yOffset) + (scaleFactor*startMenuStateCursor.getHeight()) );
            canvas.drawBitmap(startMenuStateCursor, srcBoundsCursor, dstBoundsCursor2, null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            */

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void savePresentState() {
        //TODO:
    }

    @Override
    public void loadSavedState() {
        //TODO:
    }

}