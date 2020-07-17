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
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        updateImage(gameCartridge.getContext().getResources());
    }

    @Override
    public void updateImage(Resources resources) {
        image = Assets.cropGrowableTableTile(resources, state, isWatered);
    }

    public void toggleHasPot() {
        switch (state) {
            case INITIAL:
                //when placing a pot, soil starts dry
                isWatered = false;
                state = State.PREPARED;
                break;
            case PREPARED:
                //when placing a pot, soil starts dry
                isWatered = false;
                state = State.INITIAL;
                break;
            case SEEDED:
                //intentionally blank.
                break;
        }

        updateImage(gameCartridge.getContext().getResources());
    }

    public void toggleHasSeed() {
        switch (state) {
            case INITIAL:
                //intentionally blank.
                break;
            case PREPARED:
                //when seeding, soil starts dry
                isWatered = false;
                state = State.SEEDED;
                break;
            case SEEDED:
                //when seeding, soil starts dry
                isWatered = false;
                state = State.PREPARED;
                break;
        }

        updateImage(gameCartridge.getContext().getResources());
    }

}