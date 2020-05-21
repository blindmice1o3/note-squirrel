package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.sprites.Bat;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.sprites.Ball;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class PongCartridge
        implements GameCartridge {

    public enum State {
        PAUSED, RUNNING, WON, LOST;
    }
    private State state = State.PAUSED;

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Id idGameCartridge;
    private Ball ball;
    private Bat player;
    private Bat opponent;

    private Paint textPaint;

    //private SoundPool soundPool;
    private MediaPlayer mediaPlayer;

    public PongCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.holder = holder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        textPaint = new Paint();
        //set text's pivot-point to CENTER-OF-TEXT (instead of TOP-LEFT corner).
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(64);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        //soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mediaPlayer = MediaPlayer.create(context, R.raw.corporate_ukulele);
        mediaPlayer.start();

        Bitmap spriteSheetCorgiCrusade = BitmapFactory.decodeResource(context.getResources(), R.drawable.corgi_crusade_editted);
        Bitmap spriteSheetYokoTileset = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_yoko_tileset);

        ball = new Ball(widthViewport, heightViewport);
        player = new Bat(widthViewport, heightViewport, Bat.Position.LEFT);
        opponent = new Bat(widthViewport, heightViewport, Bat.Position.RIGHT);

        ball.init(spriteSheetCorgiCrusade);
        player.init(spriteSheetYokoTileset);
        opponent.init(spriteSheetYokoTileset);
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.savePresentState()");
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.loadSavedState()");
    }

    /**
     * Update the user's bat position.
     * <p>
     * Handle touch events triggered by GameView (custom SurfaceView).
     */
    @Override
    public void getInputViewport() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.getInputViewport()");

        if (state == State.RUNNING) {
            if ( (inputManager.getEvent() != null) &&
                    (inputManager.getEvent().getY() <= heightViewport) )
            player.setBatPosition(inputManager.getEvent().getY());
        } else {
            //FIXING BUG (unreleased touch WAS immediately reinitializing the game).
            if (inputManager.isJustPressedViewport()) {
                state = State.RUNNING;
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.getInputDirectionalPad()");
        //TODO:
    }

    @Override
    public void getInputButtonPad() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.getInputButtonPad()");
        //TODO:
    }

    @Override
    public void update(long elapsed) {
        getInputViewport();

        if (state == State.RUNNING) {
            updateGame(elapsed);
        }
    }

    private void initSpritePositions() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.initSpritePositions()");

        ball.initPosition();
        player.initPosition();
        opponent.initPosition();
    }

    private void updateGame(long elapsed) {
        ///////////////////////////////////////////
        //COLLISION DETECTION (ball bounce off bat)
        ///////////////////////////////////////////
        //player (rectangle) and ball (point)
        //(x is left side of ball, y is top of ball) OR (x is left side of ball, y is bottom of ball)
        if (player.getScreenRect().contains(ball.getScreenRect().left, ball.getScreenRect().top) ||
                player.getScreenRect().contains(ball.getScreenRect().left, ball.getScreenRect().bottom)) {
            ball.moveRight();
        }
        //opponent (rectangle) and ball (point)
        //(x is right side of ball, y is top of ball) OR (x is right side of ball, y is bottom of ball)
        else if (opponent.getScreenRect().contains(ball.getScreenRect().right, ball.getScreenRect().top) ||
                opponent.getScreenRect().contains(ball.getScreenRect().right, ball.getScreenRect().bottom)) {
            ball.moveLeft();
        }
        ////////////////////////////////////////////////////////////////////////////////////
        //CHECK FOR WINNING CONDITION (can only reach here IF BALL HAVE NOT BOUNCED OFF BAT)
        ////////////////////////////////////////////////////////////////////////////////////
        //ball moved left passed player
        else if (ball.getScreenRect().left < player.getScreenRect().right) {
            Log.d(MainActivity.DEBUG_TAG, "PongCartridge.updateGame(): LOST");
            state = State.LOST;
            initSpritePositions();
        }
        //ball moved right passed opponent
        else if (ball.getScreenRect().right > opponent.getScreenRect().left) {
            Log.d(MainActivity.DEBUG_TAG, "PongCartridge.updateGame(): WON");
            state = State.WON;
            initSpritePositions();
        }

        //////////////////////////////////////////////////
        //UPDATE movement for AI (ball and opponent's bat)
        //////////////////////////////////////////////////
        ball.update(elapsed);
        opponent.update(elapsed, ball);
    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //BACKGROUND (clear the canvas by painting the background white).
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@ DRAWING-RELATED-CODE @@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            switch (state) {
                case PAUSED:
                    drawText(canvas, "Tap screen to start...");
                    break;
                case RUNNING:
                    drawGame(canvas);
                    break;
                case WON:
                    drawText(canvas, "You won!");
                    break;
                case LOST:
                    drawText(canvas, "You lost :(");
                    break;
                default:
                    Log.d(MainActivity.DEBUG_TAG, "PongCartridge.render() switch construct's default block.");
                    break;
            }
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    private void drawGame(Canvas canvas) {
        //SPRITES
        //////////////////////
        ball.draw(canvas);
        player.draw(canvas);
        opponent.draw(canvas);
        //////////////////////
    }

    private void drawText(Canvas canvas, String text) {
        //textPaint's Align is set to Align.CENTER, which means
        //its pivot-point is CENTER-OF-TEXT (not TOP-LEFT corner).
        canvas.drawText(text, canvas.getWidth() / 2, canvas.getHeight() / 2, textPaint);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        return idGameCartridge;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public void setPlayer(Player player) {
        //intentionally blank.
    }

    @Override
    public GameCamera getGameCamera() {
        return null;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        //intentionally blank.
    }

    @Override
    public SceneManager getSceneManager() {
        return null;
    }

    @Override
    public StateManager getStateManager() { return null; }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }
}