package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;

import java.io.Serializable;

public class HeadUpDisplay
        implements Serializable {

    public static final int MARGIN = 8;
    public static final int BORDER = 3;

    transient private GameCartridge gameCartridge;

    private int widthViewport;
    private int heightViewport;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    transient private Bitmap selectedItem;

    public HeadUpDisplay(GameCartridge gameCartridge) {
        widthViewport = gameCartridge.getWidthViewport();
        heightViewport = gameCartridge.getHeightViewport();
        widthPixelToViewportRatio = ((float) widthViewport) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) heightViewport) /
                gameCartridge.getGameCamera().getHeightClipInPixel();
    }

    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        selectedItem = gameCartridge.getPlayer().getSelectedItem().getImage();
    }

    public void update(long elapsed) {
        selectedItem = gameCartridge.getPlayer().getSelectedItem().getImage();
    }

    public void render(Canvas canvas) {
        //selectedItem's IMAGE position
        Rect selectedItemClip = new Rect(0, 0, selectedItem.getWidth(), selectedItem.getHeight());
        int x0 = widthViewport - (int)(MARGIN * widthPixelToViewportRatio) - (int)(TileMap.TILE_WIDTH * widthPixelToViewportRatio);
        int y0 = (int)(MARGIN * heightPixelToViewportRatio);
        int x1 = widthViewport - (int)(MARGIN * widthPixelToViewportRatio);
        int y1 = (int)(MARGIN * heightPixelToViewportRatio) + (int)(TileMap.TILE_HEIGHT * heightPixelToViewportRatio);
        Rect screenPosition = new Rect(x0, y0, x1, y1);

        //border position
        int x0Border = x0 - BORDER;
        int y0Border = y0 - BORDER;
        int x1Border = x1 + BORDER;
        int y1Border = y1 + BORDER;
        Rect border = new Rect(x0Border, y0Border, x1Border, y1Border);
        Paint paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setColor(Color.BLUE);

        //border's IMAGE
        canvas.drawRect(border, paintBorder);

        //selectedItem's IMAGE
        //////////////////////////////////////////////////////////////////////////////
        canvas.drawBitmap(selectedItem, selectedItemClip, screenPosition, null);
        //////////////////////////////////////////////////////////////////////////////
    }

}