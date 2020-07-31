package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.BedTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapHouseLevel01;

public class SceneHouseLevel01 extends Scene {

    private boolean hasLeftHouseToday;

    public SceneHouseLevel01(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        hasLeftHouseToday = true;

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

        hasLeftHouseToday = true;
        gameCartridge.getTimeManager().setIsPaused(false);
    }

    @Override
    protected void doButtonJustPressedA() {
        super.doButtonJustPressedA();

        Log.d(MainActivity.DEBUG_TAG, "SceneHouseLevel01.doButtonJustPressedA()");
        Tile tileFacing = player.getTileCurrentlyFacing();
        if (tileFacing instanceof BedTile) {
            ((BedTile) tileFacing).execute(gameCartridge, hasLeftHouseToday);
        }
    }

    public void setHasLeftHouseToday(boolean hasLeftHouseToday) {
        this.hasLeftHouseToday = hasLeftHouseToday;
    }

}