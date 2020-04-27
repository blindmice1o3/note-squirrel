package com.jackingaming.notesquirrel.gameboycolor.sprites;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

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

    public static Map<String, Bitmap> dPad;
    public static Bitmap pokemonWorldMapFull;
    public static Bitmap pokemonWorldMapPart1;

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

        initHm3Farm(resources);
        initItems(resources);
        initEntities(resources);

        //TODO: Currently loading this within DirectionalPadFragment and ButtonPadFragment (duplicate).
        initDPad(resources);

        //TODO: OutOfMemoryError... Rethink implementation of loading image resources.
        //TODO: Should only call this when JackInActivity.gameCartridge == POCKET_CRITTERS.
        initPokemonWorldMap(resources);
    }

    private static void initEntities(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources)");
        loadCorgiCrusade(resources);
        loadWintermute(resources);
        loadGobi(resources);
    }

    private static void initPokemonWorldMap(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.initPokemonWorldMap(Resources)");

        //TODO: crop FULL world map image into 10-section(HORIZONTALLY) by 10-section(VERTICALLY)
        pokemonWorldMapFull = BitmapFactory.decodeResource(resources, R.drawable.pokemon_gsc_kanto);
        Log.d(MainActivity.DEBUG_TAG, "Assets.initPokemonWorldMap(Resources)... pokemonWorldMapFull is null? " + pokemonWorldMapFull);
        Log.d(MainActivity.DEBUG_TAG, "Assets.initPokemonWorldMap(Resources)... pokemonWorldMapFull: " + pokemonWorldMapFull.getWidth() + ", " + pokemonWorldMapFull.getHeight());
        // In terms of PIXELS.
        pokemonWorldMapPart1 = Bitmap.createBitmap(pokemonWorldMapFull, 0, 1664, 1280, 1904);
        Log.d(MainActivity.DEBUG_TAG, "Assets.initPokemonWorldMap(Resources)... pokemonWorldMapPart1: " + pokemonWorldMapPart1.getWidth() + ", " + pokemonWorldMapPart1.getHeight());

        ///////////////////////////
        pokemonWorldMapFull = null;
        ///////////////////////////
        Log.d(MainActivity.DEBUG_TAG, "Assets.initPokemonWorldMap(Resources)... pokemonWorldMapFull is null? " + pokemonWorldMapFull);
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