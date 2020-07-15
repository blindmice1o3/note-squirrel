package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.TimeManager;
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

                //TILES
                if (tile instanceof GrowableGroundTile) {
                    GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

                    switch (growableGroundTile.getType()) {
                        case EMPTY:
                            //TILLED TILES WATERED
                            if ( (growableGroundTile.getState() == GrowableTile.State.PREPARED) &&
                                    (growableGroundTile.getIsWatered())) {
                                //RESET growableGroundTile
                                ((GrowableGroundTile)tile).setIsWatered(false);
                            }
                            break;
                        case CROP_SEEDED:
                            //CROP TILES WATERED
                            if (growableGroundTile.getIsWatered()) {
                                int tileWidth = sceneFarm.getTileMap().getTileWidth();
                                int tileHeight = sceneFarm.getTileMap().getTileHeight();

                                CropEntity.Id id = null;
                                switch (growableGroundTile.getSeedType()) {
                                    case TURNIP:
                                        id = CropEntity.Id.TURNIP;
                                        break;
                                    case POTATO:
                                        id = CropEntity.Id.POTATO;
                                        break;
                                    case TOMATO:
                                        id = CropEntity.Id.TOMATO;
                                        break;
                                    case CORN:
                                        id = CropEntity.Id.CORN;
                                        break;
                                    case EGGPLANT:
                                        id = CropEntity.Id.EGGPLANT;
                                        break;
                                    case PEANUT:
                                        id = CropEntity.Id.PEANUT;
                                        break;
                                    case CARROT:
                                        id = CropEntity.Id.CARROT;
                                        break;
                                    case BROCCOLI:
                                        id = CropEntity.Id.BROCCOLI;
                                        break;
                                }
                                ///////////////////////////////////////////////////////////////////////////
                                CropEntity sproutling = new CropEntity(gameCartridge, id,
                                        (tile.getxIndex() * tileWidth), (tile.getyIndex() * tileHeight));
                                ///////////////////////////////////////////////////////////////////////////

                                //Tile's CROP_ENTITY
                                growableGroundTile.setCropEntity(sproutling);
                                //Scene's ENTITY_MANAGER
                                sceneFarm.getEntityManager().addEntity(sproutling);

                                //RESET growableGroundTile
                                growableGroundTile.changeToStateInitial();
                                growableGroundTile.setIsWatered(false);
                                growableGroundTile.setType(GrowableGroundTile.Type.EMPTY);
                                growableGroundTile.setSeedType(null);
                            }
                            break;
                        case GRASS_SEEDED:
                            //TODO: track age and provide condition
                            growableGroundTile.setType(GrowableGroundTile.Type.GRASS_SPROUTED);

                            //RESET growableGroundTile
                            growableGroundTile.setIsWatered(false);
                            break;
                        case GRASS_SPROUTED:
                            //TODO: track age and provide condition
                            growableGroundTile.setType(GrowableGroundTile.Type.GRASS_HARVESTABLE);

                            //RESET growableGroundTile
                            growableGroundTile.setIsWatered(false);
                            break;
                        case GRASS_HARVESTABLE:
                            //intentionally blank.

                            //RESET growableGroundTile
                            growableGroundTile.setIsWatered(false);
                            break;
                    }

                    //ENTITIES
                    if (growableGroundTile.getCropEntity() != null) {
                        //CropEntity.incrementDaysWatered() will also updateStage() (which updates image).
                        if (growableGroundTile.getCropEntity().getIsWatered()) {
                            growableGroundTile.getCropEntity().incrementDaysWatered();
                        }

                        //RESET cropEntity
                        growableGroundTile.getCropEntity().setIsWatered(false);
                    }
                }
            }
        }

        //////////////////////////////////////////////////
        gameCartridge.getTimeManager().resetInGameClock();
        gameCartridge.getTimeManager().incrementDay();
        //////////////////////////////////////////////////
    }

}