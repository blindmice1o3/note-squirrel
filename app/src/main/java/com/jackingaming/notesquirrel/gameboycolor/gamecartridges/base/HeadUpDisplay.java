package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;

public class HeadUpDisplay {

    private Bitmap firstItem;
    private Bitmap secondItem;

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public HeadUpDisplay(GameCartridge gameCartridge) {
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        Item wateringCan = new Item(gameCartridge.getContext().getResources(), Item.Id.WATERING_CAN);
        firstItem = wateringCan.getImage();
    }



    public void update(long elapsed) {

    }

    public void render(Canvas canvas) {
        Rect firstItemClip = new Rect(0, 0, firstItem.getWidth(), firstItem.getHeight());
        Rect screenPosition = new Rect(
                (int)(85 * widthPixelToViewportRatio),
                (int)(10 * heightPixelToViewportRatio),
                (int)((85 + firstItem.getWidth()) * widthPixelToViewportRatio),
                (int)((10 + firstItem.getHeight()) * heightPixelToViewportRatio) );
        ////////////////////////////////////////////////////////////////////////
        canvas.drawBitmap(firstItem, firstItemClip, screenPosition, null);
        ////////////////////////////////////////////////////////////////////////
    }

}