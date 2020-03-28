package com.jackingaming.notesquirrel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    private Bitmap spriteSheetCorgiCrusade;

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
}