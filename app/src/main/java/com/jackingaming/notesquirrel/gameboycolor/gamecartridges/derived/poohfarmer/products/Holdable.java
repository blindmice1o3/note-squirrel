package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public interface Holdable {
    void init(GameCartridge gameCartridge);
    void initBounds();
    Rect getCollisionBounds(float xOffset, float yOffset);
    void render(Canvas canvas);
    void setPosition(float xCurrent, float yCurrent);
    boolean drop(Tile tile);
}