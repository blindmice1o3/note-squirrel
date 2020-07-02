package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class GameConsoleTile extends Tile {

    public GameConsoleTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

}