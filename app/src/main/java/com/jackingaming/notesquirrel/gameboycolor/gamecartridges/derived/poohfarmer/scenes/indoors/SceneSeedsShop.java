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
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.FlowerPotItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.GlovedHandsItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.ScissorsItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
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
    List<MenuItemHolder.Id> templateFirstPage;
    List<MenuItemHolder.Id> templateNonFirstPage;

    //TEXT-AREA
    transient private Paint paintBackground;
    transient private Paint paintFont;
    private int x0, y0, x1, y1;
    private String nameSelectedItem;
    private String descriptionSelectedItem;

    public SceneSeedsShop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 10;
        heightClipInTile = 10;

        nameSelectedItem = "";
        descriptionSelectedItem = "";

        initInventory();
        indexFirstVisibleItem = 0;

        initTemplateFirstPage();
        initTemplateNonFirstPage();

        initMenuItemHolders();
        indexMenuItemHolders = 0;

        updateTextArea();
    }

    public void initInventory() {
        inventory = new ArrayList<Item>();
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.TURNIP));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.POTATO));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.GRASS));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.GERANIUM));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.SAGE));
        inventory.add(new FlowerPotItem(gameCartridge));
        inventory.add(new GlovedHandsItem(gameCartridge));
        inventory.add(new ScissorsItem(gameCartridge));
    }

    private void updateMenuItemHolders() {

    }

    public void updateTextArea() {
        nameSelectedItem = menuItemHolders[indexMenuItemHolders].getName();
        descriptionSelectedItem = menuItemHolders[indexMenuItemHolders].getDescription();
    }

    public int getIndexFirstVisibleItem() {
        return indexFirstVisibleItem;
    }

    public void incrementIndexFirstVisibleItem(int numberOfMenuItemBuyInTemplate) {
        indexFirstVisibleItem += numberOfMenuItemBuyInTemplate;
    }

    private void initMenuItemHolders() {
        menuItemHolders = new MenuItemHolder[NUMBER_OF_MENU_ITEM_HOLDERS];

        menuItemHolders[0] = new MenuItemHolder(gameCartridge, true);
        menuItemHolders[1] = new MenuItemHolder(gameCartridge, true);
        menuItemHolders[2] = new MenuItemHolder(gameCartridge, true);
        menuItemHolders[3] = new MenuItemHolder(gameCartridge, true);
        menuItemHolders[4] = new MenuItemHolder(gameCartridge, true);

        //////////////
        setTemplate();
        //////////////
    }

    public void setTemplate() {
        List<MenuItemHolder.Id> template = determineTemplateToUse();

        for (int i = 0; i < menuItemHolders.length; i++) {
            menuItemHolders[i].setId(template.get(i));

            //FIRST_PAGE
            if (indexFirstVisibleItem == 0) {
                switch (template.get(i)) {
                    case TALK:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Talk");
                        menuItemHolders[i].setDescription("...");
                        break;
                    case BUY:
                        if ( (indexFirstVisibleItem+i-1 < inventory.size()) &&
                                (indexFirstVisibleItem+i-1 >= 0) ) {
                            menuItemHolders[i].setImage(inventory.get(indexFirstVisibleItem + i - 1).getImage());
                            menuItemHolders[i].setName(inventory.get(indexFirstVisibleItem + i - 1).getId());
                            menuItemHolders[i].setDescription("...");
                            if (inventory.get(indexFirstVisibleItem + i - 1) instanceof CropSeedItem) {
                                menuItemHolders[i].setDescription(
                                        ((CropSeedItem) inventory.get(indexFirstVisibleItem + i - 1)).getSeedType().name()
                                );
                            } else if (inventory.get(indexFirstVisibleItem + i) instanceof FlowerSeedItem) {
                                String isHerb = (((FlowerSeedItem) inventory.get(indexFirstVisibleItem + i)).getIsHerb()) ? "Herb" : "Flower";
                                menuItemHolders[i].setDescription(
                                        isHerb + ": " +
                                                ((FlowerSeedItem) inventory.get(indexFirstVisibleItem + i)).getSeedType().name()
                                );
                            }
                        }
                        break;
                    case EMPTY:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Empty");
                        menuItemHolders[i].setDescription("...");
                        break;
                    case SPILL_OVER:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Spill Over");
                        menuItemHolders[i].setDescription("...");
                        break;
                    case EXIT:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Exit");
                        menuItemHolders[i].setDescription("...");
                        break;
                }
            }
            //NON_FIRST_PAGE
            else {
                switch (template.get(i)) {
                    case TALK:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Talk");
                        menuItemHolders[i].setDescription("...");
                        break;
                    case BUY:
                        if ( (indexFirstVisibleItem+i < inventory.size()) &&
                                (indexFirstVisibleItem+i >= 0) ) {
                            menuItemHolders[i].setImage(inventory.get(indexFirstVisibleItem + i).getImage());
                            menuItemHolders[i].setName(inventory.get(indexFirstVisibleItem + i).getId());
                            menuItemHolders[i].setDescription("...");
                            if (inventory.get(indexFirstVisibleItem + i) instanceof CropSeedItem) {
                                menuItemHolders[i].setDescription(
                                        ((CropSeedItem) inventory.get(indexFirstVisibleItem + i)).getSeedType().name()
                                );
                            } else if (inventory.get(indexFirstVisibleItem + i) instanceof FlowerSeedItem) {
                                String isHerb = (((FlowerSeedItem) inventory.get(indexFirstVisibleItem + i)).getIsHerb()) ? "Herb" : "Flower";
                                menuItemHolders[i].setDescription(
                                        isHerb + ": " +
                                        ((FlowerSeedItem) inventory.get(indexFirstVisibleItem + i)).getSeedType().name()
                                );
                            }
                        }
                        break;
                    case EMPTY:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Empty");
                        menuItemHolders[i].setDescription("...");
                        break;
                    case SPILL_OVER:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Spill over");
                        menuItemHolders[i].setDescription("...");
                        break;
                    case EXIT:
                        menuItemHolders[i].initImage(gameCartridge.getContext().getResources());
                        menuItemHolders[i].setName("Exit");
                        menuItemHolders[i].setDescription("...");
                        break;
                }
            }
        }
    }

    private List<MenuItemHolder.Id> determineTemplateToUse() {
        if (indexFirstVisibleItem == 0) {
            initTemplateFirstPage();
            return templateFirstPage;
        } else {
            initTemplateNonFirstPage();
            return templateNonFirstPage;
        }
    }

    private void initTemplateFirstPage() {
        templateFirstPage = new ArrayList<MenuItemHolder.Id>();

        //[0] WILL ALWAYS HAVE MenuItemHolder.Id.TALK
        templateFirstPage.add(MenuItemHolder.Id.TALK);
        ////////////////////////////////////////////////////////////////////
        //[1] CAN THERE EVER BE AN EMPTY INVENTORY FOR SHOP?
        if (indexFirstVisibleItem < inventory.size()) {
            templateFirstPage.add(MenuItemHolder.Id.BUY);
        } else{
            templateFirstPage.add(MenuItemHolder.Id.EMPTY);
        }
        //[2] SOMETIMES exist, SOMETIMES does NOT exist
        if ( (indexFirstVisibleItem + 1) < inventory.size() ) {
            templateFirstPage.add(MenuItemHolder.Id.BUY);
        } else{
            templateFirstPage.add(MenuItemHolder.Id.EMPTY);
        }
        //[3] SOMETIMES exist, SOMETIMES does NOT exist
        if ( (indexFirstVisibleItem + 2) < inventory.size() ) {
            templateFirstPage.add(MenuItemHolder.Id.BUY);
        } else{
            templateFirstPage.add(MenuItemHolder.Id.EMPTY);
        }
        ////////////////////////////////////////////////////////////////////
        //[4] SOMETIMES MenuItemHolder.Id.SPILL_OVER, SOMETIMES MenuItemHolder.Id.EXIT
        if ( (indexFirstVisibleItem + 3) < inventory.size() ) {
            templateFirstPage.add(MenuItemHolder.Id.SPILL_OVER);
        } else {
            templateFirstPage.add(MenuItemHolder.Id.EXIT);
        }
    }

    private void initTemplateNonFirstPage() {
        templateNonFirstPage = new ArrayList<MenuItemHolder.Id>();

        //[0] WILL ALWAYS HAVE MenuItemHolder.Id.BUY
        templateNonFirstPage.add(MenuItemHolder.Id.BUY);
        ////////////////////////////////////////////////////////////////////
        //[1] SOMETIMES exist, SOMETIMES does NOT exist
        if (indexFirstVisibleItem + 1 < inventory.size()) {
            templateNonFirstPage.add(MenuItemHolder.Id.BUY);
        } else{
            templateNonFirstPage.add(MenuItemHolder.Id.EMPTY);
        }
        //[2] SOMETIMES exist, SOMETIMES does NOT exist
        if ( (indexFirstVisibleItem + 2) < inventory.size() ) {
            templateNonFirstPage.add(MenuItemHolder.Id.BUY);
        } else{
            templateNonFirstPage.add(MenuItemHolder.Id.EMPTY);
        }
        //[3] SOMETIMES exist, SOMETIMES does NOT exist
        if ( (indexFirstVisibleItem + 3) < inventory.size() ) {
            templateNonFirstPage.add(MenuItemHolder.Id.BUY);
        } else{
            templateNonFirstPage.add(MenuItemHolder.Id.EMPTY);
        }
        ////////////////////////////////////////////////////////////////////
        //[4] SOMETIMES MenuItemHolder.Id.SPILL_OVER, SOMETIMES MenuItemHolder.Id.EXIT
        if ( (indexFirstVisibleItem + 4) < inventory.size() ) {
            templateNonFirstPage.add(MenuItemHolder.Id.SPILL_OVER);
        } else {
            templateNonFirstPage.add(MenuItemHolder.Id.EXIT);
        }
    }

    public void resetIndexMenuItemHolders() {
        indexMenuItemHolders = 0;
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

        //Paint (BACKGROUND)
        paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.BLUE);

        //Paint (FONT)
        paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.YELLOW);
        paintFont.setTextSize(64);
        paintFont.setTypeface(Typeface.SANS_SERIF);
        paintFont.setTypeface(Typeface.DEFAULT_BOLD);

        setTemplate();
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

        indexFirstVisibleItem = 0;
        setTemplate();
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
                /////////////////
                updateTextArea();
                /////////////////
            } else if (inputManager.isLeftViewport()) {
                indexMenuItemHolders--;
                if (indexMenuItemHolders < 0) {
                    indexMenuItemHolders = (NUMBER_OF_MENU_ITEM_HOLDERS - 1);
                }
                /////////////////
                updateTextArea();
                /////////////////
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
                /////////////////
                updateTextArea();
                /////////////////
            } else if (inputManager.isLeftDirectionalPad()) {
                indexMenuItemHolders--;
                if (indexMenuItemHolders < 0) {
                    indexMenuItemHolders = (NUMBER_OF_MENU_ITEM_HOLDERS - 1);
                }
                /////////////////
                updateTextArea();
                /////////////////
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
            menuItemHolder.execute(this);
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
        canvas.drawRect(x0, y0, x1, y1, paintBackground);
        //text-area (text)
        Paint.FontMetrics fm = paintFont.getFontMetrics();
        int heightLine = (int) (fm.bottom - fm.top + fm.leading);
        canvas.drawText(nameSelectedItem,x0+10, y0+heightLine, paintFont);
        canvas.drawText(descriptionSelectedItem,x0+10, y0+heightLine+heightLine, paintFont);


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
    public enum Id { TALK, BUY, EMPTY, SPILL_OVER, EXIT; }
    transient private GameCartridge gameCartridge;
    private MenuItemHolder.Id id;
    private boolean isEnabled;
    private String name;
    private String description;
    transient private Bitmap image;
    public MenuItemHolder(GameCartridge gameCartridge, boolean isEnabled) {
        this.gameCartridge = gameCartridge;
        this.isEnabled = isEnabled;
        name = "";
        description = "";
    }
    public void execute(SceneSeedsShop sceneSeedsShop) {
        switch (id) {
            case TALK:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(SceneSeedsShop) TALK");
                //TODO: create a TextboxState.Textbox,  size and position it, active its line-in animations.
                Object[] args = new Object[10];
                args[0] = String.format("Hello, %s. What seeds are you buying today?", gameCartridge.getPlayer().getName());
                args[1] = Integer.valueOf(0);
                int heightClipInPixel = 10 * 16;
                float heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                        heightClipInPixel;
                args[2] = Integer.valueOf((int)(104 * heightPixelToViewportRatio));
                args[3] = Integer.valueOf(gameCartridge.getWidthViewport());
                args[4] = Integer.valueOf(gameCartridge.getHeightViewport());
                args[5] = Integer.valueOf(10);
                gameCartridge.getStateManager().push(State.Id.TEXTBOX, args);
                break;
            case BUY:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(SceneSeedsShop) BUY");
                break;
            case EMPTY:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(SceneSeedsShop) EMPTY");
                break;
            case SPILL_OVER:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(SceneSeedsShop) SPILL_OVER");
                //TODO: swap "ViewHolder" data to next page of inventory.
                int numberOfBuyMenuItemInTemplate = 0;
                if (sceneSeedsShop.getIndexFirstVisibleItem() == 0) {
                    numberOfBuyMenuItemInTemplate = 3;
                } else {
                    numberOfBuyMenuItemInTemplate = 4;
                }
                sceneSeedsShop.incrementIndexFirstVisibleItem(numberOfBuyMenuItemInTemplate);
                sceneSeedsShop.setTemplate();
                sceneSeedsShop.resetIndexMenuItemHolders();
                sceneSeedsShop.updateTextArea();
                break;
            case EXIT:
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(SceneSeedsShop) EXIT");
                Object[] directionFacing = { gameCartridge.getPlayer().getDirection(),
                        gameCartridge.getPlayer().getMoveSpeed() };
                //////////////////////////////////
                gameCartridge.getSceneManager().pop(directionFacing);
                //////////////////////////////////
                break;
        }
    }
    public void initImage(Resources resources) {
        Bitmap seedsShopSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_seeds_shop);
        switch (id) {
            case TALK:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 9, 131, 16, 16);
                break;
            case BUY:
//                image = Bitmap.createBitmap(seedsShopSpriteSheet, 9, 154, 16, 16);
                break;
//            case SEED_CROP1:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 33, 148, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 33, 132, 16, 16);
//                }
//                break;
//            case SEED_CROP2:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 57, 148, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 57, 132, 16, 16);
//                }
//                break;
//            case SEED_GRASS:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 81, 148, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 81, 132, 16, 16);
//                }
//                break;
//            case SEED_HERB1:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 149, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 105, 131, 16, 16);
//                }
//                break;
//            case SEED_HERB2:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 133, 149, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 133, 131, 16, 16);
//                }
//                break;
//            case SEED_FLOWER1:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 150, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 156, 132, 16, 16);
//                }
//                break;
//            case SEED_FLOWER2:
//                if (isEnabled) {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 180, 150, 16, 16);
//                } else {
//                    image = Bitmap.createBitmap(seedsShopSpriteSheet, 180, 132, 16, 16);
//                }
//                break;
            case EMPTY:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 9, 154, 16, 16);
                break;
            case SPILL_OVER:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 220, 131, 16, 16);
                break;
            case EXIT:
                image = Bitmap.createBitmap(seedsShopSpriteSheet, 204, 131, 16, 16);
                break;
        }
    }
    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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