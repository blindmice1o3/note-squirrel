package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;

import java.util.ArrayList;
import java.util.Map;

public abstract class TileMap {

    public enum TileType { SOLID, WALKABLE, TELEVISION, COMPUTER, GAME_CONSOLE, SIGN_POST, TRANSFER_POINT; }

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    private Handler handler;
    private Scene.Id sceneID;

    protected TileType[][] tiles;
    protected Bitmap texture;

    protected int tileWidth;
    protected int tileHeight;
    protected int xSpawnIndex;
    protected int ySpawnIndex;
    protected Map<Scene.Id, Rect> transferPoints;

    protected int widthSceneMax;
    protected int heightSceneMax;

    public TileMap(Handler handler, Scene.Id sceneID) {
        this.handler = handler;
        this.sceneID = sceneID;

        //@@@@@@@@@@@@@@@@@@@
        initTileSize();
        initSpawnPosition();
        initTransferPoints();
        initTextureAndSourceFile(handler.getGameCartridge().getContext().getResources());
        initTiles();
        //@@@@@@@@@@@@@@@@@@@
    }

    protected abstract void initTileSize();
    protected abstract void initSpawnPosition();
    protected abstract void initTransferPoints();
    protected abstract void initTextureAndSourceFile(Resources resources);
    protected abstract void initTiles();

    //TODO: do "recordLocationPriorToTransfer()" and "loadLocationPriorToTransfer()"
    // within TileMap instead of Scene's "enter()" and "exit()"
    // (e.g. instead of just having spawnPosition... also store enterPosition and exitPosition).
    public void checkTransferPointsCollision(Rect collisionBounds) {
        if ((sceneID != Scene.Id.FARM) && (sceneID != Scene.Id.FROGGER)) {
            for (Scene.Id id : transferPoints.keySet()) {
                if (transferPoints.get(id).intersect(collisionBounds)) {
                    SceneManager sceneManager = handler.getGameCartridge().getSceneManager();
                    //POP
                    if ( (id == Scene.Id.PART_01) ||
                            ((id == Scene.Id.HOME_01) && (sceneManager.getCurrentScene().getSceneID() == Scene.Id.HOME_02)) ) {
                        Object[] directionFacing = { handler.getGameCartridge().getPlayer().getDirection(),
                                handler.getGameCartridge().getPlayer().getMoveSpeed() };
                        sceneManager.pop(directionFacing);
                    }
                    //PUSH
                    else {
                        Object[] directionFacing = { handler.getGameCartridge().getPlayer().getDirection(),
                                handler.getGameCartridge().getPlayer().getMoveSpeed() };
                        sceneManager.push(id, directionFacing);
                    }
                    //////
                    break;
                    //////
                }
            }
        }
    }

    public TileType checkTile(int xIndex, int yIndex) {
        //CHECK BEYOND SCENE BOUND (e.g. inspecting off map)
        if ((xIndex < 0) ||(xIndex >= (widthSceneMax / tileWidth)) ||
                (yIndex < 0) || (yIndex >= (heightSceneMax / tileHeight))) {
            return null;
        }

        Log.d(MainActivity.DEBUG_TAG, "TileMap.checkTile(int, int) (xIndex, yIndex): (" + xIndex + ", " + yIndex + ").");

        return tiles[yIndex][xIndex];
    }

    public boolean isSolid(int xPosition, int yPosition) {
        //CHECK BEYOND SCENE BOUND (e.g. moving off map)
        if ((xPosition < 0) ||(xPosition >= widthSceneMax) ||
                (yPosition < 0) || (yPosition >= heightSceneMax)) {
            return true;
        }

        int indexColumn = xPosition / tileWidth;
        int indexRow = yPosition / tileHeight;

        //CHECK FOR TileType.WALKABLE
        if (tiles[indexRow][indexColumn] == TileType.WALKABLE) {
            return false;
        }

        //DEFAULT IS TileType.SOLID (not walkable)
        return true;
    }

