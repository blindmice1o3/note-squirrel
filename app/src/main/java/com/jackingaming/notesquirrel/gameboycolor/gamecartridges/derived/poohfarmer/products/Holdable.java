package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public interface Holdable {
    void setPosition(int xCurrent, int yCurrent);
    void pickUp();
    void drop(Tile tile);
}