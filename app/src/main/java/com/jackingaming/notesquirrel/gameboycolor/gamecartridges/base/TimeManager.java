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

    public static short year = 1;                   //4-season years (SPRING, SUMMER, FALL, WINTER)
    public static Season season = Season.SPRING;    //30-day seasons
    public static short day = 1;                    //18-hour days (6am-12am)
    //DAYLIGHT==(6am-3pm), TWILIGHT==(3pm-6pm), NIGHT==(6pm-12am)
    public static short hour = 0;   //20-real-time-second hours (average day lasts ~4 minutes)
    public static short minute = 0;
    public static short second = 0; //TIME STOPS WHEN INDOORS

    public static void update(long elapsed) {
        timePlayed += elapsed;
    }

    public static void render(Canvas canvas) {
        //Paint (BACKGROUND)
        Paint paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.WHITE);
        paintBackground.setAlpha(240);

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