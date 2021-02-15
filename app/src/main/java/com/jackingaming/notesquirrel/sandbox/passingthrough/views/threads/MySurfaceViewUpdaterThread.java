package com.jackingaming.notesquirrel.sandbox.passingthrough.views.threads;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MySurfaceViewUpdaterThread extends Thread {
    private volatile boolean running = true;

    private Game game;

    public MySurfaceViewUpdaterThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        long last = System.currentTimeMillis();

        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - last;
            last = now;

            /////////////////////
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
