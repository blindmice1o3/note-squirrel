package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.EggEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapChickenCoop;

public class SceneChickenCoop extends Scene {

    public static final int FODDER_COUNTER_MAXIMUM = 4;

    private int fodderCounter;

    private boolean isEggIncubating;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;
    transient private Rect rectOfImage;
    transient private Rect rectOnScreen;
    transient private Bitmap imageEggIncubating;

    public SceneChickenCoop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 9;
        heightClipInTile = 9;

        fodderCounter = 0;
        isEggIncubating = false;
        imageEggIncubating = null;

        entityManager.addEntity(new EggEntity(gameCartridge, 64, 96));
        entityManager.addEntity(new EggEntity(gameCartridge, 80, 96));
        entityManager.addEntity(new EggEntity(gameCartridge, 96, 96));
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapChickenCoop(gameCartridge, sceneID);
    }

    @Override
    public void enter() {
        super.enter();

        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        rectOfImage = new Rect(0, 0, 16, 16);

        Bitmap spriteSheetItems = BitmapFactory.decodeResource(gameCartridge.getContext().getResources(), R.drawable.gbc_hm2_spritesheet_items);
        imageEggIncubating = Bitmap.createBitmap(spriteSheetItems, 69, 18, 16, 16);

        gameCartridge.getTimeManager().setIsPaused(true);
    }

    @Override
    public void exit(Object[] extra) {
        super.exit(extra);

        imageEggIncubating = null;

        gameCartridge.getTimeManager().setIsPaused(false);
    }

    @Override
    public void render(Canvas canvas) {
        super.render(canvas);

        if (isEggIncubating) {
            rectOnScreen = new Rect(
                    (int)( (207 - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                    (int)( (184 - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                    (int)( ((207 - gameCartridge.getGameCamera().getX()) + 16) * widthPixelToViewportRatio ),
                    (int)( ((184 - gameCartridge.getGameCamera().getY()) + 16) * heightPixelToViewportRatio ) );
            /////////////////////////////////////////////////////////////////////////////
            canvas.drawBitmap(imageEggIncubating, rectOfImage, rectOnScreen, null);
            /////////////////////////////////////////////////////////////////////////////
        }
    }

    public int getFodderCounter() {
        return fodderCounter;
    }

    public void incrementFodderCounter() {
        fodderCounter++;
    }

    public void resetFodderCounter() {
        fodderCounter = 0;
    }

    public boolean getIsEggIncubating() {
        return isEggIncubating;
    }

    public void setIsEggIncubating(boolean isEggIncubating) {
        this.isEggIncubating = isEggIncubating;
    }

}