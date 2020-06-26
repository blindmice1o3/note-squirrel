package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.scenes;

import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Car;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.tiles.TileMapFrogger;

public class SceneFrogger extends Scene {

    private int controllerForNextEntityInstantiation;
    private int numOfCarLanes;
    private int numOfRiverLanes;
    private int chanceToInstantiate;

    public SceneFrogger(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        controllerForNextEntityInstantiation = 0;
        numOfCarLanes = 5;
        numOfRiverLanes = 4;
        chanceToInstantiate = 0;
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        Log.d(MainActivity.DEBUG_TAG, "SceneFrogger.init(GameCartridge, Player, GameCamera, SceneManager)");
        this.gameCartridge = gameCartridge;
        this.player = player;

        context = gameCartridge.getContext();
        inputManager = gameCartridge.getInputManager();
        this.sceneManager = sceneManager;

        /////////////////////////////////////////////////////////
        //////////////////////////////////////////////////
        Assets.initFroggerSprites(context.getResources());
        //////////////////////////////////////////////////

        int clipWidthInTile = 20;
        int clipHeightInTile = 15;
        int tileWidthFrogger = (int)((48f) * (15f/20f));
        int tileHeightFrogger = 48;
        gameCamera.setWidthClipInPixel(clipWidthInTile * tileWidthFrogger);
        gameCamera.setHeightClipInPixel(clipHeightInTile * tileHeightFrogger);
        /////////////////////////////////////////////////////////

        initTileMap();
        initGameCamera(gameCamera);
        initEntityManager(player);

        //fixing bug... the game camera need to use the player's spawn
        //position (which is set after "initGameCamera(GameCamera)").
        gameCamera.update(0L);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFrogger.getInputButtonPad() a-button-justPressed");


        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFrogger.getInputButtonPad() b-button-justPressed");


        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFrogger.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapFrogger(gameCartridge, sceneID);
    }

    @Override
    public void update(long elapsed) {
        //////////////////////////////
        entityManager.update(elapsed);
        //////////////////////////////

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
                        y = (8 * tileMap.getTileHeight()) + 15;               //LANE01 (CLOSEST TO TOP)
                        width = tileMap.getTileWidth();
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right Car instance.
                        entityManager.addEntity(new Car(gameCartridge, x, y,
                                Creature.Direction.RIGHT, Car.Type.PINK));

                        break;
                    case 2:
                        //Direction.LEFT
                        x = 19 * tileMap.getTileWidth();
                        y = (9 * tileMap.getTileHeight()) + 15;               //LANE02
                        width = tileMap.getTileWidth();
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left Car instance.
                        entityManager.addEntity(new Car(gameCartridge, x, y,
                                Creature.Direction.LEFT, Car.Type.PINK));

                        break;
                    case 3:
                        //Direction.RIGHT
                        x = 0;
                        y = (10 * tileMap.getTileHeight()) + 15;              //LANE03 (MIDDLE)
                        width = tileMap.getTileWidth();
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right Car instance.
                        entityManager.addEntity(new Car(gameCartridge, x, y,
                                Creature.Direction.RIGHT, Car.Type.WHITE));

                        break;
                    case 4:
                        //Direction.LEFT
                        x = 19 * tileMap.getTileWidth();
                        y = (11 * tileMap.getTileHeight()) + 15;          //LANE04
                        width = tileMap.getTileWidth();
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left Car instance.
                        entityManager.addEntity(new Car(gameCartridge, x, y,
                                Creature.Direction.LEFT, Car.Type.WHITE));

                        break;
                    case 5:
                        //Direction.RIGHT
                        x = 0;
                        y = (12 * tileMap.getTileHeight()) + 15;                      //LANE05 (CLOSEST TO BOTTOM)
                        width = tileMap.getTileWidth();
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Car.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right Car instance.
                        entityManager.addEntity(new Car(gameCartridge, x, y,
                                Creature.Direction.RIGHT, Car.Type.YELLOW));

                        break;
                    case 6:
                        //Direction.RIGHT
                        x = 0;
                        y = (3 * tileMap.getTileHeight()) + 15;               //RIVER (CLOSEST TO TOP)
                        width = Assets.logSmall.getWidth(); //Log.Size.SMALL
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right, small Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(gameCartridge, x, y,
                                Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.SMALL));

                        break;
                    case 7:
                        //Direction.LEFT
                        x = (19 * tileMap.getTileWidth());
                        y = (4 * tileMap.getTileHeight()) + 15;
                        width = Assets.logMedium.getWidth(); //Log.Size.MEDIUM
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left, medium Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(gameCartridge, x, y,
                                Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.MEDIUM));

                        break;
                    case 8:
                        //Direction.RIGHT
                        x = 0;
                        y = (5 * tileMap.getTileHeight()) + 15;
                        width = Assets.logLarge.getWidth(); //Log.Size.LARGE
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new right, large Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(gameCartridge, x, y,
                                Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.LARGE));

                        break;
                    case 9:
                        //Direction.LEFT
                        x = (19 * tileMap.getTileWidth());
                        y = (6 * tileMap.getTileHeight()) + 15;
                        width = Assets.logSmall.getWidth(); //Log.Size.SMALL
                        height = tileMap.getTileHeight();

                        //checking for overlap before instantiating new Log.
                        for (Entity e : entityManager.getEntities()) {
                            if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x + width, y + height))) {
                                return;
                            }
                        }

                        //add new left, small Log instance.
                        entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(gameCartridge, x, y,
                                Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.SMALL));

                        break;
                    default:
                        Log.d(MainActivity.DEBUG_TAG, "SceneFrogger.update(), switch (controllerForNextEntityInstantiation) construct's default block.");
                        break;
                }
            }
        }

        //////////////////////////////
        gameCamera.update(0L);
        //////////////////////////////
    }

}