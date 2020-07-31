package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors;

import android.content.Intent;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.ComputerTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.GameConsoleTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.computer.ComputerActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.indoors.TileMapHome02;

public class SceneHome02 extends Scene {

    public static final int REQUEST_CODE_COMPUTER_ACTIVITY = 14;

    public SceneHome02(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 8;
        heightClipInTile = 8;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHome02(gameCartridge, sceneID);
    }

    @Override
    protected void doButtonJustPressedA() {
        Log.d(MainActivity.DEBUG_TAG, "SceneHome02.doButtonJustPressedA()");
        Tile tileFacing = player.getTileCurrentlyFacing();
        //GAME_CONSOLE
        if (tileFacing instanceof GameConsoleTile) {
            //TODO: PocketCrittersCartridge.sceneManager will use PoohFarmerCartridge's scene.
            ////////////////////////////////////////
            Object[] extra = new Object[10];
            extra[0] = player.getDirection();
            extra[1] = player.getMoveSpeed();
            sceneManager.push(Scene.Id.FARM, extra);
            ////////////////////////////////////////
        }
        //COMPUTER
        else if (tileFacing instanceof ComputerTile) {
            gameCartridge.savePresentState();
            Log.d(MainActivity.DEBUG_TAG, "SceneHome02.doButtonJustPressedA() saved present state");

            Log.d(MainActivity.DEBUG_TAG, "SceneHome02.doButtonJustPressedA() starting ComputerActivity for result...");
            Intent computerIntent = new Intent(context, ComputerActivity.class);
            ((JackInActivity) context).startActivityForResult(computerIntent, REQUEST_CODE_COMPUTER_ACTIVITY);
        }
    }

}