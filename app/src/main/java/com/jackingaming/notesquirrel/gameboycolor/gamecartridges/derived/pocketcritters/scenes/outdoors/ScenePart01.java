package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.outdoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.entities.stationary.FishermanOld;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.tiles.outdoors.worldmap.TileMapPart01;

public class ScenePart01 extends Scene {

    public ScenePart01(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 8;
        heightClipInTile = 8;

        int xTransferPointHome01 = 1040;
        int yTransferPointHome01 = (3248 - (104 * TileMap.TILE_HEIGHT));
        int xFisherman = xTransferPointHome01;
        int yFisherman = yTransferPointHome01 + (8 * TileMap.TILE_HEIGHT);
        entityManager.addEntity(new FishermanOld(gameCartridge, xFisherman, yFisherman));
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
    protected void doButtonJustPressedA() {
        Log.d(MainActivity.DEBUG_TAG, "ScenePart01.doButtonJustPressedA()");
        //intentionally blank.
    }

}