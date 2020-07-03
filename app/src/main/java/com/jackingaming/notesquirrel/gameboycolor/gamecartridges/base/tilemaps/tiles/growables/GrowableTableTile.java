package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableTableTile extends GrowableTile {

    private boolean hasPot;

    public GrowableTableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;

        hasPot = false;
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        image = (hasPot) ? (Assets.cropPotTileWatered(gameCartridge.getContext().getResources())) : (null);
    }

    public void toggleHasPot() {
        hasPot = !hasPot;

        image = (hasPot) ? (Assets.cropPotTileWatered(gameCartridge.getContext().getResources())) : (null);
    }

}