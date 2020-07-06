package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class BugNetItem extends Item {

    public BugNetItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Bug Net";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 7, 4);
    }

    @Override
    public void execute(Tile tile) {
        //TODO:
    }

}