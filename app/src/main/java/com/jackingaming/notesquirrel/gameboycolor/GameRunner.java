package com.jackingaming.notesquirrel.gameboycolor;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

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

    public static final int FRAMES_PER_SEC = 60;
    public static final int TIME_PER_FRAME = 1000 / FRAMES_PER_SEC;

    private GameCartridge gameCartridge;
    private InputManager inputManager;
    private volatile boolean running = true;

    public GameRunner(GameCartridge gameCartridge, InputManager inputManager) {
        this.gameCartridge = gameCartridge;
        this.inputManager = inputManager;
    }

    @Override
    public void run() {
        Log.d(MainActivity.DEBUG_TAG, "GameRunner.run()");

        long lastTime = System.currentTimeMillis();

        int frameCounter = 0;
        int timeCounter = 0;

        //GAME LOOP
        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;

            timeCounter += elapsed;

            if (timeCounter >= TIME_PER_FRAME) {

                /////////////////////
                inputManager.update();
                gameCartridge.update(elapsed);
                gameCartridge.render();
                /////////////////////

                frameCounter++;
                if (frameCounter >= 60) {
                    frameCounter = 0;
                }

                timeCounter = 0;
            }

            lastTime = now;
        }
    }

    //CAN'T NAME THIS "stop()" because Thread already has a "stop()".
    public void shutdown() {
        running = false;
    }

}