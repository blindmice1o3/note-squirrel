package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class HammerItem extends Item {

    public HammerItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Hammer";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 2, 4);
    }

    @Override
    public void execute(Tile tile) {
        //TODO:
    }

}