package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
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
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() a-button-justPressed");


        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() b-button-justPressed");

            //TODO: temporary; to test toggling background of TileMapHothouse.
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() b-button-justPressed Scene.Id.HOUTHOUSE");
            ////////////////////////////////////////////////////////////////////////////////////
            ((TileMapHothouse) sceneManager.getCurrentScene().getTileMap()).toggleIsFull(context.getResources());
            ////////////////////////////////////////////////////////////////////////////////////
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHothouse.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}