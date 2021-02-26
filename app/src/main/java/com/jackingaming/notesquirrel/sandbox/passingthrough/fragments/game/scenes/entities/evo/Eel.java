package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.evo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Animation;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class Eel extends Creature {
    private static final int ANIMATION_SPEED_DEFAULT = 300;
    transient private static Bitmap[] framesPatrolLeft;
    transient private static Bitmap[] framesPatrolRight;
    transient private static Bitmap[] framesPatrolRightTurnToLeft;
    transient private static Bitmap[] framesPatrolLeftTurnToRight;
    transient private static Bitmap[] framesAttackLeft;
    transient private static Bitmap[] framesAttackRight;
    transient private static Bitmap[] framesHurtLeft;
    transient private static Bitmap[] framesHurtRight;

    transient private Animation animationPatrolLeft;
    transient private Animation animationPatrolRight;
    transient private Animation animationPatrolRightTurnToLeft;
    transient private Animation animationPatrolLeftTurnToRight;
    transient private Animation animationAttackLeft;
    transient private Animation animationAttackRight;
    transient private Animation animationHurtLeft;
    transient private Animation animationHurtRight;

    private float patrolLengthInPixelMax;
    private float patrolLengthInPixelCurrent;

    public Eel(int xSpawn, int ySpawn, Direction direction, int patrolLengthInPixelMax) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH;
        height = Tile.HEIGHT / 2;

        this.direction = direction;
        this.patrolLengthInPixelMax = patrolLengthInPixelMax;
        patrolLengthInPixelCurrent = 0f;
        moveSpeed = 0.5f;
    }

    @Override
    public void init(Game game) {
        super.init(game);

        if (framesPatrolLeft == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            framesPatrolLeft = new Bitmap[4];
            framesPatrolLeft[0] = Bitmap.createBitmap(spriteSheet, 554, 70, 32, 16);
            framesPatrolLeft[1] = Bitmap.createBitmap(spriteSheet, 587, 70, 32, 16);
            framesPatrolLeft[2] = Bitmap.createBitmap(spriteSheet, 620, 70, 32, 16);
            framesPatrolLeft[3] = Bitmap.createBitmap(spriteSheet, 654, 70, 32, 16);
        }
        animationPatrolLeft = new Animation(framesPatrolLeft, ANIMATION_SPEED_DEFAULT);

        if (framesPatrolRight == null) {
            framesPatrolRight = Animation.flipImageArrayHorizontally(framesPatrolLeft);
        }
        animationPatrolRight = new Animation(framesPatrolRight, ANIMATION_SPEED_DEFAULT);

        if (framesPatrolRightTurnToLeft == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            framesPatrolRightTurnToLeft = new Bitmap[1];
            framesPatrolRightTurnToLeft[0] = Bitmap.createBitmap(spriteSheet, 687, 70, 28, 16);
        }
        animationPatrolRightTurnToLeft = new Animation(framesPatrolRightTurnToLeft, ANIMATION_SPEED_DEFAULT);

        if (framesPatrolLeftTurnToRight == null) {
            framesPatrolLeftTurnToRight = Animation.flipImageArrayHorizontally(framesPatrolRightTurnToLeft);
        }
        animationPatrolLeftTurnToRight = new Animation(framesPatrolLeftTurnToRight, ANIMATION_SPEED_DEFAULT);

        if (framesAttackLeft == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            framesAttackLeft = new Bitmap[1];
            framesAttackLeft[0] = Bitmap.createBitmap(spriteSheet, 716, 62, 16, 32);
        }
        animationAttackLeft = new Animation(framesAttackLeft, ANIMATION_SPEED_DEFAULT);

        if (framesAttackRight == null) {
            framesAttackRight = Animation.flipImageArrayHorizontally(framesAttackLeft);
        }
        animationAttackRight = new Animation(framesAttackRight, ANIMATION_SPEED_DEFAULT);

        if (framesHurtLeft == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            framesHurtLeft = new Bitmap[1];
            framesHurtLeft[0] = Bitmap.createBitmap(spriteSheet, 732, 69, 32, 16);
        }
        animationHurtLeft = new Animation(framesHurtLeft, ANIMATION_SPEED_DEFAULT);

        if (framesHurtRight == null) {
            framesHurtRight = Animation.flipImageArrayHorizontally(framesHurtLeft);
        }
        animationHurtRight = new Animation(framesHurtRight, ANIMATION_SPEED_DEFAULT);
    }

    @Override
    public void update(long elapsed) {
        animationPatrolLeft.update(elapsed);
        animationPatrolRight.update(elapsed);
        animationPatrolRightTurnToLeft.update(elapsed);
        animationPatrolLeftTurnToRight.update(elapsed);
        animationAttackLeft.update(elapsed);
        animationAttackRight.update(elapsed);
        animationHurtLeft.update(elapsed);
        animationHurtRight.update(elapsed);

        xMove = 0f;
        yMove = 0f;

        determineNextMove();
        move();

        if (direction == Direction.LEFT) {
            image = animationPatrolLeft.getCurrentFrame();
        } else if (direction == Direction.RIGHT) {
            image = animationPatrolRight.getCurrentFrame();
        }
    }

    private void determineNextMove() {
        // PATROL (set value for future-change-in-position).
        if (patrolLengthInPixelCurrent < patrolLengthInPixelMax) {
            if (direction == Direction.LEFT) {
                xMove = -moveSpeed;
                patrolLengthInPixelCurrent += moveSpeed;
            } else if (direction == Direction.RIGHT) {
                xMove = moveSpeed;
                patrolLengthInPixelCurrent += moveSpeed;
            }
        }
        // END OF PATROL LENGTH (reverse direction).
        else {
            if (direction == Direction.LEFT) {
                direction = Direction.RIGHT;
                patrolLengthInPixelCurrent = 0f;
            } else if (direction == Direction.RIGHT) {
                direction = Direction.LEFT;
                patrolLengthInPixelCurrent = 0f;
            }
        }
    }

    @Override
    public boolean respondToEntityCollision(Entity e) {
        return true;
    }

    @Override
    public void respondToItemCollisionViaClick(Item item) {

    }

    @Override
    public void respondToItemCollisionViaMove(Item item) {

    }

    @Override
    public void respondToTransferPointCollision(String key) {

    }
}