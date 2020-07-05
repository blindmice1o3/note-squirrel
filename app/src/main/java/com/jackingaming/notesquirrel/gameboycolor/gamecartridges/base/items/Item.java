package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

import java.io.Serializable;

public class Item
        implements Serializable {

    public enum Id { AX, HAMMER, SHOVEL, SICKLE, WATERING_CAN, FISHING_POLE, BUG_NET, SEED_BAG; }

    private Id id;
    transient private Bitmap image;

    public Item(Resources resources, Id id) {
        this.id = id;
        initImage(resources);
    }

    public void initImage(Resources resources) {
        switch (id) {
            case AX:
                image = cropImage(resources, 1, 4);
                break;
            case HAMMER:
                image = cropImage(resources, 2, 4);
                break;
            case SHOVEL:
                image = cropImage(resources, 3, 4);
                break;
            case SICKLE:
                image = cropImage(resources, 4, 4);
                break;
            case WATERING_CAN:
                image = cropImage(resources, 5, 4);
                break;
            case FISHING_POLE:
                image = cropImage(resources, 6, 4);
                break;
            case BUG_NET:
                image = cropImage(resources, 7, 4);
                break;
            case SEED_BAG:
                image = cropImageSeedBag(resources);
                break;
        }
    }

    public void execute(Tile tile) {
        switch (id) {
            case AX:
                //TODO:
                break;
            case HAMMER:
                //TODO:
                break;
            case SHOVEL:
                if (tile instanceof GrowableGroundTile) {
                    ((GrowableGroundTile)tile).toggleIsTilled();
                }
                break;
            case SICKLE:
                //TODO:
                break;
            case WATERING_CAN:
                if (tile instanceof GrowableGroundTile) {
                    //ENTITY
                    if ( ((GrowableGroundTile)tile).getCropEntity() != null ) {
                        ((GrowableGroundTile)tile).getCropEntity().toggleIsWatered();
                    }
                    //TILE
                    else if ( (((GrowableGroundTile)tile).getState() == GrowableTile.State.PREPARED) ||
                            (((GrowableGroundTile)tile).getState() == GrowableTile.State.SEEDED) ) {
                        ((GrowableGroundTile)tile).toggleIsWatered();
                    }
                }
                break;
            case FISHING_POLE:
                //TODO:
                break;
            case BUG_NET:
                //TODO:
                break;
            case SEED_BAG:
                if (tile instanceof GrowableGroundTile) {
                    if ( ((GrowableGroundTile)tile).getState() == GrowableTile.State.PREPARED ){
                        if ( ((GrowableGroundTile)tile).getCropEntity() == null ) {
                            //TODO: update it to seed-being-a-tile instead of seed-being-a-crop-entity
                            ((GrowableGroundTile)tile).changeToStateSeeded(GrowableGroundTile.Type.CROP_SEEDED);
//                            ((GrowableGroundTile)tile).plantCropEntity(CropEntity.Id.POTATO);
                        }
                    }
                }
                break;
        }
    }

    /**
     * Crop sprite of item_grid_mode on-the-fly, instead of all-at-once
     * immediately when the game begins.
     *
     * @param resources Context's instance of Resources.
     * @param column A value between 1-9.
     * @param row A value between 1-9.
     * @return Sprite of an item_grid_mode from sprite sheet
     * "gbc_hm2_spritesheet_items". ***Some cells are blank.***
     */
    private Bitmap cropImage(Resources resources, int column, int row) {
        int margin = 1;
        int widthItem = 16;
        int heightItem = 16;

        // start = border + (index * (offset))
        int xStart = margin + ((column-1) * (widthItem + margin));
        int yStart = margin + ((row-1) * (heightItem + margin));

        Bitmap spriteSheetItems = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);

        return Bitmap.createBitmap(spriteSheetItems, xStart, yStart, widthItem, heightItem);
    }

    private Bitmap cropImageSeedBag(Resources resources) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.seed_bag);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 16, 16, false);

        return resizedBitmap;
    }

    public Id getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

}