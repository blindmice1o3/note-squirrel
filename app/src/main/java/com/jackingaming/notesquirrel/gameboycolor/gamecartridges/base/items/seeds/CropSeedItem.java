package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class CropSeedItem extends Item {

    public enum SeedType { GRASS, TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }

    private SeedType seedType;

    public CropSeedItem(GameCartridge gameCartridge, SeedType seedType) {
        super(gameCartridge);

        this.id = "Crop Seed";
        this.seedType = seedType;
        initPrice();
    }

    public void initPrice() {
        switch (seedType) {
            case GRASS:
                price = 500;
                break;
            case TURNIP:
                price = 120;
                break;
            case POTATO:
                price = 150;
                break;
            case TOMATO:
                price = 200;
                break;
            case CORN:
                price = 300;
                break;
            case EGGPLANT:
                price = 150;
                break;
            case PEANUT:
                price = 200;
                break;
            case CARROT:
                price = 300;
                break;
            case BROCCOLI:
                price = 500;
                break;
        }
    }

    @Override
    public void initImage(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        //TODO: not enough sprites (using "disabled" version AND some are being re-used).
        switch (seedType) {
            case GRASS:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 81, 148, 16, 16);
                break;
            case TURNIP:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 33, 148, 16, 16);
                break;
            case POTATO:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 33, 132, 16, 16);
                break;
            case TOMATO:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 57, 148, 16, 16);
                break;
            case CORN:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 57, 132, 16, 16);
                break;
            case EGGPLANT:
                //grass disabled
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 81, 132, 16, 16);
                break;
            case PEANUT:
            case CARROT:
            case BROCCOLI:
                image = cropImageCropSeedItem(resources);
                break;
        }
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableGroundTile) {
            GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

            if (growableGroundTile.getState() == GrowableTile.State.PREPARED){

                if (growableGroundTile.getCropEntity() == null) {
                    GrowableGroundTile.Type type = null;

                    if (seedType == SeedType.GRASS) {
                        type = GrowableGroundTile.Type.GRASS_SEEDED;
                    } else {
                        type = GrowableGroundTile.Type.CROP_SEEDED;
                    }

                    ///////////////////////////////////////////////////////
                    //this is for scenario: tilled/potted AND watered (when seeded, soil starts dry).
                    growableGroundTile.setIsWatered(false);
                    growableGroundTile.changeToStateSeeded(type, seedType);
                    ///////////////////////////////////////////////////////
                }
            }
        }
    }

    private Bitmap cropImageCropSeedItem(Resources resources) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.seed_bag);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 16, 16, false);

        return resizedBitmap;
    }

    public SeedType getSeedType() {
        return seedType;
    }

}