package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.TimeManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.outdoors.worldmap.TileMapPart01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products.Product;

import java.util.HashMap;
import java.util.Map;

public class Assets {

    public static Bitmap rgbTileTest;
    public static Bitmap rgbTileFarm;

    public static Bitmap[][] hm3Farm;
    public static Bitmap[][] items;
    //public static Bitmap[][] corgiCrusade;
    public static Bitmap[][] wintermute;  //MS Clippit (aka Wintermute)
    public static Bitmap[][] gobi;

    public static Bitmap pokemonWorldMapFull;
    public static Bitmap pokemonWorldMapPart1;  //Have not deleted to avoid error from TileSpriteToRGBConverter

    public static Map<String, Bitmap> dPad;

    //FROGGER_CARTRIDGE
    public static Bitmap spriteSheetFrogger, backgroundFrogger, winningRow, startingRow,
            logLarge, logMedium, logSmall, parrotRight, carPinkLeft, carWhiteRight, carYellowLeft,
            seaLionRight, bigRigLeft;
    public static Bitmap[] frogRight, frogLeft, frogUp, frogDown, turtleLeft, crocRight, snowPlowRight,
            snakeLeft, frogNPCRight, frogNPCLeft;

    public static void init(Context context) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.init(Context)");

        Resources resources = context.getResources();

        ////////////////////////////////////////////////////////////////////////////////
        /*
        Log.d(MainActivity.DEBUG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(MainActivity.DEBUG_TAG, "!!!!! TESTING rgb TO DETERMINE SOLID TILES !!!!!");
        Log.d(MainActivity.DEBUG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        rgbTileTest = BitmapFactory.decodeResource(resources, R.drawable.tile_rgb_test);

        for (int y = 0; y < rgbTileTest.getHeight(); y++) {
            int pixel = rgbTileTest.getPixel(0, y);
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);
            Log.d(MainActivity.DEBUG_TAG, "rgb at y = " + y + ": " + red + ", " + green + ", " + blue);
            if (pixel == Color.BLACK) {
                Log.d(MainActivity.DEBUG_TAG, "pixel is Color.BLACK");
            } else if (pixel == Color.RED) {
                Log.d(MainActivity.DEBUG_TAG, "pixel is Color.RED");
            } else if (pixel == Color.GREEN) {
                Log.d(MainActivity.DEBUG_TAG, "pixel is Color.GREEN");
            } else if (pixel == Color.BLUE) {
                Log.d(MainActivity.DEBUG_TAG, "pixel is Color.BLUE");
            }
        }
        */
        ////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////
        //rgbTileFarm = BitmapFactory.decodeResource(resources, R.drawable.tile_map_farm);
        /*
        StringBuilder sb = new StringBuilder();
        sb.append(" \n");
        for (int y = 0; y < rgbTileFarm.getHeight(); y++) {
            for (int x = 0; x < rgbTileFarm.getWidth(); x++) {
                int pixel = rgbTileFarm.getPixel(x, y);

                if (pixel == Color.BLACK) {
                    sb.append("XXXXX ");
                } else if (pixel == Color.RED) {
                    sb.append("sign- ");
                } else if (pixel == Color.GREEN) {
                    sb.append("tp--- ");
                } else if (pixel == Color.BLUE) {
                    sb.append("diff- ");
                } else {
                    sb.append("OOOOO ");
                }
            }
            sb.append("\n");
        }
        Log.d(MainActivity.DEBUG_TAG, sb.toString());
        */
        ////////////////////////////////////////////////////////////////////////////////

        //initHm3Farm(resources);
        //////////////////////////////
        //initFroggerSprites(resources);
        //////////////////////////////
        initItems(resources);
        initEntities(resources);

        //TODO: Currently loading this within DirectionalPadFragment and ButtonPadFragment (duplicate).
        initDPad(resources);//

