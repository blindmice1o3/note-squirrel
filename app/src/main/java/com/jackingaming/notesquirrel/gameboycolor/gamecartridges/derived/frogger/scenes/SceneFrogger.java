package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.scenes;

import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Car;

public class SceneFrogger extends Scene {

    public SceneFrogger(Handler handler, Id sceneID) {
        super(handler, sceneID);
    }

    int controllerForNextEntityInstantiation = 0;
    int numOfCarLanes = 5;
    int numOfRiverLanes = 4;
    int chanceToInstantiate = 0;
    @Override
    public void update(long elapsed) {
        //////////////////////////////
        entityManager.update(elapsed);
        //////////////////////////////

        ///////////////////////////
        int tileWidthFrogger = 48;
        int tileHeightFrogger = 48;
        ///////////////////////////

        if (entityManager.getEntities().size() < 12) {
            chanceToInstantiate = (int) (Math.random() * 100) + 1;
            Log.d(MainActivity.DEBUG_TAG, "chanceToInstantiate: " + chanceToInstantiate);

            //ONLY INSTANTIATE 5% of the time UPDATE(long) IS CALLED.
            if (chanceToInstantiate <= 5) {
                controllerForNextEntityInstantiation = (int) (Math.random() * (numOfCarLanes + numOfRiverLanes)) + 1;
                Log.d(MainActivity.DEBUG_TAG, "controllerForNextEntityInstantiation: " + controllerForNextEntityInstantiation);
                int x = 0;
                int y = 0;
                int width = 0;
                int height = 0;

                switch (controllerForNextEntityInstantiation) {
                    case 1:
                        //Direction.RIGHT
                        x = 0;
                        y = (8 * tileHeightFrogger) + 15;               //LANE01 (CLOSEST TO TOP)
                        width = tileWidthFrogger;
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right Car instance.
                        entityManager.addEntity(new Car(handler, x, y,
                                Creature.Direction.RIGHT, Car.Type.PINK));

                        break;
                    case 2:
                        //Direction.LEFT
                        x = 19 * tileWidthFrogger;
                        y = (9 * tileHeightFrogger) + 15;               //LANE02
                        width = tileWidthFrogger;
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left Car instance.
                        entityManager.addEntity(new Car(handler, x, y,
                                Creature.Direction.LEFT, Car.Type.PINK));

                        break;
                    case 3:
                        //Direction.RIGHT
                        x = 0;
                        y = (10 * tileHeightFrogger) + 15;              //LANE03 (MIDDLE)
                        width = tileWidthFrogger;
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right Car instance.
                        entityManager.addEntity(new Car(handler, x, y,
                                Creature.Direction.RIGHT, Car.Type.WHITE));

                        break;
                    case 4:
                        //Direction.LEFT
                        x = 19 * tileWidthFrogger;
                        y = (11 * tileHeightFrogger) + 15;          //LANE04
                        width = tileWidthFrogger;
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left Car instance.
                        entityManager.addEntity(new Car(handler, x, y,
                                Creature.Direction.LEFT, Car.Type.WHITE));

                        break;
                    case 5:
                        //Direction.RIGHT
                        x = 0;
                        y = (12 * tileHeightFrogger) + 15;                      //LANE05 (CLOSEST TO BOTTOM)
                        width = tileWidthFrogger;
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right Car instance.
                        entityManager.addEntity(new Car(handler, x, y,
                                Creature.Direction.RIGHT, Car.Type.YELLOW));

                        break;
                    case 6:
                        //Direction.RIGHT
                        x = 0;
                        y = (3 * tileHeightFrogger) + 15;               //RIVER (CLOSEST TO TOP)
                        width = Assets.logSmall.getWidth(); //Log.Size.SMALL
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right, small Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.SMALL));

                        break;
                    case 7:
                        //Direction.LEFT
                        x = (19 * tileWidthFrogger);
                        y = (4 * tileHeightFrogger) + 15;
                        width = Assets.logMedium.getWidth(); //Log.Size.MEDIUM
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left, medium Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.MEDIUM));

                        break;
                    case 8:
                        //Direction.RIGHT
                        x = 0;
                        y = (5 * tileHeightFrogger) + 15;
                        width = Assets.logLarge.getWidth(); //Log.Size.LARGE
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right, large Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.LARGE));

                        break;
                    case 9:
                        //Direction.LEFT
                        x = (19 * tileWidthFrogger);
                        y = (6 * tileHeightFrogger) + 15;
                        width = Assets.logSmall.getWidth(); //Log.Size.SMALL
                        height = tileHeightFrogger;

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left, small Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.SMALL));

                        break;
                    default:
                        Log.d(MainActivity.DEBUG_TAG, "Scene.update(), switch (Id.FROGGER)'s default.");
                        break;
                }
            }
        }

        //////////////////////////////
        gameCamera.update(0L);
        //////////////////////////////
    }

}