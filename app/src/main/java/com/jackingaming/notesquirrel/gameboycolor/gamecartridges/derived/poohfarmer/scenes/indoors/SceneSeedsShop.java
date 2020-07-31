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

    //TEXT-AREA
    transient private Paint paintBackground;
    transient private Paint paintFont;
    private int x0, y0, x1, y1;
    private String nameSelectedItem;
    private String priceSelectedItem;

    public SceneSeedsShop(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        widthClipInTile = 10;
        heightClipInTile = 10;

        nameSelectedItem = "";
        priceSelectedItem = "";

        initInventory();
        indexFirstVisibleItem = 0;

        initMenuItemHolders();
        indexMenuItemHolders = 0;

        updateTextArea();
    }

    public void initInventory() {
        inventory = new ArrayList<Item>();
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.MYSTERY));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.TURNIP));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.POTATO));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.GRASS));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.GERANIUM));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.SAGE));
        inventory.add(new FlowerPotItem(gameCartridge));
        inventory.add(new GlovedHandsItem(gameCartridge));
        inventory.add(new ScissorsItem(gameCartridge));
    }

    public void updateTextArea() {
        nameSelectedItem = menuItemHolders[indexMenuItemHolders].getName();
        priceSelectedItem = menuItemHolders[indexMenuItemHolders].getPrice();
    }

    public int getIndexFirstVisibleItem() {
        return indexFirstVisibleItem;
    }

    public int getIndexMenuItemHolders() {
        return indexMenuItemHolders;
    }

    public Item getItem(int index) {
        return inventory.get(index);
    }

    public void removeItem(Item item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
        }
    }

    public void incrementIndexFirstVisibleItem(int numberOfMenuItemBuyInTemplate) {
        indexFirstVisibleItem += numberOfMenuItemBuyInTemplate;
    }

    private void initMenuItemHolders() {
        menuItemHolders = new MenuItemHolder[NUMBER_OF_MENU_ITEM_HOLDERS];

        menuItemHolders[0] = new MenuItemHolder(gameCartridge);
        menuItemHolders[1] = new MenuItemHolder(gameCartridge);
        menuItemHolders[2] = new MenuItemHolder(gameCartridge);
        menuItemHolders[3] = new MenuItemHolder(gameCartridge);
        menuItemHolders[4] = new MenuItemHolder(gameCartridge);

        ////////////////////////
        updateMenuItemHolders();
        ////////////////////////
    }

    public void updateMenuItemHolders() {
        //FIRST_PAGE
        if (indexFirstVisibleItem == 0) {
            //[0]
            menuItemHolders[0].setId(MenuItemHolder.Id.TALK);
            menuItemHolders[0].initImage(gameCartridge.getContext().getResources());
            menuItemHolders[0].setName("Talk");
            menuItemHolders[0].setPrice("");

            //[1]
            if ( (indexFirstVisibleItem + 0) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 0);
                menuItemHolders[1].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[1].setItem(item);
                menuItemHolders[1].updateItem();
            } else {
                menuItemHolders[1].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[1].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[1].setName("Empty");
                menuItemHolders[1].setPrice("");
            }

            //[2]
            if ( (indexFirstVisibleItem + 1) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 1);
                menuItemHolders[2].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[2].setItem(item);
                menuItemHolders[2].updateItem();
            } else {
                menuItemHolders[2].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[2].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[2].setName("Empty");
                menuItemHolders[2].setPrice("");
            }

            //[3]
            if ( (indexFirstVisibleItem + 2) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 2);
                menuItemHolders[3].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[3].setItem(item);
                menuItemHolders[3].updateItem();
            } else {
                menuItemHolders[3].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[3].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[3].setName("Empty");
                menuItemHolders[3].setPrice("");
            }

            //[4]
            if ( (indexFirstVisibleItem + 3) < inventory.size() ) {
                menuItemHolders[4].setId(MenuItemHolder.Id.SPILL_OVER);
                menuItemHolders[4].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[4].setName("Spill over");
                menuItemHolders[4].setPrice("");

            } else {
                menuItemHolders[4].setId(MenuItemHolder.Id.EXIT);
                menuItemHolders[4].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[4].setName("Exit");
                menuItemHolders[4].setPrice("");

            }
        }
        //NON_FIRST_PAGE
        else {
            //[0]
            if ( (indexFirstVisibleItem + 0) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 0);
                menuItemHolders[0].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[0].setItem(item);
                menuItemHolders[0].updateItem();
            } else {
                menuItemHolders[1].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[1].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[1].setName("Empty");
                menuItemHolders[1].setPrice("");
            }

            //[1]
            if ( (indexFirstVisibleItem + 1) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 1);
                menuItemHolders[1].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[1].setItem(item);
                menuItemHolders[1].updateItem();
            } else {
                menuItemHolders[1].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[1].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[1].setName("Empty");
                menuItemHolders[1].setPrice("");
            }

            //[2]
            if ( (indexFirstVisibleItem + 2) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 2);
                menuItemHolders[2].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[2].setItem(item);
                menuItemHolders[2].updateItem();
            } else {
                menuItemHolders[2].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[2].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[2].setName("Empty");
                menuItemHolders[2].setPrice("");
            }

            //[3]
            if ( (indexFirstVisibleItem + 3) < inventory.size() ) {
                Item item = inventory.get(indexFirstVisibleItem + 3);
                menuItemHolders[3].setId(MenuItemHolder.Id.BUY);
                menuItemHolders[3].setItem(item);
                menuItemHolders[3].updateItem();
            } else {
                menuItemHolders[3].setId(MenuItemHolder.Id.EMPTY);
                menuItemHolders[3].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[3].setName("Empty");
                menuItemHolders[3].setPrice("");
            }

            //[4]
            if ( (indexFirstVisibleItem + 4) < inventory.size() ) {
                menuItemHolders[4].setId(MenuItemHolder.Id.SPILL_OVER);
                menuItemHolders[4].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[4].setName("Spill over");
                menuItemHolders[4].setPrice("");
            } else {
                menuItemHolders[4].setId(MenuItemHolder.Id.EXIT);
                menuItemHolders[4].initImage(gameCartridge.getContext().getResources());
                menuItemHolders[4].setName("Exit");
                menuItemHolders[4].setPrice("");
            }
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

        //INVENTORY
        for (Item item : inventory) {
            item.init(gameCartridge);
        }

        //MENU_ITEM_HOLDER
        for (MenuItemHolder menuItemHolder : menuItemHolders) {
            menuItemHolder.init(gameCartridge);
        }

        updateMenuItemHolders();
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
        updateMenuItemHolders();
        indexMenuItemHolders = 0;
        updateTextArea();
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
    protected void doButtonJustPressedA() {
        Log.d(MainActivity.DEBUG_TAG, "SceneSeedsShop.doButtonJustPressedA()");
        MenuItemHolder menuItemHolder = menuItemHolders[indexMenuItemHolders];
        ////////////////////////////////////////////
        menuItemHolder.execute(this);
        ////////////////////////////////////////////
        updateMenuItemHolders();
        updateTextArea();
    }

    @Override
    protected void doButtonJustPressedB() {
        Log.d(MainActivity.DEBUG_TAG, "SceneSeedsShop.doButtonJustPressedB()");
        Object[] directionFacing = { gameCartridge.getPlayer().getDirection(),
                gameCartridge.getPlayer().getMoveSpeed() };
        //////////////////////////////////
        sceneManager.pop(directionFacing);
        //////////////////////////////////
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
        canvas.drawText(priceSelectedItem,x0+10, y0+heightLine+heightLine, paintFont);


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
    private Item item;
    private String name;
    private String price;
    transient private Bitmap image;
    public MenuItemHolder(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
        item = null;
        name = "";
        price = "";
    }
    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
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
                Player player = gameCartridge.getPlayer();
                int indexSelectedItem = 0;
                //first page
                if (sceneSeedsShop.getIndexFirstVisibleItem() == 0) {
                    indexSelectedItem = sceneSeedsShop.getIndexFirstVisibleItem() + sceneSeedsShop.getIndexMenuItemHolders() - 1;
                }
                //non first page
                else {
                    indexSelectedItem = sceneSeedsShop.getIndexFirstVisibleItem() + sceneSeedsShop.getIndexMenuItemHolders();
                }

                Item itemToBuy = sceneSeedsShop.getItem(indexSelectedItem);
                Log.d(MainActivity.DEBUG_TAG, "MenuItemHolder.execute(SceneSeedsShop) itemToBuy, (price): " + itemToBuy + " (" + itemToBuy.getPrice() + ")");
                if (player.getCurrencyNuggets() >= itemToBuy.getPrice()) {
                    int currentNuggetsAfterBuyingItem = player.getCurrencyNuggets() - itemToBuy.getPrice();
                    /////////////////////////////////////////////////////////
                    player.setCurrencyNuggets(currentNuggetsAfterBuyingItem);
                    player.getInventory().add(itemToBuy);
                    /////////////////////////////////////////////////////////
                    sceneSeedsShop.removeItem(itemToBuy);
                }
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
                sceneSeedsShop.updateMenuItemHolders();
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
                //intentionally blank.
                break;
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
    /**
     * UPDATING: image, name, price.
     */
    public void updateItem() {
        image = item.getImage();
        name = item.getId();

        //CropSeedItem have to distinguish its seedType
        if (item instanceof CropSeedItem) {
            price = ((CropSeedItem) item).getSeedType().name() +
                    " (" + ((CropSeedItem) item).getPrice() + ")";
        }
        //FlowerSeedItem have to distinguish its seedType AND whether it's an herb or flower
        else if (item instanceof FlowerSeedItem) {
            String isHerb = (((FlowerSeedItem) item).getIsHerb()) ? "Herb" : "Flower";
            price = ((FlowerSeedItem) item).getSeedType().name() +
                    " (" + ((FlowerSeedItem) item).getPrice() + ") -> " + isHerb;
        }
        //Default
        else {
            price = "(" + item.getPrice() + ")";
        }
    }
    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
}