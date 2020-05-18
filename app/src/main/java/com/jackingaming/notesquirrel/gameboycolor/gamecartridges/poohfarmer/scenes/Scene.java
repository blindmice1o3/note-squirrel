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
                            1 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.RIGHT, Car.Type.PINK) );
            entityManager.addEntity(
                    new Car(handler,
                            3 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.LEFT, Car.Type.PINK) );
            entityManager.addEntity(
                    new Car(handler,
                            5 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.RIGHT, Car.Type.WHITE) );
            entityManager.addEntity(
                    new Car(handler,
                            7 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.LEFT, Car.Type.WHITE) );
            entityManager.addEntity(
                    new Car(handler,
                            9 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.RIGHT, Car.Type.YELLOW) );
            entityManager.addEntity(
                    new Car(handler,
                            11 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.LEFT, Car.Type.YELLOW) );
            entityManager.addEntity(
                    new Car(handler,
                            13 * tileWidthFrogger, 1 * tileHeightFrogger,
                            Creature.Direction.RIGHT, Car.Type.BIG_RIG) );
            entityManager.addEntity(
                    new Car(handler,
                            16 * tileWidthFrogger, 1 * tileHeightFrogger,
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

    public void update(long elapsed) {
        //////////////////////////////
        entityManager.update(elapsed);
        //////////////////////////////

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