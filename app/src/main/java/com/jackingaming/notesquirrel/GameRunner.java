package com.jackingaming.notesquirrel;

import android.util.Log;

/**
 * Thread whose responsibility is to update the surface (frame-by-frame).
 * <p>
 * Using Thread class instead of AsyncTask class because we want to
 * by-pass the normal Android mechanism (which you find in pretty much
 * any GUI systems) (all these systems usually have a way of posting
 * updates to a kind of thread that processes a queue and paints the
 * user interface when necessary).
 * <p>
 * Since this is a game (same for any serious animation), we want to
 * force updates to happen when we say they should happen.
 * <p>
 * Don't want to post an update to the user interface thread and
 * let it update the user interface when it feel like it's a good idea.
 * <p>
 * That's why we have a separate thread here. It's a thread that's
 * going to directly update the user interface.
 */
public class GameRunner extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {
        //game loop
        while (running) {
            // Draw stuff.
            Log.d(MainActivity.DEBUG_TAG, "Thread running.");

            //TODO: slowing things down (for now), will remove later.
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    //CAN'T CALL THIS "stop()" because Thread already has a "stop()".
    public void shutdown() {
        running = false;
    }

}