package com.jackingaming.notesquirrel.sandbox.passingthrough.views.threads;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.InputManager;

public class MySurfaceViewUpdaterThread extends Thread {
    private volatile boolean running = true;

    private Game game;
    private InputManager inputManager;

    public MySurfaceViewUpdaterThread(Game game, InputManager inputManager) {
        this.game = game;
        this.inputManager = inputManager;
    }

    @Override
    public void run() {
        long last = System.currentTimeMillis();

        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - last;
            last = now;

            /////////////////////
            inputManager.update(elapsed);
            game.update(elapsed);
            game.draw();
            /////////////////////

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}
