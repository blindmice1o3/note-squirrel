package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapHomeRival;

public class SceneHomeRival extends Scene {

    public SceneHomeRival(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 8;
        heightClipInTile = 8;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHomeRival(gameCartridge, sceneID);
    }

    @Override
    protected void doButtonJustPressedA() {
        Log.d(MainActivity.DEBUG_TAG, "SceneHomeRival.doButtonJustPressedA()");
        //intentionally blank.
    }

}