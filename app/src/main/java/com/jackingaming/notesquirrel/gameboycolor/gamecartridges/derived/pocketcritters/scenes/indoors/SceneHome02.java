package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
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
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHome02.getInputButtonPad() a-button-justPressed");

            //@@@@@TILES@@@@@
            Tile tileFacing = player.getTileCurrentlyFacing();
            if (tileFacing == null) {
                return;
            } else if (tileFacing instanceof GameConsoleTile) {
                //TODO: PocketCrittersCartridge.sceneManager will use PoohFarmerCartridge's scene.
                ////////////////////////////////////////
                Object[] extra = new Object[10];
                extra[0] = player.getDirection();
                extra[1] = player.getMoveSpeed();
                sceneManager.push(Scene.Id.FARM, extra);
                ////////////////////////////////////////
            } else if (tileFacing instanceof ComputerTile) {
                gameCartridge.savePresentState();
                Log.d(MainActivity.DEBUG_TAG, "SceneHome02.getInputButtonPad() saved present state");

                Log.d(MainActivity.DEBUG_TAG, "SceneHome02.getInputButtonPad() starting ComputerActivity for result...");
                Intent computerIntent = new Intent(context, ComputerActivity.class);
                ((JackInActivity) context).startActivityForResult(computerIntent, REQUEST_CODE_COMPUTER_ACTIVITY);
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHome02.getInputButtonPad() b-button-justPressed");

            //TODO: temporary; to test TextboxState.
            ////////////////////////////////
            Object[] extra = new Object[10];
            extra[0] = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generosity crests before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fishes would like.";
            ////////////////////////////////
            gameCartridge.getStateManager().push(State.Id.TEXTBOX, extra);
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHome02.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}