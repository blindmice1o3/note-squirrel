package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class SickleItem extends Item {

    public SickleItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Sickle";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 4, 4);
    }

    @Override
    public void execute(Tile tile) {
        //TODO:
    }

}