    public Scene.Id getSceneID() {
        return sceneID;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getxSpawnIndex() {
        return xSpawnIndex;
    }

    public int getySpawnIndex() {
        return ySpawnIndex;
    }

    public int getWidthSceneMax() {
        return widthSceneMax;
    }

    public int getHeightSceneMax() {
        return heightSceneMax;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
//    private void initTilesPart01() {
//        Log.d(MainActivity.DEBUG_TAG, "TileMap.initTilesPart01()");
//
//        ////////////////////////////////////////////////////////////////////////////////////////////
//
//        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//
//        ///////////////////////////////////////////////////////////////////////////
//        //NEED TO USE TileSpriteToRGBConverter FROM IntelliJ's PocketCritters TO
//        //GENERATE TileType[][] OF solid AND walkable FOR TILE COLLISION DETECTION.
//        ///////////////////////////////////////////////////////////////////////////
//        ////////////////////////////////////////////////////////////////////////////////////////////
//        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
//
//        ArrayList<Bitmap> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
//        ArrayList<Bitmap> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
//
//        tiles = tileSpriteToRGBConverter.generateTileMapForCollisionDetection(
//                texture, nonWalkableTileSpriteTargets, walkableTileSpriteTargets);
//        ////////////////////////////////////////////////////////////////////////////////////////////
//    }

    private ArrayList<Bitmap> initWalkableTileSpriteTargets() {
        ArrayList<Bitmap> walkableTileSpriteTargets = new ArrayList<Bitmap>();

        //NON-SOLID TILES
        //Tall-Grass -> possible PocketMonster Encounter!
        walkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1088, 3184 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //tall-grass (ROUTE01)
        walkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 864, 1760 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //tall-grass (ROUTE02)
//        walkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 800, 1184, TILE_WIDTH, TILE_HEIGHT) ); //tall-grass (Place.VIRIDIAN_FOREST [but more like Town.PEWTER_CITY's south])
//        walkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1600, 928, TILE_WIDTH, TILE_HEIGHT) ); //tall-grass (ROUTE03)

        return walkableTileSpriteTargets;
    }

    private ArrayList<Bitmap> initNonWalkableTileSpriteTargets() {
        //SOLID TILES
        ArrayList<Bitmap> nonWalkableTileSpriteTargets = new ArrayList<Bitmap>();

        ////Town.PALLET_TOWN///////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 960, 3376 - 1664, TILE_WIDTH, TILE_HEIGHT) );     //fence-blue (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 3312 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //fence-brown (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1072, 3312 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //sign-post (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 3392 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //NW-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1040, 3392 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //N-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1072, 3392 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //NE-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 3408 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //W-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1072, 3408 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //E-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 976, 3152 - 1664, TILE_WIDTH, TILE_HEIGHT) );     //bush (Town.PALLET_TOWN)

        // building_home (Town.PALLET_TOWN), starting at x == 1024, y == 3216, width/number_of_columns == 4, height/number_of_rows == 3.
        ArrayList<Bitmap> homeNoDoor = tileSpriteToRGBConverter.pullMultipleTiles(
                texture,1024, 3216 - 1664, 4, 3);
        homeNoDoor.remove(9);
        nonWalkableTileSpriteTargets.addAll(
                homeNoDoor
        );

        // building_home_roofTopOfSecondHome (Town.PALLET_TOWN).
        nonWalkableTileSpriteTargets.addAll(
                tileSpriteToRGBConverter.pullMultipleTiles(
                        texture,1152, 3216 - 1664, 4, 1)
        );

        //building_store (Town.PALLET_TOWN), starting at x == 1120, y == 3296, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<Bitmap> buildingStoreNoDoor = tileSpriteToRGBConverter.pullMultipleTiles(
                texture,1120, 3296 - 1664, 6, 4);
        buildingStoreNoDoor.remove(20);
        nonWalkableTileSpriteTargets.addAll(
                buildingStoreNoDoor
        );

        ////Town.VIRIDIAN_CITY/////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 992, 2400 - 1664, TILE_WIDTH, TILE_HEIGHT) );     //W-building_window (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1040, 2400 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //E-building_window (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 2416 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //building_pokecenter_sign (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1120, 2320 - 1664, TILE_WIDTH, TILE_HEIGHT) );    //building_pokemart_sign (Town.VIRIDIAN_CITY)

        //building_no_door (Town.VIRIDIAN_CITY), starting at x == 960, y == 2080, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<Bitmap> buildingNoDoor = tileSpriteToRGBConverter.pullMultipleTiles(
                texture,960, 2080 - 1664, 4, 2);
        nonWalkableTileSpriteTargets.addAll(
                buildingNoDoor
        );

        //building_door_roof (Town.VIRIDIAN_CITY), starting at x == 960, y == 2144, width/number_of_columns == 4, height/number_of_rows == 1.
        ArrayList<Bitmap> buildingDoorRoof = tileSpriteToRGBConverter.pullMultipleTiles(
                texture,960, 2144 - 1664, 4, 1);
        nonWalkableTileSpriteTargets.addAll(
                buildingDoorRoof
        );

        //building_gym_viridian (Town.VIRIDIAN_CITY), starting at x == 1088, y == 2080, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<Bitmap> buildingGymViridian = tileSpriteToRGBConverter.pullMultipleTiles(
                texture,1088, 2080 - 1664, 6, 4);
        buildingGymViridian.remove(22);
        nonWalkableTileSpriteTargets.addAll(
                buildingGymViridian
        );

        ////ROUTE22////////////////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 640, 2512 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //S-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 688, 2512 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //SE-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 688, 2496 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //E-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 640, 2496 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //CENTER-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 688, 2304 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //NE-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 624, 2304 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //N-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 608, 2304 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //NW-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 608, 2320 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //W-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 576, 2224 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //SW-mountain (ROUTE22)

