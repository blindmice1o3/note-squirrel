package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public interface Holdable {
    void init(GameCartridge gameCartridge);
    void render(Canvas canvas);
    void setPosition(float xCurrent, float yCurrent);
    void pickUp();
    void drop(Tile tile);
}