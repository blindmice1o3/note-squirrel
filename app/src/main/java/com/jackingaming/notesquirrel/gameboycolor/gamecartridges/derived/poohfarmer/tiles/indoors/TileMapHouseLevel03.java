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

public class TileMapHouseLevel03 extends TileMap {

    private String stringOfTiles;

    public TileMapHouseLevel03(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 6;
        ySpawnIndex = 11;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //TODO: Use tileWidth and tileHeight instance member instead of constant.
        transferPoints.put( Scene.Id.FARM, new Rect(5*TILE_WIDTH, 13*TILE_HEIGHT, (5*TILE_WIDTH)+(2*TILE_WIDTH), (13*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        /////////////////////////////////////////////
        texture = Assets.cropHouseLevel03(resources);
        /////////////////////////////////////////////

        //text-source-file of HOUSE_LEVEL_03 stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tile_house_level_03);
    }

    @Override
    protected void initTiles() {
        tiles = TileMapLoader.convertStringToTiles(stringOfTiles);

        int columns = tiles[0].length;          //Always need.
        int rows = tiles.length;                //Always need.
        widthSceneMax = columns * tileWidth;    //Always need.
        heightSceneMax = rows * tileHeight;     //Always need.
    }

}