        ////ROUTE02////////////////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 800, 2000 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //bush (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 912, 1968 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //sign-post (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 896, 1984 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //fence-brown (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 800, 1680 - 1664, TILE_WIDTH, TILE_HEIGHT) ); //fence-blue (ROUTE02)

//        ////Place.VIRIDIAN_FOREST//////////////////////////////////////////////////////////////////////////////////////
//
//        // house_door_viridian_forest (Place.VIRIDIAN_FOREST), starting at x == 1024, y == 1376, width/number_of_columns == 4, height/number_of_rows == 2.
//        ArrayList<Bitmap> houseDoorViridianForest = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,1024, 1376, 4, 2);
//        houseDoorViridianForest.remove(5);
//        nonWalkableTileSpriteTargets.addAll(
//                houseDoorViridianForest
//        );
//
//        // building_guard_house_viridian_forest (Place.VIRIDIAN_FOREST), starting at x == 1024, y == 1600, width/number_of_columns == 6, height/number_of_rows == 4.
//        ArrayList<Bitmap> buildingGuardHouseViridianForest = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,1024, 1600, 6, 4);
//        buildingGuardHouseViridianForest.remove(19);
//        nonWalkableTileSpriteTargets.addAll(
//                buildingGuardHouseViridianForest
//        );
//
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 960, 1216, TILE_WIDTH, TILE_HEIGHT) );     //NW-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 960, 1232, TILE_WIDTH, TILE_HEIGHT) );     //W-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 960, 1264, TILE_WIDTH, TILE_HEIGHT) );     //SW-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 976, 1264, TILE_WIDTH, TILE_HEIGHT) );     //S-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1072, 1264, TILE_WIDTH, TILE_HEIGHT) );    //SE-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1072, 1248, TILE_WIDTH, TILE_HEIGHT) );    //E-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 976, 1248, TILE_WIDTH, TILE_HEIGHT) );     //CENTER-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1072, 1216, TILE_WIDTH, TILE_HEIGHT) );    //NE-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 976, 1216, TILE_WIDTH, TILE_HEIGHT) );     //N-mountain (Place.VIRIDIAN_FOREST)
//
//        ////Town.PEWTER_CITY///////////////////////////////////////////////////////////////////////////////////////////
//
//        // building_museum_west (Town.PEWTER_CITY), starting at x == 800, y == 608, width/number_of_columns == 8, height/number_of_rows == 6.
//        ArrayList<Bitmap> buildingMuseumWest = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,800, 608, 8, 6);
//        nonWalkableTileSpriteTargets.addAll(
//                buildingMuseumWest
//        );
//
//        // building_museum_east (Town.PEWTER_CITY), starting at x == 928, y == 608, width/number_of_columns == 6, height/number_of_rows == 4.
//        ArrayList<Bitmap> buildingMuseumEast = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,928, 608, 6, 4);
//        nonWalkableTileSpriteTargets.addAll(
//                buildingMuseumEast
//        );
//
//        // house_door_north (Town.PEWTER_CITY), starting at x == 1088, y == 768, width/number_of_columns == 4, height/number_of_rows == 2.
//        ArrayList<Bitmap> houseDoorNorth = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,1088, 768, 4, 2);
//        houseDoorNorth.remove(5);
//        nonWalkableTileSpriteTargets.addAll(
//                houseDoorNorth
//        );
//
//        // house_door_south (Town.PEWTER_CITY), starting at x == 736, y == 1024, width/number_of_columns == 4, height/number_of_rows == 2.
//        ArrayList<Bitmap> houseDoorSouth = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,736, 1024, 4, 2);
//        houseDoorSouth.remove(5);
//        nonWalkableTileSpriteTargets.addAll(
//                houseDoorSouth
//        );
//
//        // building_gym_pewter (Town.PEWTER_CITY), starting at x == 832, y == 800, width/number_of_columns == 6, height/number_of_rows == 4.
//        ArrayList<Bitmap> buildingGymPewter = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,832, 800, 6, 4);
//        buildingGymPewter.remove(22);
//        nonWalkableTileSpriteTargets.addAll(
//                buildingGymPewter
//        );
//
//        // building_critter_center (Town.PEWTER_CITY),  starting at x == 832, y == 928, width/number_of_columns == 4, height/number_of_rows == 4.
//        ArrayList<Bitmap> buildingCritterCenter = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,832, 928, 4, 4);
//        buildingCritterCenter.remove(13);
//        nonWalkableTileSpriteTargets.addAll(
//                buildingCritterCenter
//        );
//
//        // building_critter_mart (Town.PEWTER_CITY),  starting at x == 992, y == 800, width/number_of_columns == 4, height/number_of_rows == 4.
//        ArrayList<Bitmap> buildingCritterMart = tileSpriteToRGBConverter.pullMultipleTiles(
//                texture,992, 800, 4, 4);
//        buildingCritterMart.remove(13);
//        nonWalkableTileSpriteTargets.addAll(
//                buildingCritterMart
//        );

        return nonWalkableTileSpriteTargets;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}