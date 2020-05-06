package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;

public class Robot extends Entity {

    private Bitmap mImage;
    private GameCamera mGameCamera;
    private float mWidthPixelToViewportRatio;
    private float mHeightPixelToViewportRatio;

    public Robot(Context context, GameCamera gameCamera, float xCurrent, float yCurrent,
                 float widthPixelToViewportRatio, float heightPixelToViewportRatio) {
        super(xCurrent, yCurrent);
        this.mGameCamera = gameCamera;
        this.mWidthPixelToViewportRatio = widthPixelToViewportRatio;
        this.mHeightPixelToViewportRatio = heightPixelToViewportRatio;

        initImage(context.getResources());
    }

    @Override
    public void init() {

    }

    private void initImage(Resources resources) {
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.snes_chrono_trigger_r_series);

        int xStart = 1 * (32 + 8);
        int yStart = 6 * (32 + 8);
        mImage = Bitmap.createBitmap(spriteSheet, xStart, yStart, 32, 32);
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {
        Rect bounds = new Rect(0, 0, mImage.getWidth(), mImage.getHeight());
        Rect screenRect = new Rect(
                (int)( (xCurrent - mGameCamera.getX()) * mWidthPixelToViewportRatio ),
                (int)( (yCurrent - mGameCamera.getY()) * mHeightPixelToViewportRatio ),
                (int)( ((xCurrent - mGameCamera.getX()) + width) * mWidthPixelToViewportRatio ),
                (int)( ((yCurrent - mGameCamera.getY()) + height) * mHeightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(mImage, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}