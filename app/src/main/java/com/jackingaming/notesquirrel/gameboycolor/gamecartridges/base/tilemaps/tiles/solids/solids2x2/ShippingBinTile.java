package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public class ShippingBinTile extends Tile {

    public enum Quadrant { TOP_LEFT, TOP_RIGHT, BOTTOM_LFET, BOTTOM_RIGHT; }

    private Quadrant quadrant;

    public ShippingBinTile(GameCartridge gameCartridge, int xIndex, int yIndex, Quadrant quadrant) {
        super(gameCartridge, xIndex, yIndex);

        this.quadrant = quadrant;
        init(gameCartridge);
    }

    public void init(GameCartridge gameCartridge) {
        image = Assets.cropShippingBinTile(gameCartridge.getContext().getResources(), quadrant);
    }

}