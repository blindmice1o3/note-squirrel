package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Chicken;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.EggEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapChickenCoop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SceneChickenCoop extends Scene {

    public static final int FODDER_COUNTER_MAXIMUM = 4;
    public static final int CHICKEN_COUNTER_MAXIMUM = 4;

    private int fodderCounter;
    private int chickenCounter;

    private Comparator<Chicken> chickenFeedingSorter = new ComparatorChickenFeedingSorter();

    private boolean isEggIncubating;
    private int daysIncubating;

    transient private Bitmap imageEggIncubating;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;
    transient private Rect rectOfImage;
    transient private Rect rectOnScreen;

    public SceneChickenCoop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 9;
        heightClipInTile = 9;

        fodderCounter = 0;
        chickenCounter = 0;

        isEggIncubating = false;
        daysIncubating = 0;

        imageEggIncubating = null;

        entityManager.addEntity(new EggEntity(gameCartridge, 64, 96));
        entityManager.addEntity(new EggEntity(gameCartridge, 80, 96));
        entityManager.addEntity(new EggEntity(gameCartridge, 96, 96));

        entityManager.addEntity(new Chicken(gameCartridge, 64, 112, Chicken.Stage.ADULT));
        chickenCounter++;
        entityManager.addEntity(new Chicken(gameCartridge, 80, 112, Chicken.Stage.ADULT));
        chickenCounter++;
//        entityManager.addEntity(new Chicken(gameCartridge, 96, 112, Chicken.Stage.BABY));
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

    class ComparatorChickenFeedingSorter implements Comparator<Chicken>, Serializable {
        @Override
        public int compare(Chicken firstChicken, Chicken secondChicken) {
            //less daysUnhappy: to the front.
            if (firstChicken.getDaysUnhappy() < secondChicken.getDaysUnhappy()) {
                return -1;
            }
            //more daysUnhappy: to the back.
            else if (firstChicken.getDaysUnhappy() > secondChicken.getDaysUnhappy()) {
                return 1;
            }
            //equal daysUnhappy: check daysAlive (older to the front, younger to the back).
            else {
                if (firstChicken.getDaysAlive() > secondChicken.getDaysAlive()) {
                    return -1;
                } else if (firstChicken.getDaysAlive() < secondChicken.getDaysAlive()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public void performFeeding() {
        List<Chicken> chickensAdult = new ArrayList<Chicken>();
        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof Chicken) {
                Chicken chicken = (Chicken) entity;

                if (chicken.getStage() == Chicken.Stage.ADULT) {
                    chickensAdult.add(chicken);
                }
            }
        }

        /////////////////////////////////////////////////////
        //sort based on Chicken.daysUnhappy.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            chickensAdult.sort(chickenFeedingSorter);
        }
        /////////////////////////////////////////////////////

        //TODO: more fodder than chicken (out of bounds)... more chicken than fodder (some become unhappy).
        //less (or equal) adult chickens than fodders: all EAT (check chickensAdult for index out of bounds).
        if (chickensAdult.size() <= fodderCounter) {
            //ALL EAT.
            for (Chicken chicken : chickensAdult) {
                //egg laying (ONLY happy chickens lay eggs).
                if (chicken.getDaysUnhappy() <= 0) {
                    chicken.layEgg();
                }

                //feeding (if fodder is available for this chicken, daysUnhappy decreases).
                chicken.decrementDaysUnhappy();
            }
        }
        // more adult chickens than fodders: some chickens GO HUNGRY (become unhappy [no egg laying]).
        else {
            //SOME EAT.
            for (int i = 0; i < fodderCounter; i++) {
                Chicken chicken = chickensAdult.get(i);
                //egg laying (ONLY happy chickens lay eggs).
                if (chicken.getDaysUnhappy() <= 0) {
                    chicken.layEgg();
                }

                //feeding (if fodder is available for this chicken, daysUnhappy decreases).
                chicken.decrementDaysUnhappy();
            }
            //SOME GO HUNGRY.
            for (int i = fodderCounter; i < chickensAdult.size(); i++) {
                Chicken chicken = chickensAdult.get(i);
                chicken.becomeUnhappyDueToMissedFeeding();
            }
        }
    }

    public int getDaysIncubating() {
        return daysIncubating;
    }

    public void incrementDaysIncubating() {
        daysIncubating++;
    }

    public void resetDaysIncubating() {
        daysIncubating = 0;
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

    public int getChickenCounter() {
        return chickenCounter;
    }

    public void incrementChickenCounter() {
        chickenCounter++;
    }

    public boolean getIsEggIncubating() {
        return isEggIncubating;
    }

    public void setIsEggIncubating(boolean isEggIncubating) {
        this.isEggIncubating = isEggIncubating;
    }

}