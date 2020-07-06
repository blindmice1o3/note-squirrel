package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class AxeItem extends Item {

    public AxeItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Axe";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 1, 4);
    }

    @Override
    public void execute(Tile tile) {
        //TODO:
    }

}