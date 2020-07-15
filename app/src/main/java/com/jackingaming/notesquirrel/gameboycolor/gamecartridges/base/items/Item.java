package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

import java.io.Serializable;

public abstract class Item
        implements Serializable {

    transient protected GameCartridge gameCartridge;
    transient protected Bitmap image;
    protected String id;

    public Item(GameCartridge gameCartridge) {
        init(gameCartridge);
    }

    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        initImage(gameCartridge.getContext().getResources());
    }

    public abstract void initImage(Resources resources);
    public abstract void execute(Tile tile);

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
    protected Bitmap cropImage(Resources resources, int column, int row) {
        int margin = 1;
        int widthItem = 16;
        int heightItem = 16;

        // start = border + (index * (offset))
        int xStart = margin + ((column-1) * (widthItem + margin));
        int yStart = margin + ((row-1) * (heightItem + margin));

        Bitmap spriteSheetItems = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);

        return Bitmap.createBitmap(spriteSheetItems, xStart, yStart, widthItem, heightItem);
    }

    public Bitmap getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

}