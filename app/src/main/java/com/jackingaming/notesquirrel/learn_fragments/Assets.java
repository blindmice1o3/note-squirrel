package com.jackingaming.notesquirrel.learn_fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {

    public static Bitmap[][] items;
    public static Bitmap[][] entities;

    public static void init(Resources resources) {
        initItems(resources);
        //initEntities(resources);
        //initTiles(resources);
        //etc

        initEntities(resources);
    }
    private static void initEntities(Resources resources) {
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.pc_ms_office_clippit);

        //CHECK IF IMAGE RESOURCE DID not LOAD PROPERLY
        if (imageSource == null) {
            Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources): imageSource == null");

            Log.d(MainActivity.DEBUG_TAG, "BAILING EARLY!!!!!");
            return;
        }

        //IF WE'VE MADE IT THIS FAR, THE IMAGE RESOURCE LOADED AS INTENDED
        Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources): imageSource != null");
        Log.d(MainActivity.DEBUG_TAG, "Assets.initEntities(Resources): imageSource.getWidth(), imageSource.getHeight() : " + imageSource.getWidth() + ", " + imageSource.getHeight());
        //UNPACKING THE SPRITESHEET (logic is specific to each spritesheet's layout)
        int column = 22;
        int row = 41;

        entities = new Bitmap[row][column];

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

}