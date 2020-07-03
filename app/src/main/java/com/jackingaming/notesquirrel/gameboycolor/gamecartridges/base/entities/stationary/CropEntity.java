package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;

public class CropEntity extends Entity {

    public enum Id { GRASS, TURNIP, POTATO, TOMATO, CORN, EGGPLANT, PEANUT, CARROT, BROCCOLI; }
    public enum Stage { SEEDED, ONE, TWO, THREE, HARVESTABLE; }

    public CropEntity(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);
    }

    @Override
    public void init(GameCartridge gameCartridge) {

    }

    @Override
    public void initBounds() {

    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {

    }

}