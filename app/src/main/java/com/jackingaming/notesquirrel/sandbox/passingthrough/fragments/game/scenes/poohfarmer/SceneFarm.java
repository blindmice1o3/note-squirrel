package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.PassingThroughActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.adapters.ItemRecyclerViewAdapter;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.Scene;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.BugCatchingNet;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.HoneyPot;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneFarm extends Scene {
    public static final int X_SPAWN_INDEX_DEFAULT = 4;
    public static final int Y_SPAWN_INDEX_DEFAULT = 4;
    private static SceneFarm uniqueInstance;

    private List<Item> seedShopInventory;
    transient private ItemRecyclerViewAdapter seedShopRecyclerViewAdapter;
    transient private Dialog seedShopDialog;
    private boolean inSeedShopDialogState;

    private SceneFarm() {
        super();
        List<Entity> entitiesForFarm = createEntitiesForFarm();
        entityManager.loadEntities(entitiesForFarm);
        List<Item> itemsForFarm = createItemsForFarm();
        itemManager.loadItems(itemsForFarm);

        seedShopInventory = new ArrayList<Item>();
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new HoneyPot());
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new HoneyPot());
        seedShopInventory.add(new BugCatchingNet());

        inSeedShopDialogState = false;
    }

    public boolean isInSeedShopDialogState() {
        return inSeedShopDialogState;
    }

    public void setInSeedShopDialogState(boolean inSeedShopDialogState) {
        this.inSeedShopDialogState = inSeedShopDialogState;
    }

    public Dialog getSeedShopDialog() {
        return seedShopDialog;
    }

    public static SceneFarm getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SceneFarm();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        // For scenes loaded from external file, the [create] and [init] steps in TileManager
        // are combined (unlike EntityManager and ItemManager).
        Tile[][] tilesForFarm = createAndInitTilesForFarm(game);
        tileManager.loadTiles(tilesForFarm);
        Map<String, Rect> transferPointsForFarm = createTransferPointsForFarm();
        tileManager.loadTransferPoints(transferPointsForFarm); // transferPoints are transient and should be reloaded everytime.
        tileManager.init(game); // updates tileManager's reference to the new game.

        entityManager.init(game);
        itemManager.init(game);

        for (Item item : seedShopInventory) {
            item.init(game);
        }

        ((PassingThroughActivity)game.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createSeedShopDialog();
            }
        });
    }

    private void createSeedShopDialog() {
        final Context contextFinal = game.getContext();

        // TODO: NO LONGER USING RecyclerView for seed shop!!!!
        seedShopRecyclerViewAdapter = new ItemRecyclerViewAdapter(game.getContext(), seedShopInventory);
        ItemRecyclerViewAdapter.ItemClickListener itemClickListener = new ItemRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(contextFinal, "Game.createSeedShopDialog() ItemRecyclerViewAdapter.ItemClickListener.onItemClick(View view, int position): " + seedShopInventory.get(position), Toast.LENGTH_SHORT).show();
                // TODO: buy/sell transactions.
//                Item item = seedShopInventory.get(position);
            }
        };
        seedShopRecyclerViewAdapter.setClickListener(itemClickListener);

//        View viewContainingRecyclerView = LayoutInflater.from(context).inflate(R.layout.view_cart_recyclerview, null);
        Bitmap seedShopSpriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.gbc_hm_seeds_shop);
        Bitmap seedShopMainBackgroundImage = Bitmap.createBitmap(seedShopSpriteSheet, 31, 14, 160, 104);
        View viewContainingSeedShop = LayoutInflater.from(game.getContext()).inflate(R.layout.dialog_seed_shop, null);
        ImageView imageViewMain = (ImageView) viewContainingSeedShop.findViewById(R.id.imageview_seed_shop_main_background);
        imageViewMain.setImageBitmap(seedShopMainBackgroundImage);
        ImageView imageViewItemHolder01 = (ImageView) viewContainingSeedShop.findViewById(R.id.imageview_seed_shop_item_holder_01);
        imageViewItemHolder01.setImageBitmap(seedShopInventory.get(0).getImage());
        ImageView imageViewItemHolder02 = (ImageView) viewContainingSeedShop.findViewById(R.id.imageview_seed_shop_item_holder_02);
        imageViewItemHolder02.setImageBitmap(seedShopInventory.get(1).getImage());
        ImageView imageViewItemHolder03 = (ImageView) viewContainingSeedShop.findViewById(R.id.imageview_seed_shop_item_holder_03);
        imageViewItemHolder03.setImageBitmap(seedShopInventory.get(2).getImage());
        ImageView imageViewItemHolder04 = (ImageView) viewContainingSeedShop.findViewById(R.id.imageview_seed_shop_item_holder_04);
        imageViewItemHolder04.setImageBitmap(seedShopInventory.get(3).getImage());
        ImageView imageViewItemHolder05 = (ImageView) viewContainingSeedShop.findViewById(R.id.imageview_seed_shop_item_holder_05);
        imageViewItemHolder05.setImageBitmap(seedShopInventory.get(4).getImage());

