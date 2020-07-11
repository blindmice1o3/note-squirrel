package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products.Product;

public class CropEntity extends Entity {

    public enum Id { TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }
    public enum Stage { ONE, TWO, THREE, HARVESTABLE; }

    private CropEntity.Id id;
    private Stage stage;
    private boolean isRegrowable;
    private short daysWatered;
    private boolean isWatered;

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public CropEntity(GameCartridge gameCartridge, CropEntity.Id id, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        this.id = id;
        stage = Stage.ONE;
        isRegrowable = false;
        isWatered = false;
        daysWatered = 0;

        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();
        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
        initBounds();
    }

    public void toggleIsWatered() {
        isWatered = !isWatered;

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    public void setIsWatered(boolean isWatered) {
        this.isWatered = isWatered;

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    public void incrementDaysWatered() {
        daysWatered++;

        //TODO: update stage based on daysWatered (update image afterwards?)
        updateStage();
    }

    public Product generateCropProduct() {
        Product cropProduct = null;
        Product.Id idProduct = null;
        switch (id) {
            case TURNIP:
                idProduct = Product.Id.TURNIP;
                break;
            case POTATO:
                idProduct = Product.Id.POTATO;
                break;
            case TOMATO:
                idProduct = Product.Id.TOMATO;
                break;
            case CORN:
                idProduct = Product.Id.CORN;
                break;
            case EGGPLANT:
                idProduct = Product.Id.EGGPLANT;
                break;
            case PEANUT:
                idProduct = Product.Id.PEANUT;
                break;
            case CARROT:
                idProduct = Product.Id.CARROT;
                break;
            case BROCCOLI:
                idProduct = Product.Id.BROCCOLI;
                break;
        }
        if (idProduct != null) {
            cropProduct = new Product(gameCartridge, idProduct, xCurrent, yCurrent);
        }
        return cropProduct;
    }

    private void updateStage() {
        switch (id) {
            case TURNIP:
                updateStageTurnip();
                break;
            case POTATO:
                updateStagePotato();
                break;
            case TOMATO:
                updateStageTomato();
                break;
            case CORN:
                updateStageCorn();
                break;
            case EGGPLANT:
                updateStageEggplant();
                break;
            case PEANUT:
                updateStagePeanut();
                break;
            case CARROT:
                updateStageCarrot();
                break;
            case BROCCOLI:
                updateStageBroccoli();
                break;
        }

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    @Override
    public void initBounds() {
        bounds = new Rect(0, 0, width, height);
    }

    @Override
    public void update(long elapsed) {

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

    private void updateStageTurnip() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
//            case 5:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStagePotato() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
//            case 7:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStageTomato() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
                stage = Stage.TWO;
                break;
            case 3:
                stage = Stage.THREE;
                break;
            case 4:
//            case 10:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStageCorn() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
                stage = Stage.TWO;
                break;
            case 3:
                stage = Stage.THREE;
                break;
            case 4:
//            case 14:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStageEggplant() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
//            case 5:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStagePeanut() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
//            case 10:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStageCarrot() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
//            case 7:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    private void updateStageBroccoli() {
        switch (daysWatered) {
            case 1:
                stage = Stage.ONE;
                break;
            case 2:
                stage = Stage.TWO;
                break;
            case 3:
                stage = Stage.THREE;
                break;
            case 4:
//            case 14:
                stage = Stage.HARVESTABLE;
                break;
        }
    }

    public Stage getStage() {
        return stage;
    }

    public boolean getIsRegrowable() {
        return isRegrowable;
    }

}