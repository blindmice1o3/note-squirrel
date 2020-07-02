package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.tiles;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.GenericWalkableTile;

import java.util.HashMap;

public class TileMapFrogger extends TileMap {

    public TileMapFrogger(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = 48;
        tileHeight = 48;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 10;
        ySpawnIndex = 14;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //intentionally blank.
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        texture = BitmapFactory.decodeResource(resources, R.drawable.frogger_background);
    }

    @Override
    protected void initTiles() {
        widthSceneMax = texture.getWidth();         //Always need.
        heightSceneMax = texture.getHeight();       //Always need.
        int columns = widthSceneMax / tileWidth;    //Always need.
        int rows = heightSceneMax / tileHeight;     //Always need.

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        tiles = new Tile[rows][columns];        //Always need.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                tiles[y][x] = new GenericWalkableTile(gameCartridge, x, y);
            }
        }
    }

}