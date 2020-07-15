package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimeManager
        implements Serializable {

    public interface TimeManagerListener {
        public void executeTimedEvent(int hour, int minute, boolean isPM);
    }
    private List<TimeManagerListener> timeManagerListeners;
    public void registerTimeManagerListener(TimeManagerListener timeManagerListener) {
        timeManagerListeners.add(timeManagerListener);
    }

    public enum Season { SPRING, SUMMER, FALL, WINTER; }
    public enum ModeOfDay { DAYLIGHT, TWILIGHT, NIGHT; }
    private static final float MILLISECOND_TO_MINUTE_RATIO = 20000f / 60;

    private static long timePlayed = 0L;

    private transient GameCartridge gameCartridge;
    private short year;     //4-season years (SPRING, SUMMER, FALL, WINTER)
    private Season season;  //30-day seasons
    private short day;      //18-hour days (6am-12am)

    //DAYLIGHT==(6am-3pm), TWILIGHT==(3pm-6pm), NIGHT==(6pm-12am)
    private long ticker;
    private short hour;     //20-real-time-second hours (average day lasts ~4 minutes)
    private short minute;
    private boolean isPM;
    private ModeOfDay modeOfDay;

    //TIME STOPS WHEN INDOORS
    private boolean isPaused;

    public TimeManager(GameCartridge gameCartridge) {
        init(gameCartridge);

        timeManagerListeners = new ArrayList<TimeManagerListener>();

        year = 1;
        season = Season.SPRING;
        day = 1;

        resetInGameClock();

        isPaused = false;
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

                    //ALERT ALL LISTENERS AT THE TOP OF EACH HOUR!
                    for (TimeManagerListener timeManagerListener : timeManagerListeners) {
                        Log.d(MainActivity.DEBUG_TAG, "TimeManager.update(long) alerting all registered TimeManagerListeners about NEW HOUR: " + timeManagerListener.getClass());
                        //////////////////////////////////////////////////////////
                        timeManagerListener.executeTimedEvent(hour, minute, isPM);
                        //////////////////////////////////////////////////////////
                    }

                    //noon
                    if ( (hour == 12) && (minute == 0) && (!isPM) ) {
                        isPM = true;
                    }
                    //1 o'clock (for both am and pm)
                    else if ( (hour == 13) && (minute == 0) ) {
                        hour = 1;
                    }
                    //3pm
                    else if ( (hour == 3) && (minute == 0) && (isPM) ) {
                        modeOfDay = ModeOfDay.TWILIGHT;
                    }
                    //6pm
                    else if ( (hour == 6) && (minute == 0) && (isPM) ) {
                        modeOfDay = ModeOfDay.NIGHT;
                    }
                    //midnight => TimeManager stops in-game clock.
                    else if ( (hour == 12) && (minute == 0) && (isPM) ) {
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
        Rect rectBackground = new Rect(0, (32+8+8)-heightLine+8+8, 250+8, (32+8+8)+heightLine+heightLine+8);
        /////////////////////////////////////////////////
        canvas.drawRect(rectBackground, paintBackground);
        /////////////////////////////////////////////////

        //TIME_PLAYED
        /////////////////////////////////////////////////////////////////////
        canvas.drawText(String.valueOf(timePlayed),
                255 - paintFont.measureText(String.valueOf(timePlayed)),
                (32+8+8), paintFont);
        /////////////////////////////////////////////////////////////////////

        //CALENDAR ((SEASON) DAY)
        String dayFormatted = String.format("%02d", day);
        String calendarText = "(" + season.name() + ") " + dayFormatted;
        canvas.drawText(calendarText,
                255 - paintFont.measureText(String.valueOf(calendarText)),
                (32+8+8+heightLine), paintFont);

        //GAME-CLOCK (HOUR:MINUTE)
        String hourCurrent = String.format("%02d", hour);
        String minuteCurrent = String.format("%02d", minute);
        String amOrPM = (isPM) ? ("pm") : ("am");
        String inGameClockTime = hourCurrent + ":" + minuteCurrent + amOrPM;
        canvas.drawText(inGameClockTime,
                255 - paintFont.measureText(String.valueOf(inGameClockTime)),
                (32+8+8+heightLine+heightLine), paintFont);
    }

    public void incrementDay() {
        day++;
        if (day > 30) {
            incrementSeason();
            day = 1;
        }
    }

    private void incrementSeason() {
        int indexNextSeason = season.ordinal() + 1;
        if (indexNextSeason >= Season.values().length) {
            incrementYear();
            indexNextSeason = 0;
        }
        season = Season.values()[indexNextSeason];
    }

    private void incrementYear() {
        year++;

        /////////////////////////////////////////////////////////////////////////////////
        ((JackInActivity) gameCartridge.getContext()).runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(gameCartridge.getContext(), "year: " + year, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
        /////////////////////////////////////////////////////////////////////////////////
    }

    public Season getSeason() {
        return season;
    }

    public ModeOfDay getModeOfDay() {
        return modeOfDay;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public void resetInGameClock() {
        ticker = 0L;
        hour = 6;
        minute = 0;
        isPM = false;
        modeOfDay = ModeOfDay.DAYLIGHT;
    }

}