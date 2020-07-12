package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class WateringCanItem extends Item {

    public WateringCanItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Watering Can";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 5, 4);
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableGroundTile) {
            GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

            //ENTITY
            if (growableGroundTile.getCropEntity() != null) {
                if (growableGroundTile.getCropEntity().getStage() != CropEntity.Stage.HARVESTABLE) {
                    growableGroundTile.getCropEntity().toggleIsWatered();
                }
            }
            //TILE
            else if ( (growableGroundTile.getState() == GrowableTile.State.PREPARED) ||
                    (growableGroundTile.getState() == GrowableTile.State.SEEDED) ) {
                growableGroundTile.toggleIsWatered();
            }
        }
    }

}