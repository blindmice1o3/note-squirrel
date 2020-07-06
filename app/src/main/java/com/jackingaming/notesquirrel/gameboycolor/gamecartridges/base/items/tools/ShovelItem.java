package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;

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
            ((GrowableGroundTile)tile).toggleIsTilled();
        }
    }

}