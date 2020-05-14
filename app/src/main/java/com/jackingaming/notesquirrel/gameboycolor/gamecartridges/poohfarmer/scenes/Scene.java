package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public enum Id { FARM, PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB; }

    private Context context;
    private int widthViewport;
    private int heightViewport;
    private Id sceneID;

    private TileMap tileMap;
    private List<Entity> entities;
    private Player player;
    private GameCamera gameCamera;

    public Scene(Context context, int widthViewport, int heightViewport, Id sceneID) {
        this.context = context;
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
        this.sceneID = sceneID;
    }

    public void init(Player player, GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.init(Player, GameCamera)");
        this.player = player;

        initTileMap();
        initGameCamera(gameCamera);
        initEntities(player);

        //fixing bug... the game camera need to use the player's spawn
        //position (which is set after "initGameCamera(GameCamera)").
        gameCamera.update(0L);
    }

    public void enter() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter(Object[])");

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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
        Log.d(MainActivity.DEBUG_TAG, "Scene.exit()");
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

        tileMap = new TileMap(context, sceneID);
    }

    //TODO: move some of these to Scene.enter(Object[])
    private void initEntities(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initEntities(Player)");

        player.init();
        player.setTileMap(tileMap);
        player.setxCurrent((tileMap.getxSpawnIndex() * TileMap.TILE_SIZE));
        player.setyCurrent((tileMap.getySpawnIndex() * TileMap.TILE_SIZE));

        entities = new ArrayList<Entity>();
        entities.add(player);

        ///////////////////////////////////

        if (sceneID == Id.FARM) {
            float widthPixelToViewportRatio = ((float) heightViewport) / gameCamera.getWidthClipInPixel();
            float heightPixelToViewportRatio = ((float) heightViewport) / gameCamera.getHeightClipInPixel();
            Entity robot = new Robot(context, gameCamera, (7 * TileMap.TILE_SIZE), (4 * TileMap.TILE_SIZE),
                    widthPixelToViewportRatio, heightPixelToViewportRatio);
            entities.add(robot);
        }
    }

    //TODO: move some of these to Scene.enter(Object[])
    private void initGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initGameCamera(GameCamera)");

        this.gameCamera = gameCamera;
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    public void update(long elapsed) {
        for (Entity entity : entities) {
            entity.update(elapsed);
        }
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
        for (Entity entity : entities) {
            entity.render(canvas);
        }
    }

    public TileMap getTileMap() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.getTileMap()");

        return tileMap;
    }

    public Id getSceneID() {
        return sceneID;
    }

}