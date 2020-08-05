package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.FodderEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.ProductEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.FodderStashTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Holdable;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.moveable.Robot;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import java.io.Serializable;

public abstract class Scene
        implements Serializable {

    public enum Id {
        FARM, HOTHOUSE, SHEEP_PEN, CHICKEN_COOP, COW_BARN, SEEDS_SHOP, HOUSE_01, HOUSE_02, HOUSE_03,
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

    transient private Bitmap backgroundDaylight;
    transient private Bitmap backgroundTwilight;
    transient private Bitmap backgroundNight;
    transient private Paint paintTintTwilight;
    transient private Paint paintTintNight;

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

    private void initBackgroundImages() {
        //DAYLIGHT BACKGROUND
        Log.d(MainActivity.DEBUG_TAG, "Scene.initBackgroundImages() tileMap.getTexture()... is tileMap null? " + tileMap);
        //////////////////////////////////////////
        backgroundDaylight = tileMap.getTexture();
        //////////////////////////////////////////

        //TWILIGHT BACKGROUND
        paintTintTwilight = new Paint();
        paintTintTwilight.setColorFilter(new LightingColorFilter(0xFFFFF000, 0x00000000));
        ////////////////////////////////////////////////////////////////////////////////////////////
        backgroundTwilight = Bitmap.createBitmap(backgroundDaylight.getWidth(), backgroundDaylight.getHeight(), Bitmap.Config.ARGB_8888);
        ////////////////////////////////////////////////////////////////////////////////////////////
        Canvas canvasTwilight = new Canvas(backgroundTwilight);
        canvasTwilight.drawBitmap(backgroundDaylight, 0, 0, paintTintTwilight);

        //NIGHT BACKGROUND
        paintTintNight = new Paint();
        paintTintNight.setColorFilter(new LightingColorFilter(0xFF00FFFF, 0x00000000));
        ////////////////////////////////////////////////////////////////////////////////////////////
        backgroundNight = Bitmap.createBitmap(backgroundDaylight.getWidth(), backgroundDaylight.getHeight(), Bitmap.Config.ARGB_8888);
        ////////////////////////////////////////////////////////////////////////////////////////////
        Canvas canvasNight = new Canvas(backgroundNight);
        canvasNight.drawBitmap(backgroundDaylight, 0, 0, paintTintNight);
    }

    public void enter() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.enter()");

        //ADD PRODUCT TO entityManager (moving products from one scene to another).
        if ( (player.getHoldable() != null) && (player.getHoldable() instanceof Entity) ) {
            Entity holdableEntity = (Entity) player.getHoldable();
            entityManager.addEntity(holdableEntity);
        }

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

        ///////////////////////
        initBackgroundImages();
        ///////////////////////

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

        //REMOVE PRODUCT FROM entityManager (moving products from one scene to another).
        if ( (player.getHoldable() != null) && (player.getHoldable() instanceof Entity) ) {
            Entity holdableEntity = (Entity) player.getHoldable();
            entityManager.removeEntity(holdableEntity);
        }

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
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputButtonPad() a-button-justPressed");
            doButtonJustPressedA();
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputButtonPad() b-button-justPressed");
            doButtonJustPressedB();
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "Scene.getInputButtonPad() menu-button-justPressed");
            doButtonJustPressedMenu();
        }
    }

    protected void doButtonJustPressedA() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.doButtonJustPressedA()");

        Entity entityFacing = player.getEntityCurrentlyFacing();
        Tile tileFacing = player.getTileCurrentlyFacing();

        //@@@@@HOLDING@@@@@
        if (player.getHoldable() != null) {
            //DROP (ENTITY FACING IS null)
            if (entityFacing == null) {
                player.dropHoldable(tileFacing);
            }
        }
        //@@@@@NOT HOLDING@@@@@
        else {
            //PICK UP (ENTITY_FACING IS Holdable)
            if (entityFacing instanceof Holdable) {
                Holdable holdableEntity = (Holdable) entityFacing;

                //ProductEntity (only NON-BROKEN)
                if (holdableEntity instanceof ProductEntity) {
                    if (((ProductEntity)holdableEntity).getIsWhole()) {
                        player.setHoldable(holdableEntity);
                    }
                }
                //ALL OTHER KINDS OF Holdable
                else {
                    player.setHoldable(holdableEntity);
                }
            }
            //PICK UP (TILE_FACING IS FodderStashTile)
            else if (tileFacing instanceof FodderStashTile) {
                FodderStashTile fodderStashTile = (FodderStashTile) tileFacing;

                //Player has fodder
                if (player.getFodderQuantity() > 0) {
                    FodderEntity fodderEntity = fodderStashTile.generateFodderEntity();
                    gameCartridge.getSceneManager().getCurrentScene().getEntityManager().addEntity(fodderEntity);
                    player.decrementFodderQuantity();
                    player.setHoldable(fodderEntity);
                }
            }
            //INTERACT WITH SPECIFIC Entity SUBTYPES (ENTITY FACING IS Robot)
            else if (entityFacing instanceof Robot) {
                int robotStateIndex = ((Robot) entityFacing).getState().ordinal();

                //CHANGES robot's State
                robotStateIndex++;
                if (robotStateIndex >= Robot.State.values().length) {
                    robotStateIndex = 0;
                }

                ////////////////////////////////////////////////////////////////
                ((Robot) entityFacing).setState(Robot.State.values()[robotStateIndex]);
                ////////////////////////////////////////////////////////////////
            }
            //USE ITEM (can be ENTITY or NO ENTITY)
            // (e.g. CropEntity that needs watering) or (e.g. GroundGrowableTile that needs tilling)
            else {
                /////////////////////////////////////////////
                player.getSelectedItem().execute(tileFacing);
                /////////////////////////////////////////////
            }
        }
    }

    protected void doButtonJustPressedB() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.doButtonJustPressedB()");
        //TODO: temporary; to test TextboxState.
        ////////////////////////////////
        Object[] extra = new Object[10];
        extra[0] = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generosity crests before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fishes would like.";
        ////////////////////////////////
        gameCartridge.getStateManager().push(State.Id.TEXTBOX, extra);
    }

    protected void doButtonJustPressedMenu() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.doButtonJustPressedMenu()");
        gameCartridge.getStateManager().push(State.Id.START_MENU, null);
    }

    public void getInputSelectButton() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.getInputSelectButton()");

        //TODO: push unimplemented state (SystemNotebookState... date, time, stats of resources, etc).
    }

    public void getInputStartButton() {
        Log.d(MainActivity.DEBUG_TAG, "Scene.getInputStartButton()");

        //TODO: remove from base-Scene
        player.incrementIndexSelectedItem();
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
        switch (gameCartridge.getTimeManager().getModeOfDay()) {
            case DAYLIGHT:
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                canvas.drawBitmap(backgroundDaylight, rectOfClip, rectOfViewport, null);
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                break;
            case TWILIGHT:
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                canvas.drawBitmap(backgroundTwilight, rectOfClip, rectOfViewport, null);
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                break;
            case NIGHT:
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                canvas.drawBitmap(backgroundNight, rectOfClip, rectOfViewport, null);
                //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                break;
        }

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