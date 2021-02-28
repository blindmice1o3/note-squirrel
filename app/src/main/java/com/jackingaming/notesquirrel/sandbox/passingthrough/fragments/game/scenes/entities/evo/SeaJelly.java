package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.evo;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.SeaJellyAnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.FishForm;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.FishStateManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class SeaJelly extends Creature {
    public enum State { PATROL, ATTACK, HURT; }

    private SeaJellyAnimationManager seaJellyAnimationManager;

    private State state;
    private float patrolLengthInPixelMax;
    private float patrolLengthInPixelCurrent;
    private int attackDamage;


    public SeaJelly(int xSpawn, int ySpawn, Direction direction, int patrolLengthInPixelMax) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH / 2;
        height = Tile.HEIGHT;
        moveSpeed = 0.5f;
        state = State.PATROL;
        this.direction = direction;
        this.patrolLengthInPixelMax = patrolLengthInPixelMax;
        patrolLengthInPixelCurrent = 0f;
        attackDamage = 5;

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

    private int ticker = 0;
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
                ticker++;
                //TODO: is this attack-timer-target long enough to iterate through all 4 attackFrames images???
                //make transition-back-to-State.IDLE be based on the index of attackFrames???
                if (ticker == 40) {
                    ticker = 0;
                    state = State.PATROL;
                }
                break;
            case HURT:
                ticker++;
                //only has 1 hurtFrames image, so transition-back-to-State.IDLE
                //CAN BE BASED ON A TIME LIMIT (as oppose to State.ATTACK being based on its Animation's index).
                if (ticker == 40) {
                    ticker = 0;
                    state = State.PATROL;
                }
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".update(long elapsed) switch's default block.");
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
        if (e instanceof Player) {
            Player player = (Player)e;
            if (player.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xMove, yMove))) {
                //TODO: Should the timer be reset IF ITS attackFrames ISN'T DONE WITH PREVIOUS ITERATION?
                //if the player REPEATEDLY move into the SeaJelly's movement path... it causes a weird-looking-resetting effect.
                ticker = 0;
                state = State.ATTACK;

                //TODO: RENDERING DAMAGE-DEALT-TO-PLAYER TO SCREEN (using Reward/RewardManager FOR NOW).
                ////////////////////////////////////////////////////////
                FishForm fishForm = ((FishForm)player.getForm());
                fishForm.takeDamage(attackDamage);
                fishForm.getFishStateManager().setCurrentActionState(FishStateManager.ActionState.HURT);
                //TODO: move player.setCurrentHeadAnimation = hurtHeadAnimation to Fish.tick().
                ////////////////////////////////////////////////////////
            }
        }
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