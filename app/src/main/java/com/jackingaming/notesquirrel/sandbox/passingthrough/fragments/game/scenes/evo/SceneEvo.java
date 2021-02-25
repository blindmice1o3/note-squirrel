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

    private boolean compareTwoSprites(Bitmap sprite1, Bitmap sprite2, int x, int y) {
        //if width or height are not the same, the two sprites are NOT the same.
        if ((sprite1.getWidth() != sprite1.getWidth()) || (sprite1.getHeight() != sprite2.getHeight())) {
            return false;
        }

        //if any pixels are not the same, the two sprite are NOT the same.
        //for (int y = 0; y < Tile.HEIGHT; y++) {
        //for (int x = 0; x < Tile.WIDTH; x++) {
        int pixelSprite1 = sprite1.getPixel(x, y);
        int redSprite1 = (pixelSprite1 >> 16) & 0xff;
        int greenSprite1 = (pixelSprite1 >> 8) & 0xff;
        int blueSprite1 = (pixelSprite1) & 0xff;

        int pixelSprite2 = sprite2.getPixel(x, y);
        int redSprite2 = (pixelSprite2 >> 16) & 0xff;
        int greenSprite2 = (pixelSprite2 >> 8) & 0xff;
        int blueSprite2 = (pixelSprite2) & 0xff;

        if ( ((redSprite1 == redSprite2) && (greenSprite1 == greenSprite2) && (blueSprite1 == blueSprite2)) ) {
            return true;
        }
        //}
        //}

        return false;
    }

    private Tile[][] createAndInitTilesForEvo(Game game) {
        // entire scene
        Bitmap imageEvo = cropImageEvo(game.getContext().getResources());
        int columns = imageEvo.getWidth() / Tile.WIDTH;
        int rows = imageEvo.getHeight() / Tile.HEIGHT;
        Tile[][] evo = new Tile[rows][columns];

        // tile-image targets
        Bitmap coin = Bitmap.createBitmap(imageEvo, 224, 184, Tile.WIDTH, Tile.HEIGHT);
        Bitmap brickGreen = Bitmap.createBitmap(imageEvo, 0, 200, Tile.WIDTH, Tile.HEIGHT);
        Bitmap coralPink1 = Bitmap.createBitmap(imageEvo, 176, 184, Tile.WIDTH, Tile.HEIGHT);
        Bitmap coralPink2 = Bitmap.createBitmap(imageEvo, 1632, 120, Tile.WIDTH, Tile.HEIGHT);
        Bitmap coralPink3 = Bitmap.createBitmap(imageEvo, 2384, 184, Tile.WIDTH, Tile.HEIGHT);

        ArrayList<Bitmap> solidTileSearchTargets = new ArrayList<Bitmap>();
        solidTileSearchTargets.add(coin);
        solidTileSearchTargets.add(brickGreen);
        solidTileSearchTargets.add(coralPink1);
        solidTileSearchTargets.add(coralPink2);
        solidTileSearchTargets.add(coralPink3);

        //check each pixels in the tile (16x16) within the 192tiles by 14tiles map.
        for (int y = 0; y < rows-1; y++) {
            for (int x = 0; x < columns; x++) {

                int xOffset = (x * Tile.WIDTH);
                int yOffset = (y * Tile.HEIGHT) + 8;
                Bitmap currentTile = Bitmap.createBitmap(imageEvo,
                        xOffset, yOffset, Tile.WIDTH, Tile.HEIGHT);

                //for each tile, check if it's one of the solidTileSearchTargets.
                for (Bitmap solidTileTarget : solidTileSearchTargets) {

                    int xx = 0;
                    int yy = 0;

                    if (solidTileTarget == brickGreen) {
                        xx = 0;
                        yy = 0;
                    } else {
                        xx = 9;
                        yy = 2;
                    }

                    //if it's the same, we have a SOLID tile.
                    if (compareTwoSprites(solidTileTarget, currentTile, xx, yy)) {
                        Tile tile = new Tile("x");
                        tile.setWalkable(false);
                        tile.init(game, x, y, currentTile);
                        evo[y][x] = tile;
                        break;
                    }

                    if ((evo[y][x] == null) && (solidTileTarget == coralPink1)) {
                        xx = 8;
                        yy = 14;
                        if (compareTwoSprites(solidTileTarget, currentTile, xx, yy)) {
                            Tile tile = new Tile("x");
                            tile.setWalkable(false);
                            tile.init(game, x, y, currentTile);
                            evo[y][x] = tile;
                            break;
                        }
                    }
                    if ((evo[y][x] == null) && (solidTileTarget == coralPink1)) {
                        xx = 8;
                        yy = 15;
                        if (compareTwoSprites(solidTileTarget, currentTile, xx, yy)) {
                            Tile tile = new Tile("x");
                            tile.setWalkable(false);
                            tile.init(game, x, y, currentTile);
                            evo[y][x] = tile;
                            break;
                        }
                    }

                }

                if (evo[y][x] == null) {
                    // walkable
                    Tile tile = new Tile("o");
                    tile.init(game, x, y, currentTile);
                    evo[y][x] = tile;
                }
            }
        }

        // bottom row should be all solid brick tiles.
        for (int x = 0; x < columns; x++) {
            Tile tile = new Tile("x");
            tile.setWalkable(false);
            tile.init(game, x, rows-1, brickGreen);
            evo[rows-1][x] = tile;
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