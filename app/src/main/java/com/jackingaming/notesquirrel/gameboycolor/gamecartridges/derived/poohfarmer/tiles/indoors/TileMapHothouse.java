package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors;

import android.content.res.Resources;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMapLoader;

import java.util.HashMap;

public class TileMapHothouse extends TileMap {

    private String stringOfTiles;

    public TileMapHothouse(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 5;
        ySpawnIndex = 12;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //TODO: Use tileWidth and tileHeight instance member instead of constant.
        transferPoints.put( Scene.Id.FARM, new Rect(5*TILE_WIDTH, 13*TILE_HEIGHT, (5*TILE_WIDTH)+(2*TILE_WIDTH), (13*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
    }

    @Override
    protected void initTexture(Resources resources) {
        //////////////////////////////////////////////
        texture = Assets.cropHothouseEmpty(resources);
        //////////////////////////////////////////////
    }

    @Override
    protected void initSourceFile(Resources resources) {
        //text-source-file of HOTHOUSE stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tile_hothouse);
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