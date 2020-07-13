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
    private static final float MILLISECOND_TO_MINUTE_RATIO = 20000f / 60;

    private static long timePlayed = 0L;

    private transient GameCartridge gameCartridge;
    private short year = 1;                   //4-season years (SPRING, SUMMER, FALL, WINTER)
    private Season season = Season.SPRING;    //30-day seasons
    private short day = 1;                    //18-hour days (6am-12am)

    //DAYLIGHT==(6am-3pm), TWILIGHT==(3pm-6pm), NIGHT==(6pm-12am)
    private long ticker = 0L;
    private short hour = 6;   //20-real-time-second hours (average day lasts ~4 minutes)
    private short minute = 0;
    private boolean isPM = false;

    //TIME STOPS WHEN INDOORS
    private boolean isPaused = false;

    public TimeManager(GameCartridge gameCartridge) {
        init(gameCartridge);
    }

    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
    }

    public void update(long elapsed) {
        //////////////////////
        timePlayed += elapsed;
        //////////////////////

        /////////////////////////////////////////////
        //TODO: TIME STOPS WHEN INDOORS
        if (!isPaused) {
            ticker += elapsed;

            if (ticker >= MILLISECOND_TO_MINUTE_RATIO) {
                minute++;
                ticker = 0L;

                if (minute >= 60) {
                    hour++;
                    minute = 0;

                    //noon
                    if ( (hour == 12) && (!isPM) ) {
                        isPM = true;
                    }
                    //1pm
                    else if ( (hour >= 13) && (isPM) ) {
                        hour = 1;
                    }
                    //midnight => TimeManager stops in-game clock.
                    else if ( (hour == 12) && (isPM) ) {
                        isPM = false;
                        ////////////////
                        isPaused = true;
                        ////////////////
                    }
                }
            }

        }
        /////////////////////////////////////////////
    }

    public void render(Canvas canvas) {
        //Paint (BACKGROUND)
        Paint paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.WHITE);
        paintBackground.setAlpha(230);

        //Paint (FONT)
        Paint paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.GREEN);
        paintFont.setAlpha(230);
        paintFont.setTextSize(40f);
        paintFont.setTypeface(Typeface.SANS_SERIF);

        //BACKGROUND
        //REFERENCE: https://stackoverflow.com/questions/3654321/measuring-text-height-to-be-drawn-on-canvas-android
        Paint.FontMetrics fm = paintFont.getFontMetrics();
        int heightLine = (int) (fm.bottom - fm.top + fm.leading);
        Rect rectBackground = new Rect(8, (32+8+8)-heightLine+8+8, 250+8, (32+8+8)+heightLine+8);
        /////////////////////////////////////////////////
        canvas.drawRect(rectBackground, paintBackground);
        /////////////////////////////////////////////////

        //FONT
        /////////////////////////////////////////////////////////////////////
        canvas.drawText(String.valueOf(timePlayed),
                250 - paintFont.measureText(String.valueOf(timePlayed)),
                (32+8+8), paintFont);
        /////////////////////////////////////////////////////////////////////

        //GAME-CLOCK (HOUR:MINUTE)
        String hourCurrent = String.format("%02d", hour);
        String minuteCurrent = String.format("%02d", minute);
        String amOrPM = (isPM) ? ("pm") : ("am");
        String inGameClockTime = hourCurrent + ":" + minuteCurrent + amOrPM;
        canvas.drawText(inGameClockTime,
                250 - paintFont.measureText(String.valueOf(inGameClockTime)),
                (32+8+8+heightLine), paintFont);
    }

    public void incrementDay() {
        day++;
    }

    public Season getSeason() {
        return season;
    }

}