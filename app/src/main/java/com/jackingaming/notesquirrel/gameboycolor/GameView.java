package com.jackingaming.notesquirrel.gameboycolor;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.pong.PongCartridge;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.PoohFarmerCartridge;

public class GameView extends SurfaceView
        implements SurfaceHolder.Callback {

    private GameCartridge gameCartridge;
    private GameRunner runner;

    private int widthScreen;
    private int heightScreen;

    private SurfaceHolder surfaceHolder;
    private int sideSquareScreen;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
    }

    /**
     * System's callback method when the user triggers a touch event.
     *
     * @param event The touch event's meta-data (e.g. x and y position
     *              of the user triggered touch event)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameCartridge.getInput(event);

        // If you've handled the touch event, return true.
        // If false, will NOT check for drag event (can't drag without touch).
        return true;
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceChanged(SurfaceHolder, int, int, int)");
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     *
     * When the surface is create, I want to start my GameRunner.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceCreated(SurfaceHolder)");
        ////////////////////////////////////////////////////////////////////////////////
        /*
        final Activity jackInActivity = (Activity)getContext();
        RelativeLayout relativeLayout = (RelativeLayout) jackInActivity.findViewById(R.id.relativeLayout);

        Button button = new Button(jackInActivity);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        button.setLayoutParams(layout);
        button.setText("myButton");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "kill switch engaged");
            }
        });

        relativeLayout.addView(button);
        */
        ////////////////////////////////////////////////////////////////////////////////


        widthScreen = getWidth();
        heightScreen = getHeight();


        surfaceHolder = holder;
        sideSquareScreen = Math.min(widthScreen, heightScreen);


        SurfaceView gameView = (SurfaceView) findViewById(R.id.gameView);
        gameView.getLayoutParams().width = sideSquareScreen;
        gameView.getLayoutParams().height = sideSquareScreen;
        /////////////////////////
        gameView.requestLayout();
        /////////////////////////


        gameCartridge = new PoohFarmerCartridge(getContext(), surfaceHolder, getResources(), sideSquareScreen);
        //gameCartridge = new PongCartridge(getContext(), holder, getResources(), widthScreen, heightScreen);
        runner = new GameRunner(gameCartridge);
        // Tell the Thread class to go to the "public void run()" method.
        runner.start();
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.surfaceDestroyed(SurfaceHolder)");

        if (runner != null) {
            runner.shutdown();

            /*
            NOW: want to wait for the thread to stop drawing.

            Want to NOT allow the phone to go to another application until
            this runner has stopped drawing... because if it goes to another
            application, the surface will no longer exist yet the thread will
            still be try to renderGame on it which will cause it to CRASH.
             */
            while (runner != null) {
                // This method waits for the thread to terminate.
                try {
                    runner.join();
                    runner = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void switchGame(boolean isPoohFarmer) {
        Log.d(MainActivity.DEBUG_TAG, "GameView.switchGame(boolean)");

        runner.shutdown();
        /*
        NOW: want to wait for the thread to stop drawing.
        Want to NOT allow the phone to go to another application until
        this runner has stopped drawing... because if it goes to another
        application, the surface will no longer exist yet the thread will
        still be try to renderGame on it which will cause it to CRASH.
        */
        while (runner != null) {
            // This method waits for the thread to terminate.
            try {
                runner.join();
                runner = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (isPoohFarmer) {
            gameCartridge = new PoohFarmerCartridge(getContext(), surfaceHolder, getResources(), sideSquareScreen);
        } else {
            gameCartridge = new PongCartridge(getContext(), surfaceHolder, getResources(), sideSquareScreen, sideSquareScreen);;
        }
        runner = new GameRunner(gameCartridge);
        // Tell the Thread class to go to the "public void run()" method.
        runner.start();
    }

}