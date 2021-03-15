package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.Scene;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.TileManagerLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneHome01 extends Scene {
    public static final int X_SPAWN_INDEX_DEFAULT = 2;
    public static final int Y_SPAWN_INDEX_DEFAULT = 6;

    private static SceneHome01 uniqueInstance;

    private SceneHome01() {
        super();
        List<Entity> entitiesForHome01 = createEntitiesForHome01();
        entityManager.loadEntities(entitiesForHome01);
        List<Item> itemsForHome01 = createItemsForHome01();
        itemManager.loadItems(itemsForHome01);
    }

    public static SceneHome01 getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SceneHome01();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        // For scenes loaded from external file, the [create] and [init] steps in TileManager
        // are combined (unlike EntityManager and ItemManager).
        Tile[][] tilesForHome01 = createAndInitTilesForHome01(game);
        tileManager.loadTiles(tilesForHome01);
        Map<String, Rect> transferPointsForHome01 = createTransferPointsForHome01();
        tileManager.loadTransferPoints(transferPointsForHome01); // transferPoints are transient and should be reloaded everytime.
        tileManager.init(game); // updates tileManager's reference to the new game.

        entityManager.init(game);
        itemManager.init(game);
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
        displayTransferPointSpecifications();
    }

    @Override
    public void exit() {
        super.exit();
        displayTransferPointSpecifications();
    }

    private Tile[][] createAndInitTilesForHome01(Game game) {
        String home01LoadedAsString = TileManagerLoader.loadFileAsString(game.getContext().getResources(), R.raw.tile_home01);
        Tile[][] home01 = TileManagerLoader.convertStringToTiles(home01LoadedAsString);
        Bitmap imageHome01 = cropImageHome01(game.getContext().getResources());

        // Initialize the tiles (provide image and define walkable)
        // Assign image and init() all the tiles in home01.
        for (int y = 0; y < home01.length; y++) {
            for (int x = 0; x < home01[0].length; x++) {
                int xInPixel = x * Tile.WIDTH;
                int yInPixel = y * Tile.HEIGHT;
                int widthInPixel = Tile.WIDTH;
                int heightInPixel = Tile.HEIGHT;

                Tile tile = home01[y][x];
                //GenericWalkableTile
                if (tile.getId().equals("0")) {
                    Bitmap tileSprite = Bitmap.createBitmap(imageHome01, xInPixel, yInPixel, widthInPixel, heightInPixel);
                    tile.init(game, x, y, tileSprite);
                }
                //GenericSolidTile
                else if (tile.getId().equals("1")) {
                    Bitmap tileSprite = Bitmap.createBitmap(imageHome01, xInPixel, yInPixel, widthInPixel, heightInPixel);
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                }
                //TelevisionTile
                else if (tile.getId().equals("3")) {
                    Bitmap tileSprite = Bitmap.createBitmap(imageHome01, xInPixel, yInPixel, widthInPixel, heightInPixel);
                    tile.init(game, x, y, tileSprite);
                    tile.setWalkable(false);
                } else {
                    Bitmap defaultImage = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.icon_gridview);
                    tile.init(game, x, y, defaultImage);
                    tile.setWalkable(false);
                }
            }
        }

        return home01;
    }

    private Bitmap cropImageHome01(Resources resources) {
        Log.d(MainActivity.DEBUG_TAG, "SceneHome01.cropImageHome01(Resources resources)");

        Bitmap indoorsHomeAndRoom = BitmapFactory.decodeResource(resources, R.drawable.indoors_home_and_room);
        Bitmap home01 = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        home01 = Bitmap.createBitmap(indoorsHomeAndRoom, 160, 16, 128, 128);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "Bitmap home01's (width, height): " + home01.getWidth() + ", " + home01.getHeight());

        return home01;
    }

    private Map<String, Rect> createTransferPointsForHome01() {
        Map<String, Rect> transferPoints = new HashMap<String, Rect>();
        transferPoints.put( "HOME_02", new Rect(
                (7 * Tile.WIDTH),
                (1 * Tile.HEIGHT),
                ((7 * Tile.WIDTH) + (1 * Tile.WIDTH)),
                ((1 * Tile.HEIGHT) + (1 * Tile.HEIGHT))) );
        transferPoints.put( "PART_01", new Rect(
                (2 * Tile.WIDTH),
                (7 * Tile.HEIGHT),
                ((2 * Tile.WIDTH) + (2 * Tile.WIDTH)),
                ((7 * Tile.HEIGHT) + (1 * Tile.HEIGHT))) );
        return transferPoints;
    }

    public void displayTransferPointSpecifications() {
        Rect inGameRectHome02 = tileManager.getTransferPoints().get("HOME_02");
        Rect inGameRectPart01 = tileManager.getTransferPoints().get("PART_01");
        Log.d(MainActivity.DEBUG_TAG, "home02: " + inGameRectHome02.left + ", " + inGameRectHome02.top + ", " + inGameRectHome02.right + ", " + inGameRectHome02.bottom);
        Log.d(MainActivity.DEBUG_TAG, "part01: " + inGameRectPart01.left + ", " + inGameRectPart01.top + ", " + inGameRectPart01.right + ", " + inGameRectPart01.bottom);
    }

    @Override
    public void drawCurrentFrame(Canvas canvas) {
        super.drawCurrentFrame(canvas);
        Rect screenRectOfTransferPointHome02 = GameCamera.getInstance().convertInGameRectToScreenRect(tileManager.getTransferPoints().get("HOME_02"));
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawRect(screenRectOfTransferPointHome02, paint);

        Rect screenRectOfTransferPointPart01 = GameCamera.getInstance().convertInGameRectToScreenRect(tileManager.getTransferPoints().get("PART_01"));
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(screenRectOfTransferPointPart01, paint);

        Rect screenRectOfPlayer = GameCamera.getInstance().convertInGameRectToScreenRect(Player.getInstance().getCollisionBounds(0, 0));
        paint.setColor(Color.GREEN);
        canvas.drawRect(screenRectOfPlayer, paint);
    }

    private List<Entity> createEntitiesForHome01() {
        List<Entity> entities = new ArrayList<Entity>();
        // TODO: Insert scene specific entities here.
        return entities;
    }

    private List<Item> createItemsForHome01() {
        List<Item> items = new ArrayList<Item>();
        // TODO: Insert scene specific items here.
        return items;
    }
}