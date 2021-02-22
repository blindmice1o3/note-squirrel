package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.frogger;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.Scene;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneFrogger extends Scene {
    public static final int X_SPAWN_INDEX_DEFAULT = 10;
    public static final int Y_SPAWN_INDEX_DEFAULT = 14;

    private static SceneFrogger uniqueInstance;

    private SceneFrogger() {
        super();
        List<Entity> entitiesForFrogger = createEntitiesForFrogger();
        entityManager.loadEntities(entitiesForFrogger);
        List<Item> itemsForFrogger = createItemsForFrogger();
        itemManager.loadItems(itemsForFrogger);
    }

    public static SceneFrogger getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SceneFrogger();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        // For scenes loaded from external file, the [create] and [init] steps in TileManager
        // are combined (unlike EntityManager and ItemManager).
        Tile[][] tilesForFrogger = createAndInitTilesForFrogger(game);
        tileManager.loadTiles(tilesForFrogger);
        Map<String, Rect> transferPointsForFrogger = createTransferPointsForFrogger();
        tileManager.loadTransferPoints(transferPointsForFrogger); // transferPoints are transient and should be reloaded everytime.
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
    }

    private Tile[][] createAndInitTilesForFrogger(Game game) {
        Bitmap imageFrogger = cropImageFrogger(game.getContext().getResources());
        int columns = imageFrogger.getWidth() / (3 * Tile.WIDTH);
        int rows = imageFrogger.getHeight() / (3 * Tile.HEIGHT);
        Tile[][] frogger = new Tile[rows][columns];

        // Initialize the tiles (provide image and define walkable)
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int xInPixel = x * (3 * Tile.WIDTH);
                int yInPixel = y * (3 * Tile.HEIGHT);
                int widthInPixel = (3 * Tile.WIDTH);
                int heightInPixel = (3 * Tile.HEIGHT);
                Bitmap tileSprite = Bitmap.createBitmap(imageFrogger, xInPixel, yInPixel, widthInPixel, heightInPixel);

                Tile tile = new Tile("x");
                tile.init(game, x, y, tileSprite);
                frogger[y][x] = tile;
            }
        }

        return frogger;
    }

    public static Bitmap cropImageFrogger(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.frogger_background);
    }

    private Map<String, Rect> createTransferPointsForFrogger() {
        Map<String, Rect> transferPoints = new HashMap<String, Rect>();
        return transferPoints;
    }

    private List<Entity> createEntitiesForFrogger() {
        List<Entity> entities = new ArrayList<Entity>();
        // TODO: Insert scene specific entities here.
        return entities;
    }

    private List<Item> createItemsForFrogger() {
        List<Item> items = new ArrayList<Item>();
        // TODO: Insert scene specific items here.
        return items;
    }
}