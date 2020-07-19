package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapSeedsShop;

public class SceneSeedsShop extends Scene {

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    transient private Bitmap cursorImage;
    transient private Paint paintFont;
    private int x0, y0, x1, y1;

    public SceneSeedsShop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 10;
        heightClipInTile = 10;
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        super.init(gameCartridge, player, gameCamera, sceneManager);

        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCamera.getHeightClipInPixel();

        //text-area bounds
        x0 = 0;
        y0 = (int) (93 * heightPixelToViewportRatio);
        x1 = (int) (160 * widthPixelToViewportRatio);
        y1 = (int) ((93 + 67) * heightPixelToViewportRatio);

        cursorImage = cropCursorImage(gameCartridge.getContext().getResources());

        //Paint (FONT)
        paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.CYAN);
        paintFont.setAlpha(230);
        paintFont.setTextSize(40f);
        paintFont.setTypeface(Typeface.SANS_SERIF);
    }

    @Override
    public void initTileMap() {
        tileMap = new TileMapSeedsShop(gameCartridge, sceneID);
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
    public void getInputViewport() {
        //don't call super's getInputViewport()
        //TODO:
    }

    @Override
    public void getInputDirectionalPad() {
        //don't call super's getInputDirectionalPad()
        //TODO:
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
        //BACKGROUND IMAGE
        Rect rectOfClip = gameCamera.getRectOfClip();
        Rect rectOfViewport = gameCamera.getRectOfViewport();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(tileMap.getTexture(), rectOfClip, rectOfViewport, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //text-area (background panel)
        canvas.drawRect(x0, y0, x1, y1, paintFont);



        //TILES (currently every tile's image == null)
        tileMap.render(canvas);



        //ROW TO DISPLAY SHOP'S MENU OPTION (TALK, ITEM1, ITEM2, ITEM3, ITEM4, SPILL-OVER-ARROW)
        int x0Wares = (int)((0.5f * tileMap.getTileWidth()) * widthPixelToViewportRatio);
        int y0Wares = (int)(((4 * tileMap.getTileHeight()) * heightPixelToViewportRatio) + (1 * heightPixelToViewportRatio));
        int x1Wares = x0Wares + (int)((1 * tileMap.getTileWidth()) * widthPixelToViewportRatio);
        int y1Wares = y0Wares + (int)((1 * tileMap.getTileHeight()) * heightPixelToViewportRatio);
        //CURSOR FOR MENU-ITEM1
        int x0Current = (int)(x0Wares - (4 * widthPixelToViewportRatio));
        int x1Current = (int)(x1Wares + (4 * widthPixelToViewportRatio));
        for (int i = 0; i < 5; i++) {
            Rect rectOfCursorImage = new Rect(0, 0, cursorImage.getWidth(), cursorImage.getHeight());
            Rect rectOfCursorImageOnScreen = new Rect(
                    x0Current,
                    (int)(y0Wares - (4 * heightPixelToViewportRatio)),
                    x1Current,
                    (int)(y1Wares + (4 * heightPixelToViewportRatio)) );
            canvas.drawBitmap(cursorImage, rectOfCursorImage, rectOfCursorImageOnScreen, null);
            x0Current += (x1Wares - x0Wares + (8 * widthPixelToViewportRatio) + (4 * widthPixelToViewportRatio));
            x1Current += (x1Wares - x0Wares + (8 * widthPixelToViewportRatio) + (4 * widthPixelToViewportRatio));
        }
        //MENU-ITEM1
        canvas.drawRect(x0Wares, y0Wares, x1Wares, y1Wares, paintFont);



        //ENTITIES
//        entityManager.render(canvas);

        //PRODUCTS
        productManager.render(canvas);
    }

    private Bitmap cropCursorImage(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        Bitmap cursorImage = Bitmap.createBitmap(seedsShopSpriteSheet, 5, 150, 24, 24);
        return cursorImage;
    }

}