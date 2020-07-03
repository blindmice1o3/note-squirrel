package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

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
    public void updateImage(Resources resources) {
        if (isTilled) {
            if (isWatered) {
                image = Assets.cropTileTilled(resources, true);
            } else {
                image = Assets.cropTileTilled(resources, false);
            }
        } else {
            image = null;
        }
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        updateImage(gameCartridge.getContext().getResources());
    }

    public void toggleIsTilled() {
        isTilled = !isTilled;

        updateImage(gameCartridge.getContext().getResources());
    }

    public boolean getIsTilled() {
        return isTilled;
    }

}