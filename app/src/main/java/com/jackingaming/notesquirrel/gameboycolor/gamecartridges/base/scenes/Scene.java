package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.tiles.TileMapFrogger;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapHome01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapHome02;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapHomeRival;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapLab;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.outdoors.worldmap.TileMapPart01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.outdoors.TileMapFarm;

import java.io.Serializable;

public class Scene
        implements Serializable {

    public enum Id { FARM, PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB, FROGGER; }

    transient protected Handler handler;
    protected Id sceneID;

    transient protected TileMap tileMap;
    transient protected EntityManager entityManager;
    protected Player player;
    protected GameCamera gameCamera;

    protected float xPriorScene;
    protected float yPriorScene;

    public Scene(Handler handler, Id sceneID) {
        this.handler = handler;
        this.sceneID = sceneID;
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
            player.setxCurrent((tileMap.getxSpawnIndex() * tileMap.getTileWidth()));
            player.setyCurrent((tileMap.getySpawnIndex() * tileMap.getTileHeight()));
        } else {
            player.setxCurrent(xPriorScene);
            player.setyCurrent(yPriorScene);
        }

        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() player.xCurrent, player.yCurrent: " + player.getxCurrent() + ", " + player.getyCurrent());
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

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

        switch (sceneID) {
            case FARM:
                tileMap = new TileMapFarm(handler, sceneID);
                break;
            case PART_01:
                tileMap = new TileMapPart01(handler, sceneID);
                break;
            case HOME_01:
                tileMap = new TileMapHome01(handler, sceneID);
                break;
            case HOME_02:
                tileMap = new TileMapHome02(handler, sceneID);
                break;
            case HOME_RIVAL:
                tileMap = new TileMapHomeRival(handler, sceneID);
                break;
            case LAB:
                tileMap = new TileMapLab(handler, sceneID);
                break;
            case FROGGER:
                tileMap = new TileMapFrogger(handler, sceneID);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "Scene.initTileMap() switch-construct's default block.");
                tileMap = new TileMapPart01(handler, sceneID);
                break;
        }
    }

    //TODO: move some of these to Scene.enter(Object[])
    public void initEntityManager(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initEntityManager(Player)");

        ///////////////////////////////////////////////////
        entityManager = new EntityManager(handler, player);
        ///////////////////////////////////////////////////
    }

    //TODO: move some of these to Scene.enter(Object[])
    public void initGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initGameCamera(GameCamera)");

        this.gameCamera = gameCamera;
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    public void update(long elapsed) {
        //////////////////////////////
        entityManager.update(elapsed);
        gameCamera.update(0L);
        //////////////////////////////
    }

    public void render(Canvas canvas) {
        //BACKGROUND
        Rect rectOfClip = gameCamera.getRectOfClip();
        Rect rectOfViewport = gameCamera.getRectOfViewport();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(tileMap.getTexture(), rectOfClip, rectOfViewport, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //FOREGROUND
        entityManager.render(canvas);
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public EntityManager getEntityManager() {
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