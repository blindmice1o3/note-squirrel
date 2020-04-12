package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {

    public static Bitmap rgbTileTest;

    public static Bitmap[][] corgiCrusade;
    public static Bitmap[][] hm3Farm;

    public static void init(Resources resources) {
        rgbTileTest = BitmapFactory.decodeResource(resources, R.drawable.rgb_tile_test);
        Log.d(MainActivity.DEBUG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Log.d(MainActivity.DEBUG_TAG, "!!!!! TESTING rgb TO DETERMINE SOLID TILES !!!!!");
        Log.d(MainActivity.DEBUG_TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        for (int y = 0; y < rgbTileTest.getHeight(); y++) {
            int pixel = rgbTileTest.getPixel(0, y);
            int red = Color.red(pixel);
            int green = Color.green(pixel);
            int blue = Color.blue(pixel);
            Log.d(MainActivity.DEBUG_TAG, "rgb at y = " + y + ": " + red + ", " + green + ", " + blue);
        }



        initCorgiCrusade(resources);
        initHm3Farm(resources);
    }

    public static void initHm3Farm(Resources resources) {
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

    public static void initCorgiCrusade(Resources resources) {
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

    public static Bitmap flipHorizontally(Bitmap source) {
        int xCenter = source.getWidth()/2;
        int yCenter = source.getHeight()/2;

        Matrix matrix = new Matrix();
        //////////////////////////////////////////////////
        matrix.postScale(-1, 1, xCenter, yCenter);
        //////////////////////////////////////////////////

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}