package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors;

import android.content.res.Resources;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMapLoader;

import java.util.HashMap;

public class TileMapSheepPen extends TileMap {

    private boolean isFull;
    private String stringOfTiles;

    public TileMapSheepPen(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);

        isFull = false;
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 7;
        ySpawnIndex = 12;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //TODO: Use tileWidth and tileHeight instance member instead of constant.
        transferPoints.put( Scene.Id.FARM, new Rect(6*TILE_WIDTH, 13*TILE_HEIGHT, (6*TILE_WIDTH)+(3*TILE_WIDTH), (13*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        //////////////////////////////////////////////////////////////////////////////////////////////
        texture = (isFull) ? Assets.cropSheepPenFull(resources) : Assets.cropSheepPenEmpty(resources);
        //////////////////////////////////////////////////////////////////////////////////////////////

        //text-source-file of SHEEP_PEN stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tile_sheep_pen);
    }

    @Override
    protected void initTiles() {
        tiles = TileMapLoader.convertStringToTiles(stringOfTiles);

        int columns = tiles[0].length;          //Always need.
        int rows = tiles.length;                //Always need.
        widthSceneMax = columns * tileWidth;    //Always need.
        heightSceneMax = rows * tileHeight;     //Always need.
    }

    public void toggleIsFull(Resources resources) {
        /////////////////
        isFull = !isFull;
        /////////////////

        //////////////////////////////////////////////////////////////////////////////////////////////
        texture = (isFull) ? Assets.cropSheepPenFull(resources) : Assets.cropSheepPenEmpty(resources);
        //////////////////////////////////////////////////////////////////////////////////////////////
    }

}