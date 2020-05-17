package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
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

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Id idGameCartridge;
    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;

    public PocketCrittersCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
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
            //left
            if (inputManager.isLeftViewport()) {
                player.move(Player.Direction.LEFT);
            }
            //right
            else if (inputManager.isRightViewport()) {
                player.move(Player.Direction.RIGHT);
            }
            //up
            else if (inputManager.isUpViewport()) {
                player.move(Player.Direction.UP);
            }
            //down
            else if (inputManager.isDownViewport()) {
                player.move(Player.Direction.DOWN);
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            //up
            if (inputManager.isUpDirectionalPad()) {
                player.move(Player.Direction.UP);
            }
            //down
            else if (inputManager.isDownDirectionalPad()) {
                player.move(Player.Direction.DOWN);
            }
            //left
            else if (inputManager.isLeftDirectionalPad()) {
                player.move(Player.Direction.LEFT);
            }
            //right
            else if (inputManager.isRightDirectionalPad()) {
                player.move(Player.Direction.RIGHT);
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isJustPressedButtonPad()) {
            //menu button (will launch ListFragmentDvdParentActivity)
            if (inputManager.isMenuButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "menu-button");
                Intent fragmentParentDvdIntent = new Intent(context, ListFragmentDvdParentActivity.class);
                context.startActivity(fragmentParentDvdIntent);
            }
            //a button
            else if (inputManager.isaButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "a-button");

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
            }
            //b button
            else if (inputManager.isbButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "b-button");

                if (sceneManager.getCurrentScene().getSceneID() == Scene.Id.FARM) {
                    Log.d(MainActivity.DEBUG_TAG, "PocketCrittersCartridge.getInputButtonPad() b-button: POP PoohFarmerCartridge's scene!!!");

                    ////////////////////////////////
                    Object[] extra = new Object[10];
                    extra[0] = player.getDirection();
                    extra[1] = player.getMoveSpeed();
                    sceneManager.pop(extra);
                    ////////////////////////////////
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

        sceneManager.getCurrentScene().update(elapsed);
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
            sceneManager.getCurrentScene().render(canvas);
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