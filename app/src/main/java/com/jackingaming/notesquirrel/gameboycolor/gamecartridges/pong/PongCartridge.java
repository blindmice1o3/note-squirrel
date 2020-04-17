package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.sprites.Bat;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pong.sprites.Ball;

public class PongCartridge
        implements GameCartridge {

    public enum State {
        PAUSED, RUNNING, WON, LOST;
    }
    private State state = State.PAUSED;

    private Context context;
    private SurfaceHolder holder;
    private Resources resources;

    private Ball ball;
    private Bat player;
    private Bat opponent;

    private Paint textPaint;

    private boolean cantPress = false;
    private boolean justPressed = false;
    private boolean pressing = false;

    //private SoundPool soundPool;
    private MediaPlayer mediaPlayer;

    public PongCartridge(Context context, SurfaceHolder holder, Resources resources, int widthScreen, int heightScreen) {
        this.context = context;
        this.holder = holder;
        this.resources = resources;

        //soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mediaPlayer = MediaPlayer.create(context, R.raw.corporate_ukulele);

        ball = new Ball(widthScreen, heightScreen);
        player = new Bat(widthScreen, heightScreen, Bat.Position.LEFT);
        opponent = new Bat(widthScreen, heightScreen, Bat.Position.RIGHT);

        textPaint = new Paint();
        //set text's pivot-point to CENTER-OF-TEXT (instead of TOP-LEFT corner).
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(64);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.init()");

        //TODO:
        /*
        final int startSoundId = soundPool.load(context, R.raw.avicii_tribute_concert, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (startSoundId == sampleId) {
                    soundPool.play(startSoundId, 1, 1, 1, 0, 1f);
                    Toast.makeText(context, "SoundPool.OnLoadCompleteListener.onLoadComplete(SoundPool, int, int)", Toast.LENGTH_LONG).show();
                    Log.d(MainActivity.DEBUG_TAG, "@@@@@ SoundPool.OnLoadCompleteListener.onLoadComplete(SoundPool, int, int) @@@@@");
                }
            }
        });
        */
        mediaPlayer.start();

        Bitmap spriteSheetCorgiCrusade = BitmapFactory.decodeResource(resources, R.drawable.corgi_crusade_editted);
        Bitmap spriteSheetYokoTileset = BitmapFactory.decodeResource(resources, R.drawable.pc_yoko_tileset);

        ball.init(spriteSheetCorgiCrusade);
        player.init(spriteSheetYokoTileset);
        opponent.init(spriteSheetYokoTileset);
    }

    /**
     * Update the user's bat position.
     * <p>
     * Handle touch events triggered by GameView (custom SurfaceView).
     *
     * @param event The touch event's meta-data (e.g. x and y position
     *              of the user triggered touch event)
     */
    @Override
    public void onScreenInput(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressing = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            pressing = false;
        }

        if (cantPress && !pressing) {
            cantPress = false;
        } else if (justPressed) {
            cantPress = true;
            justPressed = false;
        }
        if (!cantPress && pressing) {
            justPressed = true;
        }

        if (state == State.RUNNING) {
            player.setBatPosition(event.getY());
        } else {
            //FIXING BUG (unreleased touch WAS immediately reinitializing the game).
            if (justPressed) {
                state = State.RUNNING;
            }
        }
    }

    @Override
    public void onDirectionalPadInput(DirectionalPadFragment.Direction direction) {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.onDirectionalPadInput(Direction)");
        //TODO:
    }

    @Override
    public void onButtonPadInput(ButtonPadFragment.InputButton inputButton) {
        Log.d(MainActivity.DEBUG_TAG, "PongCartridge.onButtonPadInput(InputButton)");
    }

    @Override
    public void update(long elapsed) {
        if (state == State.RUNNING) {
            updateGame(elapsed);
        }
    }

    private void initSpritePositions() {
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
            Log.d(MainActivity.DEBUG_TAG, "LOST");
            state = State.LOST;
            initSpritePositions();
        }
        //ball moved right passed opponent
        else if (ball.getScreenRect().right > opponent.getScreenRect().left) {
            Log.d(MainActivity.DEBUG_TAG, "WON");
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

}