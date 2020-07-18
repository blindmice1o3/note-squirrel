package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.FlowerEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.FlowerSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableTableTile extends GrowableTile {

    private FlowerSeedItem.SeedType seedType;
    private FlowerEntity flowerEntity;

    public GrowableTableTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;

        seedType = null;
        flowerEntity = null;
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

    public void changeToStateSeeded(FlowerSeedItem.SeedType seedType) {
        //when seeding a pot, soil starts dry
        isWatered = false;
        state = State.SEEDED;
        this.seedType = seedType;

        updateImage(gameCartridge.getContext().getResources());
    }

    public void changeToStatePrepared() {
        //when placing a pot, soil starts dry
        isWatered = false;
        state = State.PREPARED;

        updateImage(gameCartridge.getContext().getResources());
    }

    public void changeToStateInitial() {
        //when removing a pot, soil starts dry
        isWatered = false;
        state = State.INITIAL;

        updateImage(gameCartridge.getContext().getResources());
    }

    public FlowerSeedItem.SeedType getSeedType() {
        return seedType;
    }

    public void setSeedType(FlowerSeedItem.SeedType seedType) {
        this.seedType = seedType;
    }

    public FlowerEntity getFlowerEntity() {
        return flowerEntity;
    }

    public void setFlowerEntity(FlowerEntity flowerEntity) {
        this.flowerEntity = flowerEntity;
    }

}