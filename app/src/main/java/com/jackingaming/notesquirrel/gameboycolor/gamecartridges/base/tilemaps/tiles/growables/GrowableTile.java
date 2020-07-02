package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public abstract class GrowableTile extends Tile {

    public GrowableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);
    }

}