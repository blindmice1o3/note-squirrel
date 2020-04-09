package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {

    public static Bitmap[][] corgiCrusade;

    public static void init(Resources resources) {
        initCorgiCrusade(resources);
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