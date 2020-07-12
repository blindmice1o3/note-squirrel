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
    private short daysToReachHarvestable;
    private short daysWatered;
    private boolean isWatered;

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public CropEntity(GameCartridge gameCartridge, CropEntity.Id id, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        this.id = id;
        stage = Stage.ONE;
        initIsRegrowable();
        initDaysToReachHarvestable();
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

    public void initIsRegrowable() {
        switch (id) {
            case TURNIP:
            case POTATO:
            case EGGPLANT:
            case CARROT:
                isRegrowable = false;
                break;
            case TOMATO:
            case CORN:
            case PEANUT:
            case BROCCOLI:
                isRegrowable = true;
                break;
        }
    }

    public void initDaysToReachHarvestable() {
        switch (id) {
            case TURNIP:
                daysToReachHarvestable = 5;
                break;
            case POTATO:
                daysToReachHarvestable = 7;
                break;
            case TOMATO:
                daysToReachHarvestable = 10;
                break;
            case CORN:
                daysToReachHarvestable = 14;
                break;
            case EGGPLANT:
                daysToReachHarvestable = 5;
                break;
            case PEANUT:
                daysToReachHarvestable = 10;
                break;
            case CARROT:
                daysToReachHarvestable = 7;
                break;
            case BROCCOLI:
                daysToReachHarvestable = 14;
                break;
        }
    }

    public void toggleIsWatered() {
        isWatered = !isWatered;

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    public void setIsWatered(boolean isWatered) {
        this.isWatered = isWatered;

        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    public boolean getIsWatered() {
        return isWatered;
    }

    public void incrementDaysWatered() {
        daysWatered++;

        updateStage();
    }

    public void revertToPriorStageByDecreasingDaysWatered() {
        short daysFromSecondToLastStage = 0;
        switch (id) {
            case TURNIP:
            case POTATO:
            case EGGPLANT:
            case CARROT:
                //intentionally blank.
                break;
            case TOMATO:
                daysFromSecondToLastStage = 3;
                break;
            case CORN:
                daysFromSecondToLastStage = 4;
                break;
            case PEANUT:
                daysFromSecondToLastStage = 3;
                break;
            case BROCCOLI:
                daysFromSecondToLastStage = 4;
                break;
        }
        ///////////////////////////////////////////////////////////////////////////
        daysWatered = (short) (daysToReachHarvestable - daysFromSecondToLastStage);
        ///////////////////////////////////////////////////////////////////////////

        updateStage();
        image = Assets.cropCropEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
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
        if (daysWatered < daysToReachHarvestable) {
            stage = Stage.ONE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStagePotato() {
        if (daysWatered < daysToReachHarvestable) {
            stage = Stage.ONE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageTomato() {
        if (daysWatered < 4) {
            stage = Stage.ONE;
        } else if ( (4 <= daysWatered) && (daysWatered < 7) ) {
            stage = Stage.TWO;
        } else if ( (7 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageCorn() {
        if (daysWatered < 4) {
            stage = Stage.ONE;
        } else if ( (4 <= daysWatered) && (daysWatered < 9) ) {
            stage = Stage.TWO;
        } else if ( (9 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageEggplant() {
        if (daysWatered < daysToReachHarvestable) {
            stage = Stage.ONE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStagePeanut() {
        if (daysWatered < daysToReachHarvestable) {
            stage = Stage.ONE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageCarrot() {
        if (daysWatered < daysToReachHarvestable) {
            stage = Stage.ONE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageBroccoli() {
        if (daysWatered < 4) {
            stage = Stage.ONE;
        } else if ( (4 <= daysWatered) && (daysWatered < 9) ) {
            stage = Stage.TWO;
        } else if ( (9 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    public Stage getStage() {
        return stage;
    }

    public boolean getIsRegrowable() {
        return isRegrowable;
    }

}