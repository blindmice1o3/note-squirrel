package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.InputManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.PoohAnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.HoneyPot;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome02;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneWorldMapPart01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneFarm;

public class PoohForm
        implements Form {
    transient private Game game;

    private Player player;
    private PoohAnimationManager poohAnimationManager;

    public PoohForm() {
        poohAnimationManager = new PoohAnimationManager();
    }

    @Override
    public void init(Game game) {
        this.game = game;
        player = Player.getInstance();
        poohAnimationManager.init(game);
    }

    @Override
    public void update(long elapsed) {
        // ANIMATION
        poohAnimationManager.update(elapsed);

        // ATTACK_COOLDOWN
        // TODO: placeholder for AttackCooldown

        // RESET [offset-of-next-step] TO ZERO (standing still)
        player.setxMove(0f);
        player.setyMove(0f);
        player.setMoveSpeed(Creature.MOVE_SPEED_DEFAULT);

        // USER_INPUT (determine values of [offset-of-next-step]... potential movement)
        interpretInput();

        // MOVEMENT (check tile, item, entity, and transfer point collisions... actual movement)
        player.move();

        // PREPARE_FOR_RENDER
        determineNextImage();
    }

    @Override
    public void draw(Canvas canvas) {
        // Intentionally blank.
    }

    @Override
    public void interpretInput() {
        // Check InputManager's ButtonPadFragment-specific boolean fields.
        if (game.getInputManager().isJustPressed(InputManager.Button.A)) {
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isJustPressed(InputManager.Button.A)");
            if (game.getSceneManager().getCurrentScene() instanceof SceneWorldMapPart01) {
                player.doCheckItemCollisionViaClick();
            } else if (game.getSceneManager().getCurrentScene() instanceof SceneHome02) {
                if (player.checkTileCurrentlyFacing().getId().equals("5")) {
                    game.getSceneManager().changeScene("FARM");
                } else if (player.checkTileCurrentlyFacing().getId().equals("4")) {
                    game.getSceneManager().changeScene("COMPUTER");
                }
            }
        } else if (game.getInputManager().isPressing(InputManager.Button.B)) {
            String idTileCurrentlyFacing = player.checkTileCurrentlyFacing().getId();
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isPressing(InputManager.Button.B) idTileCurrentlyFacing: " + idTileCurrentlyFacing);

            float doubledMoveSpeedDefault = 2 * Creature.MOVE_SPEED_DEFAULT;
            player.setMoveSpeed(doubledMoveSpeedDefault);

            if (game.getGameTitle().equals("Pocket Critters") &&
                    game.getSceneManager().getCurrentScene() instanceof SceneFarm) {
                game.getSceneManager().pop();
            }
        } else if (game.getInputManager().isJustPressed(InputManager.Button.MENU)) {
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isJustPressed(InputManager.Button.MENU)");
            game.getStateManager().toggleMenuState();
        }


        float moveSpeed = player.getMoveSpeed();
        // Check InputManager's DirectionPadFragment-specific boolean fields.
        if (game.getInputManager().isPressing(InputManager.Button.UP)) {
            player.setDirection(Creature.Direction.UP);
            player.setyMove(-moveSpeed);    // vertical NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.DOWN)) {
            player.setDirection(Creature.Direction.DOWN);
            player.setyMove(moveSpeed);     // vertical POSITIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.LEFT)) {
            player.setDirection(Creature.Direction.LEFT);
            player.setxMove(-moveSpeed);    // horizontal NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.RIGHT)) {
            player.setDirection(Creature.Direction.RIGHT);
            player.setxMove(moveSpeed);     // horizontal POSITIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.CENTER)) {
            player.setDirection(Creature.Direction.CENTER);
            player.setxMove(0f);            // horizontal ZERO
            player.setyMove(0f);            // vertical ZERO
        } else if (game.getInputManager().isPressing(InputManager.Button.UPLEFT)) {
            player.setDirection(Creature.Direction.UP_LEFT);
            player.setxMove(-moveSpeed);    // horizontal NEGATIVE
            player.setyMove(-moveSpeed);    // vertical NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.UPRIGHT)) {
            player.setDirection(Creature.Direction.UP_RIGHT);
            player.setxMove(moveSpeed);     // horizontal POSITIVE
            player.setyMove(-moveSpeed);    // vertical NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.DOWNLEFT)) {
            player.setDirection(Creature.Direction.DOWN_LEFT);
            player.setxMove(-moveSpeed);    // horizontal NEGATIVE
            player.setyMove(moveSpeed);     // vertical POSITIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.DOWNRIGHT)) {
            player.setDirection(Creature.Direction.DOWN_RIGHT);
            player.setxMove(moveSpeed);     // horizontal POSITIVE
            player.setyMove(moveSpeed);     // vertical POSITIVE
        }
    }

    @Override
    public void determineNextImage() {
        Bitmap imageNext = poohAnimationManager.getCurrentFrame(player.getDirection(),
                player.getxMove(), player.getyMove());
        player.setImage(imageNext);
    }

    @Override
    public void respondToTransferPointCollision(String key) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".respondToTransferPointCollision(String key) key: " + key);
        // TODO: change scene.
        game.getSceneManager().changeScene(key);
    }

    @Override
    public boolean respondToEntityCollision(Entity e) {
        // TODO: if (e instanceof SignPost) { readSignPost(); }

        if (e instanceof com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.frogger.Log) {
            return false;
        }

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
        return true;
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
}