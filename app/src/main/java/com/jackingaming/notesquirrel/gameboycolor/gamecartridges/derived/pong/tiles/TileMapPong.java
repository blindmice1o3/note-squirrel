package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.tiles;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.GenericWalkableTile;

import java.util.HashMap;

public class TileMapPong extends TileMap {

    public TileMapPong(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TileMap.TILE_WIDTH;
        tileHeight = TileMap.TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 20;
        ySpawnIndex = ((gameCartridge.getHeightViewport() / 2) -
                (gameCartridge.getPlayer().getHeight() / 2));
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //intentionally blank.
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        texture = Bitmap.createBitmap(gameCartridge.getWidthViewport(),
                gameCartridge.getHeightViewport(), Bitmap.Config.ALPHA_8);
    }

    @Override
    protected void initTiles() {
        widthSceneMax = gameCartridge.getWidthViewport();       //Always need.
        heightSceneMax = gameCartridge.getHeightViewport();     //Always need.
        int columns = widthSceneMax / tileWidth;                //Always need.
        int rows = heightSceneMax / tileHeight;                 //Always need.

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        tiles = new Tile[rows][columns];                    //Always need.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                tiles[y][x] = new GenericWalkableTile();
            }
        }
    }

}