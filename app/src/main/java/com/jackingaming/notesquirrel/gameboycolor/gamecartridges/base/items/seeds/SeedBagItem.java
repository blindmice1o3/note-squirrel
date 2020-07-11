package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds;


import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class SeedBagItem extends Item {

    public enum SeedType { GRASS, TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }

    private SeedType seedType;

    public SeedBagItem(GameCartridge gameCartridge, SeedType seedType) {
        super(gameCartridge);
        this.id = "Seed Bag";
        this.seedType = seedType;
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImageSeedBag(resources);
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
                    growableGroundTile.changeToStateSeeded(type, seedType);
                    ///////////////////////////////////////////////////////
                }
            }
        }
    }

    public SeedType getSeedType() {
        return seedType;
    }

}