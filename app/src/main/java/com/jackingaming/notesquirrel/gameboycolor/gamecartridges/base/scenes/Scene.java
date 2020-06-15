package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Car;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

import java.io.Serializable;

public class Scene
        implements Serializable {

    public enum Id { FARM, PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB, FROGGER; }

    transient private Handler handler;
    private Id sceneID;

    private int widthViewport;
    private int heightViewport;

    transient private TileMap tileMap;
    transient private EntityManager entityManager;
    private Player player;
    private GameCamera gameCamera;

    public Scene(Handler handler, Id sceneID) {
        this.handler = handler;
        this.sceneID = sceneID;

        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();
    }

    public void init(Player player, GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.init(Player, GameCamera)");
        this.player = player;

        initTileMap();
        initGameCamera(gameCamera);
        initEntityManager(player);

        //fixing bug... the game camera need to use the player's spawn
        //position (which is set after "initGameCamera(GameCamera)").
        gameCamera.update(0L);
    }

    public void enter() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter()");

        player.init();

        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() player.xCurrent, player.yCurrent: " + player.getxCurrent() + ", " + player.getyCurrent());
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() xPriorScene, yPriorScene: " + xPriorScene + ", " + yPriorScene);

        if ((xPriorScene == 0f) && (yPriorScene == 0f)) {
            player.setxCurrent((tileMap.getxSpawnIndex() * TileMap.TILE_WIDTH));
            player.setyCurrent((tileMap.getySpawnIndex() * TileMap.TILE_HEIGHT));

            //TODO: WORK-AROUND (ADJUST TILE_WIDTH and TILE_HEIGHT).
            if (sceneID == Id.FROGGER) {
                ///////////////////////////
                int tileWidthFrogger = 48;
                int tileHeightFrogger = 48;
                ///////////////////////////

                player.setxCurrent((tileMap.getxSpawnIndex() * tileWidthFrogger));
                player.setyCurrent((tileMap.getySpawnIndex() * tileHeightFrogger));
            }

        } else {
            player.setxCurrent(xPriorScene);
            player.setyCurrent(yPriorScene);
        }

        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() player.xCurrent, player.yCurrent: " + player.getxCurrent() + ", " + player.getyCurrent());
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    float xPriorScene;
    float yPriorScene;
    public void exit(Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.exit(Object[])");

        //TODO: work-around for bug to get it working.
        ///////////////
        tileMap = null;
        ///////////////

        //Record position IMMEDIATELY BEFORE colliding with transfer point.
        Player.Direction direction = (Player.Direction)extra[0];
        float moveSpeed = (float)extra[1];
        switch (direction) {
            case LEFT:
                xPriorScene = player.getxCurrent() + moveSpeed;
                yPriorScene = player.getyCurrent();
                break;
            case RIGHT:
                xPriorScene = player.getxCurrent() - moveSpeed;
                yPriorScene = player.getyCurrent();
                break;
            case UP:
                xPriorScene = player.getxCurrent();
                yPriorScene = player.getyCurrent() + moveSpeed;
                break;
            case DOWN:
                xPriorScene = player.getxCurrent();
                yPriorScene = player.getyCurrent() - moveSpeed;
                break;
        }
    }

    //TODO: move some of these to Scene.enter(Object[])
    public void initTileMap() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initTileMap()");

        tileMap = new TileMap(handler, sceneID);
    }

    //TODO: move some of these to Scene.enter(Object[])
    public void initEntityManager(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initEntityManager(Player)");

        ///////////////////////////////////////////////////
        entityManager = new EntityManager(handler, player);
        ///////////////////////////////////////////////////

        if (sceneID == Id.FARM) {
            Entity robot = new Robot(handler, (7 * TileMap.TILE_WIDTH), (5 * TileMap.TILE_HEIGHT));
            entityManager.addEntity(robot);
        }
    }

    //TODO: move some of these to Scene.enter(Object[])
    public void initGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initGameCamera(GameCamera)");

        this.gameCamera = gameCamera;
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    int controllerForNextEntityInstantiation = 0;
    int numOfCarLanes = 5;
    int numOfRiverLanes = 4;
    int chanceToInstantiate = 0;
    public void update(long elapsed) {
        //////////////////////////////
        entityManager.update(elapsed);
        //////////////////////////////

        //TODO: WORK-AROUND (ADJUST TILE_WIDTH and TILE_HEIGHT).
        if (sceneID == Id.FROGGER) {
            ///////////////////////////
            int tileWidthFrogger = 48;
            int tileHeightFrogger = 48;
            ///////////////////////////

            if (entityManager.getEntities().size() < 12) {
                chanceToInstantiate = (int)(Math.random()*100)+1;
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
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new right Car instance.
                            entityManager.addEntity(new Car(handler, x, y,
                                    Creature.Direction.RIGHT, Car.Type.PINK) );

                            break;
                        case 2:
                            //Direction.LEFT
                            x = 19 * tileWidthFrogger;
                            y = (9 * tileHeightFrogger) + 15;               //LANE02
                            width = tileWidthFrogger;
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Car.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new left Car instance.
                            entityManager.addEntity(new Car(handler, x, y,
                                    Creature.Direction.LEFT, Car.Type.PINK) );

                            break;
                        case 3:
                            //Direction.RIGHT
                            x = 0;
                            y = (10 * tileHeightFrogger) + 15;              //LANE03 (MIDDLE)
                            width = tileWidthFrogger;
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Car.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new right Car instance.
                            entityManager.addEntity(new Car(handler, x, y,
                                    Creature.Direction.RIGHT, Car.Type.WHITE) );

                            break;
                        case 4:
                            //Direction.LEFT
                            x = 19 * tileWidthFrogger;
                            y = (11 * tileHeightFrogger) + 15;          //LANE04
                            width = tileWidthFrogger;
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Car.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new left Car instance.
                            entityManager.addEntity(new Car(handler, x, y,
                                    Creature.Direction.LEFT, Car.Type.WHITE) );

                            break;
                        case 5:
                            //Direction.RIGHT
                            x = 0;
                            y = (12 * tileHeightFrogger) + 15;                      //LANE05 (CLOSEST TO BOTTOM)
                            width = tileWidthFrogger;
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Car.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new right Car instance.
                            entityManager.addEntity(new Car(handler, x, y,
                                    Creature.Direction.RIGHT, Car.Type.YELLOW) );

                            break;
                        case 6:
                            //Direction.RIGHT
                            x = 0;
                            y = (3 * tileHeightFrogger) + 15;               //RIVER (CLOSEST TO TOP)
                            width = Assets.logSmall.getWidth(); //Log.Size.SMALL
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new right, small Log instance.
                            entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                    Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.SMALL) );

                            break;
                        case 7:
                            //Direction.LEFT
                            x = (19 * tileWidthFrogger);
                            y = (4 * tileHeightFrogger) + 15;
                            width = Assets.logMedium.getWidth(); //Log.Size.MEDIUM
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new left, medium Log instance.
                            entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                    Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.MEDIUM) );

                            break;
                        case 8:
                            //Direction.RIGHT
                            x = 0;
                            y = (5 * tileHeightFrogger) + 15;
                            width = Assets.logLarge.getWidth(); //Log.Size.LARGE
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new right, large Log instance.
                            entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                    Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.LARGE) );

                            break;
                        case 9:
                            //Direction.LEFT
                            x = (19 * tileWidthFrogger);
                            y = (6 * tileHeightFrogger) + 15;
                            width = Assets.logSmall.getWidth(); //Log.Size.SMALL
                            height = tileHeightFrogger;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersect(new Rect(x, y, x+width, y+height))) {
                                    return;
                                }
                            }

                            //add new left, small Log instance.
                            entityManager.addEntity(new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log(handler, x, y,
                                    Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.Log.Size.SMALL) );

                            break;
                        default:
                            Log.d(MainActivity.DEBUG_TAG, "Scene.update(), switch (Id.FROGGER)'s default.");
                            break;
                    }
                }
            }
        }

        //////////////////////////////
        gameCamera.update(0L);
        //////////////////////////////
    }

    public void render(Canvas canvas) {
        //BACKGROUND
        Rect boundsFarm = new Rect(
                (int)gameCamera.getX(),
                (int)gameCamera.getY(),
                (int)(gameCamera.getX() + gameCamera.getWidthClipInPixel()),
                (int)(gameCamera.getY() + gameCamera.getHeightClipInPixel())
        );
        Rect screenFarm = new Rect(0, 0, widthViewport, heightViewport);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(tileMap.getTexture(), boundsFarm, screenFarm, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //FOREGROUND
        entityManager.render(canvas);
    }

    public TileMap getTileMap() {
        //Log.d(MainActivity.DEBUG_TAG, "Scene.getTileMap()");
        return tileMap;
    }

    public EntityManager getEntityManager() {
        //Log.d(MainActivity.DEBUG_TAG, "Scene.getEntities()");
        return entityManager;
    }

    public void setHandler(Handler handler) { this.handler = handler; }

    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Id getSceneID() {
        return sceneID;
    }

}