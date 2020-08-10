package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.TimeManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Chicken;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.FlowerEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.FodderEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneChickenCoop;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneHouseLevel01;

public class BedTile extends Tile
        implements TimeManager.TimeManagerListener {

    public BedTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        //////////////////////////////////////////////////////////////////////////////////
        gameCartridge.getTimeManager().registerTimeManagerListener(this,
                5, 0, true);
        //////////////////////////////////////////////////////////////////////////////////
    }

    //TODO: move into SceneChickenCoop.
    private void newDaySceneChickenCoop() {
        Scene sceneChickenCoop = gameCartridge.getSceneManager().getScene(Scene.Id.CHICKEN_COOP);

        //EGG_INCUBATOR
        if (((SceneChickenCoop)sceneChickenCoop).getIsEggIncubating()) {
            ((SceneChickenCoop)sceneChickenCoop).incrementDaysIncubating();

            if (((SceneChickenCoop)sceneChickenCoop).getDaysIncubating() == 3) {
                sceneChickenCoop.getEntityManager().addEntity(
                        new Chicken(gameCartridge, 24, 80, Chicken.Stage.BABY)
                );
                ((SceneChickenCoop)sceneChickenCoop).setIsEggIncubating(false);
                ((SceneChickenCoop)sceneChickenCoop).resetDaysIncubating();
            }
        }

        //ENTITIES (remove all instances of FodderEntity)
        for (Entity entity : sceneChickenCoop.getEntityManager().getEntities()) {
            if (entity instanceof FodderEntity) {
                //TODO: implement feeding ChickenEntity.
                entity.setActive(false);
            }
        }
        //////////////////////////////////////////////////////////
        ((SceneChickenCoop)sceneChickenCoop).resetFodderCounter();
        //////////////////////////////////////////////////////////
    }

    private void newDaySceneHothouse() {
        Scene sceneHothouse = gameCartridge.getSceneManager().getScene(Scene.Id.HOTHOUSE);
        int column = sceneHothouse.getTileMap().getWidthSceneMax() / sceneHothouse.getTileMap().getTileWidth();
        int row = sceneHothouse.getTileMap().getHeightSceneMax() / sceneHothouse.getTileMap().getTileHeight();

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                Tile tile = sceneHothouse.getTileMap().getTile(x, y);

                //TILES
                if (tile instanceof GrowableTableTile) {
                    GrowableTableTile growableTableTile = (GrowableTableTile) tile;

                    switch (growableTableTile.getState()) {
                        case INITIAL:
                            //intentionally blank.
                            break;
                        case PREPARED:
                            if (growableTableTile.getIsWatered()) {
                                //ENTITIES
                                if (growableTableTile.getFlowerEntity() != null) {
                                    //FlowerEntity.incrementDaysWatered() will also updateStage() (which updates image).
                                    if (growableTableTile.getFlowerEntity().getIsWatered()) {
                                        growableTableTile.getFlowerEntity().incrementDaysWatered();
                                    }

                                    //RESET flowerEntity
                                    growableTableTile.getFlowerEntity().setIsWatered(false);
                                }

                                //RESET growableTableTile (for scenario: potted, watered.
                                growableTableTile.setIsWatered(false);
                            }
                            break;
                        case SEEDED:
                            if (growableTableTile.getIsWatered()) {
                                int tileWidth = sceneHothouse.getTileMap().getTileWidth();
                                int tileHeight = sceneHothouse.getTileMap().getTileHeight();

                                FlowerEntity.Id id = null;
                                switch (growableTableTile.getSeedType()) {
                                    case MYSTERY:
                                        id = FlowerEntity.Id.MYSTERY;
                                        break;
                                    case GERANIUM:
                                        id = FlowerEntity.Id.GERANIUM;
                                        break;
                                    case PRIMROSE:
                                        id = FlowerEntity.Id.PRIMROSE;
                                        break;
                                    case LAVENDER:
                                        id = FlowerEntity.Id.LAVENDER;
                                        break;
                                    case ORCHID:
                                        id = FlowerEntity.Id.ORCHID;
                                        break;
                                    case SAGE:
                                        id = FlowerEntity.Id.SAGE;
                                        break;
                                    case SAFFRON:
                                        id = FlowerEntity.Id.SAFFRON;
                                        break;
                                    case ROSEMARY:
                                        id = FlowerEntity.Id.ROSEMARY;
                                        break;
                                    case CHAMOMILE:
                                        id = FlowerEntity.Id.CHAMOMILE;
                                        break;
                                }
                                ///////////////////////////////////////////////////////////////////////////
                                FlowerEntity sproutling = new FlowerEntity(gameCartridge, id,
                                        (tile.getxIndex() * tileWidth),
                                        //!!!!!!!!!!!!minus 8 pixels!!!!!!!!!!!!
                                        ((tile.getyIndex() * tileHeight) - 8) );
                                        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                ///////////////////////////////////////////////////////////////////////////

                                //Tile's FLOWER_ENTITY
                                growableTableTile.setFlowerEntity(sproutling);
                                //Scene's ENTITY_MANAGER
                                sceneHothouse.getEntityManager().addEntity(sproutling);

                                //RESET growableTableTile
                                growableTableTile.changeToStatePrepared();
                                growableTableTile.setIsWatered(false);
                            }
                            break;
                    }
                }
            }
        }
    }

    private void newDaySceneFarm() {
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
                            if ((growableGroundTile.getState() == GrowableTile.State.PREPARED) &&
                                    (growableGroundTile.getIsWatered())) {
                                //RESET growableGroundTile
                                ((GrowableGroundTile) tile).setIsWatered(false);
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
    }

    //TODO: sleep, TimeManager increment day, check all GrowableTile for isWatered,
    // if GrowableTile has state as SEEDED -> instantiate CropEntity/GrassEntity (revert state to INITIAL)
    // if GrowableGroundTile.cropEntity is not null -> increment that CropEntity's age and update its stage.
    public void execute(GameCartridge gameCartridge, boolean hasLeftHouseToday) {
        Log.d(MainActivity.DEBUG_TAG, "BedTile.execute(GameCartridge)");

        if (hasLeftHouseToday) {
            newDaySceneFarm();
            newDaySceneHothouse();
            newDaySceneChickenCoop();

            /////////////////////////////////////////////////////////////////////
            gameCartridge.getTimeManager().callRemainingActiveEventTimeObjects();
            gameCartridge.getTimeManager().setAllEventTimeObjectsToActive();
            gameCartridge.getTimeManager().resetInGameClock();
            gameCartridge.getTimeManager().incrementDay();
            /////////////////////////////////////////////////////////////////////

            //Have to leave the house before able to go to bed again
            SceneHouseLevel01 sceneHouseLevel01 = (SceneHouseLevel01) gameCartridge.getSceneManager().getScene(Scene.Id.HOUSE_01);
            sceneHouseLevel01.setHasLeftHouseToday(false);
        }
    }

    @Override
    public void executeTimedEvent() {
        Log.d(MainActivity.DEBUG_TAG, "BedTile.executeTimedEvent() sell stash");
        /////////////////////////////////////////////////////
        ShippingBinTile.sellStash(gameCartridge.getPlayer());
        /////////////////////////////////////////////////////
    }

}