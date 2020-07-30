package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools;

import android.content.res.Resources;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.FlowerEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.Product;

public class ScissorsItem extends Item {

    public ScissorsItem(GameCartridge gameCartridge) {
        super(gameCartridge);

        this.id = "Scissors";
        price = 1200;
    }

    @Override
    public void initImage(Resources resources) {
        image = cropImage(resources, 5, 5);
    }

    @Override
    public void execute(Tile tile) {
        if (tile instanceof GrowableTableTile) {
            GrowableTableTile growableTableTile = (GrowableTableTile) tile;

            if (growableTableTile.getFlowerEntity() != null) {
                FlowerEntity flowerEntity = growableTableTile.getFlowerEntity();

                if (flowerEntity.getStage() == FlowerEntity.Stage.HARVESTABLE) {
                    if (gameCartridge.getPlayer().getHoldable() == null) {
                        //instantiate Product based on FlowerEntity.Id.
                        Product flowerProduct = flowerEntity.generateFlowerProduct();
                        //ADD PRODUCT TO entityManager.
                        gameCartridge.getSceneManager().getCurrentScene().getEntityManager().addEntity(flowerProduct);
                        //compose Player with Holdable field, set to newly instantiated Product.
                        gameCartridge.getPlayer().setHoldable(flowerProduct);

                        //remove FlowerEntity from game (set tile's FlowerEntity to null and set
                        //FlowerEntity's active to false so it's removed from scene's EntityManager.
                        growableTableTile.setFlowerEntity(null);
                        growableTableTile.setSeedType(null);
                        growableTableTile.changeToStatePrepared();
                        flowerEntity.setActive(false);
                    }
                }
            }
        }
    }

}