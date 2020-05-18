package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

public class Car extends Creature {

    private GameCamera gameCamera;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Bitmap image;

    public Car(Handler handler, float xCurrent, float yCurrent) {
        super(handler, xCurrent, yCurrent);

        /////////////////////////////////////////////////
        width = 48;
        height = 48;
        bounds = new Rect(0, 0, width, height);
        /////////////////////////////////////////////////

        gameCamera = handler.getGameCartridge().getGameCamera();
        widthPixelToViewportRatio = ((float) handler.getGameCartridge().getWidthViewport()) / gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) handler.getGameCartridge().getHeightViewport()) / gameCamera.getHeightClipInPixel();

        initImage();
    }

    @Override
    public void init() {

    }

    private void initImage() {
        image = Assets.carWhiteRight;
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {
        Rect bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect screenRect = new Rect(
                (int)( (xCurrent - gameCamera.getX()) * widthPixelToViewportRatio),
                (int)( (yCurrent - gameCamera.getY()) * heightPixelToViewportRatio),
                (int)( ((xCurrent - gameCamera.getX()) + width) * widthPixelToViewportRatio),
                (int)( ((yCurrent - gameCamera.getY()) + height) * heightPixelToViewportRatio) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(image, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}