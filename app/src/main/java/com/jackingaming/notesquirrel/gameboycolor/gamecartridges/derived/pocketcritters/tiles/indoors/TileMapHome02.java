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

public class TileMapHome02 extends TileMap {

    private String stringOfTiles;

    public TileMapHome02(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 7;
        ySpawnIndex = 2;
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        transferPoints.put( Scene.Id.HOME_01, new Rect(7*TILE_WIDTH, 1*TILE_HEIGHT, (7*TILE_WIDTH)+(1*TILE_WIDTH), (1*TILE_HEIGHT)+(1*TILE_HEIGHT)) );
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        texture = Assets.cropHome02(resources);

        //text-source-file of HOME_02 stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tile_home02);
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