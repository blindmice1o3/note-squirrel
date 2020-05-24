package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class GameState
        implements State {

    private Handler handler;

    private Context context;
    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthViewport;
    private int heightViewport;

    private Player player;
    private SceneManager sceneManager;

    public GameState(Handler handler) {
        this.handler = handler;

        context = handler.getGameCartridge().getContext();
        holder = ((PocketCrittersCartridge)handler.getGameCartridge()).getHolder();
        inputManager = ((PocketCrittersCartridge)handler.getGameCartridge()).getInputManager();
        widthViewport = handler.getGameCartridge().getWidthViewport();
        heightViewport = handler.getGameCartridge().getHeightViewport();
        player = handler.getGameCartridge().getPlayer();
        sceneManager = handler.getGameCartridge().getSceneManager();
    }

    @Override
    public void getInputViewport() {
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
    }

    @Override
    public void getInputDirectionalPad() {
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
    }

    @Override
    public void getInputButtonPad() {
        //menu button (will toggle between State.GAME and State.START_MENU)
        if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "menu-button");

            ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().push(Id.START_MENU, null);
        }
        //a button
        else if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "a-button");

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
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "b-button");

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
        }
    }

    @Override
    public void update(long elapsed) {
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
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}