package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.AnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.InputManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome02;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneWorldMapPart01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.HoneyPot;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneFarm;
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
        // ANIMATION
        animationManager.update(elapsed);

        // ATTACK_COOLDOWN
        // TODO: placeholder for AttackCooldown

        // RESET [offset-of-next-step] TO ZERO (standing still)
        xMove = 0f;
        yMove = 0f;

        // USER_INPUT (determine values of [offset-of-next-step]... potential movement)
        interpretInput();

        // MOVEMENT (check tile, item, entity, and transfer point collisions... actual movement)
        move();
    }

    public void interpretInput() {
        // Check InputManager's DirectionPadFragment-specific boolean fields.
        if (game.getInputManager().isPressingDirectionPad()) {
            if (game.getInputManager().isPressing(InputManager.Button.UP)) {
                direction = Direction.UP;
                yMove = -moveSpeed; // vertical NEGATIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.DOWN)) {
                direction = Direction.DOWN;
                yMove = moveSpeed;  // vertical POSITIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.LEFT)) {
                direction = Direction.LEFT;
                xMove = -moveSpeed; // horizontal NEGATIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.RIGHT)) {
                direction = Direction.RIGHT;
                xMove = moveSpeed;  // horizontal POSITIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.CENTER)) {
                direction = Direction.CENTER;
                xMove = 0;          // horizontal ZERO
                yMove = 0;          // vertical ZERO
            } else if (game.getInputManager().isPressing(InputManager.Button.UPLEFT)) {
                direction = Direction.UP_LEFT;
                xMove = -moveSpeed; // horizontal NEGATIVE
                yMove = -moveSpeed; // vertical NEGATIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.UPRIGHT)) {
                direction = Direction.UP_RIGHT;
                xMove = moveSpeed;  // horizontal POSITIVE
                yMove = -moveSpeed; // vertical NEGATIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.DOWNLEFT)) {
                direction = Direction.DOWN_LEFT;
                xMove = -moveSpeed; // horizontal NEGATIVE
                yMove = moveSpeed;  // vertical POSITIVE
            } else if (game.getInputManager().isPressing(InputManager.Button.DOWNRIGHT)) {
                direction = Direction.DOWN_RIGHT;
                xMove = moveSpeed;  // horizontal POSITIVE
                yMove = moveSpeed;  // vertical POSITIVE
            }
        }

        // Check InputManager's ButtonPadFragment-specific boolean fields.
        if (game.getInputManager().isJustPressed(InputManager.Button.A)) {
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isJustPressed(InputManager.Button.A)");
            if (game.getSceneManager().getCurrentScene() instanceof SceneWorldMapPart01) {
                doCheckItemCollisionViaClick();
            } else if (game.getSceneManager().getCurrentScene() instanceof SceneHome02) {
                if (checkTileCurrentlyFacing().getId().equals("5")) {
                    game.getSceneManager().changeScene("FARM");
                }
            }
        } else if (game.getInputManager().isJustPressed(InputManager.Button.B)) {
            String idTileCurrentlyFacing = checkTileCurrentlyFacing().getId();
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isJustPressed(InputManager.Button.B) idTileCurrentlyFacing: " + idTileCurrentlyFacing);
            if (game.getSceneManager().getCurrentScene() instanceof SceneFarm) {
                game.getSceneManager().pop();
            }
        } // Do NOT toggle [paused] from [update()]... will NOT be able to unpause!
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
            case CENTER:
                xOffset = 0;
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
            case CENTER:
                xIndex = xIndex;
                yIndex = yIndex;
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