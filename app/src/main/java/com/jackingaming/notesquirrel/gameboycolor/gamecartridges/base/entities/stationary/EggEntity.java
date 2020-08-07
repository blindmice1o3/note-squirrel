package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.EggIncubatorTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.walkables.GenericWalkableTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Holdable;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Sellable;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneChickenCoop;

import java.io.Serializable;

public class EggEntity extends Entity
        implements Holdable, Sellable, Serializable {

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private boolean isWhole;
    private int price;

    public EggEntity(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        isWhole = true;
        establishPrice();

        init(gameCartridge);
    }

    public void establishPrice() {
        price = 50;
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

    public void initImage(Resources resources) {
        if (isWhole) {
            Bitmap spriteSheetItems = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm2_spritesheet_items);
            image = Bitmap.createBitmap(spriteSheetItems, 69, 18, 16, 16);
        } else {
            Bitmap spriteSheetCustom = BitmapFactory.decodeResource(resources, R.drawable.custom_hm_tile_sprites_sheet);
            image = Bitmap.createBitmap(spriteSheetCustom, 0, 32, 16, 16);
        }
    }

    @Override
    public void initBounds() {
        bounds = new Rect(0, 0, width, height);
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
        } else if ( (tile instanceof GrowableGroundTile) || (tile instanceof GenericWalkableTile) ) {
            int xCurrentTile = tile.getxIndex() * TileMap.TILE_WIDTH;
            int yCurrentTile = tile.getyIndex() * TileMap.TILE_HEIGHT;

            ////////////////////////
            xCurrent = xCurrentTile;
            yCurrent = yCurrentTile;
            ////////////////////////

            //TRIGGER countdown timer for removal.
            isWhole = false;
            //UPDATE image to broken.
            initImage(gameCartridge.getContext().getResources());

            return true;
        } else if (tile instanceof EggIncubatorTile) {
            SceneChickenCoop sceneChickenCoop = (SceneChickenCoop) gameCartridge.getSceneManager().getScene(Scene.Id.CHICKEN_COOP);

            sceneChickenCoop.setIsEggIncubating(true);
            setActive(false);

            return true;
        }

        return false;
    }

    private static int COUNT_DOWN_START_TIME = 2000;
    private int timeLeftBeforeRemoval = COUNT_DOWN_START_TIME;
    @Override
    public void update(long elapsed) {
        //NO LONGER isWhole (it's now broken): UPDATE countdown timer for removal).
        if (!isWhole) {
            timeLeftBeforeRemoval -= elapsed;
            if (timeLeftBeforeRemoval <= 0) {
                active = false;
            }
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
    public int getPrice() {
        return price;
    }

    @Override
    public void putInShippingBin(ShippingBinTile shippingBin) {
        shippingBin.addSellable(this);
        //REMOVE EggEntity FROM entityManager.
        gameCartridge.getSceneManager().getCurrentScene().getEntityManager().removeEntity(this);
    }

}