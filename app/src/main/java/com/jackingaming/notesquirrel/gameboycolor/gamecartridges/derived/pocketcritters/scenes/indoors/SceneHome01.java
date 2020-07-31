package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors;

import android.content.Intent;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.TelevisionTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.TelevisionActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapHome01;

public class SceneHome01 extends Scene {

    public static final int REQUEST_CODE_TELEVISION_ACTIVITY = 13;

    public SceneHome01(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 8;
        heightClipInTile = 8;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHome01(gameCartridge, sceneID);
    }

    @Override
    protected void doButtonJustPressedA() {
        Log.d(MainActivity.DEBUG_TAG, "SceneHome01.doButtonJustPressedA()");
        Tile tileFacing = player.getTileCurrentlyFacing();
        //TELEVISION
        if (tileFacing instanceof TelevisionTile) {
            gameCartridge.savePresentState();
            Log.d(MainActivity.DEBUG_TAG, "SceneHome01.doButtonJustPressedA() saved present state");

            Log.d(MainActivity.DEBUG_TAG, "SceneHome01.doButtonJustPressedA() starting TelevisionActivity for result...");
            Intent televisionIntent = new Intent(context, TelevisionActivity.class);
            ((JackInActivity) context).startActivityForResult(televisionIntent, REQUEST_CODE_TELEVISION_ACTIVITY);
        }
    }

}