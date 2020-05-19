package com.jackingaming.notesquirrel.gameboycolor.sprites;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.util.HashMap;
import java.util.Map;

public class Assets {

    public static Bitmap rgbTileTest;
    public static Bitmap rgbTileFarm;

    public static Bitmap[][] hm3Farm;
    public static Bitmap[][] items;
    public static Bitmap[][] corgiCrusade;
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
        rgbTileFarm = BitmapFactory.decodeResource(resources, R.drawable.tile_map_farm);
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
        initFroggerSprites(resources);
        //////////////////////////////
        initItems(resources);
        initEntities(resources);

        //TODO: Currently loading this within DirectionalPadFragment and ButtonPadFragment (duplicate).
        initDPad(resources);

        //TODO: OutOfMemoryError... Rethink implementation of loading image resources.
        //TODO: Should only call this when JackInActivity.gameCartridge == POCKET_CRITTERS.
        //initPokemonWorldMap(resources);
    }

    private static void initEntities(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources)");
        loadCorgiCrusade(resources);
        loadWintermute(resources);
        loadGobi(resources);
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

    public static Bitmap cropWorldMapPart01(Resources resources, Map<TileMap.Specs, Integer> specs) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.cropWorldMapPart01(Resources, Map<TileMap.Specs, Integer>)");

        Bitmap fullWorldMap = BitmapFactory.decodeResource(resources, R.drawable.pokemon_gsc_kanto);
        Bitmap croppedWorldMapPart01 = null;

        ////////////////////////////////////////////////////////////////////////////
        //Switched from hard-coded pixel values to tile index supplied by TileMap.//
        ////////////////////////////////////////////////////////////////////////////
        int xStartTileIndex = specs.get(TileMap.Specs.X_START_TILE_INDEX);
        int xEndTileIndex = specs.get(TileMap.Specs.X_END_TILE_INDEX);
        int yStartTileIndex = specs.get(TileMap.Specs.Y_START_TILE_INDEX);
        int yEndTileIndex = specs.get(TileMap.Specs.Y_END_TILE_INDEX);

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

    private static void initHm3Farm(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initHm3Farm(Resources)");
        //LOAD SPRITESHEET
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 3;
        int row = 4;

        hm3Farm = new Bitmap[row][column];

        //SPRING (farm, flower plot, hot spring)
        hm3Farm[0][0] = Bitmap.createBitmap(spriteSheet, 14, 39, 384, 400);
        hm3Farm[0][1] = Bitmap.createBitmap(spriteSheet, 14, 442, 48, 112);
        hm3Farm[0][2] = Bitmap.createBitmap(spriteSheet, 318, 442, 80, 80);

        //SUMMER (farm, flower plot, hot spring)
        hm3Farm[1][0] = Bitmap.createBitmap(spriteSheet, 410, 40, 384, 400);
        hm3Farm[1][1] = Bitmap.createBitmap(spriteSheet, 410, 443, 48, 112);
        hm3Farm[1][2] = Bitmap.createBitmap(spriteSheet, 714, 443, 80, 80);

        //FALL (farm, flower plot, hot spring)
        hm3Farm[2][0] = Bitmap.createBitmap(spriteSheet, 806, 40, 384, 400);
        hm3Farm[2][1] = Bitmap.createBitmap(spriteSheet, 806, 443, 48, 112);
        hm3Farm[2][2] = Bitmap.createBitmap(spriteSheet, 1100, 443, 80, 80);

        //WINTER (farm, flower plot, hot spring)
        hm3Farm[3][0] = Bitmap.createBitmap(spriteSheet, 1202, 40, 384, 400);
        hm3Farm[3][1] = Bitmap.createBitmap(spriteSheet, 1202, 443, 48, 112);
        hm3Farm[3][2] = Bitmap.createBitmap(spriteSheet, 1506, 443, 80, 80);
    }

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

    private static void loadCorgiCrusade(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadCorgiCrusade(Resources)");
        //LOAD SPRITESHEET
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.corgi_crusade_editted);

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 3;
        int row = 4;

        corgiCrusade = new Bitmap[row][column];

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