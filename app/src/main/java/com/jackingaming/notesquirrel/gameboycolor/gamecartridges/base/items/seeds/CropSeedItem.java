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
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImageCropSeedItem(resources);
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