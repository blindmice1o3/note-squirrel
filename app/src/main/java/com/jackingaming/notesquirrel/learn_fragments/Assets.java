package com.jackingaming.notesquirrel.learn_fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {

    public static Bitmap[][] items;

    public static void init(Resources resources) {
        initItems(resources);
        //initEntities(resources);
        //initTiles(resources);
        //etc
    }

    private static void initItems(Resources resources){
        //LOAD SPRITESHEET
        Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);

        //CHECK IF IMAGE RESOURCE DID not LOAD PROPERLY
        if (imageSource == null) {
            Log.d(MainActivity.DEBUG_TAG, "Assets.init(Resources): imageSource == null");

            Log.d(MainActivity.DEBUG_TAG, "BAILING EARLY!!!!!");
            return;
        }

        //IF WE'VE MADE IT THIS FAR, THE IMAGE RESOURCE LOADED AS INTENDED
        Log.d(MainActivity.DEBUG_TAG, "Assets.init(Resources): imageSource != null");

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

}