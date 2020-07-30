package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public interface Holdable {
    void init(GameCartridge gameCartridge);
    void setPosition(float xCurrent, float yCurrent);
    boolean drop(Tile tile);
}