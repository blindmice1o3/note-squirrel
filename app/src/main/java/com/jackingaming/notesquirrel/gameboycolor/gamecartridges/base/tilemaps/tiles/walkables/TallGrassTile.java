package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class TallGrassTile extends Tile {

    public TallGrassTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.WALKABLE;
    }

}