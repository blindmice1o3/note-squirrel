package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.FeedingStallTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.GenericWalkableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Holdable;

public class FodderEntity extends Entity
        implements Holdable {

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public FodderEntity(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
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

        initImage(gameCartridge.getContext().getResources());
        initBounds();
    }

    public void initImage(Resources resources) {
        Bitmap spriteSheetItems = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);
        image = Bitmap.createBitmap(spriteSheetItems, 103, 1, 16, 16);
    }

    @Override
    public void initBounds() {
        bounds = new Rect(0, 0, width, height);
    }

    @Override
    public void updatePosition(float xCurrent, float yCurrent) {
        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
    }

    @Override
    public boolean drop(Tile tile) {
        //Drop onto ground.
        if ( (tile instanceof GrowableGroundTile) || (tile instanceof GenericWalkableTile)) {
            int xCurrentTile = tile.getxIndex() * TileMap.TILE_WIDTH;
            int yCurrentTile = tile.getyIndex() * TileMap.TILE_HEIGHT;

            ////////////////////////
            xCurrent = xCurrentTile;
            yCurrent = yCurrentTile;
            ////////////////////////

            return true;
        }
        //Drop into FeedingStallTile.
        else if (tile instanceof FeedingStallTile) {
            //TODO: increment SceneChickenCoop's fodderCount.

            return true;
        }

        return false;
    }

    @Override
    public void update(long elapsed) {
        //intentionally blank.
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