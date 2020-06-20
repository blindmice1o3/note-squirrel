package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.content.Intent;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapHouseLevel01;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class SceneHouseLevel01 extends Scene {

    public SceneHouseLevel01(Handler handler, Id sceneID) {
        super(handler, sceneID);
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHouseLevel01(handler, sceneID);
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


        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "GameState.getInputButtonPad() menu-button-justPressed");

            Intent fragmentParentDvdIntent = new Intent(context, ListFragmentDvdParentActivity.class);
            context.startActivity(fragmentParentDvdIntent);
        }
    }

}