package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTableTile;

public class FlowerPotItem extends Item {

    public FlowerPotItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Flower Pot";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 9, 6);
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableTableTile) {
            ((GrowableTableTile)tile).toggleHasPot();
        }
    }

}