//        RecyclerView recyclerView = (RecyclerView) viewContainingRecyclerView.findViewById(R.id.recyclerview_view_cart);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(seedShopRecyclerViewAdapter);
//        int numberOfColumns = 4;
//        recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        seedShopDialog = new AlertDialog.Builder(game.getContext())
                .setView(viewContainingSeedShop)
                .create();
        seedShopDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                game.setPaused(false);
                inSeedShopDialogState = false;
            }
        });
    }

    public void showSeedShopDialog() {
        game.setPaused(true);
        inSeedShopDialogState = true;
        ((PassingThroughActivity)game.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seedShopDialog.show();
            }
        });
    }

    @Override
    public void enter() {
        super.enter();

        if (xLastKnown == 0 && yLastKnown == 0) {
            Player.getInstance().setX(X_SPAWN_INDEX_DEFAULT * Tile.WIDTH);
            Player.getInstance().setY(Y_SPAWN_INDEX_DEFAULT * Tile.HEIGHT);
        } else {
            Player.getInstance().setX(xLastKnown);
            Player.getInstance().setY(yLastKnown);
        }
        GameCamera.getInstance().update(0L);
    }

    private Tile[][] createAndInitTilesForFarm(Game game) {
        //rgbTileMapFarm is an image where each pixel represents a tile.
        Bitmap rgbTileMapFarm = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.tile_map_farm);
        int columns = rgbTileMapFarm.getWidth();            //Always need.
        int rows = rgbTileMapFarm.getHeight();              //Always need.

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Tile[][] tiles = new Tile[rows][columns];           //Always need.
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        Bitmap imageFarm = cropImageFarm(game.getContext().getResources(), Season.SPRING);
        //DEFINE EACH ELEMENT.
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int xInPixel = x * Tile.WIDTH;
                int yInPixel = y * Tile.HEIGHT;
                int widthInPixel = Tile.WIDTH;
                int heightInPixel = Tile.HEIGHT;
                Bitmap tileSprite = Bitmap.createBitmap(imageFarm, xInPixel, yInPixel, widthInPixel, heightInPixel);

                int pixel = rgbTileMapFarm.getPixel(x, y);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                if (pixel == Color.BLACK) {
                    Tile tile = new Tile("black");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new GenericSolidTile(gameCartridge, x, y);
                } else if (pixel == Color.WHITE) {
                    Tile tile = new Tile("white");
                    tile.init(game, x, y, tileSprite);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new GrowableGroundTile(gameCartridge, x, y);
                    //tiles[y][x] = new GenericWalkableTile(gameCartridge, x, y);
                } else if (pixel == Color.RED) {
                    Tile tile = new Tile("red");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new SignPostTile(gameCartridge, x, y);
                } else if (pixel == Color.GREEN) {
                    Tile tile = new Tile("green");
                    tile.init(game, x, y, tileSprite);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new TransferPointTile(gameCartridge, x, y);
                }
                //SHIPPING_BIN_TILE
                else if ( (red == 255) && (green == 255) && (blue == 1) ) {
                    Tile tile = new Tile("topleft");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.TOP_LEFT);
                } else if ( (red == 255) && (green == 255) && (blue == 2) ) {
                    Tile tile = new Tile("topright");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.TOP_RIGHT);
                } else if ( (red == 255) && (green == 255) && (blue == 3) ) {
                    Tile tile = new Tile("bottomleft");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.BOTTOM_LFET);
                } else if ( (red == 255) && (green == 255) && (blue == 4) ) {
                    Tile tile = new Tile("bottomright");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new ShippingBinTile(gameCartridge, x, y, ShippingBinTile.Quadrant.BOTTOM_RIGHT);
                }
