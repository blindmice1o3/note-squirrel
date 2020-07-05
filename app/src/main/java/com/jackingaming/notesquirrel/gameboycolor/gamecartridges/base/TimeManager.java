package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import java.io.Serializable;

public class TimeManager
        implements Serializable {

    public enum Season { SPRING, SUMMER, FALL, WINTER; }

    public static Season season = Season.SPRING;
    private static long timePlayed = 0L;

    public static void update(long elapsed) {
        timePlayed += elapsed;
    }

}