package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.AnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.SceneWorldMapPart01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.HoneyPot;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class Player extends Creature {
    private static Player uniqueInstance;
    private AnimationManager animationManager;

    private Player(int xSpawn, int ySpawn) {
        super(xSpawn, ySpawn);
    }

    public static Player getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Player(
                    (SceneWorldMapPart01.X_SPAWN_INDEX_DEFAULT * Tile.WIDTH),
                    (SceneWorldMapPart01.Y_SPAWN_INDEX_DEFAULT * Tile.HEIGHT));
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        super.init(game);
        animationManager = new AnimationManager();
        animationManager.init(game.getContext().getResources());
    }

    @Override
    public void update(long elapsed) {
        animationManager.update(elapsed);

        xMove = 0f;
        yMove = 0f;

        move(direction);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap imageByDirection = animationManager.getCurrentFrame(direction);
        Rect rectOfImage = new Rect(0, 0, imageByDirection.getWidth(), imageByDirection.getHeight());
        Rect rectOnScreen = new Rect(
                (int) ((x - GameCamera.getInstance().getX()) * GameCamera.getInstance().getWidthPixelToViewportRatio()),
                (int) ((y - GameCamera.getInstance().getY()) * GameCamera.getInstance().getHeightPixelToViewportRatio()),
                (int) ((x + width - GameCamera.getInstance().getX()) * GameCamera.getInstance().getWidthPixelToViewportRatio()),
                (int) ((y + height - GameCamera.getInstance().getY()) * GameCamera.getInstance().getHeightPixelToViewportRatio()));
        canvas.drawBitmap(imageByDirection, rectOfImage, rectOnScreen, null);
    }

    @Override
    public void respondToTransferPointCollision(String key) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".respondToTransferPointCollision(String key) key: " + key);
        // TODO: change scene.
        game.getSceneManager().changeScene(key);
    }

    @Override
    public void respondToEntityCollision(Entity e) {
        // TODO: if (e instanceof SignPost) { readSignPost(); }

//        ((PassingThroughActivity)game.getContext()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(game.getContext(), getClass().getSimpleName() + ".respondToEntityCollision(Entity e) testing Toast-message from non-UI thread.", Toast.LENGTH_SHORT).show();
//            }
//        });

//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(game.getContext(), getClass().getSimpleName() + ".respondToEntityCollision(Entity e) testing Toast-message from non-UI thread.", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void respondToItemCollisionViaClick(Item item) {
        game.addItemToBackpack(item);
        game.getSceneManager().getCurrentScene().getItemManager().removeItem(item);
    }

    @Override
    public void respondToItemCollisionViaMove(Item item) {
        if (item instanceof HoneyPot) {
            game.getSceneManager().getCurrentScene().getItemManager().removeItem(item);
            game.incrementCurrency();
        }
    }

    public void doCheckItemCollisionViaClick() {
        int xOffset = 0;
        int yOffset = 0;
        switch (direction) {
            case UP:
                xOffset = 0;
                yOffset = -height;
                break;
            case DOWN:
                xOffset = 0;
                yOffset = height;
                break;
            case LEFT:
                xOffset = -width;
                yOffset = 0;
                break;
            case RIGHT:
                xOffset = width;
                yOffset = 0;
                break;
            case UP_LEFT:
                xOffset = -width;
                yOffset = -height;
                break;
            case UP_RIGHT:
                xOffset = width;
                yOffset = -height;
                break;
            case DOWN_LEFT:
                xOffset = -width;
                yOffset = height;
                break;
            case DOWN_RIGHT:
                xOffset = width;
                yOffset = height;
                break;
            default:
                return;
        }

        checkItemCollision(xOffset, yOffset, true);
    }

    public Tile checkTileCurrentlyFacing() {
        Tile[][] tiles = game.getSceneManager().getCurrentScene().getTileManager().getTiles();

        int xIndex = (int)(x / Tile.WIDTH);
        int yIndex = (int)(y / Tile.HEIGHT);
        switch (direction) {
            case UP:
                yIndex = yIndex - 1;
                break;
            case DOWN:
                yIndex = yIndex + 1;
                break;
            case LEFT:
                xIndex = xIndex - 1;
                break;
            case RIGHT:
                xIndex = xIndex + 1;
                break;
            case UP_LEFT:
                xIndex = xIndex - 1;
                yIndex = yIndex - 1;
                break;
            case UP_RIGHT:
                xIndex = xIndex + 1;
                yIndex = yIndex - 1;
                break;
            case DOWN_LEFT:
                xIndex = xIndex - 1;
                yIndex = yIndex + 1;
                break;
            case DOWN_RIGHT:
                xIndex = xIndex + 1;
                yIndex = yIndex + 1;
                break;
            default:
                return null;
        }

        return tiles[yIndex][xIndex];
    }
}