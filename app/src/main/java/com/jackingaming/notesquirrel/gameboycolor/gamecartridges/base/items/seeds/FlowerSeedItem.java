package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class FlowerSeedItem extends Item {

    public enum SeedType { MYSTERY, GERANIUM, PRIMROSE, LAVENDER, ORCHID, SAGE, SAFFRON, ROSEMARY, CHAMOMILE; }

    private SeedType seedType;
    private boolean isHerb;

    public FlowerSeedItem(GameCartridge gameCartridge, SeedType seedType) {
        super(gameCartridge);

        this.id = "Flower Seed";
        this.seedType = seedType;
        initIsHerb();
        initPrice();
    }

    private void initPrice() {
        switch (seedType) {
            case MYSTERY:
                price = 0;
                break;
            case GERANIUM:
                price = 100;
                break;
            case PRIMROSE:
                price = 300;
                break;
            case LAVENDER:
                price = 100;
                break;
            case ORCHID:
                price = 300;
                break;
            case SAGE:
                price = 100;
                break;
            case SAFFRON:
                price = 100;
                break;
            case ROSEMARY:
                price = 100;
                break;
            case CHAMOMILE:
                price = 150;
                break;
        }
    }

    private void initIsHerb() {
        switch (seedType) {
            case MYSTERY:
            case GERANIUM:
            case PRIMROSE:
            case LAVENDER:
            case ORCHID:
                isHerb = false;
                break;
            case SAGE:
            case SAFFRON:
            case ROSEMARY:
            case CHAMOMILE:
                isHerb = true;
                break;
        }
    }

    @Override
    public void initImage(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        //TODO: not enough sprites (using "disabled" version).
        switch (seedType) {
            //FLOWERS
            case MYSTERY:
                image = BitmapFactory.decodeResource(resources, R.drawable.green2478);
                break;
            case GERANIUM:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 150, 16, 16);
                break;
            case PRIMROSE:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 132, 16, 16);
                break;
            case LAVENDER:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 180, 150, 16, 16);
                break;
            case ORCHID:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 180, 132, 16, 16);
                break;
            //HERBS
            case SAGE:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 149, 16, 16);
                break;
            case SAFFRON:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 131, 16, 16);
                break;
            case ROSEMARY:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 133, 149, 16, 16);
                break;
            case CHAMOMILE:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 133, 131, 16, 16);
                break;
        }
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableTableTile) {
            GrowableTableTile growableTableTile = (GrowableTableTile) tile;

            if ( (growableTableTile.getState() == GrowableTile.State.PREPARED) &&
                    (growableTableTile.getSeedType() == null) &&
                    (growableTableTile.getFlowerEntity() == null) ) {
                ////////////////////////////////////////////////
                growableTableTile.changeToStateSeeded(seedType);
                ////////////////////////////////////////////////
            }
        }
    }

    public SeedType getSeedType() {
        return seedType;
    }

    public boolean getIsHerb() {
        return isHerb;
    }

}