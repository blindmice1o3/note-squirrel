package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.SeedBagItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableGroundTile extends GrowableTile {

    public enum Type { EMPTY, CROP_SEEDED, GRASS_SEEDED, GRASS_SPROUTED, GRASS_HARVESTABLE; }

    private Type type;
    private SeedBagItem.SeedType seedType;
    private CropEntity cropEntity;

    public GrowableGroundTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.WALKABLE;

        type = Type.EMPTY;
        seedType = null;
        cropEntity = null;
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);

        updateImage(gameCartridge.getContext().getResources());
    }

    @Override
    public void updateImage(Resources resources) {
        image = Assets.cropGrowableGroundTile(resources, state, isWatered, type,
                gameCartridge.getTimeManager().getSeason());
    }

    //TODO: update it to seed-being-a-tile instead of seed-being-a-crop-entity
    public void changeToStateSeeded(Type type, SeedBagItem.SeedType seedType) {
//    public void plantCropEntity(CropEntity.Id idCropEntity) {
        state = State.SEEDED;
        this.type = type;
        this.seedType = seedType;

        updateImage(gameCartridge.getContext().getResources());
        //TODO: move instantiation of CropEntity to another method that's
        // post-watering-and-sleeping. Would have to know what kind of CropEntity.Id was planted.

    }

    public void changeToStatePrepared() {
        state = State.PREPARED;

        updateImage(gameCartridge.getContext().getResources());
    }

    public void changeToStateInitial() {
        state = State.INITIAL;

        updateImage(gameCartridge.getContext().getResources());
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;

        updateImage(gameCartridge.getContext().getResources());
    }

    public SeedBagItem.SeedType getSeedType() {
        return seedType;
    }

    public void setSeedType(SeedBagItem.SeedType seedType) {
        this.seedType = seedType;
    }

    public CropEntity getCropEntity() {
        return cropEntity;
    }

    public void setCropEntity(CropEntity cropEntity) {
        this.cropEntity = cropEntity;
    }

}