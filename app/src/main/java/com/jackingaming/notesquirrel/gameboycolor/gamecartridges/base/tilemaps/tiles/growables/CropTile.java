package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;

public class CropTile extends GrowableTile {

    public CropTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.WALKABLE;
    }

}