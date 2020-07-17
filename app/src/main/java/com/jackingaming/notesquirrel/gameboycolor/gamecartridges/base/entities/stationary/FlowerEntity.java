package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

public class FlowerEntity extends Entity {

    public enum Id { GERANIUM, PRIMROSE, LAVENDER, ORCHID; }
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
        image = Assets.cropFlowerEntity(gameCartridge.getContext().getResources(), id, stage, isWatered);
        initBounds();
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
        }
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

}