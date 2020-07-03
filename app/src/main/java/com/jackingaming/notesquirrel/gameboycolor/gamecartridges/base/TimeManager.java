package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import java.io.Serializable;

public class TimeManager
        implements Serializable {

    public enum Season { SPRING, SUMMER, FALL, WINTER; }
    public static Season season;

    transient private GameCartridge gameCartridge;

    private long milliElapsed;
    private long timePlayed;

    public TimeManager(GameCartridge gameCartridge) {
        season = Season.SPRING;

        this.gameCartridge = gameCartridge;

        timePlayed = 0L;
    }

    public void update(long elapsed) {

    }

}