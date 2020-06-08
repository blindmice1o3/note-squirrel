package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.SerializationDoer;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.items.BackpackActivity;
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

        context = handler.getGameCartridge().getContext();
        holder = ((PocketCrittersCartridge)handler.getGameCartridge()).getHolder();
        inputManager = ((PocketCrittersCartridge)handler.getGameCartridge()).getInputManager();
        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();

        sceneManager = handler.getGameCartridge().getSceneManager();

        indexMenu = 0;
    }

    @Override
    public void getInputViewport() {
        //left
        if (inputManager.isLeftViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputViewport() left-button-justPressed");
        }
        //right
        else if (inputManager.isRightViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputViewport() right-button-justPressed");
        }
        //up
        else if (inputManager.isUpViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputViewport() up-button-justPressed");
            indexMenu--;
            if (indexMenu < 0) {
                indexMenu = (MenuItem.values().length-1);
            }
        }
        //down
        else if (inputManager.isDownViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputViewport() down-button-justPressed");
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
                Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputDirectionalPad() up-button-pressing");
                indexMenu--;
                if (indexMenu < 0) {
                    indexMenu = (MenuItem.values().length - 1);
                }
            }
            //down
            else if (inputManager.isDownDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputDirectionalPad() down-button-pressing");
                indexMenu++;
                if (indexMenu > (MenuItem.values().length - 1)) {
                    indexMenu = 0;
                }
            }
            //left
            else if (inputManager.isLeftDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputDirectionalPad() left-button-pressing");
            }
            //right
            else if (inputManager.isRightDirectionalPad()) {
                Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputDirectionalPad() right-button-pressing");
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            //TODO: handle menu item selected by indexMenu.
            MenuItem selectedMenuItem = MenuItem.values()[indexMenu];
            switch (selectedMenuItem) {
                case CRITTER_DEX:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed CRITTER_DEX");
                    break;
                case BELT_LIST:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed BELT_LIST");
                    break;
                case BACKPACK_LIST:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed BACKPACK_LIST");
                    handler.getGameCartridge().savePresentState();
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() saved present state");

                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() starting BackpackActivity for result...");
                    Intent backpackIntent = new Intent(context, BackpackActivity.class);
                    ((JackInActivity)context).startActivityForResult(backpackIntent, JackInActivity.REQUEST_CODE_BACKPACK_ACTIVITY);
                    break;
                case LOAD:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed LOAD");
                    SerializationDoer.loadReadFromFile(handler.getGameCartridge(), true);
                    break;
                case SAVE:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed SAVE");
                    SerializationDoer.saveWriteToFile(handler.getGameCartridge(), true);
                    break;
                case OPTION:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed OPTION");
                    break;
                case EXIT:
                    Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() a-button-justPressed EXIT");
                    ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().pop();
                    break;
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() b-button-justPressed");
            ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().pop();
        }
        //menu button (pop State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "StartMenuState.getInputButtonPad() menu-button-justPressed");
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

}