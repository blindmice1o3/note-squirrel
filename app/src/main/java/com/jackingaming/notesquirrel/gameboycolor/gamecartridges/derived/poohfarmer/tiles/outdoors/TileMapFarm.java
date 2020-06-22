package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.outdoors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;

import java.util.HashMap;

public class TileMapFarm extends TileMap {

    // Each pixel represents a tile.
    private Bitmap rgbTileMap;

    public TileMapFarm(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
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
        transferPoints = new HashMap<Scene.Id, Rect>();

        //TODO: Use tileWidth and tileHeight instance member instead of constant.
        transferPoints.put( Scene.Id.HOTHOUSE, new Rect(17*TILE_WIDTH, 15*TILE_HEIGHT, (17*TILE_WIDTH)+(1*TILE_WIDTH), (15*TILE_HEIGHT)+(1*TILE_HEIGHT)) );

        transferPoints.put( Scene.Id.SHEEP_PEN, new Rect(21*TILE_WIDTH, 7*TILE_HEIGHT, (21*TILE_WIDTH)+(1*TILE_WIDTH), (7*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
        transferPoints.put( Scene.Id.CHICKEN_COOP, new Rect(18*TILE_WIDTH, 5*TILE_HEIGHT, (18*TILE_WIDTH)+(1*TILE_WIDTH), (5*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
        transferPoints.put( Scene.Id.COW_BARN, new Rect(10*TILE_WIDTH, 4*TILE_HEIGHT, (10*TILE_WIDTH)+(1*TILE_WIDTH), (4*TILE_HEIGHT)+(1*TILE_HEIGHT)) );

        transferPoints.put( Scene.Id.HOUSE_01, new Rect(5*TILE_WIDTH, 11*TILE_HEIGHT, (5*TILE_WIDTH)+(1*TILE_WIDTH), (11*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
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