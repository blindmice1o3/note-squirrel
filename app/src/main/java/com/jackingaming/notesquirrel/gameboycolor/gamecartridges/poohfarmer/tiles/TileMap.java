package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class TileMap {

    public enum TileType { SOLID, WALKABLE, SIGN_POST, TRANSFER_POINT; }
    public static final int TILE_SIZE = 16;

    Context context;

    private Bitmap texture;
    private TileType[][] tiles;

    private int xSpawnIndex;
    private int ySpawnIndex;

    private int columns;
    private int rows;
    private int widthSceneMax;
    private int heightSceneMax;

    private GameCartridge.Id cartridgeID;

    public TileMap(Context context, GameCartridge.Id cartridgeID) {
        this.context = context;
        this.cartridgeID = cartridgeID;

        initSpawnPosition();
        initTexture();
        initTiles();
    }

    private void initSpawnPosition() {
        switch (cartridgeID) {
            case POOH_FARMER:
                xSpawnIndex = 4;
                ySpawnIndex = 4;
                break;
            case POCKET_CRITTERS:
                xSpawnIndex = 69;
                ySpawnIndex = 103;
                break;
        }
    }

    private void initTexture() {
        switch (cartridgeID) {
            case POOH_FARMER:
                //SPRING
                texture = Assets.hm3Farm[0][0];
                break;
            case POCKET_CRITTERS:
                texture = Assets.pokemonWorldMapPart1;
                break;
        }
    }

    private void initTiles() {
        switch (cartridgeID) {
            case POOH_FARMER:
                initTilesPoohFarmer();
                break;
            case POCKET_CRITTERS:
                initTilesPocketCritters();
                break;
        }
    }

    private void initTilesPoohFarmer() {
        Bitmap rgbTileMap = Assets.rgbTileFarm;

        columns = rgbTileMap.getWidth();        //Always need.
        rows = rgbTileMap.getHeight();          //Always need.
        widthSceneMax = columns * TILE_SIZE;    //Always need.
        heightSceneMax = rows * TILE_SIZE;      //Always need.
        tiles = new TileType[rows][columns];    //Always need.

        //DEFINE EACH ELEMENT.
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int pixel = rgbTileMap.getPixel(x, y);

                if (pixel == Color.BLACK) {
                    tiles[y][x] = TileType.SOLID;
                } else if (pixel == Color.WHITE) {
                    tiles[y][x] = TileType.WALKABLE;
                } else if (pixel == Color.RED) {
                    tiles[y][x] = TileType.SIGN_POST;
                } else if (pixel == Color.GREEN) {
                    tiles[y][x] = TileType.TRANSFER_POINT;
                }
                //TODO: handle special tiles (stashWood, flowerPlot, hotSpring)
                else if (pixel == Color.BLUE) {
                    tiles[y][x] = TileType.SOLID;
                }
            }
        }
    }

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private void initTilesPocketCritters() {
        //CROPPED world map.
        columns = texture.getWidth() / TILE_SIZE;   //Always need.
        rows = texture.getHeight() / TILE_SIZE;     //Always need.
        widthSceneMax = texture.getWidth();         //Always need.
        heightSceneMax = texture.getHeight();       //Always need.
        tiles = new TileType[rows][columns];        //Always need.

        //TODO: Instead of parsing the world map image for each run, create something similar to rgbTileFarm.
        ////////////////////////////////////////////////////////////////////////////////////////////
        //text-source-file of the FULL world map stored as String.
        int resId = R.raw.tiles_world_map;
        String stringOfTiles = TileMapLoader.loadFileAsString(context, resId);
        //FULL world map (280-tiles by 270-tiles).
        TileType[][] fullWorldMap = TileMapLoader.convertStringToTiles(stringOfTiles);

        //DEFINE EACH ELEMENT. (TO CROP TO PROPER SIZE)
        //TODO: these values should be used to crop the full map IMAGE from Assets class.
        int xStartTileIndex = 0;    //In terms of number of TILE.
        int yStartTileIndex = 104;  //In terms of number of TILE.
        int xEndTileIndex = 81;     //EXCLUSIVE (can be +1 index out of bound).
        int yEndTileIndex = 223;    //EXCLUSIVE (can be +1 index out of bound [e.g. array.length]).
        for (int y = yStartTileIndex; y < yEndTileIndex; y++) {
            // Arrays.copyOfRange()'s "from" is inclusive while "to" is exclusive.
            tiles[y - yStartTileIndex] = Arrays.copyOfRange(fullWorldMap[y], xStartTileIndex, xEndTileIndex);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        ///////////////////////////////////////////////////////////////////////////
        //NEED TO USE TileSpriteToRGBConverter FROM IntelliJ's PocketCritters TO
        //GENERATE TileType[][] OF solid AND walkable FOR TILE COLLISION DETECTION.
        ///////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
//        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
//
//        ArrayList<Bitmap> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
//        ArrayList<Bitmap> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
//
//        tiles = tileSpriteToRGBConverter.generateTileMapForCollisionDetection(
//                texture, nonWalkableTileSpriteTargets, walkableTileSpriteTargets);
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    public boolean isSolid(int xPosition, int yPosition) {
        //CHECK BEYOND SCENE BOUND (e.g. moving off map)
        if ((xPosition < 0) ||(xPosition >= widthSceneMax) ||
                (yPosition < 0) || (yPosition >= heightSceneMax)) {
            return true;
        }

        int indexColumn = xPosition / TILE_SIZE;
        int indexRow = yPosition / TILE_SIZE;

        //CHECK FOR TileType.WALKABLE
        if (tiles[indexRow][indexColumn] == TileType.WALKABLE) {
            return false;
        }

        //TODO: handle TileType.TRANSFER_POINT

        //DEFAULT IS TileType.SOLID (not walkable)
        return true;
    }

    public Bitmap getTexture() {
        return texture;
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

    private ArrayList<Bitmap> initWalkableTileSpriteTargets() {
        ArrayList<Bitmap> walkableTileSpriteTargets = new ArrayList<Bitmap>();

        //NON-SOLID TILES
        //Tall-Grass -> possible PocketMonster Encounter!
        walkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1088, 3184 - 1664, TILE_SIZE, TILE_SIZE) ); //tall-grass (ROUTE01)
        walkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 864, 1760 - 1664, TILE_SIZE, TILE_SIZE) ); //tall-grass (ROUTE02)
