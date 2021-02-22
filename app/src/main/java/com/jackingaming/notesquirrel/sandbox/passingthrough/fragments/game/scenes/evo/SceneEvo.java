package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo;

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

public class SceneEvo extends Scene {
    public static final int X_SPAWN_INDEX_DEFAULT = 4;
    public static final int Y_SPAWN_INDEX_DEFAULT = 4;

    private static SceneEvo uniqueInstance;

    private SceneEvo() {
        super();
        List<Entity> entitiesForEvo = createEntitiesForEvo();
        entityManager.loadEntities(entitiesForEvo);
        List<Item> itemsForEvo = createItemsForEvo();
        itemManager.loadItems(itemsForEvo);
    }

    public static SceneEvo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SceneEvo();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        // For scenes loaded from external file, the [create] and [init] steps in TileManager
        // are combined (unlike EntityManager and ItemManager).
        Tile[][] tilesForEvo = createAndInitTilesForEvo(game);
        tileManager.loadTiles(tilesForEvo);
        Map<String, Rect> transferPointsForEvo = createTransferPointsForEvo();
        tileManager.loadTransferPoints(transferPointsForEvo); // transferPoints are transient and should be reloaded everytime.
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

    private Tile[][] createAndInitTilesForEvo(Game game) {
        Bitmap imageEvo = cropImageEvo(game.getContext().getResources());
        int columns = imageEvo.getWidth() / Tile.WIDTH;
        int rows = imageEvo.getHeight() / Tile.HEIGHT;
        Tile[][] evo = new Tile[rows][columns];

        // Initialize the tiles (provide image and define walkable)
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int xInPixel = x * Tile.WIDTH;
                int yInPixel = y * Tile.HEIGHT;
                int widthInPixel = Tile.WIDTH;
                int heightInPixel = Tile.HEIGHT;
                Bitmap tileSprite = Bitmap.createBitmap(imageEvo, xInPixel, yInPixel, widthInPixel, heightInPixel);

                Tile tile = new Tile("x");
                tile.init(game, x, y, tileSprite);
                evo[y][x] = tile;
            }
        }

        return evo;
    }

    public static Bitmap cropImageEvo(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.mario_7_2);
    }

    private Map<String, Rect> createTransferPointsForEvo() {
        Map<String, Rect> transferPoints = new HashMap<String, Rect>();
        return transferPoints;
    }

    private List<Entity> createEntitiesForEvo() {
        List<Entity> entities = new ArrayList<Entity>();
        // TODO: Insert scene specific entities here.
        return entities;
    }

    private List<Item> createItemsForEvo() {
        List<Item> items = new ArrayList<Item>();
        // TODO: Insert scene specific items here.
        return items;
    }
}