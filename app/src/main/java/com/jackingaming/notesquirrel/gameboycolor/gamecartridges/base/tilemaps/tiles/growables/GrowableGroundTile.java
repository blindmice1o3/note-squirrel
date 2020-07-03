package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class GrowableGroundTile extends GrowableTile {

    private boolean isTilled;
    private CropEntity cropEntity;

    public GrowableGroundTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.WALKABLE;

        isTilled = false;
        cropEntity = null;
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

    @Override
    public void render(Canvas canvas, int x, int y) {
        super.render(canvas, x, y);

        if (cropEntity != null) {
            cropEntity.render(canvas);
        }
    }

    public void toggleIsTilled() {
        isTilled = !isTilled;

        updateImage(gameCartridge.getContext().getResources());
    }

    public boolean getIsTilled() {
        return isTilled;
    }

    public CropEntity getCropEntity() {
        return cropEntity;
    }

    public void plantCropEntity(CropEntity.Id idCropEntity) {
        int tileWidth = gameCartridge.getSceneManager().getCurrentScene().getTileMap().getTileWidth();
        int tileHeight = gameCartridge.getSceneManager().getCurrentScene().getTileMap().getTileHeight();
        /////////////////////////////////////////////////////////////
        CropEntity seed = new CropEntity(gameCartridge, idCropEntity,
                (xIndex * tileWidth), (yIndex * tileHeight));
        /////////////////////////////////////////////////////////////

        //this tile's CROP_ENTITY
        cropEntity = seed;
        //Scene's ENTITY_MANAGER
        gameCartridge.getSceneManager().getCurrentScene().getEntityManager().addEntity(seed);
    }

}