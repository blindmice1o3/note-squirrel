package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors;

import android.content.res.Resources;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMapLoader;

import java.util.HashMap;

public class TileMapHomeRival extends TileMap {

    private String stringOfTiles;

    public TileMapHomeRival(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 2;
        ySpawnIndex = 6;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        transferPoints.put( Scene.Id.PART_01, new Rect(2*TILE_WIDTH, 7*TILE_HEIGHT, (2*TILE_WIDTH)+(2*TILE_WIDTH), (7*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
    }

    @Override
    protected void initTexture(Resources resources) {
        texture = Assets.cropHomeRival(resources);
    }

    @Override
    protected void initSourceFile(Resources resources) {
        //text-source-file of HOME_RIVAL stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tile_home_rival);
    }

    @Override
    protected void initTiles() {
        tiles = TileMapLoader.convertStringToTiles(gameCartridge, stringOfTiles);

        int columns = tiles[0].length;          //Always need.
        int rows = tiles.length;                //Always need.
        widthSceneMax = columns * tileWidth;    //Always need.
        heightSceneMax = rows * tileHeight;     //Always need.
    }

}