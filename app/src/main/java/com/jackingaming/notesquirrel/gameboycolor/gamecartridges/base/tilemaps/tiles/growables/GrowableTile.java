package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public abstract class GrowableTile extends Tile {

    protected boolean isWatered;
    protected boolean isPlanted;

    public GrowableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        isWatered = false;
        isPlanted = false;
    }

    public void toggleIsWatered() {
        isWatered = !isWatered;
    }

    public void toggleIsPlanted() {
        isPlanted = !isPlanted;
    }

}