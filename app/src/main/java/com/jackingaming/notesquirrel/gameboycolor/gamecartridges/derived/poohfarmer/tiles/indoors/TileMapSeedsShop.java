package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.GenericSolidTile;

import java.util.HashMap;

public class TileMapSeedsShop extends TileMap {

    public TileMapSeedsShop(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TileMap.TILE_WIDTH;
        tileHeight = TileMap.TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        //intentionally blank.
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //intentionally blank.
    }

    @Override
    protected void initTexture(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        texture = Bitmap.createBitmap(seedsShopSpriteSheet, 31, 14, 160, 104);
    }

    @Override
    protected void initSourceFile(Resources resources) {
        //intentionally blank.
    }

    @Override
    protected void initTiles() {
        widthSceneMax = gameCartridge.getWidthViewport();       //Always need.
        heightSceneMax = gameCartridge.getHeightViewport();     //Always need.
        int columns = 10;                                       //Always need.
        int rows = 10;                                          //Always need.

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        tiles = new Tile[rows][columns];                        //Always need.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                tiles[y][x] = new GenericSolidTile(gameCartridge, x, y);
            }
        }
    }

}