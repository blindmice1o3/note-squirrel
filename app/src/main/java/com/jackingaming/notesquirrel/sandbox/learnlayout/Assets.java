package com.jackingaming.notesquirrel.sandbox.learnlayout;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {

    public static Bitmap[][] items;
    public static Bitmap[][] tiles;
    public static Bitmap[][] wintermute;  //MS Clippit (aka Wintermute)
    public static Bitmap[][] corgiCrusade;
    public static Bitmap[][] gobi;

    public static void init(Resources resources) {
        initItems(resources);
        initTiles(resources);
        initEntities(resources);
    }

    public static Bitmap grabPokemonWorldMapSection(Resources resources, int column, int row) {
        Log.d(MainActivity.DEBUG_TAG, "Assets.grabPokemonWorldMapSection(Resources, int, int)");
        Bitmap pokemonWorldMapCropped = null;

        Bitmap pokemonWorldMapFull = BitmapFactory.decodeResource(resources, R.drawable.pokemon_gsc_kanto);
        Log.d(MainActivity.DEBUG_TAG, "Assets.grabPokemonWorldMapSection(Resources, int, int)... pokemonWorldMapFull is null? " + pokemonWorldMapFull);
        Log.d(MainActivity.DEBUG_TAG, "Assets.grabPokemonWorldMapSection(Resources, int, int)... pokemonWorldMapFull's (width, height): " + pokemonWorldMapFull.getWidth() + ", " + pokemonWorldMapFull.getHeight());

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        int widthSection = pokemonWorldMapFull.getWidth() / 10;
        int heightSection = pokemonWorldMapFull.getHeight() / 10;

        int xStart = (column - 1) * widthSection;
        int yStart = (row - 1) * heightSection;

        pokemonWorldMapCropped = Bitmap.createBitmap(pokemonWorldMapFull, xStart, yStart, widthSection, heightSection);
        //pokemonWorldMapCropped = Bitmap.createBitmap(pokemonWorldMapFull, 0, 1664, 1280, 1904);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        Log.d(MainActivity.DEBUG_TAG, "Assets.grabPokemonWorldMapSection(Resources, int, int)... pokemonWorldMapCropped's (width, height): " + pokemonWorldMapCropped.getWidth() + ", " + pokemonWorldMapCropped.getHeight());
        ///////////////////////////
        pokemonWorldMapFull = null;
        ///////////////////////////
        Log.d(MainActivity.DEBUG_TAG, "Assets.grabPokemonWorldMapSection(Resources, int, int)... pokemonWorldMap is null? " + pokemonWorldMapFull);

        return pokemonWorldMapCropped;
    }

    private static void initTiles(Resources resources) {
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.pc_yoko_tileset);

        //CHECK IF IMAGE RESOURCE DID not LOAD PROPERLY
        if (imageSource == null) {
            Log.d(MainActivity.DEBUG_TAG, "Assets.initTiles(Resources): imageSource == null");

            Log.d(MainActivity.DEBUG_TAG, "BAILING EARLY!!!!!");
            return;
        }

        //IF WE'VE MADE IT THIS FAR, THE IMAGE RESOURCE LOADED AS INTENDED
        Log.d(MainActivity.DEBUG_TAG, "Assets.initTiles(Resources): imageSource != null");

        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 6;
        int row = 20;

        tiles = new Bitmap[row][column];

        int margin = 1;
        int tileWidth = 40;
        int tileHeight = 40;

        int xCurrent = margin;
        int yCurrent = margin;

        //if row < 16
        for (int y = 0; y < row; y++) {
            if (y >= 16) {
                tileHeight = 48;
            }
            for (int x = 0; x < column; x++) {
                tiles[y][x] = Bitmap.createBitmap(imageSource, xCurrent, yCurrent, tileWidth, tileHeight);
                xCurrent += (tileWidth);
            }
            xCurrent = margin;
            yCurrent += (tileHeight);
        }
        Log.d(MainActivity.DEBUG_TAG, "Assets.initTiles(Resources): FINISHED!!!");
    }

    private static void initEntities(Resources resources) {
        loadWintermute(resources);
        loadGobi(resources);
        loadCorgiCrusade(resources);

        Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources): FINISHED!!!");
    }

    private static void initItems(Resources resources){
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);

        //CHECK IF IMAGE RESOURCE DID not LOAD PROPERLY
        if (imageSource == null) {
            Log.d(MainActivity.DEBUG_TAG, "Assets.initItems(Resources): imageSource == null");

            Log.d(MainActivity.DEBUG_TAG, "BAILING EARLY!!!!!");
            return;
        }

        //IF WE'VE MADE IT THIS FAR, THE IMAGE RESOURCE LOADED AS INTENDED
        Log.d(MainActivity.DEBUG_TAG, "Assets.initItems(Resources): imageSource != null");

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
        Log.d(MainActivity.DEBUG_TAG, "Assets.initItems(Resources): FINISHED!!!");
    }

    private static void loadWintermute(Resources resources) {
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.pc_ms_office_clippit);

        //CHECK IF IMAGE RESOURCE DID not LOAD PROPERLY
        if (imageSource == null) {
            Log.d(MainActivity.DEBUG_TAG, "Assets.loadWintermute(Resources): imageSource == null");

            Log.d(MainActivity.DEBUG_TAG, "BAILING EARLY!!!!!");
            return;
        }

        //IF WE'VE MADE IT THIS FAR, THE IMAGE RESOURCE LOADED AS INTENDED
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadWintermute(Resources): imageSource != null");
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadWintermute(Resources): imageSource.getWidth(), imageSource.getHeight() : " + imageSource.getWidth() + ", " + imageSource.getHeight());
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
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadWintermute(Resources): FINISHED!!!");
    }

    private static void loadCorgiCrusade(Resources resources) {
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

        Log.d(MainActivity.DEBUG_TAG, "Assets.initCorgiCrusade(Resources): FINISHED!!!");
    }

    private static void loadGobi(Resources resources) {
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

        Log.d(MainActivity.DEBUG_TAG, "Assets.initGobi(Resources): FINISHED!!!");
    }

    private static Bitmap flipHorizontally(Bitmap source) {
        int xCenter = source.getWidth()/2;
        int yCenter = source.getHeight()/2;

        Matrix matrix = new Matrix();
        //////////////////////////////////////////////////
        matrix.postScale(-1, 1, xCenter, yCenter);
        //////////////////////////////////////////////////

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}