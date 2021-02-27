package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish;

import android.graphics.Bitmap;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.animations.Animation;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Form;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;

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

        bounds.x = 2;
        bounds.y = 1;
        bounds.width = 28;
        bounds.height = 13;
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

    @Override
    public void update(long elapsed) {
        // ANIMATION
        idleHeadAnimation.update(elapsed);
        eatHeadAnimation.update(elapsed);
        biteHeadAnimation.update(elapsed);
        hurtHeadAnimation.update(elapsed);

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
    public void interpretInput() {

    }

    @Override
    public void determineNextImage() {

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