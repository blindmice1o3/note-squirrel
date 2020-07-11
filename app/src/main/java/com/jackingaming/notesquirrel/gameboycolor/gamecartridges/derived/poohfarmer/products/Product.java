package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;

import java.io.Serializable;

public class Product
        implements Holdable, Sellable, Serializable {

    public enum Id { TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }

    private Product.Id id;
    private boolean isWhole;
    private int price;

    transient private GameCartridge gameCartridge;
    transient private Bitmap image;
    private float xCurrent;
    private float yCurrent;
    private int width;
    private int height;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public Product(GameCartridge gameCartridge, Product.Id id, float xCurrent, float yCurrent) {
        this.id = id;
        isWhole = true;
        establishPrice();

        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
        width = TileMap.TILE_WIDTH;
        height = TileMap.TILE_HEIGHT;
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();
        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        initImage(gameCartridge.getContext().getResources());
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
        }
    }

    public void initImage(Resources resources) {
        image = Assets.cropCropProduct(resources, id, isWhole);
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
    public void setPosition(float xCurrent, float yCurrent) {
        this.xCurrent = xCurrent;
        this.yCurrent = yCurrent;
    }

    @Override
    public void pickUp() {

    }

    @Override
    public void drop(Tile tile) {

    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void putInShippingBin(ShippingBinTile shippingBin) {

    }

}