package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.GenericWalkableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Holdable;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Sellable;

import java.io.Serializable;

public class Product extends Entity
        implements Holdable, Sellable, Serializable {

    public enum Id { TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI,
        MYSTERY, GERANIUM, PRIMROSE, LAVENDER, ORCHID, SAGE, SAFFRON, ROSEMARY, CHAMOMILE; }

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Product.Id id;
    private boolean isWhole;
    private int price;

    public Product(GameCartridge gameCartridge, float xCurrent, float yCurrent, Product.Id id) {
        super(gameCartridge, xCurrent, yCurrent);

        this.id = id;
        isWhole = true;
        establishPrice();

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        initImage(gameCartridge.getContext().getResources());
        initBounds();
    }

    @Override
    public void initBounds() {
        bounds = new Rect(0, 0, width, height);
    }

    @Override
    public void update(long elapsed) {
        //intentionally blank.
    }

    public void establishPrice() {
        switch (id) {
            case TURNIP:
                price = 40;
                break;
            case POTATO:
                price = 60;
                break;
            case TOMATO:
                price = 80;
                break;
            case CORN:
                price = 100;
                break;
            case EGGPLANT:
                price = 40;
                break;
            case PEANUT:
                price = 40;
                break;
            case CARROT:
                price = 100;
                break;
            case BROCCOLI:
                price = 100;
                break;
            case MYSTERY:
                price = 9000;
                break;
            case GERANIUM:
                price = 250;
                break;
            case PRIMROSE:
                price = 350;
                break;
            case LAVENDER:
                price = 250;
                break;
            case ORCHID:
                price = 500;
                break;
            case SAGE:
                price = 200;
                break;
            case SAFFRON:
                price = 200;
                break;
            case ROSEMARY:
                price = 150;
                break;
            case CHAMOMILE:
                price = 150;
                break;
        }
    }

    public void initImage(Resources resources) {
        switch (id) {
            //OUTDOORS
            case TURNIP:
            case POTATO:
            case TOMATO:
            case CORN:
            case EGGPLANT:
            case PEANUT:
            case CARROT:
            case BROCCOLI:
                image = Assets.cropCropProduct(resources, id, isWhole);
                break;
            //INDOORS
            case MYSTERY:
                image = BitmapFactory.decodeResource(resources, R.drawable.green2174);
                break;
            case GERANIUM:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.GERANIUM, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case PRIMROSE:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.PRIMROSE, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case LAVENDER:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.LAVENDER, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case ORCHID:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.ORCHID, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case SAGE:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.SAGE, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case SAFFRON:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.SAFFRON, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case ROSEMARY:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.ROSEMARY, FlowerEntity.Stage.HARVESTABLE, false);
                break;
            case CHAMOMILE:
                image = Assets.cropFlowerEntity(resources, FlowerEntity.Id.CHAMOMILE, FlowerEntity.Stage.HARVESTABLE, false);
                break;
        }
    }

    @Override
    public void render(Canvas canvas) {
        Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCartridge.getGameCamera().getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCartridge.getGameCamera().getY()) + height) * heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    @Override
    public void updatePosition(float xCurrent, float yCurrent) {
        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
    }

    @Override
    public boolean drop(Tile tile) {
        if (tile instanceof ShippingBinTile) {
            ShippingBinTile shippingBinTile = (ShippingBinTile) tile;
            //////////////////////////////////
            putInShippingBin(shippingBinTile);
            //////////////////////////////////

            return true;
        } else if ( (tile instanceof GrowableGroundTile) || (tile instanceof GenericWalkableTile)) {
            int xCurrentTile = tile.getxIndex() * TileMap.TILE_WIDTH;
            int yCurrentTile = tile.getyIndex() * TileMap.TILE_HEIGHT;

            ////////////////////////
            xCurrent = xCurrentTile;
            yCurrent = yCurrentTile;
            ////////////////////////

            return true;

        }

        return false;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void putInShippingBin(ShippingBinTile shippingBin) {
        shippingBin.addSellable(this);
        //REMOVE PRODUCT FROM entityManager.
        gameCartridge.getSceneManager().getCurrentScene().getEntityManager().removeEntity(this);
    }

}