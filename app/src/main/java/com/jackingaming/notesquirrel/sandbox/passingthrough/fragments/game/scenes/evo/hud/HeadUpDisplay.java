package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo.hud;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.FishForm;

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

    private void renderHUD(Canvas canvas) {
        Player playerBase = Player.getInstance();
        FishForm player = ((FishForm)playerBase.getForm());
        /* TOP OF SCREEN */
        //HP BAR
        //TODO: make the hud's hp bar use a percentage-based system so the width won't change when healthMax changes.
        int currentHealthPercent = (int)(((float)player.getHealth() / (float)player.getHealthMax()) * 100); //need floating-point-division; int-division lobs-off digits.
        //System.out.println("currentHealthPercent: " + currentHealthPercent);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(28, 11, 104, 12, paint);
        //g.fillRect(28, 11, 10*(player.getHealthMax()) +4, 12);
        paint.setColor(Color.GREEN);
        //g.fillRect(30, 13, 10*player.getHealth(), 8);
        canvas.drawRect(30, 13, currentHealthPercent, 8, paint);

        //HP
        paint.setColor(Color.GREEN);
        canvas.drawText("hp: ", 10, 20, paint);
        canvas.drawText(Integer.toString(player.getHealth()), 10, 35, paint);

        //XP
        paint.setColor(Color.WHITE);
        canvas.drawText("experiencePoints: " + player.getExperiencePoints(),
                game.getWidthViewport()/2, 20, paint);
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