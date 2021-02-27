package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.InputManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.Animation;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Form;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class FishForm
        implements Form {
    public static final int BODY_ANIMATION_SPEED_DEFAULT = 600;
    public static final int HEAD_ANIMATION_SPEED_DEFAULT = 400;
    public enum DirectionFacing { LEFT, RIGHT; }

    transient private Game game;

    private Player player;
    private FishStateManager fishStateManager;
    private DirectionFacing directionFacing;

    //EXPERIENCE POINTS
    private int experiencePoints;

    //MAX_HEALTH
    private int health;
    private int healthMax;

    //ANIMATIONS
    private Animation idleHeadAnimation, eatHeadAnimation, biteHeadAnimation, hurtHeadAnimation;
    private Animation currentHeadAnimation;
    private Animation currentBodyAnimation;

    //ATTACK TIMER
    private long attackCooldown = 800000000L, attackTimer = attackCooldown;

    private int speed;
    private int damageBite;
    private int armor;

    public FishForm(Player player) {
        this.player = player;
        fishStateManager = new FishStateManager(player);
    }

    @Override
    public void init(Game game) {
        this.game = game;
        fishStateManager.init(game);

        directionFacing = DirectionFacing.RIGHT;
        experiencePoints = 2000;
        ///////////////
        healthMax = 20;
        ///////////////
        health = healthMax;

        Assets.init(game);

        player.setWidth(Tile.WIDTH);
        player.setHeight(Tile.HEIGHT / 2);
        player.setBounds(new Rect(0, 0, player.getWidth(), player.getHeight()));

        initAnimations();

/*
        currentHeadImage = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [fishStateManager.getCurrentActionState().ordinal()]
                [0];
        currentBodyImage = Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentFinPectoral().ordinal()]
                [fishStateManager.getCurrentTail().ordinal()]
                [0];
 */

        speed = fishStateManager.getAgility();
        damageBite = fishStateManager.getDamageBite();
        armor = fishStateManager.getDefense();
    }

    public void initAnimations() {
        initHeadAnimations();
        switch (fishStateManager.getCurrentTail()) {
            case ORIGINAL:
                currentBodyAnimation = new Animation(
                        Assets.tailOriginal[fishStateManager.getCurrentBodySize().ordinal()]
                                [fishStateManager.getCurrentBodyTexture().ordinal()]
                                [fishStateManager.getCurrentFinPectoral().ordinal()], BODY_ANIMATION_SPEED_DEFAULT);
                break;
            case COELAFISH:
                currentBodyAnimation = new Animation(
                        Assets.tailCoelafish[fishStateManager.getCurrentBodySize().ordinal()]
                                [fishStateManager.getCurrentBodyTexture().ordinal()]
                                [fishStateManager.getCurrentFinPectoral().ordinal()], BODY_ANIMATION_SPEED_DEFAULT);
                break;
            case TERATISU:
                currentBodyAnimation = new Animation(
                        Assets.tailTeratisu[fishStateManager.getCurrentBodySize().ordinal()]
                                [fishStateManager.getCurrentBodyTexture().ordinal()]
                                [fishStateManager.getCurrentFinPectoral().ordinal()], BODY_ANIMATION_SPEED_DEFAULT);
                break;
            case ZINICHTHY:
                currentBodyAnimation = new Animation(
                        Assets.tailZinichthy[fishStateManager.getCurrentBodySize().ordinal()]
                                [fishStateManager.getCurrentBodyTexture().ordinal()]
                                [fishStateManager.getCurrentFinPectoral().ordinal()], BODY_ANIMATION_SPEED_DEFAULT);
                break;
            case KURASELACHE:
                currentBodyAnimation = new Animation(
                        Assets.tailKuraselache[fishStateManager.getCurrentBodySize().ordinal()]
                                [fishStateManager.getCurrentBodyTexture().ordinal()]
                                [fishStateManager.getCurrentFinPectoral().ordinal()], BODY_ANIMATION_SPEED_DEFAULT);
                break;
            default:
                System.out.println("Fish.updateHeadAndTailAnimations() switch-construct's default.");
                break;
        }
    }

    public void initHeadAnimations() {
        //for FishStateManager.ActionState.NONE
        Bitmap[] idleFrames = new Bitmap[1];
        idleFrames[0] = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [0];
        idleHeadAnimation = new Animation(idleFrames, HEAD_ANIMATION_SPEED_DEFAULT);

        //for FishStateManager.ActionState.HURT
        Bitmap[] hurtFrames = new Bitmap[1];
        hurtFrames[0] = Assets.hurtFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.HURT.ordinal()]
                [0];
        hurtHeadAnimation = new Animation(hurtFrames, HEAD_ANIMATION_SPEED_DEFAULT);

        //for FishStateManager.ActionState.EAT
        Bitmap[] eatFrames = new Bitmap[3];
        eatFrames[0] = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [1];
        eatFrames[1] = Assets.eatFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.EAT.ordinal()]
                [2];
        eatFrames[2] = Assets.biteFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.BITE.ordinal()]
                [2];
        eatHeadAnimation = new Animation(eatFrames, HEAD_ANIMATION_SPEED_DEFAULT);

        //for FishStateManager.ActionState.BITE
        Bitmap[] biteFrames = new Bitmap[3];
        biteFrames[0] = Assets.biteFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.BITE.ordinal()]
                [0];
        biteFrames[1] = Assets.biteFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.BITE.ordinal()]
                [1];
        biteFrames[2] = Assets.hurtFrames[fishStateManager.getCurrentBodySize().ordinal()]
                [fishStateManager.getCurrentBodyTexture().ordinal()]
                [fishStateManager.getCurrentJaws().ordinal()]
                [FishStateManager.ActionState.HURT.ordinal()]
                [1];
        biteHeadAnimation = new Animation(biteFrames, HEAD_ANIMATION_SPEED_DEFAULT);

        /////////////////////////////////////////
        currentHeadAnimation = idleHeadAnimation;
        /////////////////////////////////////////
    }

    private int hurtTimer = 0;
    private static final int TARGET_HURT_TIMER = 20;
    @Override
    public void update(long elapsed) {
        //HEAD ANIMATION
        switch (fishStateManager.getCurrentActionState()) {
            case HURT:
                currentHeadAnimation = hurtHeadAnimation;
                //no need tick (1 frame).
                hurtTimer++;
                if (hurtTimer == TARGET_HURT_TIMER) {
                    hurtTimer = 0;
                    fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);
                }
                break;
            case BITE:
                currentHeadAnimation.update(elapsed);
                if (currentHeadAnimation.isLastFrame()) {
                    fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);
                }
                break;
            case EAT:
                currentHeadAnimation.update(elapsed);
                if (currentHeadAnimation.isLastFrame()) {
                    fishStateManager.setCurrentActionState(FishStateManager.ActionState.NONE);
                }
                break;
            case NONE:
                currentHeadAnimation = idleHeadAnimation;
                //no need tick (1 frame).
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".update(long elapsed) switch's default.");
        }
        //BODY ANIMATION
        currentBodyAnimation.update(elapsed);

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
        //ACTUAL IMAGE OF FISH
        if (directionFacing == DirectionFacing.RIGHT) {
            Bitmap imageBody = currentBodyAnimation.getCurrentFrame();
            Bitmap imageHead = currentHeadAnimation.getCurrentFrame();

            Rect rectBody = new Rect(
                    (int)player.getX(),
                    (int)player.getY(),
                    (int)(player.getX() + (2 * player.getWidth() / 3f)),
                    (int)(player.getY() + player.getHeight()));
            Rect rectHead = new Rect(
                    (int)(player.getX() + (2 * player.getWidth() / 3f)),
                    (int)player.getY(),
                    (int)(player.getX() + player.getWidth()),
                    (int)(player.getY() + player.getHeight()));

            Rect rectBodyImage = new Rect(0, 0, imageBody.getWidth(), imageBody.getHeight());
            Rect rectBodyOnScreen = GameCamera.getInstance().convertToScreenRect(rectBody);
            Rect rectHeadImage = new Rect(0, 0, imageHead.getWidth(), imageHead.getHeight());
            Rect rectHeadOnScreen = GameCamera.getInstance().convertToScreenRect(rectHead);

            canvas.drawBitmap(imageBody, rectBodyImage, rectBodyOnScreen, null);
            canvas.drawBitmap(imageHead, rectHeadImage, rectHeadOnScreen, null);
        } else if (directionFacing == DirectionFacing.LEFT) {
            //FLIP IMAGES of head and body.
            Bitmap imageHeadFlipped = Animation.flipImageHorizontally(currentHeadAnimation.getCurrentFrame());
            Bitmap imageBodyFlipped = Animation.flipImageHorizontally(currentBodyAnimation.getCurrentFrame());

            Rect rectHead = new Rect(
                    (int)player.getX(),
                    (int)player.getY(),
                    (int)(player.getX() + (1 * player.getWidth() / 3f)),
                    (int)(player.getY() + player.getHeight()));
            Rect rectBody = new Rect(
                    (int)(player.getX() + (1 * player.getWidth() / 3f)),
                    (int)player.getY(),
                    (int)(player.getX() + player.getWidth()),
                    (int)(player.getY() + player.getHeight()));

            Rect rectHeadImage = new Rect(0, 0, imageHeadFlipped.getWidth(), imageHeadFlipped.getHeight());
            Rect rectHeadOnScreen = GameCamera.getInstance().convertToScreenRect(rectHead);
            Rect rectBodyImage = new Rect(0, 0, imageBodyFlipped.getWidth(), imageBodyFlipped.getHeight());
            Rect rectBodyOnScreen = GameCamera.getInstance().convertToScreenRect(rectBody);

            canvas.drawBitmap(imageHeadFlipped, rectHeadImage, rectHeadOnScreen, null);
            canvas.drawBitmap(imageBodyFlipped, rectBodyImage, rectBodyOnScreen, null);
        }
    }

    @Override
    public void interpretInput() {
        // Check InputManager's ButtonPadFragment-specific boolean fields.
        if (game.getInputManager().isJustPressed(InputManager.Button.A)) {
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isJustPressed(InputManager.Button.A)");
            currentHeadAnimation = biteHeadAnimation;
            currentHeadAnimation.resetIndex();
            fishStateManager.setCurrentActionState(FishStateManager.ActionState.BITE);
        } else if (game.getInputManager().isJustPressed(InputManager.Button.B)) {
            // [ALERT] Check isJustPressed() BEFORE isPressing().
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isJustPressed(InputManager.Button.B)");
            currentHeadAnimation = eatHeadAnimation;
            currentHeadAnimation.resetIndex();
            fishStateManager.setCurrentActionState(FishStateManager.ActionState.EAT);
        } else if (game.getInputManager().isPressing(InputManager.Button.B)) {
            // [ALERT] Check isJustPressed() BEFORE isPressing().
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".interpretInput() isPressing(InputManager.Button.B)");
            float doubledMoveSpeedDefault = 2 * Creature.MOVE_SPEED_DEFAULT;
            player.setMoveSpeed(doubledMoveSpeedDefault);
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
            directionFacing = DirectionFacing.LEFT;
            player.setDirection(Creature.Direction.LEFT);
            player.setxMove(-moveSpeed);    // horizontal NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.RIGHT)) {
            directionFacing = DirectionFacing.RIGHT;
            player.setDirection(Creature.Direction.RIGHT);
            player.setxMove(moveSpeed);     // horizontal POSITIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.CENTER)) {
            player.setDirection(Creature.Direction.CENTER);
            player.setxMove(0f);            // horizontal ZERO
            player.setyMove(0f);            // vertical ZERO
        } else if (game.getInputManager().isPressing(InputManager.Button.UPLEFT)) {
            directionFacing = DirectionFacing.LEFT;
            player.setDirection(Creature.Direction.UP_LEFT);
            player.setxMove(-moveSpeed);    // horizontal NEGATIVE
            player.setyMove(-moveSpeed);    // vertical NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.UPRIGHT)) {
            directionFacing = DirectionFacing.RIGHT;
            player.setDirection(Creature.Direction.UP_RIGHT);
            player.setxMove(moveSpeed);     // horizontal POSITIVE
            player.setyMove(-moveSpeed);    // vertical NEGATIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.DOWNLEFT)) {
            directionFacing = DirectionFacing.LEFT;
            player.setDirection(Creature.Direction.DOWN_LEFT);
            player.setxMove(-moveSpeed);    // horizontal NEGATIVE
            player.setyMove(moveSpeed);     // vertical POSITIVE
        } else if (game.getInputManager().isPressing(InputManager.Button.DOWNRIGHT)) {
            directionFacing = DirectionFacing.RIGHT;
            player.setDirection(Creature.Direction.DOWN_RIGHT);
            player.setxMove(moveSpeed);     // horizontal POSITIVE
            player.setyMove(moveSpeed);     // vertical POSITIVE
        }
    }

    @Override
    public void determineNextImage() {
        player.setImage(null);
    }

    @Override
    public void respondToTransferPointCollision(String key) {

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

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealthMax() {
        return healthMax;
    }

    public void setHealthMax(int healthMax) {
        this.healthMax = healthMax;
    }
}