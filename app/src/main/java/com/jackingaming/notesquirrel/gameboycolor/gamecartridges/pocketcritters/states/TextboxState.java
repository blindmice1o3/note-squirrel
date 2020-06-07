package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class TextboxState
        implements State {

    private Handler handler;

    private Context context;
    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthViewport;
    private int heightViewport;

    private SceneManager sceneManager;
    private Paint paintBackground;
    private Paint paintText;

    public TextboxState(Handler handler) {
        this.handler = handler;

        context = handler.getGameCartridge().getContext();
        holder = ((PocketCrittersCartridge)handler.getGameCartridge()).getHolder();
        inputManager = ((PocketCrittersCartridge)handler.getGameCartridge()).getInputManager();
        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();

        sceneManager = handler.getGameCartridge().getSceneManager();

        paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.BLUE);

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(Color.YELLOW);
        paintText.setTextSize(64);
        paintText.setTypeface(Typeface.SANS_SERIF);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void getInputViewport() {
        //up
        if (inputManager.isUpViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputViewport() up-button-justPressed");
        }
        //down
        else if (inputManager.isDownViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputViewport() down-button-justPressed");
        }
        //left
        else if (inputManager.isLeftViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputViewport() left-button-justPressed");
        }
        //right
        else if (inputManager.isRightViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputViewport() right-button-justPressed");
        }
    }

    @Override
    public void getInputDirectionalPad() {
        //up
        if (inputManager.isUpDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputDirectionalPad() up-button-pressing");
        }
        //down
        else if (inputManager.isDownDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputDirectionalPad() down-button-pressing");
        }
        //left
        else if (inputManager.isLeftDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputDirectionalPad() left-button-pressing");
        }
        //right
        else if (inputManager.isRightDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputDirectionalPad() right-button-pressing");
        }
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputButtonPad() a-button-justPressed");
        }
        //b button (pop State.TEXTBOX)
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputButtonPad() b-button-justPressed");

            ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().pop();
        }
        //menu button
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputButtonPad() menu-button-justPressed");
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

            //RE-DRAW state.GAME as background (otherwise white background because we'd cleared earlier)
            sceneManager.getCurrentScene().render(canvas);



            //DRAW BACKGROUND
            int xStartBackground = 0;
            int yStartBackground = (int)((2/3f) * heightViewport);
            Rect background = new Rect(xStartBackground, yStartBackground, widthViewport, heightViewport);
            canvas.drawRect(background, paintBackground);

            //DRAW TEXT
            int padding = 10;
            Paint.FontMetrics fm = paintText.getFontMetrics();
            int lineHeight = (int) (fm.bottom - fm.top); // maybe "+ fm.leading"

            int xStartText = xStartBackground + padding;
            int yStartText = yStartBackground + lineHeight; //normally starts top-left
            String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
            canvas.drawText(text, xStartText, yStartText, paintText);



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