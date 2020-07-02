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

public class TileMapLab extends TileMap {

    private String stringOfTiles;

    public TileMapLab(GameCartridge gameCartridge, Scene.Id sceneID) {
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
        ySpawnIndex = 10;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //TODO: Use tileWidth and tileHeight instance member instead of constant.
        transferPoints.put( Scene.Id.PART_01, new Rect(4*TILE_WIDTH, 11*TILE_HEIGHT, (4*TILE_WIDTH)+(2*TILE_WIDTH), (11*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
    }

    @Override
    protected void initTexture(Resources resources) {
        texture = Assets.cropLab(resources);
    }

    @Override
    protected void initSourceFile(Resources resources) {
        //text-source-file of LAB stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tile_lab);
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