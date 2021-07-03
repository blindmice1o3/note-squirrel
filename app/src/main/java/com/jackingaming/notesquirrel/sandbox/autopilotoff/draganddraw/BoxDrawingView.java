package com.jackingaming.notesquirrel.sandbox.autopilotoff.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.draganddraw.models.Box;

import java.util.ArrayList;

public class BoxDrawingView extends View {
    public static final String TAG = "BoxDrawingView";

    private ArrayList<Box> boxes = new ArrayList<Box>();
    private Box currentBox;
    private Paint boxPaint;
    private Paint backgroundPaint;

    // Used when creating the view in code
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    // Used when inflating the view from XML
    public BoxDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Paint the boxes a nice semitransparent red (ARGB)
        boxPaint = new Paint();
        boxPaint.setColor(0x22ff0000);

        // Paint the background off-white
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());

        Log.i(TAG, "Received event at x=" + curr.x +
                ", y=" + curr.y + ":");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, " ACTION_DOWN");
                // Reset drawing state
                currentBox = new Box(curr);
                boxes.add(currentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, " ACTION_MOVE");
                if (currentBox != null) {
                    currentBox.setCurrent(curr);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, " ACTION_UP");
                currentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, " ACTION_CANCEL");
                currentBox = null;
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Fill the background
        canvas.drawPaint(backgroundPaint);

        for (Box box : boxes) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, boxPaint);
        }
    }

    public static final String KEY_PARCELABLE_SUPER_STATE = "superState";
    public static final String KEY_BOXES = "boxes";
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_PARCELABLE_SUPER_STATE, super.onSaveInstanceState());

        bundle.putSerializable(KEY_BOXES, boxes);
        Log.i(TAG, "onSaveInstanceState() boxes.size(): " + boxes.size());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            boxes = (ArrayList<Box>) bundle.getSerializable(KEY_BOXES);
            Log.i(TAG, "onRestoreInstanceState() boxes.size(): " + boxes.size());

            state = bundle.getParcelable(KEY_PARCELABLE_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }
}