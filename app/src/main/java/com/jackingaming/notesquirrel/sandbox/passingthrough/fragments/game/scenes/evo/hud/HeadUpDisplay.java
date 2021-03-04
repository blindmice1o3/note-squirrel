package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo.hud;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.FishForm;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class HeadUpDisplay
        implements Serializable {
    transient private Game game;
    private ArrayList<ComponentHUD> timedNumericIndicators;

    public HeadUpDisplay(Game game) {
        this.game = game;
        timedNumericIndicators = new ArrayList<ComponentHUD>();
        widthScreen = game.getWidthViewport();
        xCenterOfScreen = widthScreen / 2;
    }

    public void update(long elapsed) {
        Iterator<ComponentHUD> it = timedNumericIndicators.iterator();
        while (it.hasNext()) {
            ComponentHUD componentHUD = it.next();

            ////////////////////
            componentHUD.update(elapsed);
            ////////////////////

            if (componentHUD.isTimerFinished()) {
                it.remove();
            }
        }
    }

    public void render(Canvas canvas) {
        for (int i = 0; i < timedNumericIndicators.size(); i++) {
            ComponentHUD componentHUD = timedNumericIndicators.get(i);
            ///////////////////////
            componentHUD.render(canvas);
            ///////////////////////
        }

        //hp and exp indicators.
        renderHUD(canvas);
    }

    private static final int MARGIN_SIZE_HORIZONTAL = Tile.WIDTH;
    private static final int MARGIN_SIZE_VERTICAL = Tile.HEIGHT;
    private static final int PADDING_SIZE_HORIZONTAL = 2;
    private static final int PADDING_SIZE_VERTICAL = 2;
    private int widthScreen;
    private int xCenterOfScreen;
    private void renderHUD(Canvas canvas) {
        Player playerBase = Player.getInstance();
        FishForm player = ((FishForm)playerBase.getForm());

        // HP BAR

        Paint paint = new Paint();
        //HP LABEL
        paint.setColor(Color.GREEN);
        String labelHP = "hp: ";
        paint.setTextSize(40f);
        Rect bounds = new Rect();
        paint.getTextBounds(labelHP, 0, labelHP.length(), bounds);
        int heightLabelHP = bounds.height();
        int widthLabelHP = bounds.width();
        canvas.drawText(labelHP, MARGIN_SIZE_HORIZONTAL, MARGIN_SIZE_VERTICAL + heightLabelHP, paint);
        canvas.drawText(Integer.toString(player.getHealth()), MARGIN_SIZE_HORIZONTAL,
                MARGIN_SIZE_VERTICAL + heightLabelHP + heightLabelHP, paint);

        // HP BAR BACKGROUND
        int x0HPBarBackground = MARGIN_SIZE_HORIZONTAL + widthLabelHP + MARGIN_SIZE_HORIZONTAL;
        int y0HPBarBackground = heightLabelHP;
        int x1HPBarBackground = xCenterOfScreen - MARGIN_SIZE_HORIZONTAL ;
        int y1HPBarBackground = heightLabelHP + MARGIN_SIZE_VERTICAL;
        paint.setColor(Color.BLACK);
        canvas.drawRect(x0HPBarBackground, y0HPBarBackground, x1HPBarBackground, y1HPBarBackground, paint);
        // The HUD's hp bar uses percent so the width won't change when healthMax changes.
        // Use floating-point-division (int-division lobs-off digits).
        float currentHealthPercent = (float)player.getHealth() / (float)player.getHealthMax();
        if (currentHealthPercent < 0) {
            currentHealthPercent = 0;
        }
        // HP BAR FOREGROUND
        int x0HPBarForeground = MARGIN_SIZE_HORIZONTAL + widthLabelHP + MARGIN_SIZE_HORIZONTAL + PADDING_SIZE_HORIZONTAL;
        int y0HPBarForeground = heightLabelHP + PADDING_SIZE_VERTICAL;
        int lengthOfHPBarForeground = xCenterOfScreen - (MARGIN_SIZE_HORIZONTAL + PADDING_SIZE_HORIZONTAL) - x0HPBarForeground;
        int x1HPBarForeground = x0HPBarForeground + (int)(lengthOfHPBarForeground * currentHealthPercent);
        int y1HPBarForeground = heightLabelHP + MARGIN_SIZE_VERTICAL - PADDING_SIZE_VERTICAL;
        paint.setColor(Color.GREEN);
        canvas.drawRect(x0HPBarForeground, y0HPBarForeground, x1HPBarForeground, y1HPBarForeground, paint);

        //XP
        paint.setColor(Color.WHITE);
        canvas.drawText("experiencePoints: " + player.getExperiencePoints(),
                xCenterOfScreen + MARGIN_SIZE_HORIZONTAL, MARGIN_SIZE_VERTICAL + heightLabelHP, paint);
    }

    public void addTimedNumericIndicator(ComponentHUD componentHUD) {
        timedNumericIndicators.add(componentHUD);
    }

    // GETTERS AND SETTERS

    public ArrayList<ComponentHUD> getTimedNumericIndicators() {
        return timedNumericIndicators;
    }

    public void setTimedNumericIndicators(ArrayList<ComponentHUD> timedNumericIndicators) {
        this.timedNumericIndicators = timedNumericIndicators;
    }
}