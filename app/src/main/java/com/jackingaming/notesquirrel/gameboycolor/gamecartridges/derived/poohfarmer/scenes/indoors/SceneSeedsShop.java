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
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.CropSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.FlowerSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.GlovedHandsItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.ScissorsItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.TextboxState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.tiles.indoors.TileMapSeedsShop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SceneSeedsShop extends Scene {

    private static final int NUMBER_OF_MENU_ITEM_HOLDERS = 5;

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private List<Item> inventory;
    private int indexFirstVisibleItem;
    private MenuItemHolder[] menuItemHolders;
    private int indexMenuItemHolders;
    transient private Bitmap cursorImage;

    transient private Paint paintFont;
    private int x0, y0, x1, y1;

    public SceneSeedsShop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 10;
        heightClipInTile = 10;

        initInventory();
        indexFirstVisibleItem = 0;
        initMenuItemHolders();
        indexMenuItemHolders = 0;
    }

    public void initInventory() {
        inventory = new ArrayList<Item>();
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.TURNIP));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.POTATO));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.GRASS));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.GERANIUM));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.SAGE));
        inventory.add(new GlovedHandsItem(gameCartridge));
        inventory.add(new ScissorsItem(gameCartridge));
    }

    private void updateMenuItemHolders() {

    }

    private List<MenuItemHolder.Id> initTemplateFirstPage() {
        List<MenuItemHolder.Id> templateFirstPage = new ArrayList<MenuItemHolder.Id>();
        templateFirstPage.add(MenuItemHolder.Id.TALK);
        templateFirstPage.add(MenuItemHolder.Id.SEED_CROP1);
        templateFirstPage.add(MenuItemHolder.Id.SEED_GRASS);
        templateFirstPage.add(MenuItemHolder.Id.SEED_FLOWER1);
        templateFirstPage.add(MenuItemHolder.Id.SPILL_OVER);

        //[0] WILL ALWAYS HAVE MenuItemHolder.Id.TALK
        //[1] CAN THERE EVER BE AN EMPTY INVENTORY FOR SHOP?
        //[2]
        //[3] SOMETIMES exist, SOMETIMES does NOT exist
        //[4] SOMETIMES MenuItemHolder.Id.SPILL_OVER, SOMETIMES MenuItemHolder.Id.EXIT

        return templateFirstPage;
    }

    private void templateLastPage() {
        //[0]
        //[1]
        //[2]
        //[3]
        //[4] WILL ALWAYS HAVE MenuItemHolder.Id.EXIT
    }

    private void templateMiddlePages() {

    }

    public void setTemplate(List<MenuItemHolder.Id> template) {
        for (int i = 0; i < menuItemHolders.length; i++) {
            menuItemHolders[i].setId(template.get(i));
            //if (template.get(i) == MenuItemHolder.Id.BUY) {
            //    menuItemHolders[i].updateDataSource();    //image, description, etc.
            //}
        }
    }

    private void initMenuItemHolders() {
        menuItemHolders = new MenuItemHolder[5];
        menuItemHolders[0] = new MenuItemHolder(gameCartridge, MenuItemHolder.Id.TALK, true);
        menuItemHolders[1] = new MenuItemHolder(gameCartridge, MenuItemHolder.Id.SEED_CROP1, true);
        menuItemHolders[2] = new MenuItemHolder(gameCartridge, MenuItemHolder.Id.SEED_GRASS, true);
        menuItemHolders[3] = new MenuItemHolder(gameCartridge, MenuItemHolder.Id.SEED_FLOWER1, true);
        menuItemHolders[4] = new MenuItemHolder(gameCartridge, MenuItemHolder.Id.SPILL_OVER, true);
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        super.init(gameCartridge, player, gameCamera, sceneManager);

        int widthClipInPixel = 10 * tileMap.getTileWidth();
        int heightClipInPixel = 10 * tileMap.getTileHeight();
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                widthClipInPixel;
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                heightClipInPixel;

        //text-area bounds
        x0 = 0;
        y0 = (int) (104 * heightPixelToViewportRatio);
        x1 = (int) (160 * widthPixelToViewportRatio);
        y1 = (int) ((104 + 56) * heightPixelToViewportRatio);

        cursorImage = cropCursorImage(gameCartridge.getContext().getResources());

        //Paint (FONT)
        paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.CYAN);
        paintFont.setAlpha(230);
        paintFont.setTextSize(40f);
        paintFont.setTypeface(Typeface.SANS_SERIF);

        for (MenuItemHolder menuItemHolder : menuItemHolders) {
            menuItemHolder.init(gameCartridge);
        }
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

        indexMenuItemHolders = 0;
        gameCartridge.getTimeManager().setIsPaused(false);
    }

    @Override
    public void getInputViewport() {
        //don't call super's getInputViewport()
        //TODO:
        if (inputManager.isJustPressedViewport()) {
            if (inputManager.isRightViewport()) {
                indexMenuItemHolders++;
                if (indexMenuItemHolders >= NUMBER_OF_MENU_ITEM_HOLDERS) {
                    indexMenuItemHolders = 0;
                }
            } else if (inputManager.isLeftViewport()) {
                indexMenuItemHolders--;
                if (indexMenuItemHolders < 0) {
                    indexMenuItemHolders = (NUMBER_OF_MENU_ITEM_HOLDERS - 1);
                }
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        //don't call super's getInputDirectionalPad()
        //TODO:
        if (inputManager.isJustPressedDirectionalPad()) {
            if (inputManager.isRightDirectionalPad()) {
                indexMenuItemHolders++;
                if (indexMenuItemHolders >= NUMBER_OF_MENU_ITEM_HOLDERS) {
                    indexMenuItemHolders = 0;
                }
            } else if (inputManager.isLeftDirectionalPad()) {
                indexMenuItemHolders--;
                if (indexMenuItemHolders < 0) {
                    indexMenuItemHolders = (NUMBER_OF_MENU_ITEM_HOLDERS - 1);
                }
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        //don't call super's getInputButtonPad()
        //a button
        if (inputManager.isaButtonPad()) {
            Log.d(MainActivity.DEBUG_TAG, "SceneSeedsShop.getInputButtonPad() a-button-justPressed");
            //TODO:
            MenuItemHolder menuItemHolder = menuItemHolders[indexMenuItemHolders];
            menuItemHolder.execute();
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
        int x0Wares = (int)((1 * tileMap.getTileWidth()) * widthPixelToViewportRatio);
        int y0Wares = (int)(((4.5f * tileMap.getTileHeight()) * heightPixelToViewportRatio));
        int x1Wares = x0Wares + (int)((1 * tileMap.getTileWidth()) * widthPixelToViewportRatio);
        int y1Wares = y0Wares + (int)((1 * tileMap.getTileHeight()) * heightPixelToViewportRatio);
        //CURSOR FOR MENU-ITEM1
        int x0Cursor = (int)(x0Wares - (4 * widthPixelToViewportRatio));
        int x1Cursor = (int)(x1Wares + (4 * widthPixelToViewportRatio));
        for (int i = 0; i < NUMBER_OF_MENU_ITEM_HOLDERS; i++) {
            if (i == indexMenuItemHolders) {
                Rect rectOfCursorImage = new Rect(0, 0, cursorImage.getWidth(), cursorImage.getHeight());
                Rect rectOfCursorImageOnScreen = new Rect(
                        x0Cursor,
                        (int) (y0Wares - (4 * heightPixelToViewportRatio)),
                        x1Cursor,
                        (int) (y1Wares + (4 * heightPixelToViewportRatio)));
                //CURSOR////////////////////////////////////////////////////////////////////////////
                canvas.drawBitmap(cursorImage, rectOfCursorImage, rectOfCursorImageOnScreen, null);
                ////////////////////////////////////////////////////////////////////////////////////
            }

            //MENU-ITEM////////////////////////////////////////////////////
            Bitmap menuItemIcon = menuItemHolders[i].getImage();
            Rect rectMenuItemIcon = new Rect(0, 0, menuItemIcon.getWidth(), menuItemIcon.getHeight());
            Rect rectMenuItemIconOnScreen = new Rect(x0Wares, y0Wares, x1Wares, y1Wares);
            canvas.drawBitmap(menuItemIcon, rectMenuItemIcon, rectMenuItemIconOnScreen, null);
//            canvas.drawRect(x0Wares, y0Wares, x1Wares, y1Wares, paintFont);
            ///////////////////////////////////////////////////////////////

            //INCREMENT TO NEXT MENU-ITEM
            x0Cursor += (int)(((1 * tileMap.getTileWidth()) + 12) * widthPixelToViewportRatio);
            x1Cursor += (int)(((1 * tileMap.getTileWidth()) + 12) * widthPixelToViewportRatio);
            x0Wares += (int)(((1 * tileMap.getTileWidth()) + 12) * widthPixelToViewportRatio);
            x1Wares += (int)(((1 * tileMap.getTileWidth()) + 12) * widthPixelToViewportRatio);
        }



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

class MenuItemHolder
        implements Serializable {
    public enum Id { TALK, SEED_CROP1, SEED_CROP2, SEED_GRASS, SEED_FLOWER1, SEED_FLOWER2,
        SEED_HERB1, SEED_HERB2, EXIT, SPILL_OVER; }
    //public enum Id { TALK, BUY, CONTINUE, EXIT; }
    transient private GameCartridge gameCartridge;
    private MenuItemHolder.Id id;
    private boolean isEnabled;
    transient private Bitmap image;
    public MenuItemHolder(GameCartridge gameCartridge, MenuItemHolder.Id id, boolean isEnabled) {
        this.gameCartridge = gameCartridge;
        this.id = id;
        this.isEnabled = isEnabled;
        initImage(gameCartridge.getContext().getResources());
    }
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        initImage(gameCartridge.getContext().getResources());
    }
    public void execute() {
        switch (id) {
            case TALK:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) TALK");
                //TODO: create a TextboxState.Textbox,  size and position it, active its line-in animations.
                Object[] args = { String.format("Hello, %s. What seeds are you buying today?", gameCartridge.getPlayer().getName()) };
                gameCartridge.getStateManager().push(State.Id.TEXTBOX, args);
                break;
            case SEED_CROP1:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_CROP1");
                break;
            case SEED_CROP2:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_CROP2");
                break;
            case SEED_GRASS:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_GRASS");
                break;
            case SEED_FLOWER1:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_FLOWER1");
                break;
            case SEED_FLOWER2:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_FLOWER2");
                break;
            case SEED_HERB1:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_HERB1");
                break;
            case SEED_HERB2:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SEED_HERB2");
                break;
            case EXIT:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) EXIT");
                Object[] directionFacing = { gameCartridge.getPlayer().getDirection(),
                        gameCartridge.getPlayer().getMoveSpeed() };
                //////////////////////////////////
                gameCartridge.getSceneManager().pop(directionFacing);
                //////////////////////////////////
                break;
            case SPILL_OVER:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(MenuItemHolder.Id) SPILL_OVER");
                //TODO: swap "ViewHolder" data to next page of inventory.
                break;
        }
    }
    public void initImage(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        switch (id) {
            case TALK:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 9, 131, 16, 16);
                break;
            case SEED_CROP1:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 33, 148, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 33, 132, 16, 16);
                }
                break;
            case SEED_CROP2:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 57, 148, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 57, 132, 16, 16);
                }
                break;
            case SEED_GRASS:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 81, 148, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 81, 132, 16, 16);
                }
                break;
            case SEED_HERB1:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 149, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 131, 16, 16);
                }
                break;
            case SEED_HERB2:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 133, 149, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 133, 131, 16, 16);
                }
                break;
            case SEED_FLOWER1:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 150, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 132, 16, 16);
                }
                break;
            case SEED_FLOWER2:
                if (isEnabled) {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 180, 150, 16, 16);
                } else {
                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 180, 132, 16, 16);
                }
                break;
            case EXIT:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 204, 131, 16, 16);
                break;
            case SPILL_OVER:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 220, 131, 16, 16);
                break;
        }
    }
    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public boolean getIsEnabled() {
        return isEnabled;
    }
    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
}