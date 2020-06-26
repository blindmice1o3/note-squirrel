package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.outdoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.IGameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.outdoors.TileMapFarm;

public class SceneFarm extends Scene {

    public SceneFarm(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);
        Log.d(MainActivity.DEBUG_TAG, "SceneFarm(GameCartridge, Id) constructor");
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        Log.d(MainActivity.DEBUG_TAG, "SceneFarm.init(GameCartridge, Player, GameCamera, SceneManager)");
        this.gameCartridge = gameCartridge;
        this.player = player;

        context = gameCartridge.getContext();
        inputManager = gameCartridge.getInputManager();
        this.sceneManager = sceneManager;

        /////////////////////////////////////////////////////////
        gameCartridge.getGameCamera().setWidthClipInPixel((9*TileMap.TILE_WIDTH));
        gameCartridge.getGameCamera().setHeightClipInPixel((9*TileMap.TILE_HEIGHT));
        /////////////////////////////////////////////////////////

        initTileMap();
        initGameCamera(gameCamera);
        initEntityManager(player);

        //fixing bug... the game camera need to use the player's spawn
        //position (which is set after "initGameCamera(GameCamera)").
        gameCamera.update(0L);
    }

    @Override
    public void exit(Object[] extra) {
        super.exit(extra);

        //SOMETIMES POPPING OFF POOH_FARMER's SceneFarm TO GO BACK TO POCKET_CRITTERS's SceneHome02.
        if (gameCartridge.getIdGameCartridge() != IGameCartridge.Id.POOH_FARMER) {
            /////////////////////////////////////////////////////////
            gameCartridge.getGameCamera().setWidthClipInPixel((GameCamera.CLIP_WIDTH_IN_TILE*TileMap.TILE_WIDTH));
            gameCartridge.getGameCamera().setHeightClipInPixel((GameCamera.CLIP_HEIGHT_IN_TILE*TileMap.TILE_HEIGHT));
            /////////////////////////////////////////////////////////
        }
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapFarm(gameCartridge, sceneID);
    }

    @Override
    public void initEntityManager(Player player) {
        super.initEntityManager(player);

        Entity robot = new Robot(gameCartridge, (7 * tileMap.getTileWidth()), (5 * tileMap.getTileHeight()));
        entityManager.addEntity(robot);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFarm.getInputButtonPad() a-button-justPressed");

            //TODO:
            //@@@@@TILES@@@@@
            player.getTileTypeCurrentlyFacing();   //currently only using for pocket_critters

            //@@@@@ENTITIES@@@@@
            Entity entity = player.getEntityCurrentlyFacing();
            //CHANGES robot's State (cycles incrementally)
            if (entity instanceof Robot) {
                int robotStateIndex = ((Robot) entity).getState().ordinal();

                robotStateIndex++;

                if (robotStateIndex >= Robot.State.values().length) {
                    robotStateIndex = 0;
                }

                ////////////////////////////////////////////////////////////////
                ((Robot) entity).setState(Robot.State.values()[robotStateIndex]);
                ////////////////////////////////////////////////////////////////
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFarm.getInputButtonPad() b-button-justPressed");

            if (gameCartridge.getIdGameCartridge() == GameCartridge.Id.POCKET_CRITTERS) {
                Log.d(MainActivity.DEBUG_TAG, "SceneFarm.getInputButtonPad() b-button: POP PoohFarmerCartridge's scene!!!");

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
                gameCartridge.getStateManager().push(State.Id.TEXTBOX, extra);
            }
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFarm.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}