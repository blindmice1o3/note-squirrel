package com.jackingaming.notesquirrel.gameboycolor;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class GameView extends SurfaceView
        implements SurfaceHolder.Callback {

    private GameRunner runner;

    private SurfaceHolder holder;

    private int widthScreen;
    private int heightScreen;
    private int sideSquareScreen;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, "GameView(Context, AttributeSet) constructor");

        getHolder().addCallback(this);
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceChanged(SurfaceHolder, int, int, int)");

        DirectionalPadFragment directionalPadFragment = (DirectionalPadFragment)
                ((JackInActivity)getContext()).getSupportFragmentManager().findFragmentById(R.id.directionalPadFragment);
        ButtonPadFragment buttonPadFragment = (ButtonPadFragment)
                ((JackInActivity)getContext()).getSupportFragmentManager().findFragmentById(R.id.buttonPadFragment);

        ////////////////////////////////////
        directionalPadFragment.initBounds();
        buttonPadFragment.initBounds();
        ////////////////////////////////////
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     *
     * When the surface is create, I want to start my GameRunner.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceCreated(SurfaceHolder)");

        //SurfaceView gameView = (SurfaceView) findViewById(R.id.gameView);
        SurfaceView gameView = (SurfaceView) findViewById(R.id.viewportfragment_gameview);

        this.holder = holder;
        widthScreen = getWidth();
        heightScreen = getHeight();

        ///////////////////////////////////////////////////////
        sideSquareScreen = Math.min(widthScreen, heightScreen);
        gameView.getLayoutParams().width = sideSquareScreen;
        gameView.getLayoutParams().height = sideSquareScreen;
        gameView.requestLayout();
        ///////////////////////////////////////////////////////

        JackInActivity jackInActivity = (JackInActivity) getContext();
        //////////////////////////////////////////////////////////////////////////////////////
        runGameCartridge(jackInActivity.getGameCartridge(), jackInActivity.getInputManager(),
                widthScreen, heightScreen);
        //////////////////////////////////////////////////////////////////////////////////////
    }

    public int getWidthScreen() { return widthScreen; }

    public int getHeightScreen() { return heightScreen; }

    public void runGameCartridge(GameCartridge gameCartridge, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.runGameCartridge(GameCartridge, InputManager, int, int)");

        if ( (gameCartridge != null) || (inputManager != null) ) {
            //////////////////////////////////////////////////////////////////////
            inputManager.init(widthScreen, heightScreen);
            gameCartridge.init(holder, inputManager, widthScreen, heightScreen);
            if (((JackInActivity)getContext()).getSavedInstanceState() != null) {
                Log.d(MainActivity.DEBUG_TAG, "GameView.runGameCartridge(GameCartridge, InputManager, int, int) calling gameCartridge.loadSavedState()");
                gameCartridge.loadSavedState();
            }
            //////////////////////////////////////////////////////////////////////

            /////////////////////////////////////////////////////
            runner = new GameRunner(gameCartridge, inputManager);
            // Tell the Thread class to goto "public void run()".
            runner.start();
            /////////////////////////////////////////////////////
        } else {
            Log.d(MainActivity.DEBUG_TAG, "GameView.runGameCartridge(GameCartridge, InputManager, int, int) ERROR: gameCartridge or inputManger is null!");
        }
    }

    /**
     * NOW: want to wait for the thread to stop drawing.
     *
     * Want to NOT allow the phone to go to another application until
     * this runner has stopped drawing... because if it goes to another
     * application, the surface will no longer exist yet the thread will
     * still be try to renderGame on it which will cause it to CRASH.
     */
    public void shutDownRunner() {
        Log.d(MainActivity.DEBUG_TAG, "GameView.shutDownRunner()");

        if (runner != null) {
            runner.shutdown();

            // Wait for the thread to terminate.
            while (runner != null) {
                try {
                    runner.join();
                    runner = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceDestroyed(SurfaceHolder)");

        /////////////////
        shutDownRunner();
        /////////////////
    }

}