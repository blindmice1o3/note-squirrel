package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.evo;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.EelAnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class Eel extends Creature {
    public enum State { PATROL, TURN, CHASE, ATTACK, HURT; }

    private EelAnimationManager eelAnimationManager;

    private float patrolLengthInPixelMax;
    private float patrolLengthInPixelCurrent;
    private State state;

    public Eel(int xSpawn, int ySpawn, Direction direction, int patrolLengthInPixelMax) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH;
        height = Tile.HEIGHT / 2;

        this.direction = direction;
        this.patrolLengthInPixelMax = patrolLengthInPixelMax;
        patrolLengthInPixelCurrent = 0f;
        moveSpeed = 0.5f;
        state = State.PATROL;
        eelAnimationManager = new EelAnimationManager();
    }

    @Override
    public void init(Game game) {
        super.init(game);
        eelAnimationManager.init(game);
    }

    @Override
    public void update(long elapsed) {
        eelAnimationManager.update(elapsed);

        xMove = 0f;
        yMove = 0f;

        determineNextMove();
        move();

        determineNextImage();
    }

    private void determineNextMove() {
        switch (state) {
            case PATROL:
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
                    patrolLengthInPixelCurrent = 0f;
                    state = State.TURN;
                }
                break;
            case TURN:
                if (direction == Direction.LEFT) {
                    direction = Direction.RIGHT;
                } else if (direction == Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                state = State.PATROL;
                break;
        }
    }

    private void determineNextImage() {
        Direction directionOfMyself = direction;
        image = eelAnimationManager.getCurrentFrame(state, directionOfMyself);
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