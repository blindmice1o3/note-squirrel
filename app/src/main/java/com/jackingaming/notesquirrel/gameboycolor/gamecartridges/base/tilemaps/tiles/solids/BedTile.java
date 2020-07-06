package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class BedTile extends Tile {

    public BedTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

    public void execute(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "BedTile.execute(GameCartridge)");

        Scene sceneFarm = gameCartridge.getSceneManager().getScene(Scene.Id.FARM);
        int column = sceneFarm.getTileMap().getWidthSceneMax() / sceneFarm.getTileMap().getTileWidth();
        int row = sceneFarm.getTileMap().getHeightSceneMax() / sceneFarm.getTileMap().getTileHeight();

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                Tile tile = sceneFarm.getTileMap().getTile(x, y);

                if (tile instanceof GrowableGroundTile) {
                    GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

                    if ( (growableGroundTile.getState() == GrowableTile.State.SEEDED) &&
                            (growableGroundTile.getIsWatered()) ) {

                        if ( growableGroundTile.getType() == GrowableGroundTile.Type.CROP_SEEDED ) {
                            int tileWidth = sceneFarm.getTileMap().getTileWidth();
                            int tileHeight = sceneFarm.getTileMap().getTileHeight();

                            //TODO: currently default is POTATO CropEntity.
                            ///////////////////////////////////////////////////////////////////////////
                            CropEntity sproutling = new CropEntity(gameCartridge, CropEntity.Id.POTATO,
                                    (tile.getxIndex() * tileWidth), (tile.getyIndex() * tileHeight));
                            ///////////////////////////////////////////////////////////////////////////

                            //Tile's CROP_ENTITY
                            growableGroundTile.setCropEntity(sproutling);
                            //Scene's ENTITY_MANAGER
                            sceneFarm.getEntityManager().addEntity(sproutling);

                            //RESET growableGroundTile
                            growableGroundTile.setState(GrowableTile.State.INITIAL);
                            growableGroundTile.setIsWatered(false);
                            growableGroundTile.setType(GrowableGroundTile.Type.EMPTY);
                        }
                    } else {
                        //may be GrowableTile.State.PREPARED (tilled, unseeded) that had been watered.
                        ((GrowableGroundTile)tile).setIsWatered(false);
                    }
                }
            }
        }

    }

}