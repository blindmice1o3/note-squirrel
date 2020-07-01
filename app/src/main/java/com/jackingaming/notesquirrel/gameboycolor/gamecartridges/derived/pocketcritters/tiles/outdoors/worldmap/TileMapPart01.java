package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.outdoors.worldmap;

import android.content.res.Resources;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMapLoader;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TileMapPart01 extends TileMap {

    public enum Specs { X_START_TILE_INDEX, X_END_TILE_INDEX, Y_START_TILE_INDEX, Y_END_TILE_INDEX; }

    private Map<Specs, Integer> specs; //ONLY USE FOR full-world-map related TEXTURE and TILES.
    private String stringOfTiles;

    public TileMapPart01(GameCartridge gameCartridge, Scene.Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    protected void initTileSize() {
        tileWidth = TILE_WIDTH;
        tileHeight = TILE_HEIGHT;
    }

    @Override
    protected void initSpawnPosition() {
        xSpawnIndex = 69;
        ySpawnIndex = 103;

        //ONLY USE FOR full-world-map related TEXTURE and TILES.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        specs = new HashMap<Specs, Integer>();
        specs.put(Specs.X_START_TILE_INDEX, 0);
        specs.put(Specs.X_END_TILE_INDEX, 80);
        specs.put(Specs.Y_START_TILE_INDEX, 104);
        specs.put(Specs.Y_END_TILE_INDEX, 223);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    @Override
    protected void initTransferPoints() {
        transferPoints = new HashMap<Scene.Id, Rect>();

        //TODO: Clean up values.
        transferPoints.put( Scene.Id.HOME_01, new Rect(1040, (3248-(104*TILE_HEIGHT)), 1040+TILE_WIDTH, (3248-(104*TILE_HEIGHT))+TILE_HEIGHT) );
        transferPoints.put( Scene.Id.HOME_RIVAL, new Rect(1168, (3248-(104*TILE_HEIGHT)), 1168+TILE_WIDTH, (3248-(104*TILE_HEIGHT))+TILE_HEIGHT) );
        transferPoints.put( Scene.Id.LAB, new Rect(1152, (3344-(104*TILE_HEIGHT)), 1152+TILE_WIDTH, (3344-(104*TILE_HEIGHT))+TILE_HEIGHT) );
    }

    @Override
    protected void initTextureAndSourceFile(Resources resources) {
        texture = Assets.cropWorldMapPart01(resources, specs);

        //text-source-file of the FULL world map stored as String.
        stringOfTiles = TileMapLoader.loadFileAsString(resources, R.raw.tiles_world_map);
    }

    @Override
    protected void initTiles() {
        //FULL world map (280-tiles by 270-tiles).
        Tile[][] fullWorldMap = TileMapLoader.convertStringToTiles(stringOfTiles);

        //DEFINE EACH ELEMENT. (TO CROP TO PROPER SIZE)
        //TODO: these values should be used to crop the full map IMAGE from Assets class.
        int xStartTileIndex = specs.get(Specs.X_START_TILE_INDEX);  //In terms of number of TILE.
        int xEndTileIndex = specs.get(Specs.X_END_TILE_INDEX);      //EXCLUSIVE (can be +1 index out of bound).
        int yStartTileIndex = specs.get(Specs.Y_START_TILE_INDEX);  //In terms of number of TILE.
        int yEndTileIndex = specs.get(Specs.Y_END_TILE_INDEX);      //EXCLUSIVE (can be +1 index out of bound [e.g. array.length]).

        int columns = xEndTileIndex - xStartTileIndex;  //Always need.
        int rows = yEndTileIndex - yStartTileIndex;     //Always need.
        widthSceneMax = columns * tileWidth;            //Always need.
        heightSceneMax = rows * tileHeight;             //Always need.

        //CROPPED world map.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        tiles = new Tile[rows][columns];            //Always need.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        for (int y = yStartTileIndex; y < yEndTileIndex; y++) {
            // Arrays.copyOfRange()'s "from" is inclusive while "to" is exclusive.
            tiles[y - yStartTileIndex] = Arrays.copyOfRange(fullWorldMap[y], xStartTileIndex, xEndTileIndex);
        }
    }

}