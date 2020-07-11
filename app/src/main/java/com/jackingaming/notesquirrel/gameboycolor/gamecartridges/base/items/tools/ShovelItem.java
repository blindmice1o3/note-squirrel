package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class ShovelItem extends Item {

    public ShovelItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Shovel";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 3, 4);
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableGroundTile) {
            GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

            //untilled, not seeded/grass, and no occupied by CropEntity.
            if ( (growableGroundTile.getState() == GrowableTile.State.INITIAL) &&
                    (growableGroundTile.getType() == GrowableGroundTile.Type.EMPTY) &&
                    (growableGroundTile.getCropEntity() == null) ) {
                growableGroundTile.changeToStatePrepared();
            }
        }
    }

}