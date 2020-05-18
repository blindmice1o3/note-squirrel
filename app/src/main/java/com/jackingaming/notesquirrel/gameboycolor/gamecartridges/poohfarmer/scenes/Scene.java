package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Car;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public enum Id { FARM, PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB, FROGGER; }

    private Handler handler;
    private Id sceneID;

    private int widthViewport;
    private int heightViewport;

    private TileMap tileMap;
    private EntityManager entityManager;
    //private List<Entity> entities;
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

        //@@maybe not@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //TODO: call handler.setCurrentScene(this);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        if ((xPriorScene != 0f) && (yPriorScene != 0f)) {
            player.setxCurrent(xPriorScene);
            player.setyCurrent(yPriorScene);
            gameCamera.update(0L);
        }
    }

    float xPriorScene;
    float yPriorScene;
    public void exit(Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.exit(Object[])");
        //TODO: implement

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
    private void initTileMap() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initTileMap()");

        tileMap = new TileMap(handler, sceneID);
    }

    //TODO: move some of these to Scene.enter(Object[])
    private void initEntityManager(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initEntityManager(Player)");

        player.init();
        player.setxCurrent((tileMap.getxSpawnIndex() * TileMap.TILE_WIDTH));
        player.setyCurrent((tileMap.getySpawnIndex() * TileMap.TILE_HEIGHT));

        ///////////////////////////////////////////////////
        entityManager = new EntityManager(handler, player);
        ///////////////////////////////////////////////////

        if (sceneID == Id.FARM) {
            Entity robot = new Robot(handler, (7 * TileMap.TILE_WIDTH), (5 * TileMap.TILE_HEIGHT));
            entityManager.addEntity(robot);
        }

        //TODO: WORK-AROUND (ADJUST TILE_WIDTH and TILE_HEIGHT).
        if (sceneID == Id.FROGGER) {
            //////////////////////////////////////////////////////////////////////////////////
            int tileWidthFrogger = 48;
            int tileHeightFrogger = 48;
            //player.setBounds( new Rect(0, 0, tileWidthFrogger, tileHeightFrogger) );
            //////////////////////////////////////////////////////////////////////////////////

            player.setxCurrent((tileMap.getxSpawnIndex() * tileWidthFrogger));
            player.setyCurrent((tileMap.getySpawnIndex() * tileHeightFrogger));

            entityManager.addEntity(
                    new Car(handler,
                            0 * tileWidthFrogger, (8 * tileHeightFrogger) + 15,
                            Creature.Direction.RIGHT, Car.Type.PINK) );
            entityManager.addEntity(
                    new Car(handler,
                            19 * tileWidthFrogger, (9 * tileHeightFrogger) + 15,
                            Creature.Direction.LEFT, Car.Type.PINK) );
            entityManager.addEntity(
                    new Car(handler,
                            0 * tileWidthFrogger, (10 * tileHeightFrogger) + 15,
                            Creature.Direction.RIGHT, Car.Type.WHITE) );
            entityManager.addEntity(
                    new Car(handler,
                            19 * tileWidthFrogger, (11 * tileHeightFrogger) + 15,
                            Creature.Direction.LEFT, Car.Type.WHITE) );
            entityManager.addEntity(
                    new Car(handler,
                            0 * tileWidthFrogger, (12 * tileHeightFrogger) + 15,
                            Creature.Direction.RIGHT, Car.Type.YELLOW) );
            entityManager.addEntity(
                    new Car(handler,
                            19 * tileWidthFrogger, (13 * tileHeightFrogger) + 15,
                            Creature.Direction.LEFT, Car.Type.YELLOW) );

            entityManager.addEntity(
                    new Car(handler,
                            0 * tileWidthFrogger, (6 * tileHeightFrogger) + 15,
                            Creature.Direction.RIGHT, Car.Type.BIG_RIG) );
            entityManager.addEntity(
                    new Car(handler,
                            19 * tileWidthFrogger, (7 * tileHeightFrogger) + 15,
                            Creature.Direction.LEFT, Car.Type.BIG_RIG) );

            entityManager.addEntity(
                    new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log(handler,
                            4 * tileWidthFrogger, 3 * tileHeightFrogger,
                            Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log.Size.SMALL) );
            entityManager.addEntity(
                    new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log(handler,
                            7 * tileWidthFrogger, 4 * tileHeightFrogger,
                            Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log.Size.MEDIUM) );
            entityManager.addEntity(
                    new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log(handler,
                            2 * tileWidthFrogger, 5 * tileHeightFrogger,
                            Creature.Direction.LEFT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log.Size.LARGE) );
            entityManager.addEntity(
                    new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log(handler,
                            7 * tileWidthFrogger, 3 * tileHeightFrogger,
                            Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log.Size.SMALL) );
            entityManager.addEntity(
                    new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log(handler,
                            11 * tileWidthFrogger, 4 * tileHeightFrogger,
                            Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log.Size.MEDIUM) );
            entityManager.addEntity(
                    new com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log(handler,
                            2 * tileWidthFrogger, 6 * tileHeightFrogger,
                            Creature.Direction.RIGHT, com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities.Log.Size.LARGE) );
        }
    }

    //TODO: move some of these to Scene.enter(Object[])
    private void initGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initGameCamera(GameCamera)");

        this.gameCamera = gameCamera;
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    int controllerForNextEntityInstantiation = 0;
    int numTypeOfCars = 2;
    int numTypeOfRiverEntities = 4;
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
                    controllerForNextEntityInstantiation = (int) (Math.random() * (numTypeOfCars+numTypeOfRiverEntities)) + 1;
                    Log.d(MainActivity.DEBUG_TAG, "controllerForNextEntityInstantiation: " + controllerForNextEntityInstantiation);
                    int x = 0;
                    int y = 0;
                    int width = 0;
                    int height = 0;

                    switch (controllerForNextEntityInstantiation) {
                        case 1:
                            x = 0;
                            y = (8 * tileHeightFrogger) + 15;
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
                            x = 19 * tileWidthFrogger;
                            y = (9 * tileHeightFrogger) + 15;
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
                            //Log.Size.SMALL

                            /*
                            x = (widthInNumOfTile - 1) * Tile.screenTileWidth;;
                            y = (3*Tile.screenTileHeight);
                            width = Assets.logSmall.getWidth();
                            height = Tile.screenTileHeight;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                    return;
                                }
                            }

                            //add new Log instance (FAST!!! Size.SMALL).
                            Log fastLog = new Log(handler, x, y, Log.Size.SMALL, Log.MovementDirection.LEFT);
                            fastLog.setSpeed(2);
                            entityManager.addEntity(fastLog);
                            */

                            x = 0;
                            y = (10 * tileHeightFrogger) + 15;
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
                            //Log.Size.SMALL

                            /*
                            x = 0;
                            y = (4*Tile.screenTileHeight);
                            width = Assets.logSmall.getWidth();
                            height = Tile.screenTileHeight;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                    return;
                                }
                            }

                            //add new Log instance (Size.SMALL).
                            entityManager.addEntity(new Log(handler, x, y, Log.Size.SMALL, Log.MovementDirection.RIGHT));
                            */

                            x = 19 * tileWidthFrogger;
                            y = (11 * tileHeightFrogger) + 15;
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
                            //Log.Size.LARGE

                            /*
                            x = (widthInNumOfTile - 1) * Tile.screenTileWidth;;
                            y = (5*Tile.screenTileHeight);
                            width = Assets.logLarge.getWidth();
                            height = Tile.screenTileHeight;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                    return;
                                }
                            }

                            //add new Log instance (Size.LARGE).
                            entityManager.addEntity(new Log(handler, x, y, Log.Size.LARGE, Log.MovementDirection.LEFT));
                            */

                            x = 0;
                            y = (12 * tileHeightFrogger) + 15;
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
                            //Log.Size.MEDIUM

                            /*
                            x = 0;
                            y = (6*Tile.screenTileHeight);
                            width = Assets.logMedium.getWidth();
                            height = Tile.screenTileHeight;

                            //checking for overlap before instantiating new Log.
                            for (Entity e : entityManager.getEntities()) {
                                if (e.getCollisionBounds(0, 0).intersects(new Rectangle(x, y, width, height))) {
                                    return;
                                }
                            }

                            //add new Log instance (Size.MEDIUM).
                            entityManager.addEntity(new Log(handler, x, y, Log.Size.MEDIUM, Log.MovementDirection.RIGHT));
                            */

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
        Log.d(MainActivity.DEBUG_TAG, "Scene.getTileMap()");

        return tileMap;
    }

    public List<Entity> getEntities() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.getEntities()");

        return entityManager.getEntities();
    }

    public Id getSceneID() {
        return sceneID;
    }

}