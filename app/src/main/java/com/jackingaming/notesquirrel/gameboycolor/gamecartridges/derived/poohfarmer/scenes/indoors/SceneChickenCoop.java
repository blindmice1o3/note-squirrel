package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapChickenCoop;

public class SceneChickenCoop extends Scene {

    public static final int FODDER_COUNTER_MAXIMUM = 4;

    private int fodderCounter;

    public SceneChickenCoop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 9;
        heightClipInTile = 9;

        fodderCounter = 0;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapChickenCoop(gameCartridge, sceneID);
    }

    @Override
    public void enter() {
        super.enter();

        gameCartridge.getTimeManager().setIsPaused(true);
    }

    @Override
    public void exit(Object[] extra) {
        super.exit(extra);

        gameCartridge.getTimeManager().setIsPaused(false);
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

}