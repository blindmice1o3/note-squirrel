package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.BedTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapHouseLevel01;

public class SceneHouseLevel01 extends Scene {

    public SceneHouseLevel01(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 9;
        heightClipInTile = 9;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapHouseLevel01(gameCartridge, sceneID);
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
            Log.d(MainActivity.DEBUG_TAG, "SceneHouseLevel01.getInputButtonPad() a-button-justPressed");

            //@@@@@TILES@@@@@
            Tile tileFacing = player.getTileCurrentlyFacing();
            if (tileFacing == null) {
                return;
            } else if (tileFacing instanceof BedTile) {
                //TODO: sleep, TimeManager increment day, check all GrowableTile for isWatered,
                // if GrowableTile has state as SEEDED -> instantiate CropEntity/GrassEntity (revert state to INITIAL)
                // if GrowableGroundTile.cropEntity is not null -> increment that CropEntity's age and update its stage.
                ((BedTile)tileFacing).execute(gameCartridge);
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHouseLevel01.getInputButtonPad() b-button-justPressed");


        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHouseLevel01.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}