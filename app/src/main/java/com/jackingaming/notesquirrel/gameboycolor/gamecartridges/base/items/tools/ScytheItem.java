package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;

public class ScytheItem extends Item {

    public ScytheItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Scythe";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 4, 4);
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableGroundTile){
            GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

            if (growableGroundTile.getType() == GrowableGroundTile.Type.GRASS_HARVESTABLE) {
                int incrementedFodderQuantity = gameCartridge.getPlayer().getFodderQuantity() + 1;
                ///////////////////////////////////////////////////////////////////////
                gameCartridge.getPlayer().setFodderQuantity(incrementedFodderQuantity);
                ///////////////////////////////////////////////////////////////////////

                growableGroundTile.setType(GrowableGroundTile.Type.GRASS_SEEDED);
            }
        }
    }

}