package com.jackingaming.notesquirrel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView
        implements SurfaceHolder.Callback {

    private Bitmap spriteSheetCorgiCrusade;

    private GameRunner runner;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        spriteSheetCorgiCrusade = BitmapFactory.decodeResource(getResources(), R.drawable.corgi_crusade);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Will draw on our surface.
        SurfaceHolder holder = getHolder();
        //synchronize?
        Canvas canvas = holder.lockCanvas();

        if (canvas != null) {
            //clear the canvas
            canvas.drawColor(Color.WHITE);

            /////////////////////////////////////////////////////////////////////////
            //draw on the canvas
            canvas.drawBitmap(spriteSheetCorgiCrusade, 50, 50, null);
            /////////////////////////////////////////////////////////////////////////

            //unlock it and post our updated drawing to it.
            holder.unlockCanvasAndPost(canvas);
        }

        // If you've handled the touch event, return true.
        // If false, will NOT check for drag event (can't drag without touch).
        return true;
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(MainActivity.DEBUG_TAG, "changed");
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     *
     * When the surface is create, I want to start my GameRunner.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "created");

        runner = new GameRunner();
        // Tell the Thread class to go to the "public void run()" method.
        runner.start();
    }

    /**
     * We don't call this method directly, it's used by the SurfaceHolder.Callback interface.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(MainActivity.DEBUG_TAG, "destroyed");

        if (runner != null) {
            runner.shutdown();

            /*
            NOW: want to wait for the thread to stop drawing.

            Want to NOT allow the phone to go to another application until
            this runner has stopped drawing... because if it goes to another
            application, the surface will no longer exist yet the thread will
            still be try to draw on it which will cause it to CRASH.
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

}