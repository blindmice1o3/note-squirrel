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
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHome01.getInputButtonPad() a-button-justPressed");

            //@@@@@TILES@@@@@
            Tile tileFacing = player.getTileCurrentlyFacing();

            //TODO: may not be needed (was intended for tiles at edge of TileMap)
            if (tileFacing == null) {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing is null");
                return;
            }

            Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + tileFacing.getClass());
            //TODO: change game cartridge or change scene or start new Activity???
            if (tileFacing instanceof TelevisionTile) {
                gameCartridge.savePresentState();
                Log.d(MainActivity.DEBUG_TAG, "SceneHome01.getInputButtonPad() saved present state");

                Log.d(MainActivity.DEBUG_TAG, "SceneHome01.getInputButtonPad() starting TelevisionActivity for result...");
                Intent televisionIntent = new Intent(context, TelevisionActivity.class);
                ((JackInActivity) context).startActivityForResult(televisionIntent, REQUEST_CODE_TELEVISION_ACTIVITY);
            } else {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + tileFacing.getClass());

                final String message = tileFacing.getClass().getSimpleName();
                /////////////////////////////////////////////////////////////////////////////////
                ((JackInActivity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                });
                /////////////////////////////////////////////////////////////////////////////////
            }
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHome01.getInputButtonPad() b-button-justPressed");

            //TODO: temporary; to test TextboxState.
            ////////////////////////////////
            Object[] extra = new Object[10];
            extra[0] = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generosity crests before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fishes would like.";
            ////////////////////////////////
            gameCartridge.getStateManager().push(State.Id.TEXTBOX, extra);
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneHome01.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}