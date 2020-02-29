package com.jackingaming.notesquirrel;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PointCollector implements View.OnTouchListener {

    //SUBSCRIBERS who are interested in THE SUBJECT'S (this) ArrayList<Point> data.
    private IPointCollectorListener listener;
    private List<Point> points = new ArrayList<Point>();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = Math.round(event.getX());
        int y = Math.round(event.getY());

        String message = String.format("Coordinates: (%d, %d)", x, y);
        //display message to Logcat monitor.
        Log.d(MainActivity.DEBUG_TAG, message);

        Point point = new Point(x, y);
        //now that we've verified the x and y values using Logcat's monitor, we can
        //add it to our collection of android.graphics.Point objects.
        points.add(point);

        //calling the PUSH method to pass data to interested/registered SUBSCRIBERS.
        if (listener != null) {
            if (points.size() == 4) {
                //when we've collected 4 Point objects, use the OBSERVER_PATTERN (subject/subscribers)
                //to call the subject's PUSH method.
                listener.pointsCollected(points);
                points.clear();
            }

            /*
            if (points.size() < 4) {
                //PUSH single Point-data to interested/registered SUBSCRIBERS.
                listener.singlePointCollected(point);
            } else if (points.size() == 4) {
                //when we've collected 4 Point objects, use the OBSERVER_PATTERN (subject/subscribers)
                //to call the subject's PUSH method.
                listener.pointsCollected(points);
                points.clear();
            }
            */
        }

        return false;
    }

    //method to REGISTER interested subscribers.
    public void setListener(IPointCollectorListener listener) {
        this.listener = listener;
    }

}