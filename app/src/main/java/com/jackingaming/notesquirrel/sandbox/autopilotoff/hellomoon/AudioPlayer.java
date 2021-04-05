package com.jackingaming.notesquirrel.sandbox.autopilotoff.hellomoon;

import android.content.Context;
import android.media.MediaPlayer;

import com.jackingaming.notesquirrel.R;

public class AudioPlayer {
    private MediaPlayer player;

    public void stop() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void play(Context context) {
        stop();

        player = MediaPlayer.create(context, R.raw.corporate_ukulele);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });

        player.start();
    }
}