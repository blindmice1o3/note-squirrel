package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import java.io.Serializable;

public abstract class Scene
        implements Serializable {

    public enum Id {
        FARM, HOTHOUSE, SHEEP_PEN, CHICKEN_COOP, COW_BARN, HOUSE_01, HOUSE_02, HOUSE_03,
        PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB,
        FROGGER, PONG;
    }

    transient protected GameCartridge gameCartridge;
    protected Id sceneID;

    transient protected Context context;
    transient protected InputManager inputManager;
    transient protected SceneManager sceneManager;

    protected int widthClipInTile;
    protected int heightClipInTile;

    protected TileMap tileMap;
    protected EntityManager entityManager;
    protected Player player;
    protected GameCamera gameCamera;

    protected float xPriorScene;
    protected float yPriorScene;

    public Scene(GameCartridge gameCartridge, Id sceneID) {
        this.gameCartridge = gameCartridge;
        this.sceneID = sceneID;

        widthClipInTile = 8;
        heightClipInTile = 8;

        /////////////////////////////////////////////////////////////
        entityManager = new EntityManager(gameCartridge.getPlayer());
        initTileMap();
        /////////////////////////////////////////////////////////////
    }

    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.init(GameCartridge, Player, GameCamera, SceneManager)");
        this.gameCartridge = gameCartridge;
        this.player = player;

        context = gameCartridge.getContext();
        inputManager = gameCartridge.getInputManager();
        this.sceneManager = sceneManager;

//        tileMap.init(gameCartridge);
        initGameCamera(gameCamera);
        initEntityManager(player);

        //fixing bug... the game camera need to use the player's spawn
        //position (which is set after "initGameCamera(GameCamera)").
        gameCamera.update(0L);
    }

    public void enter() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter()");
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() player.xCurrent, player.yCurrent: " + player.getxCurrent() + ", " + player.getyCurrent());
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() xPriorScene, yPriorScene: " + xPriorScene + ", " + yPriorScene);

        if ((xPriorScene == 0f) && (yPriorScene == 0f)) {
            player.setxCurrent((tileMap.getxSpawnIndex() * tileMap.getTileWidth()));
            player.setyCurrent((tileMap.getySpawnIndex() * tileMap.getTileHeight()));
        } else {
            player.setxCurrent(xPriorScene);
            player.setyCurrent(yPriorScene);
        }

        //EACH SUBCLASS OF Scene CAN OVERWRITE ITS CLIP SIZE WITHIN THE CONSTRUCTOR/////
        if ((widthClipInTile != 0) && (heightClipInTile != 0)) {
            gameCamera.setWidthClipInPixel(widthClipInTile * tileMap.getTileWidth());
            gameCamera.setHeightClipInPixel(heightClipInTile * tileMap.getTileHeight());
        }
        ////////////////////////////////////////////////////////////////////////////////

        tileMap.init(gameCartridge);
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
        for (Entity e : entityManager.getEntities()) {
            e.init(gameCartridge);
        }
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter() player.xCurrent, player.yCurrent: " + player.getxCurrent() + ", " + player.getyCurrent());

        //TODO: base this from what's saved via StartMenuState's OPTION (settings).
        gameCartridge.getHeadUpDisplay().setIsVisible(true);
    }

    public void exit(Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.exit(Object[])");

        //TODO: work-around for bug to get it working.
        /////////////////////////
        tileMap.setTexture(null);
        /////////////////////////

        //Record position IMMEDIATELY BEFORE colliding with transfer point.
        Player.Direction direction = (Player.Direction) extra[0];
        float moveSpeed = (float) extra[1];
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

    public abstract void initTileMap();

    //TODO: move some of these to Scene.enter(Object[])
    public void initEntityManager(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initEntityManager(Player)");

        entityManager.removePreviousPlayer();
        entityManager.addEntity(player);
    }

    //TODO: move some of these to Scene.enter(Object[])
    public void initGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "Scene.initGameCamera(GameCamera)");

        this.gameCamera = gameCamera;
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    public void getInputViewport() {
        //up
        if (inputManager.isUpViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputViewport() up-button-justPressed");
            player.move(Player.Direction.UP);
        }
        //down
        else if (inputManager.isDownViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputViewport() down-button-justPressed");
            player.move(Player.Direction.DOWN);
        }
        //left
        else if (inputManager.isLeftViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputViewport() left-button-justPressed");
            player.move(Player.Direction.LEFT);
        }
        //right
        else if (inputManager.isRightViewport()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputViewport() right-button-justPressed");
            player.move(Player.Direction.RIGHT);
        }
    }

    public void getInputDirectionalPad() {
        //up
        if (inputManager.isUpDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputDirectionalPad() up-button-pressing");
            player.move(Player.Direction.UP);
        }
        //down
        else if (inputManager.isDownDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputDirectionalPad() down-button-pressing");
            player.move(Player.Direction.DOWN);
        }
        //left
        else if (inputManager.isLeftDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputDirectionalPad() left-button-pressing");
            player.move(Player.Direction.LEFT);
        }
        //right
        else if (inputManager.isRightDirectionalPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputDirectionalPad() right-button-pressing");
            player.move(Player.Direction.RIGHT);
        }
    }

    public void getInputButtonPad() {
        //TODO: so far each subclass of Scene has overridden (no default).
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputButtonPad() a-button-justPressed");
            //TODO:
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputButtonPad() b-button-justPressed");
            //TODO:
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

    public void getInputSelectButton() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.getInputSelectButton()");
        //TODO:
    }

    public void getInputStartButton() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.getInputStartButton()");
        //TODO:
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

        //TILES
        tileMap.render(canvas);

        //ENTITIES
        entityManager.render(canvas);
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

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