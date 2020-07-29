package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;

public class BushEntity extends Entity {

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public BushEntity(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        updateImage();
        initBounds();
    }

    public void updateImage() {
        Bitmap spriteSheetItems = BitmapFactory.decodeResource(gameCartridge.getContext().getResources(), R.drawable.gbc_hm2_spritesheet_items);
        image = Bitmap.createBitmap(spriteSheetItems, 120, 0, 16, 16);
    }

    @Override
    public void initBounds() {
        bounds = new Rect(0, 0, width, height);
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {
        Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCartridge.getGameCamera().getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCartridge.getGameCamera().getY()) + height) * heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}