        //TODO: OutOfMemoryError... Rethink implementation of loading image resources.
        //TODO: Should only call this when JackInActivity.gameCartridge == POCKET_CRITTERS.
        //initPokemonWorldMap(resources);
    }

    private static void initEntities(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources)");
        //loadCorgiCrusade(resources);
        loadWintermute(resources);
        loadGobi(resources);
    }

    public static Bitmap[][] cropCorgiCrusade(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCorgiCrusade(Resources)");

        //LOAD SPRITESHEET
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.corgi_crusade_editted);

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 3;
        int row = 4;

        Bitmap[][] corgiCrusade = new Bitmap[row][column];

        int xCurrent = 0;
        int yCurrent = 0;
        int tileWidth = 114;
        int tileHeight = 188;

        for (int y = 0; y < (row-1); y++) {

            if (y == 1) {
                tileHeight = 185;
            } else if (y == 2) {
                tileWidth = 185;
                tileHeight = 164;
            }

            for (int x = 0; x < column; x++) {
                corgiCrusade[y][x] = Bitmap.createBitmap(spriteSheet, xCurrent, yCurrent, tileWidth, tileHeight);
                xCurrent += (tileWidth);
            }

            xCurrent = 0;
            yCurrent += (tileHeight);
        }

        for (int x = 0; x < column; x++) {
            corgiCrusade[3][x] = flipHorizontally(corgiCrusade[2][x]);
        }

        return corgiCrusade;
    }

    public static Bitmap cropHome01(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHome01(Resources)");

        Bitmap indoorsHomeAndRoom = BitmapFactory.decodeResource(resources, R.drawable.indoors_home_and_room);
        Bitmap home01 = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        home01 = Bitmap.createBitmap(indoorsHomeAndRoom, 160, 16, 128, 128);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "home01: " + home01.getWidth() + ", " + home01.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsHomeAndRoom = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHome01(Resources)... indoorsHomeAndRoom is null? " + indoorsHomeAndRoom);

        return home01;
    }

    public static Bitmap cropHome02(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHome02(Resources)");

        Bitmap indoorsHomeAndRoom = BitmapFactory.decodeResource(resources, R.drawable.indoors_home_and_room);
        Bitmap home02 = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        home02 = Bitmap.createBitmap(indoorsHomeAndRoom, 16, 16, 128, 128);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "home02: " + home02.getWidth() + ", " + home02.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsHomeAndRoom = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHome02(Resources)... indoorsHomeAndRoom is null? " + indoorsHomeAndRoom);

        return home02;
    }

    public static Bitmap cropHomeRival(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHomeRival(Resources)");

        Bitmap indoorsHomeAndRoom = BitmapFactory.decodeResource(resources, R.drawable.indoors_home_and_room);
        Bitmap homeRival = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        homeRival = Bitmap.createBitmap(indoorsHomeAndRoom, 304, 16, 128, 128);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "homeRival: " + homeRival.getWidth() + ", " + homeRival.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsHomeAndRoom = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHomeRival(Resources)... indoorsHomeAndRoom is null? " + indoorsHomeAndRoom);

        return homeRival;
    }

    public static Bitmap cropLab(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropLab(Resources)");

        Bitmap indoorsHomeAndRoom = BitmapFactory.decodeResource(resources, R.drawable.indoors_home_and_room);
        Bitmap lab = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        lab = Bitmap.createBitmap(indoorsHomeAndRoom, 23, 544, 160, 192);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "lab: " + lab.getWidth() + ", " + lab.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsHomeAndRoom = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropLab(Resources)... indoorsHomeAndRoom is null? " + indoorsHomeAndRoom);

        return lab;
    }

    public static Bitmap cropHothouseEmpty(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHothouseEmpty(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap hothouseEmpty = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        hothouseEmpty = Bitmap.createBitmap(indoorsFarmHM2, 223, 1200, 192, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "hothouseEmpty: " + hothouseEmpty.getWidth() + ", " + hothouseEmpty.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHothouseEmpty(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return hothouseEmpty;
    }

    public static Bitmap cropHothouseFull(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHothouseFull(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap hothouseFull = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        hothouseFull = Bitmap.createBitmap(indoorsFarmHM2, 419, 1200, 192, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "hothouseFull: " + hothouseFull.getWidth() + ", " + hothouseFull.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHothouseFull(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return hothouseFull;
    }

    public static Bitmap cropGrowableTableTile(Resources resources, GrowableTile.State state,
                                               boolean isWatered) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropGrowableTableTile(Resources, GrowableTile.State, boolean)... (state: " + state + "), (isWatered: " + isWatered + ").");

        Bitmap spriteSheetPlantsHothouseHM2 = null;
        Bitmap spriteTableTile = null;

        //SELECT SPRITE_SHEET
        if (isWatered) {
            spriteSheetPlantsHothouseHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_hothouse_plants1);
        } else {
            spriteSheetPlantsHothouseHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_hothouse_plants2);
        }

        //CROP SPRITE
        switch (state) {
            case INITIAL:
                spriteTableTile = null;
                break;
            case PREPARED:
                if (isWatered) {
                    spriteTableTile = Bitmap.createBitmap(spriteSheetPlantsHothouseHM2, 8, 120, 16, 16);
                } else {
                    spriteTableTile = Bitmap.createBitmap(spriteSheetPlantsHothouseHM2, 24, 56, 16, 16);
                }
                break;
            case SEEDED:
                if (isWatered) {
                    spriteTableTile = Bitmap.createBitmap(spriteSheetPlantsHothouseHM2, 120, 120, 16, 16);
                } else {
                    spriteTableTile = Bitmap.createBitmap(spriteSheetPlantsHothouseHM2, 56, 120, 16, 16);
                }
                break;
        }

        return spriteTableTile;
    }

    //TODO: crop, tile, time system (growing system)
    public static Bitmap cropGrowableGroundTile(Resources resources, GrowableTile.State state,
                                                boolean isWatered, GrowableGroundTile.Type type) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropGrowableGroundTile(Resources, GrowableTile.State, boolean, GrowableGroundTile.Type)... (state: " + state + "), (isWatered: " + isWatered + "), (type: " + type + ").");

        Bitmap spriteSheetCropsAndItems = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_crops_and_items);
        Bitmap spriteGroundTile = null;


        switch (state) {
            case INITIAL:
                spriteGroundTile = null;
                break;
            case PREPARED:
                if (isWatered) {
                    spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 24, 19, 16, 16);
                } else {
                    spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 7, 19, 16, 16);
                }
                break;
            case SEEDED:
                switch (type) {
                    case CROP_SEEDED:
                        switch (TimeManager.season) {
                            case SPRING:
                            case SUMMER:
                            case FALL:
                                if (isWatered) {
                                    spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 24, 106, 16, 16);
                                } else {
                                    spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 7, 106, 16, 16);
                                }
                                break;
                            case WINTER:
                                if (isWatered) {
                                    spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 24, 289, 16, 16);
                                } else {
                                    spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 7, 289, 16, 16);
                                }
                                break;
                        }
                        break;
                    case GRASS_SEEDED:
                        switch (TimeManager.season) {
                            case SPRING:
                            case SUMMER:
                            case FALL:
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 7, 60, 16, 16);
                                break;
                            case WINTER:
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 125, 60, 16, 16);
                                break;
                        }
                        break;
                    case GRASS_SPROUTED:
                        switch (TimeManager.season) {
                            case SPRING:
                            case SUMMER:
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 24, 60, 16, 16);
                                break;
                            case FALL:
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 82, 60, 16, 16);
                                break;
                            case WINTER:
                                //same as GRASS_SEEDED
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 125, 60, 16, 16);
                                break;
                        }
                        break;
                    case GRASS_HARVESTABLE:
                        switch (TimeManager.season) {
                            case SPRING:
                            case SUMMER:
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 41, 60, 16, 16);
                                break;
                            case FALL:
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 99, 60, 16, 16);
                                break;
                            case WINTER:
                                //same as GRASS_SEEDED
                                spriteGroundTile = Bitmap.createBitmap(spriteSheetCropsAndItems, 125, 60, 16, 16);
                                break;
                        }
                        break;
                }
                break;
        }

        return spriteGroundTile;
    }

    public static Bitmap cropShippingBinTile(Resources resources, ShippingBinTile.Quadrant quadrant) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropShippingBinTile(Resources, ShippingBinTile.Quadrant)");

        Bitmap customTilesSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.custom_hm_tile_sprites_sheet);
        Bitmap shippingBinTile = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        switch (quadrant) {
            case TOP_LEFT:
                shippingBinTile = Bitmap.createBitmap(customTilesSpriteSheet, 0, 112, 16, 16);
                break;
            case TOP_RIGHT:
                shippingBinTile = Bitmap.createBitmap(customTilesSpriteSheet, 16, 112, 16, 16);
                break;
            case BOTTOM_LFET:
                shippingBinTile = Bitmap.createBitmap(customTilesSpriteSheet, 0, 128, 16, 16);
                break;
            case BOTTOM_RIGHT:
                shippingBinTile = Bitmap.createBitmap(customTilesSpriteSheet, 16, 128, 16, 16);
                break;
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "shippingBinTile: " + shippingBinTile.getWidth() + ", " + shippingBinTile.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        customTilesSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropShippingBinTile(Resources, ShippingBinTile.Quadrant)... customTilesSpriteSheet is null? " + customTilesSpriteSheet);

        return shippingBinTile;
    }

    //crop_product
    public static Bitmap cropCropProduct(Resources resources, Product.Id id, boolean isWhole) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCropProduct(Resources, Product.Id, boolean)");

        Bitmap cropsAndItemsSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_crops_and_items);
        Bitmap cropProduct = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        switch (id) {
            case TURNIP:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 106, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 106, 16, 16);
                }
                break;
            case POTATO:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 125, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 125, 16, 16);
                }
                break;
            case TOMATO:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 160, 166, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 177, 166, 16, 16);
                }
                break;
            case CORN:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 160, 185, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 177, 185, 16, 16);
                }
                break;
            case EGGPLANT:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 230, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 230, 16, 16);
                }
                break;
            case PEANUT:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 249, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 249, 16, 16);
                }
                break;
            case CARROT:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 289, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 289, 16, 16);
                }
                break;
            case BROCCOLI:
                if (isWhole) {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 160, 308, 16, 16);
                } else {
                    cropProduct = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 177, 308, 16, 16);
                }
                break;
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "cropProduct: " + cropProduct.getWidth() + ", " + cropProduct.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        cropsAndItemsSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCropProduct(Resources, Product.Id, boolean)... cropsAndItemsSpriteSheet is null? " + cropsAndItemsSpriteSheet);

        return cropProduct;
    }

    //crop_entity
    public static Bitmap cropCropEntity(Resources resources, CropEntity.Id id, CropEntity.Stage stage, boolean isWatered) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCropEntity(Resources, CropEntity.Id, CropEntity.Stage, boolean)");

        Bitmap cropsAndItemsSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_crops_and_items);
        Bitmap cropEntity = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        switch (id) {
            case TURNIP:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 106, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 106, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 106, 16, 16);
                        break;
                }
                break;

            case POTATO:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 125, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 125, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 125, 16, 16);
                        break;
                }
                break;

            case TOMATO:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 166, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 166, 16, 16);
                        }
                        break;
                    case TWO:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 166, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 166, 16, 16);
                        }
                        break;
                    case THREE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 126, 166, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 166, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 143, 166, 16, 16);
                        break;
                }
                break;

            case CORN:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 185, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 185, 16, 16);
                        }
                        break;
                    case TWO:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 185, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 185, 16, 16);
                        }
                        break;
                    case THREE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 126, 185, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 185, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 143, 185, 16, 16);
                        break;
                }
                break;

            case EGGPLANT:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 230, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 230, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 230, 16, 16);
                        break;
                }
                break;

            case PEANUT:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 249, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 249, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 249, 16, 16);
                        break;
                }
                break;

            case CARROT:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 289, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 289, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 289, 16, 16);
                        break;
                }
                break;

            case BROCCOLI:

                switch (stage) {
                    case ONE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 58, 308, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 41, 308, 16, 16);
                        }
                        break;
                    case TWO:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 92, 308, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 75, 308, 16, 16);
                        }
                        break;
                    case THREE:
                        if (isWatered) {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 126, 308, 16, 16);
                        } else {
                            cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 109, 308, 16, 16);
                        }
                        break;
                    case HARVESTABLE:
                        cropEntity = Bitmap.createBitmap(cropsAndItemsSpriteSheet, 143, 308, 16, 16);
                        break;
                }
                break;

        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "cropEntity: " + cropEntity.getWidth() + ", " + cropEntity.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        cropsAndItemsSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCropEntity(Resources, CropEntity.Id, CropEntity.Stage, boolean)... cropsAndItemsSpriteSheet is null? " + cropsAndItemsSpriteSheet);

        return cropEntity;
    }

    public static Bitmap cropSheepPenEmpty(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropSheepPenEmpty(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap sheepPenEmpty = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        sheepPenEmpty = Bitmap.createBitmap(indoorsFarmHM2, 8, 902, 240, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "sheepPenEmpty: " + sheepPenEmpty.getWidth() + ", " + sheepPenEmpty.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropSheepPenEmpty(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return sheepPenEmpty;
    }

    public static Bitmap cropSheepPenFull(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropSheepPenFull(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap sheepPenFull = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        sheepPenFull = Bitmap.createBitmap(indoorsFarmHM2, 252, 902, 240, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "sheepPenFull: " + sheepPenFull.getWidth() + ", " + sheepPenFull.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropSheepPenFull(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return sheepPenFull;
    }

    public static Bitmap cropChickenCoopEmpty(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropChickenCoopEmpty(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap chickenCoopEmpty = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        chickenCoopEmpty = Bitmap.createBitmap(indoorsFarmHM2, 78, 603, 240, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "chickenCoopEmpty: " + chickenCoopEmpty.getWidth() + ", " + chickenCoopEmpty.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropChickenCoopEmpty(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return chickenCoopEmpty;
    }

    public static Bitmap cropChickenCoopFull(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropChickenCoopFull(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap chickenCoopFull = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        chickenCoopFull = Bitmap.createBitmap(indoorsFarmHM2, 322, 603, 240, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "chickenCoopFull: " + chickenCoopFull.getWidth() + ", " + chickenCoopFull.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropChickenCoopFull(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return chickenCoopFull;
    }

    public static Bitmap cropCowBarnEmpty(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCowBarnEmpty(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap cowBarnEmpty = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        cowBarnEmpty = Bitmap.createBitmap(indoorsFarmHM2, 8, 304, 240, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "cowBarnEmpty: " + cowBarnEmpty.getWidth() + ", " + cowBarnEmpty.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCowBarnEmpty(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return cowBarnEmpty;
    }

    public static Bitmap cropCowBarnFull(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCowBarnFull(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap cowBarnFull = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        cowBarnFull = Bitmap.createBitmap(indoorsFarmHM2, 252, 304, 240, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "cowBarnFull: " + cowBarnFull.getWidth() + ", " + cowBarnFull.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropCowBarnFull(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return cowBarnFull;
    }

    public static Bitmap cropHouseLevel01(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHouseLevel01(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap houseLevel01 = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        houseLevel01 = Bitmap.createBitmap(indoorsFarmHM2, 6, 6, 160, 192);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "houseLevel01: " + houseLevel01.getWidth() + ", " + houseLevel01.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHouseLevel01(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return houseLevel01;
    }

    public static Bitmap cropHouseLevel02(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHouseLevel02(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap houseLevel02 = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        houseLevel02 = Bitmap.createBitmap(indoorsFarmHM2, 171, 6, 192, 192);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "houseLevel02: " + houseLevel02.getWidth() + ", " + houseLevel02.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHouseLevel02(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return houseLevel02;
    }

    public static Bitmap cropHouseLevel03(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHouseLevel03(Resources)");

        Bitmap indoorsFarmHM2 = BitmapFactory.decodeResource(resources, R.drawable.hm2_farm_indoors);
        Bitmap houseLevel03 = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        houseLevel03 = Bitmap.createBitmap(indoorsFarmHM2, 368, 6, 256, 256);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "houseLevel03: " + houseLevel03.getWidth() + ", " + houseLevel03.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        indoorsFarmHM2 = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHouseLevel03(Resources)... indoorsFarmHM2 is null? " + indoorsFarmHM2);

        return houseLevel03;
    }

    public static Bitmap cropWorldMapPart01(Resources resources, Map<TileMapPart01.Specs, Integer> specs) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropWorldMapPart01(Resources, Map<TileMap.Specs, Integer>)");

        Bitmap fullWorldMap = BitmapFactory.decodeResource(resources, R.drawable.pokemon_gsc_kanto);
        Bitmap croppedWorldMapPart01 = null;

        ////////////////////////////////////////////////////////////////////////////
        //Switched from hard-coded pixel values to tile index supplied by TileMap.//
        ////////////////////////////////////////////////////////////////////////////
        int xStartTileIndex = specs.get(TileMapPart01.Specs.X_START_TILE_INDEX);
        int xEndTileIndex = specs.get(TileMapPart01.Specs.X_END_TILE_INDEX);
        int yStartTileIndex = specs.get(TileMapPart01.Specs.Y_START_TILE_INDEX);
        int yEndTileIndex = specs.get(TileMapPart01.Specs.Y_END_TILE_INDEX);

        // In terms of PIXELS.
        int x = xStartTileIndex * TileMap.TILE_WIDTH;
        int widthSceneMax = (xEndTileIndex - xStartTileIndex) * TileMap.TILE_WIDTH;
        int y = yStartTileIndex * TileMap.TILE_HEIGHT;
        int heightSceneMax = (yEndTileIndex - yStartTileIndex) * TileMap.TILE_HEIGHT;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedWorldMapPart01 = Bitmap.createBitmap(fullWorldMap, x, y, widthSceneMax, heightSceneMax);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedWorldMapPart01: " + croppedWorldMapPart01.getWidth() + ", " + croppedWorldMapPart01.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        fullWorldMap = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropWorldMapPart01(Resources, Map<TileMap.Specs, Integer>)... fullWorldMap is null? " + fullWorldMap);

        return croppedWorldMapPart01;
    }

    public static Bitmap cropStartMenuStateCursor(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropStartMenuStateCursor(Resources)");

        Bitmap startMenuSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.start_menu_state);
        Bitmap startMenuStateCursor = Bitmap.createBitmap(startMenuSpriteSheet, 3, 162, 7, 7);

        return startMenuStateCursor;
    }

    public static Bitmap cropStartMenuState(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropStartMenuState(Resources)");

        Bitmap startMenuSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.start_menu_state);
        Bitmap startMenuState = Bitmap.createBitmap(startMenuSpriteSheet, 239, 3, 75, 124);

        return startMenuState;
    }

    public static Bitmap cropFarmSpring(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmSpring(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFarmSpring = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFarmSpring = Bitmap.createBitmap(farmSpriteSheet, 14, 39, 384, 400);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFarmSpring: " + croppedFarmSpring.getWidth() + ", " + croppedFarmSpring.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmSpring(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFarmSpring;
    }

    public static Bitmap cropFarmSummer(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmSummer(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFarmSummer = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFarmSummer = Bitmap.createBitmap(farmSpriteSheet, 410, 40, 384, 400);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFarmSummer: " + croppedFarmSummer.getWidth() + ", " + croppedFarmSummer.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmSummer(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFarmSummer;
    }

    public static Bitmap cropFarmFall(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmFall(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFarmFall = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFarmFall = Bitmap.createBitmap(farmSpriteSheet, 806, 40, 384, 400);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFarmFall: " + croppedFarmFall.getWidth() + ", " + croppedFarmFall.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmFall(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFarmFall;
    }

    public static Bitmap cropFarmWinter(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmWinter(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFarmWinter = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFarmWinter = Bitmap.createBitmap(farmSpriteSheet, 1202, 40, 384, 400);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFarmWinter: " + croppedFarmWinter.getWidth() + ", " + croppedFarmWinter.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFarmWinter(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFarmWinter;
    }

    public static Bitmap cropFlowerPlotSpring(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotSpring(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFlowerPlotSpring = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFlowerPlotSpring = Bitmap.createBitmap(farmSpriteSheet, 14, 442, 48, 112);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFlowerPlotSpring: " + croppedFlowerPlotSpring.getWidth() + ", " + croppedFlowerPlotSpring.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotSpring(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFlowerPlotSpring;
    }

    public static Bitmap cropFlowerPlotSummer(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotSummer(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFlowerPlotSummer = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFlowerPlotSummer = Bitmap.createBitmap(farmSpriteSheet, 410, 443, 48, 112);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFlowerPlotSummer: " + croppedFlowerPlotSummer.getWidth() + ", " + croppedFlowerPlotSummer.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotSummer(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFlowerPlotSummer;
    }

    public static Bitmap cropFlowerPlotFall(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotFall(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFlowerPlotFall = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFlowerPlotFall = Bitmap.createBitmap(farmSpriteSheet, 806, 443, 48, 112);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFlowerPlotFall: " + croppedFlowerPlotFall.getWidth() + ", " + croppedFlowerPlotFall.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotFall(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFlowerPlotFall;
    }

    public static Bitmap cropFlowerPlotWinter(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotWinter(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedFlowerPlotWinter = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedFlowerPlotWinter = Bitmap.createBitmap(farmSpriteSheet, 1202, 443, 48, 112);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedFlowerPlotWinter: " + croppedFlowerPlotWinter.getWidth() + ", " + croppedFlowerPlotWinter.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropFlowerPlotWinter(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedFlowerPlotWinter;
    }

    public static Bitmap cropHotSpringSpring(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringSpring(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedHotSpringSpring = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedHotSpringSpring = Bitmap.createBitmap(farmSpriteSheet, 318, 442, 80, 80);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedHotSpringSpring: " + croppedHotSpringSpring.getWidth() + ", " + croppedHotSpringSpring.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringSpring(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedHotSpringSpring;
    }

    public static Bitmap cropHotSpringSummer(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringSummer(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedHotSpringSummer = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedHotSpringSummer = Bitmap.createBitmap(farmSpriteSheet, 714, 443, 80, 80);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedHotSpringSummer: " + croppedHotSpringSummer.getWidth() + ", " + croppedHotSpringSummer.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringSummer(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedHotSpringSummer;
    }

    public static Bitmap cropHotSpringFall(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringFall(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedHotSpringFall = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedHotSpringFall = Bitmap.createBitmap(farmSpriteSheet, 1100, 443, 80, 80);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedHotSpringFall: " + croppedHotSpringFall.getWidth() + ", " + croppedHotSpringFall.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringFall(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedHotSpringFall;
    }

    public static Bitmap cropHotSpringWinter(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringWinter(Resources)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedHotSpringWinter = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        croppedHotSpringWinter = Bitmap.createBitmap(farmSpriteSheet, 1506, 443, 80, 80);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "croppedHotSpringWinter: " + croppedHotSpringWinter.getWidth() + ", " + croppedHotSpringWinter.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //May be redundant because local variable.
        farmSpriteSheet = null;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropHotSpringWinter(Resources)... farmSpriteSheet is null? " + farmSpriteSheet);

        return croppedHotSpringWinter;
    }

//    private static void initHm3Farm(Resources resources) {
//        Log.d(MainActivity.DEBUG_TAG, "Assets.initHm3Farm(Resources)");
//        //LOAD SPRITESHEET
//        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
//
//        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
//        int column = 3;
//        int row = 4;
//
//        hm3Farm = new Bitmap[row][column];
//
//        //SPRING (farm, flower plot, hot spring)
//        hm3Farm[0][0] = Bitmap.createBitmap(spriteSheet, 14, 39, 384, 400);
//        hm3Farm[0][1] = Bitmap.createBitmap(spriteSheet, 14, 442, 48, 112);
//        hm3Farm[0][2] = Bitmap.createBitmap(spriteSheet, 318, 442, 80, 80);
//
//        //SUMMER (farm, flower plot, hot spring)
//        hm3Farm[1][0] = Bitmap.createBitmap(spriteSheet, 410, 40, 384, 400);
//        hm3Farm[1][1] = Bitmap.createBitmap(spriteSheet, 410, 443, 48, 112);
//        hm3Farm[1][2] = Bitmap.createBitmap(spriteSheet, 714, 443, 80, 80);
//
//        //FALL (farm, flower plot, hot spring)
//        hm3Farm[2][0] = Bitmap.createBitmap(spriteSheet, 806, 40, 384, 400);
//        hm3Farm[2][1] = Bitmap.createBitmap(spriteSheet, 806, 443, 48, 112);
//        hm3Farm[2][2] = Bitmap.createBitmap(spriteSheet, 1100, 443, 80, 80);
//
//        //WINTER (farm, flower plot, hot spring)
//        hm3Farm[3][0] = Bitmap.createBitmap(spriteSheet, 1202, 40, 384, 400);
//        hm3Farm[3][1] = Bitmap.createBitmap(spriteSheet, 1202, 443, 48, 112);
//        hm3Farm[3][2] = Bitmap.createBitmap(spriteSheet, 1506, 443, 80, 80);
//    }

    public static void initFroggerSprites(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initFroggerSprites(Resources) START...");

        Bitmap spriteSheetFrogger = BitmapFactory.decodeResource(resources, R.drawable.frogger_entities);

        winningRow = Bitmap.createBitmap(spriteSheetFrogger, 0, 55,  399,52);
        startingRow = Bitmap.createBitmap(spriteSheetFrogger, 0, 119, 399, 34);
        logLarge = Bitmap.createBitmap(spriteSheetFrogger, 7, 166, 177,21);
        logMedium = Bitmap.createBitmap(spriteSheetFrogger, 7, 198, 116, 21);
        logSmall = Bitmap.createBitmap(spriteSheetFrogger, 7, 230, 84, 21);
        parrotRight = Bitmap.createBitmap(spriteSheetFrogger, 140, 236, 16, 16);
        carPinkLeft = Bitmap.createBitmap(spriteSheetFrogger, 10, 267, 28, 20);
        carWhiteRight = Bitmap.createBitmap(spriteSheetFrogger, 46, 265, 24, 24);
        carYellowLeft = Bitmap.createBitmap(spriteSheetFrogger, 82, 264, 24, 26);
        seaLionRight = Bitmap.createBitmap(spriteSheetFrogger, 116, 271, 32, 18);
        bigRigLeft = Bitmap.createBitmap(spriteSheetFrogger, 106, 302, 46, 18);

        frogRight = new Bitmap[2];
        frogRight[0] = Bitmap.createBitmap(spriteSheetFrogger, 13, 334, 17, 23);
        frogRight[1] = Bitmap.createBitmap(spriteSheetFrogger, 43, 335, 25, 22);
        frogLeft = new Bitmap[2];
        frogLeft[0] = Bitmap.createBitmap(spriteSheetFrogger, 83, 335, 17, 23);
        frogLeft[1] = Bitmap.createBitmap(spriteSheetFrogger, 112, 338, 25, 22);
        frogUp = new Bitmap[2];
        frogUp[0] = Bitmap.createBitmap(spriteSheetFrogger, 12, 369, 23, 17);
        frogUp[1] = Bitmap.createBitmap(spriteSheetFrogger, 46, 366, 22, 25);
        frogDown = new Bitmap[2];
        frogDown[0] = Bitmap.createBitmap(spriteSheetFrogger, 80, 369, 23, 17);
        frogDown[1] = Bitmap.createBitmap(spriteSheetFrogger, 114, 366, 22, 25);
        turtleLeft = new Bitmap[5];
        turtleLeft[0] = Bitmap.createBitmap(spriteSheetFrogger, 15, 406, 31, 22);
        turtleLeft[1] = Bitmap.createBitmap(spriteSheetFrogger, 54, 407, 31, 22);
        turtleLeft[2] = Bitmap.createBitmap(spriteSheetFrogger, 94, 408, 29, 19);
        turtleLeft[3] = Bitmap.createBitmap(spriteSheetFrogger, 134, 408, 29, 21);
        turtleLeft[4] = Bitmap.createBitmap(spriteSheetFrogger, 179, 408, 26, 21);
        crocRight = new Bitmap[2];
        crocRight[0] = Bitmap.createBitmap(spriteSheetFrogger, 156, 332, 89, 29);
        crocRight[1] = Bitmap.createBitmap(spriteSheetFrogger, 156, 373, 89, 21);
        snowPlowRight = new Bitmap[3];
        snowPlowRight[0] = Bitmap.createBitmap(spriteSheetFrogger, 11, 301, 23, 21);
        snowPlowRight[1] = Bitmap.createBitmap(spriteSheetFrogger, 42, 301, 23, 21);
        snowPlowRight[2] = Bitmap.createBitmap(spriteSheetFrogger, 73, 301, 23, 21);
        snakeLeft = new Bitmap[4];
        snakeLeft[0] = Bitmap.createBitmap(spriteSheetFrogger, 184, 226, 38, 10);
        snakeLeft[1] = Bitmap.createBitmap(spriteSheetFrogger, 185, 251, 37, 13);
        snakeLeft[2] = Bitmap.createBitmap(spriteSheetFrogger, 184, 276, 38, 16);
        snakeLeft[3] = Bitmap.createBitmap(spriteSheetFrogger, 185, 304, 37, 13);
        frogNPCRight = new Bitmap[2];
        frogNPCRight[0] = Bitmap.createBitmap(spriteSheetFrogger, 236, 407, 20, 24);
        frogNPCRight[1] = Bitmap.createBitmap(spriteSheetFrogger, 270, 409, 27, 24);
        frogNPCLeft = new Bitmap[2];
        frogNPCLeft[0] = Bitmap.createBitmap(spriteSheetFrogger, 315, 407, 19, 24);
        frogNPCLeft[1] = Bitmap.createBitmap(spriteSheetFrogger, 348, 409, 28, 23);

        Log.d(MainActivity.DEBUG_TAG, "Assets.initFroggerSprites(Resources) FINISH!!!");
    }

    private static void initDPad(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initDPad(Resources)");
        Bitmap source = BitmapFactory.decodeResource(resources, R.drawable.d_pad);

        dPad = new HashMap<String, Bitmap>();

        dPad.put("up", Bitmap.createBitmap(source, 62, 365, 52, 40));
        dPad.put("left", Bitmap.createBitmap(source, 22, 405, 40, 52));
        dPad.put("center", Bitmap.createBitmap(source, 62, 405, 52, 52));
        dPad.put("right", Bitmap.createBitmap(source, 114, 405, 40, 52));
        dPad.put("down", Bitmap.createBitmap(source, 62, 457, 52, 40));

        dPad.put("menu", Bitmap.createBitmap(source, 172, 375, 136, 52));
        dPad.put("a", Bitmap.createBitmap(source, 172, 435, 64, 52));
        dPad.put("b", Bitmap.createBitmap(source, 244, 435, 64, 52));
    }

    private static void initItems(Resources resources){
        Log.d(MainActivity.DEBUG_TAG, "Assets.initItems(Resources)");
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 9;
        int row = 9;

        items = new Bitmap[row][column];

        int margin = 1;
        int tileWidth = 16;
        int tileHeight = 16;

        int xCurrent = margin;
        int yCurrent = margin;

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                items[y][x] = Bitmap.createBitmap(imageSource, xCurrent, yCurrent, tileWidth, tileHeight);
                xCurrent += (tileWidth + margin);
            }
            xCurrent = margin;
            yCurrent += (tileHeight + margin);
        }
    }

    private static void loadWintermute(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadWintermute(Resources)");
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.pc_ms_office_clippit);

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 22;
        int row = 41;

        wintermute = new Bitmap[row][column];

        //TODO: everything in loadWintermute(Resources) was adapted from initItems(Resources) and has
        //not yet been tailored to this spritesheet (e.g. should NOT have margin at all).
        //
        //Which will also be different for the tiles spritesheet (which has margins, but not between
        //each sprites like the items spritesheet).
        int margin = 0;
        int tileWidth = 124;
        int tileHeight = 93;

        int xCurrent = margin;
        int yCurrent = margin;

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                wintermute[y][x] = Bitmap.createBitmap(imageSource, xCurrent, yCurrent, tileWidth, tileHeight);
                xCurrent += (tileWidth + margin);
            }
            xCurrent = margin;
            yCurrent += (tileHeight + margin);
        }
    }

    private static void loadGobi(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadGobi(Resources)");
        //LOAD SPRITESHEET
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.snes_breath_of_fire_gobi);

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 12;
        int row = 1;

        gobi = new Bitmap[row][column];

        int margin = 5;
        int xCurrent = margin;
        int yCurrent = margin;
        int tileWidth = 19;
        int tileHeight = 24;
        int horizontalGap = 8;

        for (int x = 0; x < column; x++) {
            switch (x) {
                case 0:
                    tileWidth = 19;
                    break;
                case 1:
                    tileWidth = 18;
                    break;
                case 2:
                    tileWidth = 19;
                    break;

                case 3:
                    tileWidth = 16;
                    break;
                case 4:
                    tileWidth = 20;
                    break;
                case 5:
                    tileWidth = 16;
                    break;

                case 6:
                    tileWidth = 19;
                    break;
                case 7:
                    tileWidth = 18;
                    break;
                case 8:
                    tileWidth = 19;
                    break;

                case 9:
                    tileWidth = 18;
                    break;
                case 10:
                    tileWidth = 20;
                    break;
                case 11:
                    tileWidth = 18;
                    break;
            }

            gobi[0][x] = Bitmap.createBitmap(spriteSheet, xCurrent, yCurrent, tileWidth, tileHeight);
            xCurrent += (tileWidth + horizontalGap);
        }
    }

    public static Bitmap flipHorizontally(Bitmap source) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.flipHorizontally(Bitmap)");
        int xCenter = source.getWidth()/2;
        int yCenter = source.getHeight()/2;

        Matrix matrix = new Matrix();
        //////////////////////////////////////////////////
        matrix.postScale(-1, 1, xCenter, yCenter);
        //////////////////////////////////////////////////

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}