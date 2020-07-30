package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.Product;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Holdable;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapHothouse;

public class SceneHothouse extends Scene {

    public SceneHothouse(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 9;
        heightClipInTile = 9;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHothouse(gameCartridge, sceneID);
    }

    @Override
    public void enter() {
        super.enter();

        gameCartridge.getTimeManager().setIsPaused(true);
    }

    @Override
    public void exit(Object[] extra) {
        super.exit(extra);

        gameCartridge.getTimeManager().setIsPaused(false);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() a-button-justPressed");

            //@@@@@ENTITIES@@@@@
            Entity entity = player.getEntityCurrentlyFacing();
            if ( (entity instanceof Holdable) && (player.getHoldable() == null) ) {
                Holdable holdableEntity = (Holdable) entity;
                player.setHoldable(holdableEntity);
                Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() player's holdable: " + player.getHoldable().toString());

                return;
            }

            //@@@@@TILES@@@@@
            Tile tileFacing = player.getTileCurrentlyFacing();
            if (tileFacing != null) {
                //@@@@@HOLDING@@@@@
                if (player.getHoldable() != null) {
                    player.dropHoldable(tileFacing);
                }
                //@@@@@ITEM/TOOL@@@@@
                else {
                    player.getSelectedItem().execute(tileFacing);
                }
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() b-button-justPressed");


        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}