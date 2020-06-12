package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.computer.ComputerActivity;
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
import com.jackingaming.notesquirrel.television.TelevisionActivity;

public class GameState
        implements State {

    public static final int REQUEST_CODE_TELEVISION_ACTIVITY = 13;
    public static final int REQUEST_CODE_COMPUTER_ACTIVITY = 14;

    private Handler handler;
    private Id id;

    private Context context;
    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthViewport;
    private int heightViewport;

    private Player player;
    private SceneManager sceneManager;

    public GameState(Handler handler) {
        this.handler = handler;
        id = Id.GAME;

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
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() a-button-justPressed");

            //@@@@@TILES@@@@@
            TileMap.TileType tileFacing = player.getTileTypeCurrentlyFacing();  //currently only using for pocket_critters

            //TODO: may not be needed (was intended for tiles at edge of TileMap)
            if (tileFacing == null) {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing is null");
                return;
            }

            Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + tileFacing.name());
            //TODO: change game cartridge or change scene or start new Activity???
            if (tileFacing == TileMap.TileType.GAME_CONSOLE) {
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
            } else if (tileFacing == TileMap.TileType.TELEVISION) {
                handler.getGameCartridge().savePresentState();
                Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() saved present state");

                Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() starting TelevisionActivity for result...");
                Intent televisionIntent = new Intent(context, TelevisionActivity.class);
                ((JackInActivity)context).startActivityForResult(televisionIntent, REQUEST_CODE_TELEVISION_ACTIVITY);
            } else if (tileFacing == TileMap.TileType.COMPUTER) {
                handler.getGameCartridge().savePresentState();
                Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() saved present state");

                Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() starting ComputerActivity for result...");
                Intent computerIntent = new Intent(context, ComputerActivity.class);
                ((JackInActivity)context).startActivityForResult(computerIntent, REQUEST_CODE_COMPUTER_ACTIVITY);
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

            //@@@@@ENTITIES@@@@@
            Entity entity = player.getEntityCurrentlyFacing();
            //CHANGES robot's State (cycles incrementally)
            if (entity instanceof Robot) {
                int robotStateIndex = ((Robot)entity).getState().ordinal();

                robotStateIndex++;

                if (robotStateIndex >= Robot.State.values().length) {
                    robotStateIndex = 0;
                }

                ////////////////////////////////////////////////////////////////
                ((Robot)entity).setState(Robot.State.values()[robotStateIndex]);
                ////////////////////////////////////////////////////////////////
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() b-button-justPressed");

            if (sceneManager.getCurrentScene().getSceneID() == Scene.Id.FARM) {
                Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() b-button: POP PoohFarmerCartridge's scene!!!");

                ////////////////////////////////
                Object[] extra = new Object[10];
                extra[0] = player.getDirection();
                extra[1] = player.getMoveSpeed();
                sceneManager.pop(extra);
                ////////////////////////////////
            }
            //TODO: temporary; to test TextboxState.
            else {
                ////////////////////////////////
                Object[] extra = new Object[10];
                extra[0] = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generosity crests before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fishes would like.";
                ////////////////////////////////
                ((PocketCrittersCartridge) handler.getGameCartridge()).getStateManager().push(Id.TEXTBOX, extra);
            }
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() menu-button-justPressed");

            ((PocketCrittersCartridge)handler.getGameCartridge()).getStateManager().push(Id.START_MENU, null);
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
        Log.d(MainActivity.DEBUG_TAG, "GameState.enter(Object[]) State.Id: " + id);
    }

    @Override
    public void exit() {

    }

    @Override
    public Id getId() {
        return id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}