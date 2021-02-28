package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo.hud;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;

import java.io.Serializable;

public class ComponentHUD
        implements Serializable {
    public enum ComponentType { HP, EXP, DAMAGE; }

    transient protected Game game;

    protected float x, y;
    protected ComponentType currentComponentType;
    protected int value;

    protected long timeElapsed, timerTarget;
    protected boolean timerFinished;

    public ComponentHUD(Game game, ComponentType componentType, int value, Entity entity) {
        this.game = game;

        this.currentComponentType = componentType;
        this.value = value;
        x = entity.getX();
        y = entity.getY();

        timerFinished = false;

        timeElapsed = 0L;
        timerTarget = 5_000L;
//        timerTarget = 5_000_000_000L;      //TODO: timerTarget has to be in NANOSECOND now!!!!
    }

    public void update(long elapsed) {
        timeElapsed += elapsed;

        //System.out.println("ComponentHUD.tick(long), timeElapsed: " + this.timeElapsed + " | timerTarget: " + timerTarget);

        if (timeElapsed >= timerTarget) {
            timerFinished = true;
        }
    }

    public void render(Canvas canvas) {
        GameCamera gameCamera = GameCamera.getInstance();
        Paint paint = new Paint();
        paint.setTextSize(40f);
        switch (currentComponentType) {
            case HP:
                paint.setColor(Color.GREEN);
                canvas.drawText("+" + Integer.toString(value),
                        (int) ((x - gameCamera.getX()) * gameCamera.getWidthPixelToViewportRatio()),
                        (int) ((y - gameCamera.getY()) * gameCamera.getHeightPixelToViewportRatio()), paint);
                break;
            case EXP:
                paint.setColor(Color.WHITE);
                canvas.drawText("+" + Integer.toString(value),
                        (int) ((x - gameCamera.getX()) * gameCamera.getWidthPixelToViewportRatio()),
                        (int) ((y - gameCamera.getY() + 5) * gameCamera.getHeightPixelToViewportRatio()), paint);
                break;
            case DAMAGE:
                paint.setColor(Color.RED);
                canvas.drawText("-" + Integer.toString(value),
                        (int) ((x - gameCamera.getX() - 5) * gameCamera.getWidthPixelToViewportRatio()),
                        (int) ((y - gameCamera.getY() - 5) * gameCamera.getHeightPixelToViewportRatio()), paint);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".render(Canvas canvas), switch's default.");
                break;
        }
    }

    // GETTERS AND SETTERS

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isTimerFinished() {
        return timerFinished;
    }

    public void setTimerFinished(boolean timerFinished) {
        this.timerFinished = timerFinished;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}