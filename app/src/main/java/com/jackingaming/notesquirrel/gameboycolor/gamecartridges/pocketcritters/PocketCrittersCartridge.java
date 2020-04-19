package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.input.DirectionalPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

public class PocketCrittersCartridge
        implements GameCartridge {

    private Context context;
    private Resources resources;
    private SurfaceHolder holder;
    private int sideSquareScreen;

    private Bitmap texture;

    public PocketCrittersCartridge(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public void init(SurfaceHolder holder, int sideSquareScreen) {
        this.holder = holder;
        this.sideSquareScreen = sideSquareScreen;

        this.texture = Assets.pokemonWorldMapPart1;
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.init(SurfaceHolder, int)... pokemonWorldMapPart1: " + texture.getWidth() + ", " + texture.getHeight());
    }

    @Override
    public void savePresentState() {

    }

    @Override
    public void loadSavedState() {

    }

    @Override
    public void onScreenInput(MotionEvent event) {

    }

    @Override
    public void onDirectionalPadInput(DirectionalPadFragment.Direction direction) {

    }

    @Override
    public void onButtonPadInput(ButtonPadFragment.InputButton inputButton) {

    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            //sceneCurrent.render(canvas);
            //BACKGROUND
            //gameCamera's coordinates
            Rect boundsPalletTown = new Rect(960, 1520, 1279, 1792);
            Rect screenPalletTown = new Rect(0, 0, sideSquareScreen, sideSquareScreen);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            canvas.drawBitmap(texture, boundsPalletTown, screenPalletTown, null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            //FOREGROUND
            /*
            for (Entity entity : entities) {
                entity.render(canvas);
            }
            */
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}