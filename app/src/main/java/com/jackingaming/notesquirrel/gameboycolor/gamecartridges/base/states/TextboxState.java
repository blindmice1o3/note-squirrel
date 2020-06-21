package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextboxState
        implements State, Serializable {

    transient private Handler handler;
    private Id id;

    transient private InputManager inputManager;
    transient private SurfaceHolder surfaceHolder;    //used to get Canvas
    private int widthViewport;
    private int heightViewport;

    transient private SceneManager sceneManager;      //initialize in enter(Object[])

    transient private Textbox textbox;

    public TextboxState(Handler handler) {
        this.handler = handler;
        id = Id.TEXTBOX;

        inputManager = handler.getGameCartridge().getInputManager();
        surfaceHolder = handler.getGameCartridge().getSurfaceHolder();
        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();

        initTextbox();
    }

    private void initTextbox() {
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

        ////////////////////////////////////////////////////////////////////////
        textbox = new Textbox(x0Background, y0Background, x1Background, y1Background,
                margin, paintBackground, paintText);
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

            //TODO:
            textbox.turnToNextPage();
        }
        //b button (pop State.TEXTBOX)
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputButtonPad() b-button-justPressed");

            //TODO:
            if (textbox.getIndexPageCurrent() == 0) {
                handler.getGameCartridge().getStateManager().pop();
            } else {
                textbox.turnToPreviousPage();
            }
        }
        //menu button
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "TextboxState.getInputButtonPad() menu-button-justPressed");
        }
    }

    @Override
    public void update(long elapsed) {
        ////////////////////////
        textbox.update(elapsed);
        ////////////////////////
    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = surfaceHolder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@RE-DRAW state.GAME as background@@@
            // (otherwise white background because we'd cleared earlier)
            sceneManager.getCurrentScene().render(canvas);

            ///////////////////////
            textbox.render(canvas);
            ///////////////////////

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            surfaceHolder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    @Override
    public void enter(Object[] args) {
        Log.d(MainActivity.DEBUG_TAG, "TextboxState.enter(Object[]) State.Id: " + id);

        sceneManager = ((GameState)handler.getGameCartridge().getStateManager().getState(State.Id.GAME)).getSceneManager();

        if (args != null) {
            if (args[0] instanceof String) {
                textbox.setTextFull( ((String)args[0]) );
            }
        }

        // Might split default textFull. Will always reset textbox's pageCurrent and indexes.
        textbox.initTextbox();
    }

    @Override
    public void exit() {

    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    class Textbox {

        private int x0Background;
        private int y0Background;
        private int x1Background;
        private int y1Background;
        private int margin;
        private Paint paintBackground;
        private Paint paintText;
        private String textFull;

        private int heightLine;
        private int xStartText;
        private int yStartText;
        private int widthMax;
        private int heightMax;

        private List<String> lines;
        private int numberOfLinesPerPage;
        private List<List<String>> pages;

        private int indexPageCurrent;
        private int indexLineOfPageCurrent;
        private int indexCharOfLineCurrent;

        public Textbox(int x0Background, int y0Background, int x1Background, int y1Background,
                       int margin, Paint paintBackground, Paint paintText) {
            this.x0Background = x0Background;
            this.y0Background = y0Background;
            this.x1Background = x1Background;
            this.y1Background = y1Background;
            this.margin = margin;
            this.paintBackground = paintBackground;
            this.paintText = paintText;

            // Default text.
            textFull = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        }

        public void setTextFull(String textFull) {
            this.textFull = textFull;
        }

        public void initTextbox() {
            initLines();
            initPages();

            indexPageCurrent = 0;

            resetPageAnimation();
        }

        private void resetPageAnimation() {
            timer = 0;
            indexLineOfPageCurrent = 0;
            indexCharOfLineCurrent = 0;
        }

        private void initLines() {
            lines = new ArrayList<String>();

            String[] words = textFull.split(" ");
            StringBuilder sb = new StringBuilder();

            //REFERENCE: https://stackoverflow.com/questions/3654321/measuring-text-height-to-be-drawn-on-canvas-android
            Paint.FontMetrics fm = paintText.getFontMetrics();
            heightLine = (int) (fm.bottom - fm.top + fm.leading);

            xStartText = x0Background + margin;
            yStartText = y0Background + margin - (int)(fm.top); //normally starts at BASELINE

            // Area to work with.
            widthMax = x1Background - x0Background - margin - margin;
            heightMax = y1Background - y0Background - margin - margin;

            numberOfLinesPerPage = heightMax / heightLine;

            //TODO:  (1) split textFull into line-length-chunks.
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
                }
            }
            // The for-loop may not have added the last line.
            if (sb.toString().length() > 0) {
                lines.add(sb.toString());
            }

            // At this point, textFull is divided into line-length-chunks
            // (but may have more lines than will fit on one page).
        }

        private void initPages() {
            pages = new ArrayList<List<String>>();

            //TODO: (2) determine how many lines will fit (lay out lines onto pages).

            // One page.
            if (lines.size() <= numberOfLinesPerPage) {
                pages.add(lines);
            }
            // Multiple pages.
            else {
                List<String> page = new ArrayList<String>();
                int counter = 0;

                for (int i = 0; i < lines.size(); i++) {
                    counter++;

                    if (counter <= numberOfLinesPerPage) {
                        page.add(lines.get(i));
                    }
                    // Page is full.
                    else {
                        pages.add(page);
                        page = new ArrayList<String>();
                        counter = 0;
                        i--;    //Haven't added current line to page.
                    }
                }
                // Last page has lines, but not a full page (for-loop didn't add it to pages).
                if (lines.size() % numberOfLinesPerPage > 0) {
                    pages.add(page);
                }
            }
        }

        public void turnToNextPage() {
            indexPageCurrent++;

            // Check if out-of-bounds.
            if (indexPageCurrent >= pages.size()) {
                indexPageCurrent = (pages.size() - 1);
            } else {
                // Reset revealing-animation only when page is actually turned.
                resetPageAnimation();
            }
        }

        public void turnToPreviousPage() {
            indexPageCurrent--;

            // Check if out-of-bounds.
            if (indexPageCurrent < 0) {
                indexPageCurrent = 0;
            } else {
                // Reset revealing-animation only when page is actually turned.
                resetPageAnimation();
            }
        }

        private long timer = 0;
        private static final long DELAY = 100; // 100 milliseconds (0.1 second) before revealing next character.
        public void update(long elapsed) {
            timer += elapsed;
            if (timer >= DELAY) {
                //TODO: reveal one character at a time for each line.
                List<String> pageCurrent = pages.get(indexPageCurrent);
                String lineCurrent = pageCurrent.get(indexLineOfPageCurrent);

                // Reveal next character.
                /////////////////////////
                indexCharOfLineCurrent++;
                /////////////////////////

                // Check if out-of-bounds (end of line).
                if (indexCharOfLineCurrent >= lineCurrent.length()) {
                    // Reveal next line (LINE FEED).
                    /////////////////////////
                    indexLineOfPageCurrent++;
                    /////////////////////////

                    // Check if out-of-bounds (end of page).
                    if (indexLineOfPageCurrent >= pageCurrent.size()) {
                        indexLineOfPageCurrent = (pageCurrent.size() - 1);
                        indexCharOfLineCurrent = (lineCurrent.length() - 1);
                    } else {
                        // Reset index of current line (CARRIAGE RETURN).
                        indexCharOfLineCurrent = 0;
                    }
                }

                // Reset timer.
                //////////
                timer = 0;
                //////////
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
            List<String> pageCurrent = pages.get(indexPageCurrent);
            for (int i = 0; i < pageCurrent.size(); i++) {
                // Regular drawText() for lines before current line.
                if (i < indexLineOfPageCurrent) {
                    String linePrevious = pageCurrent.get(i);
                    /////////////////////////////////////////////////////
                    canvas.drawText(linePrevious, xCurrent, yCurrent, paintText);
                    /////////////////////////////////////////////////////
                    yCurrent += heightLine;
                }
                // Type-writer animation for current line.
                else {
                    String lineCurrent = pageCurrent.get(indexLineOfPageCurrent);

                    if (indexCharOfLineCurrent < lineCurrent.length()) {
                        String revealedText = lineCurrent.substring(0, indexCharOfLineCurrent);
                        /////////////////////////////////////////////////////
                        canvas.drawText(revealedText, xCurrent, yCurrent, paintText);
                        /////////////////////////////////////////////////////
                    } else {
                        /////////////////////////////////////////////////////
                        canvas.drawText(lineCurrent, xCurrent, yCurrent, paintText);
                        /////////////////////////////////////////////////////
                    }
                }
            }
        }

        public int getIndexPageCurrent() {
            return indexPageCurrent;
        }

    }

}