//        walkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 800, 1184, TILE_SIZE, TILE_SIZE) ); //tall-grass (Place.VIRIDIAN_FOREST [but more like Town.PEWTER_CITY's south])
//        walkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1600, 928, TILE_SIZE, TILE_SIZE) ); //tall-grass (ROUTE03)

        return walkableTileSpriteTargets;
    }

    private ArrayList<Bitmap> initNonWalkableTileSpriteTargets() {
        //SOLID TILES
        ArrayList<Bitmap> nonWalkableTileSpriteTargets = new ArrayList<Bitmap>();

        ////Town.PALLET_TOWN///////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 960, 3376 - 1664, TILE_SIZE, TILE_SIZE) );     //fence-blue (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 3312 - 1664, TILE_SIZE, TILE_SIZE) );    //fence-brown (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1072, 3312 - 1664, TILE_SIZE, TILE_SIZE) );    //sign-post (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 3392 - 1664, TILE_SIZE, TILE_SIZE) );    //NW-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1040, 3392 - 1664, TILE_SIZE, TILE_SIZE) );    //N-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1072, 3392 - 1664, TILE_SIZE, TILE_SIZE) );    //NE-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 3408 - 1664, TILE_SIZE, TILE_SIZE) );    //W-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1072, 3408 - 1664, TILE_SIZE, TILE_SIZE) );    //E-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 976, 3152 - 1664, TILE_SIZE, TILE_SIZE) );     //bush (Town.PALLET_TOWN)

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
                Bitmap.createBitmap(texture, 992, 2400 - 1664, TILE_SIZE, TILE_SIZE) );     //W-building_window (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1040, 2400 - 1664, TILE_SIZE, TILE_SIZE) );    //E-building_window (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1024, 2416 - 1664, TILE_SIZE, TILE_SIZE) );    //building_pokecenter_sign (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 1120, 2320 - 1664, TILE_SIZE, TILE_SIZE) );    //building_pokemart_sign (Town.VIRIDIAN_CITY)

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
                Bitmap.createBitmap(texture, 640, 2512 - 1664, TILE_SIZE, TILE_SIZE) ); //S-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 688, 2512 - 1664, TILE_SIZE, TILE_SIZE) ); //SE-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 688, 2496 - 1664, TILE_SIZE, TILE_SIZE) ); //E-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 640, 2496 - 1664, TILE_SIZE, TILE_SIZE) ); //CENTER-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 688, 2304 - 1664, TILE_SIZE, TILE_SIZE) ); //NE-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 624, 2304 - 1664, TILE_SIZE, TILE_SIZE) ); //N-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 608, 2304 - 1664, TILE_SIZE, TILE_SIZE) ); //NW-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 608, 2320 - 1664, TILE_SIZE, TILE_SIZE) ); //W-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 576, 2224 - 1664, TILE_SIZE, TILE_SIZE) ); //SW-mountain (ROUTE22)

        ////ROUTE02////////////////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 800, 2000 - 1664, TILE_SIZE, TILE_SIZE) ); //bush (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 912, 1968 - 1664, TILE_SIZE, TILE_SIZE) ); //sign-post (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 896, 1984 - 1664, TILE_SIZE, TILE_SIZE) ); //fence-brown (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Bitmap.createBitmap(texture, 800, 1680 - 1664, TILE_SIZE, TILE_SIZE) ); //fence-blue (ROUTE02)

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
//                Bitmap.createBitmap(texture, 960, 1216, TILE_SIZE, TILE_SIZE) );     //NW-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 960, 1232, TILE_SIZE, TILE_SIZE) );     //W-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 960, 1264, TILE_SIZE, TILE_SIZE) );     //SW-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 976, 1264, TILE_SIZE, TILE_SIZE) );     //S-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1072, 1264, TILE_SIZE, TILE_SIZE) );    //SE-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1072, 1248, TILE_SIZE, TILE_SIZE) );    //E-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 976, 1248, TILE_SIZE, TILE_SIZE) );     //CENTER-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 1072, 1216, TILE_SIZE, TILE_SIZE) );    //NE-mountain (Place.VIRIDIAN_FOREST)
//        nonWalkableTileSpriteTargets.add(
//                Bitmap.createBitmap(texture, 976, 1216, TILE_SIZE, TILE_SIZE) );     //N-mountain (Place.VIRIDIAN_FOREST)
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

}