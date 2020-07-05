package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;

public abstract class GrowableTile extends Tile {

    public enum State { INITIAL, PREPARED, SEEDED; }

    protected State state;
    protected boolean isWatered;

    public GrowableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        state = State.INITIAL;
        isWatered = false;
    }

    public abstract void updateImage(Resources resources);

    public void toggleIsWatered() {
        isWatered = !isWatered;

        updateImage(gameCartridge.getContext().getResources());
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean getIsWatered() {
        return isWatered;
    }

    public void setIsWatered(boolean isWatered) {
        this.isWatered = isWatered;
    }
}