package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.staging;

import android.util.Log;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.Frame;

import java.util.List;

public class ImageViewAnimationRunner implements Runnable {

    private volatile boolean running;

    private ImageView imageView;
    private List<Frame> framesSelectedByUser;

    public ImageViewAnimationRunner(ImageView imageView, List<Frame> framesSelectedByUser) {
        this.running = false;

        this.imageView = imageView;
        this.framesSelectedByUser = framesSelectedByUser;
    }

    public void shutdown() {
        running = false;
    }

    @Override
    public void run() {
        int index = 0;
        int speed = 3_000;
        long lastTime = System.currentTimeMillis();
        long timer = 0L;

        running = true;
        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;

            timer += elapsed;

            Log.d(MainActivity.DEBUG_TAG, "timer: " + timer);
            if (timer > speed) {
                index++;
                timer = 0;

                if (index >= framesSelectedByUser.size()) {
                    index = 0;
                }

                Log.d(MainActivity.DEBUG_TAG, "WHILE LOOP index: " + index);
                Frame frame = framesSelectedByUser.get(index);
                imageView.setImageBitmap(frame.getImageUserSelected());
            }

            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}