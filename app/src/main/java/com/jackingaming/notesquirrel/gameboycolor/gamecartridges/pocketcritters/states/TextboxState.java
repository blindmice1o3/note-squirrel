package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

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

import java.util.ArrayList;
import java.util.List;

public class TextboxState
        implements State {

    private Handler handler;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthViewport;
    private int heightViewport;
    private SceneManager sceneManager;

    private Page page;

    public TextboxState(Handler handler) {
        this.handler = handler;

        holder = ((PocketCrittersCartridge)handler.getGameCartridge()).getHolder();
        inputManager = ((PocketCrittersCartridge)handler.getGameCartridge()).getInputManager();
        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();
        sceneManager = handler.getGameCartridge().getSceneManager();

        initPage();
    }

    private void initPage() {
        int x0Background = 0;
        int y0Background = (int)((2/3f) * heightViewport);
        int x1Background = widthViewport;
        int y1Background = heightViewport;
        int margin = 10;

        Paint paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.BLUE);

        Paint paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(Color.YELLOW);
        paintText.setTextSize(64);
        paintText.setTypeface(Typeface.SANS_SERIF);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);

        // Complete text to display.
        //String textFull = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String textFull = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generousity cresses before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fish would like.";

        ////////////////////////////////////////////////////////////////////////
        page = new Page(x0Background, y0Background, x1Background, y1Background,
                margin, paintBackground, paintText, textFull);
        ////////////////////////////////////////////////////////////////////////
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

            //@@@RE-DRAW state.GAME as background@@@
            // (otherwise white background because we'd cleared earlier)
            sceneManager.getCurrentScene().render(canvas);

            ////////////////////
            page.render(canvas);
            ////////////////////

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

    class Page {

        private int x0Background;
        private int y0Background;
        private int x1Background;
        private int y1Background;
        private int margin;
        private Paint paintBackground;
        private Paint paintText;
        private String textFull;

        private int lineHeight;
        private int xStartText;
        private int yStartText;
        private int widthMax;
        private int heightMax;

        private List<String> lines;

        public Page(int x0Background, int y0Background, int x1Background, int y1Background,
                    int margin, Paint paintBackground, Paint paintText, String textFull) {
            this.x0Background = x0Background;
            this.y0Background = y0Background;
            this.x1Background = x1Background;
            this.y1Background = y1Background;
            this.margin = margin;
            this.paintBackground = paintBackground;
            this.paintText = paintText;
            this.textFull = textFull;

            initLines();
        }

        private void initLines() {
            lines = new ArrayList<String>();

            String[] words = textFull.split(" ");
            StringBuilder sb = new StringBuilder();

            Paint.FontMetrics fm = paintText.getFontMetrics();
            lineHeight = (int) (fm.bottom - fm.top + fm.leading);
            xStartText = x0Background + margin;
            yStartText = y0Background + margin + lineHeight; //normally starts top-left
            // Area to work with.
            widthMax = x1Background - x0Background - margin - margin;
            heightMax = y1Background - y0Background - margin - margin;

            //TODO:  (1) split full text into line-size-chunks
            for (int i = 0; i < words.length; i++) {
                if ((paintText.measureText(sb.toString()) + paintText.measureText(words[i] + " "))
                        < widthMax) {
                    sb.append(words[i]);
                    sb.append(" ");
                } else {
                    //NEXT WORD WOULDN'T FIT ON LINE... DO NOT INCREMENT i!!!
                    i--;
                    //ADD CURRENT LINE TO COLLECTION OF LINES
                    lines.add(sb.toString());
                    //RESTART StringBuilder
                    sb = new StringBuilder();
                    //TODO: (2) determine how many lines will fit
                    //TODO: if yStartText reaches heightMax... must start a new page.
                }
            }
        }

        public void render(Canvas canvas) {
            //@@@DRAW BACKGROUND PANEL@@@
            Rect background = new Rect(x0Background, y0Background, x1Background, y1Background);
            /////////////////////////////////////////////
            canvas.drawRect(background, paintBackground);
            /////////////////////////////////////////////

            int xCurrent = xStartText;
            int yCurrent = yStartText;
            //@@@DRAW TEXT@@@
            for (String line : lines) {
                /////////////////////////////////////////////////////
                canvas.drawText(line, xCurrent, yCurrent, paintText);
                /////////////////////////////////////////////////////
                yCurrent += lineHeight;
            }
        }

    }

}