package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableTableTile extends GrowableTile {

    public GrowableTableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

    @Override
    public void updateImage(Resources resources) {
        image = Assets.cropGrowableTableTile(resources, state, isWatered);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        updateImage(gameCartridge.getContext().getResources());
    }

    public void toggleHasPot() {
        if (state == State.INITIAL) {
            state = State.PREPARED;
        } else if (state == State.PREPARED) {
            state = State.INITIAL;
        }

        updateImage(gameCartridge.getContext().getResources());
    }

    public void toggleHasSeed() {
        if (state == State.PREPARED) {
            state = State.SEEDED;
        } else if (state == State.SEEDED) {
            state = State.PREPARED;
        }

        updateImage(gameCartridge.getContext().getResources());
    }

}