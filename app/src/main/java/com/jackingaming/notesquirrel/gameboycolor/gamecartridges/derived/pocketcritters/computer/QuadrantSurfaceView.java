package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.computer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.GameRunner;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class QuadrantSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {

    private GameRunner runner;

    private SurfaceHolder holder;

    private int widthScreen;
    private int heightScreen;
    private int sideSquareScreen;

    public QuadrantSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     *
     * When the surface is create, I want to start my GameRunner.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.surfaceCreated(SurfaceHolder)");

        SurfaceView surfaceViewQuadrant01 = (SurfaceView) findViewById(R.id.surfaceview_quadrant_01);
        SurfaceView surfaceViewQuadrant02 = (SurfaceView) findViewById(R.id.surfaceview_quadrant_02);
        SurfaceView surfaceViewQuadrant03 = (SurfaceView) findViewById(R.id.surfaceview_quadrant_03);
        SurfaceView surfaceViewQuadrant04 = (SurfaceView) findViewById(R.id.surfaceview_quadrant_04);

        this.holder = holder;
        widthScreen = getWidth();
        heightScreen = getHeight();

        ///////////////////////////////////////////////////////
        sideSquareScreen = Math.min(widthScreen, heightScreen);
//        surfaceViewQuadrant01.getLayoutParams().width = sideSquareScreen;
//        surfaceViewQuadrant01.getLayoutParams().height = sideSquareScreen;
//        surfaceViewQuadrant01.requestLayout();
        ///////////////////////////////////////////////////////

        QuadrantActivity quadrantActivity = (QuadrantActivity) getContext();
        //////////////////////////////////////////////////////////////////////////////////////
        if (surfaceViewQuadrant01 == this) {
            runGameCartridge(quadrantActivity.getGameCartridge01(), quadrantActivity.getInputManager01(),
//            runGameCartridge(quadrantActivity.getGameCartridge01(), quadrantActivity.getInputManager(),
                    widthScreen, heightScreen);
        } else if (surfaceViewQuadrant02 == this) {
            runGameCartridge(quadrantActivity.getGameCartridge02(), quadrantActivity.getInputManager02(),
//            runGameCartridge(quadrantActivity.getGameCartridge02(), quadrantActivity.getInputManager(),
                    widthScreen, heightScreen);
        } else if (surfaceViewQuadrant03 == this) {
            runGameCartridge(quadrantActivity.getGameCartridge03(), quadrantActivity.getInputManager03(),
//            runGameCartridge(quadrantActivity.getGameCartridge03(), quadrantActivity.getInputManager(),
                    widthScreen, heightScreen);
        } else if (surfaceViewQuadrant04 == this) {
            runGameCartridge(quadrantActivity.getGameCartridge04(), quadrantActivity.getInputManager04(),
//            runGameCartridge(quadrantActivity.getGameCartridge04(), quadrantActivity.getInputManager(),
                    widthScreen, heightScreen);
        }
        //////////////////////////////////////////////////////////////////////////////////////
    }

    private void runGameCartridge(GameCartridge gameCartridge, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.runGameCartridge(GameCartridge, InputManager, int, int)");

        if ( (gameCartridge != null) || (inputManager != null) ) {
            //////////////////////////////////////////////////////////////////////
            inputManager.init(widthScreen, heightScreen);
            gameCartridge.init(holder, inputManager, widthScreen, heightScreen);
            //DON'T CALL loadSavedState() ON ACTIVITY'S VERY FIRST START-UP.
            if (((QuadrantActivity) getContext()).getSavedInstanceState() != null) {
                Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.runGameCartridge(GameCartridge, InputManager, int, int) calling gameCartridge.loadSavedState()");
                gameCartridge.loadSavedState();
            }
            //////////////////////////////////////////////////////////////////////

            /////////////////////////////////////////////////////
            runner = new GameRunner(gameCartridge, inputManager);
            // Tell the Thread class to goto "public void run()".
            runner.start();
            /////////////////////////////////////////////////////
        } else {
            Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.runGameCartridge(GameCartridge, InputManager, int, int) ERROR: gameCartridge or inputManger is null!");
        }
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.surfaceChanged(SurfaceHolder, int, int, int)");
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
        Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.shutDownRunner()");

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
        Log.d(MainActivity.DEBUG_TAG, "QuadrantSurfaceView.surfaceDestroyed(SurfaceHolder)");

        /////////////////
        shutDownRunner();
        /////////////////
    }

}