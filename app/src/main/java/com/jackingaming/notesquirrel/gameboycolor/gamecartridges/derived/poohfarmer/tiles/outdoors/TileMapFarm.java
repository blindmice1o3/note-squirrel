package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.outdoors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;

public class TileMapFarm extends TileMap {

    // Each pixel represents a tile.
    private Bitmap rgbTileMap;

    public TileMapFarm(Handler handler, Scene.Id sceneID) {
        super(handler, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 4;
        ySpawnIndex = 4;
    }

    @Override
    protected void initTransferPoints() {
        //intentionally blank (for now).
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        texture = Assets.cropFarmSpring(resources);

        //image-source-file of the farm's tiles stored as pixels.
        rgbTileMap = BitmapFactory.decodeResource(resources, R.drawable.tile_map_farm);;
    }

    @Override
    protected void initTiles() {
        //rgbTileMap is an image where each pixel represents a tile.
        int columns = rgbTileMap.getWidth();            //Always need.
        int rows = rgbTileMap.getHeight();              //Always need.
        widthSceneMax = columns * tileWidth;            //Always need.
        heightSceneMax = rows * tileHeight;             //Always need.

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        tiles = new TileType[rows][columns];            //Always need.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //DEFINE EACH ELEMENT.
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int pixel = rgbTileMap.getPixel(x, y);

                if (pixel == Color.BLACK) {
                    tiles[y][x] = TileType.SOLID;
                } else if (pixel == Color.WHITE) {
                    tiles[y][x] = TileType.WALKABLE;
                } else if (pixel == Color.RED) {
                    tiles[y][x] = TileType.SIGN_POST;
                } else if (pixel == Color.GREEN) {
                    tiles[y][x] = TileType.TRANSFER_POINT;
                }
                //TODO: handle special tiles (stashWood, flowerPlot, hotSpring)
                else if (pixel == Color.BLUE) {
                    tiles[y][x] = TileType.SOLID;
                }
            }
        }
    }
}