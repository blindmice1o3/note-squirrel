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

public class SeaJelly extends Creature {
    private static final int ANIMATION_SPEED_DEFAULT = 300;
    transient private static Bitmap[] framesPatrol;
    transient private static Bitmap[] framesAttackLeft;
    transient private static Bitmap[] framesAttackRight;
    transient private static Bitmap[] framesHurt;

    transient private Animation animationPatrol;
    transient private Animation animationAttackLeft;
    transient private Animation animationAttackRight;
    transient private Animation animationHurt;

    private float patrolLengthInPixelMax;
    private float patrolLengthInPixelCurrent;

    public SeaJelly(int xSpawn, int ySpawn, Direction direction, int patrolLengthInPixelMax) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH / 2;
        height = Tile.HEIGHT;

        this.direction = direction;
        this.patrolLengthInPixelMax = patrolLengthInPixelMax;
        patrolLengthInPixelCurrent = 0f;
        moveSpeed = 0.5f;
    }

    @Override
    public void init(Game game) {
        super.init(game);

        if (framesPatrol == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            // CENTER (forward-facing)
            framesPatrol = new Bitmap[3];
            framesPatrol[0] = Bitmap.createBitmap(spriteSheet, 552, 29, 16, 32);
            framesPatrol[1] = Bitmap.createBitmap(spriteSheet, 568, 29, 16, 32);
            framesPatrol[2] = Bitmap.createBitmap(spriteSheet, 584, 29, 16, 32);
        }
        animationPatrol = new Animation(framesPatrol, ANIMATION_SPEED_DEFAULT);

        if (framesAttackLeft == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            framesAttackLeft = new Bitmap[4];
            framesAttackLeft[0] = Bitmap.createBitmap(spriteSheet, 599, 27, 16, 32);
            framesAttackLeft[1] = Bitmap.createBitmap(spriteSheet, 619, 29, 16, 32);
            framesAttackLeft[2] = Bitmap.createBitmap(spriteSheet, 639, 34, 32, 32);
            framesAttackLeft[3] = Bitmap.createBitmap(spriteSheet, 674, 32, 32, 32);
        }
        animationAttackLeft = new Animation(framesAttackLeft, ANIMATION_SPEED_DEFAULT);

        if (framesAttackRight == null) {
            framesAttackRight = Animation.flipImageArrayHorizontally(framesAttackLeft);
        }
        animationAttackRight = new Animation(framesAttackRight, ANIMATION_SPEED_DEFAULT);

        if (framesHurt == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);
            // CENTER (forward-facing)
            framesHurt = new Bitmap[1];
            framesHurt[0] = Bitmap.createBitmap(spriteSheet, 714, 28, 16, 32);
        }
        animationHurt = new Animation(framesHurt, ANIMATION_SPEED_DEFAULT);
    }

    @Override
    public void update(long elapsed) {
        animationPatrol.update(elapsed);
        animationAttackLeft.update(elapsed);
        animationAttackRight.update(elapsed);
        animationHurt.update(elapsed);

        xMove = 0f;
        yMove = 0f;

        determineNextMove();
        move();

        image = animationPatrol.getCurrentFrame();
    }

    private void determineNextMove() {
        // PATROL (set value for future-change-in-position).
        if (patrolLengthInPixelCurrent < patrolLengthInPixelMax) {
            switch (direction) {
                case DOWN:
                    yMove = moveSpeed;
                    patrolLengthInPixelCurrent += moveSpeed;
                    break;
                case UP:
                    yMove = -moveSpeed;
                    patrolLengthInPixelCurrent += moveSpeed;
                    break;
                default:
                    yMove = 0f;
                    break;
            }
        }
        // END OF PATROL LENGTH (reverse direction).
        else {
            if (direction == Direction.DOWN) {
                direction = Direction.UP;
                patrolLengthInPixelCurrent = 0f;
            } else if (direction == Direction.UP) {
                direction = Direction.DOWN;
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