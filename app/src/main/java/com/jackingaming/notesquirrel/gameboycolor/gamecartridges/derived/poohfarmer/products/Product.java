package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2.ShippingBinTile;

import java.io.Serializable;

public class Product
        implements Holdable, Sellable, Serializable {

    public enum Id { TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }

    transient private GameCartridge gameCartridge;

    private int xCurrent;
    private int yCurrent;

    private Product.Id id;
    private boolean isPristine;
    transient private Bitmap image;

    private int price;

    public Product(GameCartridge gameCartridge, Product.Id id) {
        this.id = id;
        isPristine = true;

        init(gameCartridge);

        establishPrice();
    }

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
        image = Assets.cropCropProduct(resources, id, isPristine);
    }

    public void render(Canvas canvas) {

    }

    @Override
    public void setPosition(int xCurrent, int yCurrent) {
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