//                //TODO: handle special tiles (stashWood, flowerPlot, hotSpring)
                else if (pixel == Color.BLUE) {
                    Tile tile = new Tile("blue");
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
//                    tiles[y][x] = new GenericSolidTile(gameCartridge, x, y);
                } else {
                    Tile tile = new Tile("default");
                    Bitmap defaultImage = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.icon_gridview);
                    tile.init(game, x, y, defaultImage);
                    tile.setWalkable(false);
                    tiles[y][x] = tile;
                }
            }
        }

        return tiles;
    }

    public enum Season { SPRING, SUMMER, FALL, WINTER; }
    public static Bitmap cropImageFarm(Resources resources, Season season) {
        Log.d(MainActivity.DEBUG_TAG, "SceneFarm.cropFarmSpring(Resources resources, Season season)");

        Bitmap farmSpriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm3_farm);
        Bitmap croppedImageFarm = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        switch (season) {
            case SPRING:
                croppedImageFarm = Bitmap.createBitmap(farmSpriteSheet, 14, 39, 384, 400);
                break;
            case SUMMER:
                croppedImageFarm = Bitmap.createBitmap(farmSpriteSheet, 410, 40, 384, 400);
                break;
            case FALL:
                croppedImageFarm = Bitmap.createBitmap(farmSpriteSheet, 806, 40, 384, 400);
                break;
            case WINTER:
                croppedImageFarm = Bitmap.createBitmap(farmSpriteSheet, 1202, 40, 384, 400);
                break;
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Bitmap croppedImageFarm's (width, height): " + croppedImageFarm.getWidth() + ", " + croppedImageFarm.getHeight());

        return croppedImageFarm;
    }

    private Map<String, Rect> createTransferPointsForFarm() {
        Map<String, Rect> transferPoints = new HashMap<String, Rect>();
        transferPoints.put( "HOUSE_LEVEL_01", new Rect((12 * Tile.WIDTH), (15 * Tile.HEIGHT),
                (12 * Tile.WIDTH) + (1 * Tile.WIDTH), (15 * Tile.HEIGHT) + (1 * Tile.HEIGHT)) );
        transferPoints.put( "HOTHOUSE", new Rect((17 * Tile.WIDTH), (15 * Tile.HEIGHT),
                (17 * Tile.WIDTH) + (1 * Tile.WIDTH), (15 * Tile.HEIGHT) + (1 * Tile.HEIGHT)) );

        transferPoints.put( "SHEEP_PEN", new Rect((21 * Tile.WIDTH), (7 * Tile.HEIGHT),
                (21 * Tile.WIDTH) + (1 * Tile.WIDTH), (7 * Tile.HEIGHT) + (1 * Tile.HEIGHT)) );
        transferPoints.put( "CHICKEN_COOP", new Rect((18 * Tile.WIDTH), (5 * Tile.HEIGHT),
                (18 * Tile.WIDTH) + (1 * Tile.WIDTH), (5 * Tile.HEIGHT) + (1 * Tile.HEIGHT)) );
        transferPoints.put( "COW_BARN", new Rect((10 * Tile.WIDTH), (4 * Tile.HEIGHT),
                (10 * Tile.WIDTH) + (1 * Tile.WIDTH), (4 * Tile.HEIGHT) + (1 * Tile.HEIGHT)) );

        transferPoints.put( "SEEDS_SHOP", new Rect((5 * Tile.WIDTH), (11 * Tile.HEIGHT),
                (5 * Tile.WIDTH) + (1 * Tile.WIDTH), (11 * Tile.HEIGHT) + (1 * Tile.HEIGHT)) );
        return transferPoints;
    }

    private List<Entity> createEntitiesForFarm() {
        List<Entity> entities = new ArrayList<Entity>();
        // TODO: Insert scene specific entities here.
        return entities;
    }

    private List<Item> createItemsForFarm() {
        List<Item> items = new ArrayList<Item>();
        // TODO: Insert scene specific items here.
        return items;
    }
}