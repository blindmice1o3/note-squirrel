package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class TelevisionTile extends Tile {

    public TelevisionTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

}