package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products.Product;

public class GlovedHandsItem extends Item {

    public GlovedHandsItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Gloved Hands";
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 9, 3);
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableGroundTile) {
            GrowableGroundTile growableGroundTile = (GrowableGroundTile) tile;

            if ( growableGroundTile.getCropEntity() != null ) {
                CropEntity cropEntity = growableGroundTile.getCropEntity();

                if ( cropEntity.getStage() == CropEntity.Stage.HARVESTABLE ) {
                    if (gameCartridge.getPlayer().getHoldable() == null) {
                        //instantiate Product based on CropEntity.Id.
                        Product cropProduct = cropEntity.generateCropProduct();
                        //compose Player with Holdable field, set to newly instantiated Product.
                        gameCartridge.getPlayer().setHoldable(cropProduct);

                        //TODO: move into a CropEntity method.
                        //compose CropEntity with boolean field named "regrow".
                        //if regrow is true, revert CropEntity's stage.
                        if (cropEntity.getIsRegrowable()) {
                            cropEntity.revertToPriorStageByDecreasingDaysWatered();
                        }
                        //otherwise remove CropEntity from game (set tile's CropEntity to null
                        //and set CropEntity's active to false so it's removed from scene's
                        //EntityManager.
                        else {
                            growableGroundTile.setCropEntity(null);
                            cropEntity.setActive(false);
                        }
                    }
                }
            }
        }
    }

}