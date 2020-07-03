package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public abstract class GrowableTile extends Tile {

    protected boolean isWatered;
    protected boolean isPlanted;

    public GrowableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        isWatered = false;
        isPlanted = false;
    }

    public abstract void updateImage(Resources resources);

    public void toggleIsWatered() {
        isWatered = !isWatered;

        updateImage(gameCartridge.getContext().getResources());
    }

    public void toggleIsPlanted() {
        isPlanted = !isPlanted;
    }

}