package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.outdoors;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.outdoors.worldmap.TileMapPart01;

public class ScenePart01 extends Scene {

    public ScenePart01(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 8;
        heightClipInTile = 8;
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        Log.d(MainActivity.DEBUG_TAG, "SceneFarm.init(GameCartridge, Player, GameCamera, SceneManager)");
        this.gameCartridge = gameCartridge;
        this.player = player;

        context = gameCartridge.getContext();
        inputManager = gameCartridge.getInputManager();
        this.sceneManager = sceneManager;

        /////////////////////////////////////////////////////////
        gameCartridge.getGameCamera().setWidthClipInPixel((GameCamera.CLIP_WIDTH_IN_TILE*TileMap.TILE_WIDTH));
        gameCartridge.getGameCamera().setHeightClipInPixel((GameCamera.CLIP_HEIGHT_IN_TILE*TileMap.TILE_HEIGHT));
        /////////////////////////////////////////////////////////

        initTileMap();
        initGameCamera(gameCamera);
        initEntityManager(player);

        //fixing bug... the game camera need to use the player's spawn
        //position (which is set after "initGameCamera(GameCamera)").
        gameCamera.update(0L);
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapPart01(gameCartridge, sceneID);
    }

    @Override
    public void getInputButtonPad() {
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePart01.getInputButtonPad() a-button-justPressed");

            //@@@@@TILES@@@@@
            Tile tileFacing = player.getTileCurrentlyFacing();

            //TODO: may not be needed (was intended for tiles at edge of TileMap)
            if (tileFacing == null) {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing is null");
                return;
            }

            Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + tileFacing.getClass().getSimpleName());
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
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePart01.getInputButtonPad() b-button-justPressed");

            //TODO: temporary; to test changing selected item (seeing result in HUD).
            ////////////////////////////////////
            player.incrementIndexSelectedItem();
            ////////////////////////////////////
//            //TODO: temporary; to test TextboxState.
//            ////////////////////////////////
//            Object[] extra = new Object[10];
//            extra[0] = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generosity crests before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fishes would like.";
//            ////////////////////////////////
//            gameCartridge.getStateManager().push(State.Id.TEXTBOX, extra);
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "ScenePart01.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

}