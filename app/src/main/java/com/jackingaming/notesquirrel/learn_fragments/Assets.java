package com.jackingaming.notesquirrel.learn_fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class Assets {
    private Bitmap imageSource;
    private Bitmap[][] items;

    public Assets(Resources resources) {
        imageSource = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);
        if (imageSource != null) {
            Log.d(MainActivity.DEBUG_TAG, "Assets.init(Resources): imageSource != null");
        } else {
            Log.d(MainActivity.DEBUG_TAG, "Assets.init(Resources): imageSource == null");
        }
        initItems();
    }

    private void initItems(){
        //private static Bitmap[][] initItems(Resources resources){
        //Bitmap[][] returner;

        int column = 9;
        int row = 9;
        //Bitmap imageSource = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);
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

        //return returner;
    }

    public Bitmap[][] getItems() {
        return items;
    }

    public Bitmap getImageSource() {
        return imageSource;
    }
}
