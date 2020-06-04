package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;

public class Item {

    public enum Id { AX, HAMMER, SHOVEL, SICKLE, WATERING_CAN, FISHING_POLE, BUG_NET; }

    private Id id;
    private Bitmap image;

    public Item (Resources resources, Id id) {
        this.id = id;
        initImage(resources);
    }

    private void initImage(Resources resources) {
        switch (id) {
            case AX:
                image = cropImage(resources, 1, 4);
                break;
            case HAMMER:
                image = cropImage(resources, 2, 4);
                break;
            case SHOVEL:
                image = cropImage(resources, 3, 4);
                break;
            case SICKLE:
                image = cropImage(resources, 4, 4);
                break;
            case WATERING_CAN:
                image = cropImage(resources, 5, 4);
                break;
            case FISHING_POLE:
                image = cropImage(resources, 6, 4);
                break;
            case BUG_NET:
                image = cropImage(resources, 7, 4);
                break;
        }
    }

    /**
     * Crop sprite of item_grid_mode on-the-fly, instead of all-at-once
     * immediately when the game begins.
     *
     * @param resources Context's instance of Resources.
     * @param column A value between 1-9.
     * @param row A value between 1-9.
     * @return Sprite of an item_grid_mode from sprite sheet
     * "gbc_hm2_spritesheet_items". ***Some cells are blank.***
     */
    private Bitmap cropImage(Resources resources, int column, int row) {
        int margin = 1;
        int widthItem = 16;
        int heightItem = 16;

        // start = border + (index * (offset))
        int xStart = margin + ((column-1) * (widthItem + margin));
        int yStart = margin + ((row-1) * (heightItem + margin));

        Bitmap spriteSheetItems = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);

        return Bitmap.createBitmap(spriteSheetItems, xStart, yStart, widthItem, heightItem);
    }

    public Id getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

}