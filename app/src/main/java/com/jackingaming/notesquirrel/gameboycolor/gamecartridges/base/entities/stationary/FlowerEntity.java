package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class FlowerEntity extends Entity {

    public enum Id { GERANIUM, PRIMROSE, LAVENDER, ORCHID, SAGE, SAFFRON, ROSEMARY, CHAMOMILE; }
    public enum Stage { ONE, TWO, THREE, HARVESTABLE; }

    private FlowerEntity.Id id;
    private Stage stage;
    private short daysToReachHarvestable;
    private short daysWatered;
    private boolean isWatered;

    transient private Bitmap image;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    public FlowerEntity(GameCartridge gameCartridge, FlowerEntity.Id id, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        this.id = id;
        stage = Stage.ONE;
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
        updateImage();
        initBounds();
    }

    public void updateImage() {
        image = Assets.cropFlowerEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
    }

    private void initDaysToReachHarvestable() {
        switch (id) {
            case GERANIUM:
                daysToReachHarvestable = 6;
                break;
            case PRIMROSE:
                daysToReachHarvestable = 9;
                break;
            case LAVENDER:
                daysToReachHarvestable = 6;
                break;
            case ORCHID:
                daysToReachHarvestable = 9;
                break;
            case SAGE:
                daysToReachHarvestable = 9;
                break;
            case SAFFRON:
                daysToReachHarvestable = 9;
                break;
            case ROSEMARY:
                daysToReachHarvestable = 6;
                break;
            case CHAMOMILE:
                daysToReachHarvestable = 6;
                break;
        }
    }

    public void toggleIsWatered() {
        isWatered = !isWatered;

        updateImage();
    }

    public void setIsWatered(boolean isWatered) {
        this.isWatered = isWatered;

        updateImage();
    }

    public boolean getIsWatered() {
        return isWatered;
    }

    public void incrementDaysWatered() {
        daysWatered++;

        updateStage();
    }

    private void updateStage() {
        switch (id) {
            case GERANIUM:
                updateStageGeranium();
                break;
            case PRIMROSE:
                updateStagePrimrose();
                break;
            case LAVENDER:
                updateStageLavender();
                break;
            case ORCHID:
                updateStageOrchid();
                break;
            case SAGE:
                updateStageSage();
                break;
            case SAFFRON:
                updateStageSaffron();
                break;
            case ROSEMARY:
                updateStageRosemary();
                break;
            case CHAMOMILE:
                updateStageChamomile();
                break;
        }

        updateImage();
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

    private void updateStageGeranium() {
        if (daysWatered < 2) {
            stage = Stage.ONE;
        } else if ( (2 <= daysWatered) && (daysWatered < 4) ) {
            stage = Stage.TWO;
        } else if ( (4 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStagePrimrose() {
        if (daysWatered < 3) {
            stage = Stage.ONE;
        } else if ( (3 <= daysWatered) && (daysWatered < 6) ) {
            stage = Stage.TWO;
        } else if ( (6 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageLavender() {
        if (daysWatered < 2) {
            stage = Stage.ONE;
        } else if ( (2 <= daysWatered) && (daysWatered < 4) ) {
            stage = Stage.TWO;
        } else if ( (4 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageOrchid() {
        if (daysWatered < 3) {
            stage = Stage.ONE;
        } else if ( (3 <= daysWatered) && (daysWatered < 6) ) {
            stage = Stage.TWO;
        } else if ( (6 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageSage() {
        if (daysWatered < 3) {
            stage = Stage.ONE;
        } else if ( (3 <= daysWatered) && (daysWatered < 6) ) {
            stage = Stage.TWO;
        } else if ( (6 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageSaffron() {
        if (daysWatered < 3) {
            stage = Stage.ONE;
        } else if ( (3 <= daysWatered) && (daysWatered < 6) ) {
            stage = Stage.TWO;
        } else if ( (6 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageRosemary() {
        if (daysWatered < 2) {
            stage = Stage.ONE;
        } else if ( (2 <= daysWatered) && (daysWatered < 4) ) {
            stage = Stage.TWO;
        } else if ( (4 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    private void updateStageChamomile() {
        if (daysWatered < 2) {
            stage = Stage.ONE;
        } else if ( (2 <= daysWatered) && (daysWatered < 4) ) {
            stage = Stage.TWO;
        } else if ( (4 <= daysWatered) && (daysWatered < daysToReachHarvestable) ) {
            stage = Stage.THREE;
        } else {
            stage = Stage.HARVESTABLE;
        }
    }

    public Stage getStage() {
        return stage;
    }

}