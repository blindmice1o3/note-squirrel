package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
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
            //ENTITY
            if ( ((GrowableGroundTile)tile).getCropEntity() != null ) {
                ((GrowableGroundTile)tile).getCropEntity().toggleIsWatered();
            }
            //TILE
            else if ( (((GrowableGroundTile)tile).getState() == GrowableTile.State.PREPARED) ||
                    (((GrowableGroundTile)tile).getState() == GrowableTile.State.SEEDED) ) {
                ((GrowableGroundTile)tile).toggleIsWatered();
            }
        }
    }

}