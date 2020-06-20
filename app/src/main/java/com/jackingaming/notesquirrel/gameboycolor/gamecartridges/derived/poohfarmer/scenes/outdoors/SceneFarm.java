package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.outdoors;

import android.content.Intent;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.outdoors.TileMapFarm;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class SceneFarm extends Scene {

    public SceneFarm(Handler handler, Id sceneID) {
        super(handler, sceneID);
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapFarm(handler, sceneID);
    }

    @Override
    public void initEntityManager(Player player) {
        super.initEntityManager(player);

        Entity robot = new Robot(handler, (7 * tileMap.getTileWidth()), (5 * tileMap.getTileHeight()));
        entityManager.addEntity(robot);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() a-button-justPressed");

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
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() b-button-justPressed");

            if (handler.getGameCartridge().getIdGameCartridge() == GameCartridge.Id.POCKET_CRITTERS) {
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
                handler.getGameCartridge().getStateManager().push(State.Id.TEXTBOX, extra);
            }
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() menu-button-justPressed");

            Intent fragmentParentDvdIntent = new Intent(context, ListFragmentDvdParentActivity.class);
            context.startActivity(fragmentParentDvdIntent);
        }
    }

}