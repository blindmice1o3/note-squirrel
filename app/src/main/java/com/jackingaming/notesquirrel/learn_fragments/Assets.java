package com.jackingaming.notesquirrel.learn_fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {

    public static Bitmap[][] items;
    public static Bitmap[][] tiles;
    public static Bitmap[][] entities;

    public static void init(Resources resources) {
        initItems(resources);
        initTiles(resources);
        initEntities(resources);
    }

    private static void initTiles(Resources resources) {
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.pc_yoko_tileset1);

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

        entities = new Bitmap[row][column];

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
                entities[y][x] = Bitmap.createBitmap(imageSource, xCurrent, yCurrent, tileWidth, tileHeight);
                xCurrent += (tileWidth + margin);
            }
            xCurrent = margin;
            yCurrent += (tileHeight + margin);
        }
        Log.d(MainActivity.DEBUG_TAG, "Assets.loadWintermute(Resources): FINISHED!!!");
    }

}