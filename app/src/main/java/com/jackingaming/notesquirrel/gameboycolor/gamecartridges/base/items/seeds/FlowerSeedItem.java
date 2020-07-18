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

    public enum SeedType { GERANIUM, PRIMROSE, LAVENDER, ORCHID, SAGE, SAFFRON, ROSEMARY, CHAMOMILE; }

    private SeedType seedType;
    private boolean isHerb;

    public FlowerSeedItem(GameCartridge gameCartridge, SeedType seedType) {
        super(gameCartridge);

        this.id = "Flower Seed";
        this.seedType = seedType;
        initIsHerb();
    }

    private void initIsHerb() {
        switch (seedType) {
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
                initImage(gameCartridge.getContext().getResources());
                break;
        }
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImageFlowerSeedItem(resources);
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

    private Bitmap cropImageFlowerSeedItem(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        Bitmap flowerSeedItem = null;
        if (isHerb) {
            flowerSeedItem = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 150, 16, 16);
        } else {
            flowerSeedItem = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 150, 16, 16);
        }

        return flowerSeedItem;
    }

    public SeedType getSeedType() {
        return seedType;
    }

}