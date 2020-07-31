package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.outdoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.BushEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.RockEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.moveable.Robot;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.outdoors.TileMapFarm;

public class SceneFarm extends Scene {

    public SceneFarm(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);
        Log.d(MainActivity.DEBUG_TAG, "SceneFarm(GameCartridge, Id) constructor");

        widthClipInTile = 9;
        heightClipInTile = 9;

        Entity robot = new Robot(gameCartridge, (7 * TileMap.TILE_WIDTH), (5 * TileMap.TILE_HEIGHT));
        entityManager.addEntity(robot);

        Entity rock = new RockEntity(gameCartridge, (7 * TileMap.TILE_WIDTH), (6 * TileMap.TILE_HEIGHT));
        entityManager.addEntity(rock);
        Entity bush = new BushEntity(gameCartridge, (8 * TileMap.TILE_WIDTH), (6 * TileMap.TILE_HEIGHT));
        entityManager.addEntity(bush);
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapFarm(gameCartridge, sceneID);
    }

    @Override
    protected void doButtonJustPressedB() {
        Log.d(MainActivity.DEBUG_TAG, "SceneFarm.doButtonJustPressedB()");
        if (gameCartridge.getIdGameCartridge() == GameCartridge.Id.POCKET_CRITTERS) {
            Log.d(MainActivity.DEBUG_TAG, "SceneFarm.doButtonJustPressedB(): POP PocketCritterCartridge's SceneFarm!!!");

            //!!!!! PREVENTS BRINGING-BACK Holdable to PocketCritterCartridge !!!!!
            if (player.getHoldable() == null) {
                ////////////////////////////////
                Object[] extra = new Object[10];
                extra[0] = player.getDirection();
                extra[1] = player.getMoveSpeed();
                sceneManager.pop(extra);
                ////////////////////////////////s
            }
        }
        //TODO: temporary; to test TextboxState.
        else {
            ////////////////////////////////
            Object[] extra = new Object[10];
            extra[0] = "The cat in the hat will never give a fish what it desires most, the key to the city of moonlight. This is true for fall, winter, and spring... but NOT summer. In the summer, the fashionable feline's generosity crests before breaking into a surge of outward actions which benefit the entire animal community, far more than just that of fishes who desire the key to the city of moonlight. Unfortunately, summer passes quicker than most fishes would like.";
            ////////////////////////////////
            gameCartridge.getStateManager().push(State.Id.TEXTBOX, extra);
        }
    }

}