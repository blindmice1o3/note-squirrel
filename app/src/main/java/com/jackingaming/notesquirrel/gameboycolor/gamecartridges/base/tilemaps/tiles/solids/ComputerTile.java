package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class ComputerTile extends Tile {

    public ComputerTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

}