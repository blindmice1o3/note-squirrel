package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;

import java.io.Serializable;

public class Tile
        implements Serializable {

    public enum Walkability { SOLID, WALKABLE; }

    transient protected GameCartridge gameCartridge;
    protected int xIndex, yIndex;

    protected Walkability walkability;
    transient protected Bitmap image;

    public Tile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        this.gameCartridge = gameCartridge;
        this.xIndex = xIndex;
        this.yIndex = yIndex;

        this.walkability = Walkability.SOLID;
        this.image = null;
    }

    public void init(GameCartridge gameCartridge, Bitmap image) {
        this.gameCartridge = gameCartridge;
        this.image = image;
    }

    public Walkability getWalkability() {
        return walkability;
    }

    public void render(Canvas canvas, int x, int y) {
        if (image != null) {
            float widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                    gameCartridge.getGameCamera().getWidthClipInPixel();
            float heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                    gameCartridge.getGameCamera().getHeightClipInPixel();

            Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
            Rect rectOnScreen = new Rect(
                    (int)( x * widthPixelToViewportRatio ),
                    (int)( y * heightPixelToViewportRatio ),
                    (int)( (x + TileMap.TILE_WIDTH) * widthPixelToViewportRatio ),
                    (int)( (y + TileMap.TILE_HEIGHT) * heightPixelToViewportRatio ) );

            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        }
    }

}