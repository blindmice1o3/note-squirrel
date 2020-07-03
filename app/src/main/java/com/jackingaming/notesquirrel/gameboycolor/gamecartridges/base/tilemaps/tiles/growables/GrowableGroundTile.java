package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableGroundTile extends GrowableTile {

    private boolean isTilled;

    public GrowableGroundTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.WALKABLE;

        isTilled = false;
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        image = (isTilled) ? (Assets.cropTileTilled(gameCartridge.getContext().getResources(), false)) : (null);
    }

    public void toggleIsTilled() {
        isTilled = !isTilled;

        image = (isTilled) ? (Assets.cropTileTilled(gameCartridge.getContext().getResources(), false)) : (null);
    }

}