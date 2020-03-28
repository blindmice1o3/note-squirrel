package com.jackingaming.notesquirrel.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.game.sprites.Corgi;
import com.jackingaming.notesquirrel.game.sprites.Sprite;

public class Game {

    private SurfaceHolder holder;
    private Resources resources;

    private Corgi corgi;

    public Game(int widthSurfaceView, int heightSurfaceView, SurfaceHolder holder, Resources resources) {
        this.holder = holder;
        this.resources = resources;

        corgi = new Corgi(widthSurfaceView, heightSurfaceView);
    }

    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "Game.init()");
        Bitmap spriteSheetCorgiCrusade = BitmapFactory.decodeResource(resources, R.drawable.corgi_crusade);

        corgi.init(spriteSheetCorgiCrusade);
    }

    public void update(long elapsed) {
        corgi.update(elapsed);
    }

    public void draw() {
        //synchronize?
        Canvas canvas = holder.lockCanvas();

        if (canvas != null) {
            //Log.d(MainActivity.DEBUG_TAG, "Game.draw() canvas is NOT null.");
            //clear the canvas (by painting the background white).
            canvas.drawColor(Color.WHITE);

            /////////////////////////////////////////////////////////////////////////
            //draw on the canvas:
            //sprites
            corgi.draw(canvas);
            /////////////////////////////////////////////////////////////////////////

            //unlock it and post our updated drawing to it.
            holder.unlockCanvasAndPost(canvas);
        }
    }

}