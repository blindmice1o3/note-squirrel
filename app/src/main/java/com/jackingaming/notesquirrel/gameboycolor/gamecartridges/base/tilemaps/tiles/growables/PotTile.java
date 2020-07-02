package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class PotTile extends GrowableTile {

    private boolean isEmptyTable;
    private boolean isWatered;
    private boolean isPlanted;

    public PotTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;

        isEmptyTable = true;
        isWatered = false;
        isPlanted = false;
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        image = (isEmptyTable) ? (null) : (Assets.cropPotTileWatered(gameCartridge.getContext().getResources()));
    }

    public void toggleIsEmptyTable() {
        isEmptyTable = !isEmptyTable;

        image = (isEmptyTable) ? (null) : (Assets.cropPotTileWatered(gameCartridge.getContext().getResources()));
    }

    public void toggleIsWatered() {
        isWatered = !isWatered;
    }

    public void toggleIsPlanted() {
        isPlanted = !isPlanted;
    }

}