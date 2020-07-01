package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.sprites.Ball;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.sprites.Bat;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.tiles.TileMapPong;

public class ScenePong extends Scene {

    public enum Mode {
        PAUSED, RUNNING, WON, LOST;
    }
    private Mode mode = Mode.PAUSED;

    private Ball ball;
    private Bat playerAsSprite;
    private Bat opponent;

    transient private Paint textPaint;

    //private SoundPool soundPool;
    private MediaPlayer mediaPlayer;

    public ScenePong(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 8;
        heightClipInTile = 8;
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        super.init(gameCartridge, player, gameCamera, sceneManager);

        textPaint = new Paint();
        //set text's pivot-point to CENTER-OF-TEXT (instead of TOP-LEFT corner).
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(64);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        //TODO: if check is a temporary bug-fix (in other games, after loading... music played).
        //Every scenes init() is called when loading.
        if (gameCartridge.getIdGameCartridge() == GameCartridge.Id.PONG) {
            //soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            mediaPlayer = MediaPlayer.create(context, R.raw.corporate_ukulele);
            mediaPlayer.start();
        }

        Bitmap spriteSheetCorgiCrusade = BitmapFactory.decodeResource(context.getResources(), R.drawable.corgi_crusade_editted);
        Bitmap spriteSheetYokoTileset = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_yoko_tileset);

        ball = new Ball(gameCartridge.getWidthViewport(), gameCartridge.getHeightViewport());
        playerAsSprite = new Bat(gameCartridge.getWidthViewport(), gameCartridge.getHeightViewport(), Bat.Position.LEFT);
        opponent = new Bat(gameCartridge.getWidthViewport(), gameCartridge.getHeightViewport(), Bat.Position.RIGHT);

        ball.init(spriteSheetCorgiCrusade);
        playerAsSprite.init(spriteSheetYokoTileset);
        opponent.init(spriteSheetYokoTileset);
    }

    @Override
    public void enter() {
        super.enter();

        //TODO: base this from what's saved via StartMenuState's OPTION (settings).
        gameCartridge.getHeadUpDisplay().setIsVisible(false);
    }

    /**
     * Update the user's bat position.
     * <p>
     * Handle touch events triggered by GameView (custom SurfaceView).
     */
    @Override
    public void getInputViewport() {
        Log.d(MainActivity.DEBUG_TAG, "ScenePong.getInputViewport()");
        if (mode == Mode.RUNNING) {
            if ( (inputManager.getEvent() != null) &&
                    (inputManager.getEvent().getY() <= gameCartridge.getHeightViewport()) )
                playerAsSprite.setBatPosition(inputManager.getEvent().getY());
        } else {
            //FIXING BUG (unreleased touch WAS immediately reinitializing the game).
            if (inputManager.isJustPressedViewport()) {
                mode = Mode.RUNNING;
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        Log.d(MainActivity.DEBUG_TAG, "ScenePong.getInputButtonPad()");
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapPong(gameCartridge, sceneID);
    }

    @Override
    public void update(long elapsed) {
        ///////////////////
        getInputViewport();
        ///////////////////

        if (mode == Mode.RUNNING) {
            updateGame(elapsed);
        }
    }

    private void initSpritePositions() {
        Log.d(MainActivity.DEBUG_TAG, "ScenePong.initSpritePositions()");

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
            Log.d(MainActivity.DEBUG_TAG, "ScenePong.updateGame(): LOST");
            mode = Mode.LOST;
            initSpritePositions();
        }
        //ball moved right passed opponent
        else if (ball.getRectOnScreen().right > opponent.getRectOnScreen().left) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePong.updateGame(): WON");
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
    public void render(Canvas canvas) {
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
                Log.d(MainActivity.DEBUG_TAG, "@@@@@ScenePong.render() switch construct's default block.@@@@@");
                break;
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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

}