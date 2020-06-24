package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.sprites.Bat;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.sprites.Ball;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import static android.content.Context.MODE_PRIVATE;

public class PongCartridge
        implements GameCartridge {

    public enum Mode {
        PAUSED, RUNNING, WON, LOST;
    }
    private Mode mode = Mode.PAUSED;

    private Context context;
    private Id idGameCartridge;

    private SurfaceHolder surfaceHolder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Player player;
    private GameCamera gameCamera;
    private StateManager stateManager;

    private Ball ball;
    private Bat playerAsSprite;
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
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
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
        playerAsSprite = new Bat(widthViewport, heightViewport, Bat.Position.LEFT);
        opponent = new Bat(widthViewport, heightViewport, Bat.Position.RIGHT);

        ball.init(spriteSheetCorgiCrusade);
        playerAsSprite.init(spriteSheetYokoTileset);
        opponent.init(spriteSheetYokoTileset);

        gameCamera = new GameCamera(widthViewport, heightViewport);
        player = new Player(this);
        ///////////////////////////////////////////////////
        stateManager = new StateManager(this);
        ///////////////////////////////////////////////////
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.savePresentState()");

        //HANDLES JackInActivity.gameCartridge/////////////////////////////////////////
        //only THIS activity can get access to THIS preference file.
        SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
        //Editor is an inner-class of the SharedPreferences class.
        SharedPreferences.Editor editor = prefs.edit();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //Save enum as integer (its index value).
        //!!!Used in JackInActivity's constructor (orientation change)!!!
        editor.putInt("idGameCartridge", idGameCartridge.ordinal());
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //HAVE TO tell editor to actually save the values we'd put into it.
        editor.commit();
        /////////////////////////////////////////////////////////////////////////////////

        //TODO:
        //SerializationDoer.saveViaOS(this);
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.loadSavedState()");

        // !!!THIS CHECKING FOR NULL IS NECESSARY!!!
        //if (handler != null) {
        //TODO:
        //SerializationDoer.loadViaOS(this);
        //}
    }

    /**
     * Update the user's bat position.
     * <p>
     * Handle touch events triggered by GameView (custom SurfaceView).
     */
    @Override
    public void getInputViewport() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.getInputViewport()");

        if (mode == Mode.RUNNING) {
            if ( (inputManager.getEvent() != null) &&
                    (inputManager.getEvent().getY() <= heightViewport) )
            playerAsSprite.setBatPosition(inputManager.getEvent().getY());
        } else {
            //FIXING BUG (unreleased touch WAS immediately reinitializing the game).
            if (inputManager.isJustPressedViewport()) {
                mode = Mode.RUNNING;
            }
        }

        //TODO:
//        if (inputManager.isJustPressedViewport()) {
//            stateManager.getCurrentState().getInputViewport();
//        }
    }

    @Override
    public void getInputDirectionalPad() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.getInputDirectionalPad()");

        if (inputManager.isPressingDirectionalPad()) {
            stateManager.getCurrentState().getInputDirectionalPad();
        }
    }

    @Override
    public void getInputButtonPad() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.getInputButtonPad()");

        if (inputManager.isJustPressedButtonPad()) {
            stateManager.getCurrentState().getInputButtonPad();
        }
    }

    @Override
    public void update(long elapsed) {
        //////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        //////////////////////////

        //TODO:
        //stateManager.getCurrentState().update(elapsed);

        if (mode == Mode.RUNNING) {
            updateGame(elapsed);
        }
    }

    private void initSpritePositions() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.initSpritePositions()");

        ball.initPosition();
        playerAsSprite.initPosition();
        opponent.initPosition();
    }

    private void updateGame(long elapsed) {
        ///////////////////////////////////////////
        //COLLISION DETECTION (ball bounce off bat)
        ///////////////////////////////////////////
        //player (rectangle) and ball (point)
        //(x is left side of ball, y is top of ball) OR (x is left side of ball, y is bottom of ball)
        if (playerAsSprite.getRectOnScreen().contains(ball.getRectOnScreen().left, ball.getRectOnScreen().top) ||
                playerAsSprite.getRectOnScreen().contains(ball.getRectOnScreen().left, ball.getRectOnScreen().bottom)) {
            ball.moveRight();
        }
        //opponent (rectangle) and ball (point)
        //(x is right side of ball, y is top of ball) OR (x is right side of ball, y is bottom of ball)
        else if (opponent.getRectOnScreen().contains(ball.getRectOnScreen().right, ball.getRectOnScreen().top) ||
                opponent.getRectOnScreen().contains(ball.getRectOnScreen().right, ball.getRectOnScreen().bottom)) {
            ball.moveLeft();
        }
        ////////////////////////////////////////////////////////////////////////////////////
        //CHECK FOR WINNING CONDITION (can only reach here IF BALL HAVE NOT BOUNCED OFF BAT)
        ////////////////////////////////////////////////////////////////////////////////////
        //ball moved left passed player
        else if (ball.getRectOnScreen().left < playerAsSprite.getRectOnScreen().right) {
            Log.d(MainActivity.DEBUG_TAG, "PongCartridge.updateGame(): LOST");
            mode = Mode.LOST;
            initSpritePositions();
        }
        //ball moved right passed opponent
        else if (ball.getRectOnScreen().right > opponent.getRectOnScreen().left) {
            Log.d(MainActivity.DEBUG_TAG, "PongCartridge.updateGame(): WON");
            mode = Mode.WON;
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
        //TODO:
        //stateManager.getCurrentState().render();

        //synchronize?
        ////////////////////////////////////
        Canvas canvas = surfaceHolder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //BACKGROUND (clear the canvas by painting the background white).
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@ DRAWING-RELATED-CODE @@@@@@@@@@@@@@@
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            switch (mode) {
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
            surfaceHolder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    private void drawGame(Canvas canvas) {
        //SPRITES
        //////////////////////
        ball.draw(canvas);
        playerAsSprite.draw(canvas);
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
    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public GameCamera getGameCamera() {
        return gameCamera;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        return ((GameState)stateManager.getState(State.Id.GAME)).getSceneManager();
    }

    @Override
    public StateManager getStateManager() {
        return stateManager;
    }

}