package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.entities.stationary;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;

public class FishermanOld extends Entity {

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    transient private Bitmap imageDown;
    transient private Bitmap imageLeft;
    transient private Bitmap imageRight;
    transient private Bitmap imageDownWithFaceMask;

    public FishermanOld(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "FishermanOld.init(GameCartridge)");
        this.gameCartridge = gameCartridge;
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        initImage(gameCartridge.getContext().getResources());
        initBounds();
    }

    @Override
    public void initBounds() {
        Log.d(MainActivity.DEBUG_TAG, "FishermanOld.initBounds()");
        bounds = new Rect(0, 0, width, height);
    }

    public void initImage(Resources resources) {
        Bitmap spriteSheetItems = BitmapFactory.decodeResource(resources, R.drawable.snes_hm_fisherman);

        int x = 6;
        int y = 6;
        int width = 23;
        int height = 24;
        imageDown = Bitmap.createBitmap(spriteSheetItems, x, y, width, height);
        x += (width + 1);
        imageLeft = Bitmap.createBitmap(spriteSheetItems, x, y, width, height);
        x += (width + 1);
        imageRight = Bitmap.createBitmap(spriteSheetItems, x, y, width, height);
        x += (width + 1);
        imageDownWithFaceMask = Bitmap.createBitmap(spriteSheetItems, x, y, width, height);
    }

    @Override
    public void update(long elapsed) {
        //intentionally blank.
    }

    @Override
    public void render(Canvas canvas) {
        Rect rectOfImage = new Rect(0, 0, imageDownWithFaceMask.getWidth(), imageDownWithFaceMask.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCartridge.getGameCamera().getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCartridge.getGameCamera().getY()) + height) * heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(imageDownWithFaceMask, rectOfImage, rectOnScreen, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}
