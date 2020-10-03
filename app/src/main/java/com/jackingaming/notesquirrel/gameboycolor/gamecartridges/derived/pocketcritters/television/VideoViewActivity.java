package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.jackingaming.notesquirrel.R;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity {

    private static final String FILE_NAME = "television_news_recording";

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = (VideoView) findViewById(R.id.videoview_launched_by_television_activity);

        if (mediaController == null) {
            mediaController = new MediaController(VideoViewActivity.this);
            mediaController.setAnchorView(videoView);
        }
        videoView.setMediaController(mediaController);

        Uri rawUri = getRawUri(FILE_NAME);
        videoView.setVideoURI(rawUri);

        videoView.start();
    }

    private Uri getRawUri(String fileName) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator + File.separator + getPackageName() + File.separator + "raw/" + fileName);
    }

}