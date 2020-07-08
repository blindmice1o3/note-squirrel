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
            if (((GrowableGroundTile)tile).getState() == GrowableTile.State.PREPARED){
                if (((GrowableGroundTile)tile).getCropEntity() == null) {
                    switch (seedType) {
                        case GRASS:
                            ((GrowableGroundTile) tile).changeToStateSeeded(GrowableGroundTile.Type.GRASS_SEEDED, seedType);
                            break;
                        case TURNIP:
                        case POTATO:
                        case TOMATO:
                        case CORN:
                        case EGGPLANT:
                        case PEANUT:
                        case CARROT:
                        case BROCCOLI:
                            ((GrowableGroundTile) tile).changeToStateSeeded(GrowableGroundTile.Type.CROP_SEEDED, seedType);
                            break;
                    }
                }
            }
        }
    }

    public SeedType getSeedType() {
        return seedType;
    }

}