package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.evo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.EelAnimationManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.DamageDoer;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Damageable;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.FishForm;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.HoneyPot;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class Eel extends Creature
        implements Damageable, DamageDoer {
    public enum State { PATROL, TURN, CHASE, ATTACK, HURT; }
    public static final int HEALTH_MAX_DEFAULT = 3;

    private EelAnimationManager eelAnimationManager;

    private State state;
    private float patrolLengthInPixelMax;
    private float patrolLengthInPixelCurrent;

    //ATTACK TIMER
    private long attackCooldown = 1_500L, attackTimer = attackCooldown;

    private int attackDamage;
    private int healthMax;
    private int health;

    public Eel(int xSpawn, int ySpawn, Direction direction, int patrolLengthInPixelMax) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH;
        height = Tile.HEIGHT / 2;
        moveSpeed = 0.5f;
        state = State.PATROL;
        this.direction = direction;
        this.patrolLengthInPixelMax = patrolLengthInPixelMax;
        patrolLengthInPixelCurrent = 0f;

        attackDamage = 1;
        healthMax = HEALTH_MAX_DEFAULT;
        health = healthMax;

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

        // ATTACK_COOLDOWN
        tickAttackCooldown(elapsed);

        xMove = 0f;
        yMove = 0f;

        determineNextMove();
        move();

        determineNextImage();
    }

    private void tickAttackCooldown(long elapsed) {
        attackTimer += elapsed;
        //attackTimer gets reset to 0 in respondToEntityCollision(Entity).
    }

    private int ticker = 0;
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
            case ATTACK:
                ticker++;
                //TODO: is this attack-timer-target long enough to iterate through all 2 attackFrames images???
                //make transition-back-to-State.PATROL be based on the index of attackFrames???
                if (ticker == 40) {
                    ticker = 0;
                    state = State.PATROL;
                }
                break;
            case HURT:
                ticker++;
                //only has 1 hurtFrames image, so transition-back-to-State.PATROL
                //CAN BE BASED ON A TIME LIMIT (as oppose to State.ATTACK being based on its Animation's index).
                if (ticker == 40) {
                    ticker = 0;
                    state = State.PATROL;
                }
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".determineNextMove() switch's default block.");
                break;
        }
    }

    private void determineNextImage() {
        Direction directionOfMyself = direction;
        image = eelAnimationManager.getCurrentFrame(state, directionOfMyself);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rectOfImage = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect rectOnScreen = null;

        if (state == State.ATTACK) {
            // Transform from "wide, short" to "narrow, tall".
            Rect rectOfEel = getCollisionBounds(0f, 0f);
            int width = rectOfEel.right - rectOfEel.left;
            int height = rectOfEel.bottom - rectOfEel.top;

            if (direction == Direction.RIGHT) {
                rectOfEel.left = rectOfEel.right - (width / 2); // scoot image to right edge
            }
            rectOfEel.right = rectOfEel.left + (width / 2);
            rectOfEel.bottom = rectOfEel.top + (2 * height);

            rectOnScreen = GameCamera.getInstance().convertToScreenRect(rectOfEel);
        } else {
            // No transformation needed, use normal bounds.
            rectOnScreen = GameCamera.getInstance().convertToScreenRect(getCollisionBounds(0f, 0f));
        }

        canvas.drawBitmap(image, rectOfImage, rectOnScreen, null);
    }

    @Override
    public boolean respondToEntityCollision(Entity e) {
        if (e instanceof Player) {
            // ATTACK_COOLDOWN
            if (attackTimer < attackCooldown) {
                return true;
            }

            // PERFORM ATTACK
            attackTimer = 0;
            Player player = (Player) e;
            FishForm fishForm = ((FishForm) player.getForm());
            doDamage(fishForm);
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

    @Override
    public void takeDamage(int incomingDamage) {
        state = State.HURT;
        health -= incomingDamage;

        if (health <= 0) {
            active = false;
            die();
        }
    }

    @Override
    public void die() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".die()");
        // TODO: drop items, reward exp points, etc.
        Item honeyPot = new HoneyPot();
        int widthOfHoneyPot = Tile.WIDTH / 2;
        int heightOfHoneyPot = Tile.HEIGHT / 2;
        honeyPot.setWidth(widthOfHoneyPot);
        honeyPot.setHeight(heightOfHoneyPot);
        int xCenterOfKelpAccountingForWidthOfHoneyPot = (int) (x + (width / 2) - (widthOfHoneyPot / 2));
        int yCenterOfKelpAccountingForHeightOfHoneyPot = (int) (y + (height / 2) - (heightOfHoneyPot / 2));
        honeyPot.setPosition(xCenterOfKelpAccountingForWidthOfHoneyPot, yCenterOfKelpAccountingForHeightOfHoneyPot);
        honeyPot.init(game);
        game.getSceneManager().getCurrentScene().getItemManager().addItem(honeyPot);
    }

    @Override
    public void doDamage(Damageable damageable) {
        state = State.ATTACK;
        damageable.takeDamage(attackDamage);
    }
}