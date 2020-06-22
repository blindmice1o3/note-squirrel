package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.content.Intent;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapHothouse;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class SceneHothouse extends Scene {

    public SceneHothouse(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHothouse(gameCartridge, sceneID);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() a-button-justPressed");


        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() b-button-justPressed");

            //TODO: temporary; to test toggling background of TileMapHothouse.
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() b-button-justPressed Scene.Id.HOUTHOUSE");
            ////////////////////////////////////////////////////////////////////////////////////
            ((TileMapHothouse) sceneManager.getCurrentScene().getTileMap()).toggleIsFull(context.getResources());
            ////////////////////////////////////////////////////////////////////////////////////
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() menu-button-justPressed");

            Intent fragmentParentDvdIntent = new Intent(context, ListFragmentDvdParentActivity.class);
            context.startActivity(fragmentParentDvdIntent);
        }
    }

}