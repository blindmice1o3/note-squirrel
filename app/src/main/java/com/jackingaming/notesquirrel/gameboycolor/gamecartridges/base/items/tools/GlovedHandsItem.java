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
            if ( ((GrowableGroundTile)tile).getCropEntity() != null ) {
                if ( ((GrowableGroundTile)tile).getCropEntity().getStage() == CropEntity.Stage.HARVESTABLE ) {
                    if (gameCartridge.getPlayer().getHoldable() == null) {
                        //instantiate Product based on CropEntity.Id.
                        Product cropProduct = ((GrowableGroundTile) tile).getCropEntity().generateCropProduct();
                        //compose Player with Holdable field, set to newly instantiated Product.
                        gameCartridge.getPlayer().setHoldable(cropProduct);

                        //TODO:
                        //compose CropEntity with boolean field named "regrow".
                        //if regrow is true, revert CropEntity's stage.
                        //otherwise remove CropEntity from game (set tile's CropEntity to null
                        //and set CropEntity's active to false so it's removed from scene's
                        //EntityManager.
                        //implement ShippingBinTile's adding to stash.
                    }
                }
            }
        }
    }

}