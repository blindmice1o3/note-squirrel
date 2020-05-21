package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {

    private Handler handler;
    private Player player;

    private ArrayList<Entity> entities;

    private Comparator<Entity> renderSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            return (a instanceof Player) ? (1) : (-1);

            /*
            //compare based on the y-value of the Entity's BOTTOM-left corner.
            if ( (a.getyCurrent() + a.getHeight()) < (b.getyCurrent() + b.getHeight()) ) {
                return -1;
            } else {
                return 1;
            }
            */
        }
    };

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        entities = new ArrayList<Entity>();
        entities.add(player);
    }

    public void removePreviousPlayer() {
        Log.d(MainActivity.DEBUG_TAG, "EntityManager.removePreviousPlayer()");

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity entityFromArrayList = it.next();
            ////////////////////////////////////
            if (entityFromArrayList == player) {
                it.remove();
                break;
            }
            ////////////////////////////////////
        }
    }

    public void update(long elapsed) {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();

            //////////////////
            e.update(elapsed);
            //////////////////

            /////////////////////
            //check for active.
            if (!e.isActive()) {
                it.remove();
            }
            /////////////////////
        }
        /////////////////////////////////////////////////////
        //sort based on y-value before rendering.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            entities.sort(renderSorter);
        }
        /////////////////////////////////////////////////////
    }

    public void render(Canvas canvas) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(canvas);
        }
/*
        for (Entity e : entities) {
            e.render(canvas);
        }
*/
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void savePresentState() {
        //TODO:
    }

    public void loadSavedState() {
        //TODO:
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}