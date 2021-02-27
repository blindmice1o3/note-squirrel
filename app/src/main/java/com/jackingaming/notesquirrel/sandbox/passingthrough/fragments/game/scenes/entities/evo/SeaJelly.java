package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.evo;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.SeaJellyAnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class SeaJelly extends Creature {
    public enum State { PATROL, ATTACK, HURT; }

    private SeaJellyAnimationManager seaJellyAnimationManager;

    private float patrolLengthInPixelMax;
    private float patrolLengthInPixelCurrent;
    private State state;

    public SeaJelly(int xSpawn, int ySpawn, Direction direction, int patrolLengthInPixelMax) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH / 2;
        height = Tile.HEIGHT;

        this.direction = direction;
        this.patrolLengthInPixelMax = patrolLengthInPixelMax;
        patrolLengthInPixelCurrent = 0f;
        moveSpeed = 0.5f;
        state = State.PATROL;
        seaJellyAnimationManager = new SeaJellyAnimationManager();
    }

    @Override
    public void init(Game game) {
        super.init(game);
        seaJellyAnimationManager.init(game);
    }

    @Override
    public void update(long elapsed) {
        seaJellyAnimationManager.update(elapsed);

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
                    if (direction == Direction.DOWN) {
                        yMove = moveSpeed;
                        patrolLengthInPixelCurrent += moveSpeed;
                    } else if (direction == Direction.UP) {
                        yMove = -moveSpeed;
                        patrolLengthInPixelCurrent += moveSpeed;
                    }
                }
                // END OF PATROL LENGTH (reverse direction).
                else {
                    patrolLengthInPixelCurrent = 0f;
                    if (direction == Direction.DOWN) {
                        direction = Direction.UP;
                    } else if (direction == Direction.UP) {
                        direction = Direction.DOWN;
                    }
                }
                break;
            case ATTACK:
                break;
            case HURT:
                break;
        }
    }

    private void determineNextImage() {
        // TODO: if opponent present, figure out direction.
        Direction directionOfOpponent = Direction.LEFT;
        image = seaJellyAnimationManager.getCurrentFrame(state, directionOfOpponent);
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