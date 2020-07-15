package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapSeedsShop;

public class SceneSeedsShop extends Scene {

    public SceneSeedsShop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 10;
        heightClipInTile = 10;
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapSeedsShop(gameCartridge, sceneID);
    }

    @Override
    public void getInputButtonPad() {
        //don't call super's getInputButtonPad()
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneSeedsShop.getInputButtonPad() a-button-justPressed");
            //TODO:
        }
        //b button
        else if (inputManager.isbButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneSeedsShop.getInputButtonPad() b-button-justPressed");

            Object[] directionFacing = { gameCartridge.getPlayer().getDirection(),
                    gameCartridge.getPlayer().getMoveSpeed() };
            //////////////////////////////////
            sceneManager.pop(directionFacing);
            //////////////////////////////////
        }
        //menu button (push State.START_MENU)
        else if (inputManager.isMenuButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneSeedsShop.getInputButtonPad() menu-button-justPressed");

            gameCartridge.getStateManager().push(State.Id.START_MENU, null);
        }
    }

    @Override
    public void render(Canvas canvas) {
        //don't call super's render(Canvas)
        //BACKGROUND
        Rect rectOfClip = gameCamera.getRectOfClip();
        Rect rectOfViewport = gameCamera.getRectOfViewport();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(tileMap.getTexture(), rectOfClip, rectOfViewport, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //TILES
        tileMap.render(canvas);

        //ENTITIES
//        entityManager.render(canvas);

        //PRODUCTS
        productManager.render(canvas);
    }

}