package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.scenes;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.tiles.TileMapPong;

public class ScenePong extends Scene {

    public ScenePong(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePong.getInputButtonPad() a-button-justPressed");


        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePong.getInputButtonPad() b-button-justPressed");


        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePong.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapPong(gameCartridge, sceneID);
    }

    @Override
    public void update(long elapsed) {
        //////////////////////////////
        entityManager.update(elapsed);
        //////////////////////////////

        //TODO:
    }

}