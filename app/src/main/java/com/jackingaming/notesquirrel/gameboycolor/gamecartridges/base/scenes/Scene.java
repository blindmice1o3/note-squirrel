package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import java.io.Serializable;

public abstract class Scene
        implements Serializable {

    public enum Id { FARM, HOTHOUSE, SHEEP_PEN, CHICKEN_COOP, COW_BARN, HOUSE_01, HOUSE_02, HOUSE_03,
        PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB,
        FROGGER; }

    transient protected Handler handler;
    protected Id sceneID;

    transient protected Context context;
    transient protected InputManager inputManager;
    transient protected SceneManager sceneManager;

    transient protected TileMap tileMap;
    transient protected EntityManager entityManager;
    protected Player player;
    protected GameCamera gameCamera;

    protected float xPriorScene;
    protected float yPriorScene;

    public Scene(Handler handler, Id sceneID) {
        this.sceneID = sceneID;
    }

    public void init(Handler handler, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.init(Handler, Player, GameCamera, SceneManager)");
        this.handler = handler;
        this.player = player;

        context = handler.getGameCartridge().getContext();
        inputManager = handler.getGameCartridge().getInputManager();
        this.sceneManager = sceneManager;

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

    public abstract void getInputButtonPad();
    public abstract void initTileMap();

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