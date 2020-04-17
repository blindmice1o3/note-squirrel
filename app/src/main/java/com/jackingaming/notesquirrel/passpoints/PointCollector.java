package com.jackingaming.notesquirrel.passpoints;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jackingaming.notesquirrel.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class PointCollector implements View.OnTouchListener {

    public static final int NUM_POINTS = 4;

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
            if (points.size() == NUM_POINTS) {
                //when we've collected 4 Point objects, use the OBSERVER_PATTERN (subject/subscribers)
                //to call the subject's PUSH method.
                listener.pointsCollected(points);

                //points.clear();
                //John from caveofprogramming.com said this was a bug...
                //he said we've collected the points then CLEAR them immediately...
                //but I'm not sure it is a bug... I think we USED the collected
                //points and have to clear them before each successive attempt to log-in.

                //Actually, we just moved this clearing-function to another place and call
                //it from the ImageActivity.pointsColected()'s thread-worker's onPostExecute().
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

    //only call this AFTER we save the 4 points to our database (in ImageActvity.pointsCollected()'s
    //thread-worker's onPostExecute() method).
    public void clear() {
        points.clear();
    }

    //method to REGISTER interested subscribers.
    public void setListener(IPointCollectorListener listener) {
        this.listener = listener;
    }

}