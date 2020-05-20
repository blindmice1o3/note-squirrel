package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class PocketCrittersCartridge
        implements GameCartridge {

    public enum State { GAME, START_MENU; }

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Id idGameCartridge;
    private State state;

    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;

    public PocketCrittersCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
        state = State.GAME;
    }

    @Override
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.holder = holder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        Assets.init(context);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Handler handler = new Handler(this);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        gameCamera = new GameCamera(handler);
        player = new Player(handler);
        sceneManager = new SceneManager(handler);
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.savePresentState()");
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.loadSavedState()");
    }

    @Override
    public void getInputViewport() {
        if (inputManager.isJustPressedViewport()) {
            switch (state) {
                case GAME:
                    //left
                    if (inputManager.isLeftViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.GAME left-button-justPressed");
                        player.move(Player.Direction.LEFT);
                    }
                    //right
                    else if (inputManager.isRightViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.GAME right-button-justPressed");
                        player.move(Player.Direction.RIGHT);
                    }
                    //up
                    else if (inputManager.isUpViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.GAME up-button-justPressed");
                        player.move(Player.Direction.UP);
                    }
                    //down
                    else if (inputManager.isDownViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.GAME down-button-justPressed");
                        player.move(Player.Direction.DOWN);
                    }
                    break;
                case START_MENU:
                    //left
                    if (inputManager.isLeftViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU left-button-justPressed");
                    }
                    //right
                    else if (inputManager.isRightViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU right-button-justPressed");
                    }
                    //up
                    else if (inputManager.isUpViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU up-button-justPressed");
                    }
                    //down
                    else if (inputManager.isDownViewport()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputViewport() state == State.START_MENU down-button-justPressed");
                    }
                    break;
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            switch (state) {
                case GAME:
                    //up
                    if (inputManager.isUpDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.GAME up-button-pressing");
                        player.move(Player.Direction.UP);
                    }
                    //down
                    else if (inputManager.isDownDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.GAME down-button-pressing");
                        player.move(Player.Direction.DOWN);
                    }
                    //left
                    else if (inputManager.isLeftDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.GAME left-button-pressing");
                        player.move(Player.Direction.LEFT);
                    }
                    //right
                    else if (inputManager.isRightDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.GAME right-button-pressing");
                        player.move(Player.Direction.RIGHT);
                    }
                    break;
                case START_MENU:
                    //up
                    if (inputManager.isUpDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU up-button-pressing");
                    }
                    //down
                    else if (inputManager.isDownDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU down-button-pressing");
                    }
                    //left
                    else if (inputManager.isLeftDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU left-button-pressing");
                    }
                    //right
                    else if (inputManager.isRightDirectionalPad()) {
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputDirectionalPad() state == State.START_MENU right-button-pressing");
                    }
                    break;
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isJustPressedButtonPad()) {
            //menu button (will toggle between State.GAME and State.START_MENU)
            if (inputManager.isMenuButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "menu-button");

                state = (state == State.GAME) ? (State.START_MENU) : (State.GAME);
            }
            //a button
            else if (inputManager.isaButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "a-button");

                switch (state) {
                    case GAME:
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.GAME a-button-justPressed");
                        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        //TILES
                        TileMap.TileType tileFacing = player.getTileTypeCurrentlyFacing();  //currently only using for pocket_critters

                        if (tileFacing == null) {
                            Log.d(MainActivity.DEBUG_TAG, "tileFacing is null");
                            return;
                        }
                        //TODO: if (player.getTileTypeCurrentlyFacing() == TileType.GAME_CONSOLE),
                        // change game cartridge or change scene or start new Activity???
                        else if (tileFacing == TileMap.TileType.GAME_CONSOLE) {
                            Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + tileFacing.name());

                            final String message = "GaMeCoNsOlE tile.";
                            /////////////////////////////////////////////////////////////////////////////////
                            ((JackInActivity)context).runOnUiThread(new Runnable() {
                                public void run() {
                                    final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                            });
                            /////////////////////////////////////////////////////////////////////////////////


                            //TODO: PocketCrittersCartridge.sceneManager will use PoohFarmerCartridge's scene.
                            ////////////////////////////////////////
                            Object[] extra = new Object[10];
                            extra[0] = player.getDirection();
                            extra[1] = player.getMoveSpeed();
                            sceneManager.push(Scene.Id.FARM, extra);
                            ////////////////////////////////////////
                        } else {
                            Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + tileFacing.name());

                            final String message = tileFacing.name();
                            /////////////////////////////////////////////////////////////////////////////////
                            ((JackInActivity)context).runOnUiThread(new Runnable() {
                                public void run() {
                                    final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toast.show();
                                }
                            });
                            /////////////////////////////////////////////////////////////////////////////////
                        }

                        //ENTITIES
                        Entity entity = player.getEntityCurrentlyFacing();
                        //CHANGES robot's State (cycles incrementally)
                        if(entity instanceof Robot) {
                            int robotStateIndex = ((Robot)entity).getState().ordinal();

                            robotStateIndex++;

                            if (robotStateIndex >= Robot.State.values().length) {
                                robotStateIndex = 0;
                            }

                            ////////////////////////////////////////////////////////////////
                            ((Robot)entity).setState(Robot.State.values()[robotStateIndex]);
                            ////////////////////////////////////////////////////////////////
                        }
                        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        break;
                    case START_MENU:
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU a-button-justPressed");
                        break;
                }
            }
            //b button
            else if (inputManager.isbButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "b-button");

                switch (state) {
                    case GAME:
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.GAME b-button-justPressed");
                        if (sceneManager.getCurrentScene().getSceneID() == Scene.Id.FARM) {
                            Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() b-button: POP PoohFarmerCartridge's scene!!!");

                            ////////////////////////////////
                            Object[] extra = new Object[10];
                            extra[0] = player.getDirection();
                            extra[1] = player.getMoveSpeed();
                            sceneManager.pop(extra);
                            ////////////////////////////////
                        }
                        break;
                    case START_MENU:
                        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() state == State.START_MENU b-button-justPressed");
                        break;
                }
            }
        }
    }

    @Override
    public void update(long elapsed) {
        ////////////////////////////////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        ////////////////////////////////////////////////////

        switch (state) {
            case GAME:
                sceneManager.getCurrentScene().update(elapsed);
                break;
            case START_MENU:
                //TODO:
                break;
        }

    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            switch (state) {
                case GAME:
                    sceneManager.getCurrentScene().render(canvas);
                    break;
                case START_MENU:
                    //TODO: 2020_05_19 PocketCrittersCartridge.render(Canvas) state == State.START_MENU

                    //RE-DRAW state.GAME as background (otherwise white background because we'd cleared earlier)
                    sceneManager.getCurrentScene().render(canvas);

                    //DRAW BACKGROUND
                    Bitmap startMenuState = Assets.cropStartMenuState(context.getResources());
                    int scaleFactor = 6;

                    Rect srcBoundsBackground = new Rect(0,
                            0,
                            startMenuState.getWidth(),
                            startMenuState.getHeight());
                    Rect dstBoundsBackground = new Rect( (widthViewport-(scaleFactor*startMenuState.getWidth())),
                            ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2))),
                            widthViewport,
                            (heightViewport/2)+(scaleFactor*(startMenuState.getHeight()/2)) );
                    canvas.drawBitmap(startMenuState, srcBoundsBackground, dstBoundsBackground, null);

                    //DRAW CURSOR
                    Bitmap startMenuStateCursor = Assets.cropStartMenuStateCursor(context.getResources());

                    Rect srcBoundsCursor = new Rect(0,
                            0,
                            startMenuStateCursor.getWidth(),
                            startMenuStateCursor.getHeight());
                    int xOffset = 5 * scaleFactor;
                    int yOffset = 13 * scaleFactor;
                    Rect dstBoundsCursor = new Rect( (widthViewport-(scaleFactor*startMenuState.getWidth()) + xOffset),
                            ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2)) + yOffset),
                            (widthViewport-(scaleFactor*startMenuState.getWidth()) + xOffset) + (scaleFactor*startMenuStateCursor.getWidth()),
                            ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2)) + yOffset) + (scaleFactor*startMenuStateCursor.getHeight()) );
                    canvas.drawBitmap(startMenuStateCursor, srcBoundsCursor, dstBoundsCursor, null);

                    //TODO: DRAW SECOND CURSOR (temporary to figure out yOffset).
                    //////////////////////////////
                    yOffset += (16 * scaleFactor);
                    //////////////////////////////
                    Rect dstBoundsCursor2 = new Rect( (widthViewport-(scaleFactor*startMenuState.getWidth()) + xOffset),
                            ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2)) + yOffset),
                            (widthViewport-(scaleFactor*startMenuState.getWidth()) + xOffset) + (scaleFactor*startMenuStateCursor.getWidth()),
                            ((heightViewport/2)-(scaleFactor*(startMenuState.getHeight()/2)) + yOffset) + (scaleFactor*startMenuStateCursor.getHeight()) );
                    canvas.drawBitmap(startMenuStateCursor, srcBoundsCursor, dstBoundsCursor2, null);
                    break;
            }
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        return idGameCartridge;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public GameCamera getGameCamera() {
        return gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }

}