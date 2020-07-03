package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableTableTile extends GrowableTile {

    private boolean hasPot;

    public GrowableTableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;

        hasPot = false;
    }

    @Override
    public void updateImage(Resources resources) {
        if (hasPot) {
            image = Assets.cropPotTileWatered(gameCartridge.getContext().getResources());
        } else {
            image = null;
        }
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        updateImage(gameCartridge.getContext().getResources());
    }

    public void toggleHasPot() {
        hasPot = !hasPot;

        updateImage(gameCartridge.getContext().getResources());
    }

}