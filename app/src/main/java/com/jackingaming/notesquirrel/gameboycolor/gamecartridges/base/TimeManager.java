package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.io.Serializable;

public class TimeManager
        implements Serializable {

    public enum Season { SPRING, SUMMER, FALL, WINTER; }

    private static long timePlayed = 0L;

    public static Season season = Season.SPRING;
    public static short day = 1;
    public static short hour = 0;
    public static short minute = 0;
    public static short second = 0;

    public static void update(long elapsed) {
        timePlayed += elapsed;
    }

    public static void render(Canvas canvas) {
        //Paint (BACKGROUND)
        Paint paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.WHITE);
        paintBackground.setAlpha(250);

        //Paint (FONT)
        Paint paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.GREEN);
        paintFont.setAlpha(250);
        paintFont.setTextSize(40f);
        paintFont.setTypeface(Typeface.SANS_SERIF);

        //BACKGROUND
        //REFERENCE: https://stackoverflow.com/questions/3654321/measuring-text-height-to-be-drawn-on-canvas-android
        Paint.FontMetrics fm = paintFont.getFontMetrics();
        int heightLine = (int) (fm.bottom - fm.top + fm.leading);
        Rect rectBackground = new Rect(8, (32+8+8)-heightLine+8+8, 250+8, (32+8+8)+8);
        /////////////////////////////////////////////////
        canvas.drawRect(rectBackground, paintBackground);
        /////////////////////////////////////////////////

        //FONT
        /////////////////////////////////////////////////////////////////////
        canvas.drawText(String.valueOf(timePlayed),
                250 - paintFont.measureText(String.valueOf(timePlayed)),
                (32+8+8), paintFont);
        /////////////////////////////////////////////////////////////////////
    }

    public static void incrementDay() {
        day++;
    }

}