package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Damageable;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;

public class FrogForm
        implements Form, Damageable {
    transient private Game game;
    private Player player;

    public FrogForm(Player player) {
        this.player = player;
    }

    @Override
    public void init(Game game) {
        this.game = game;
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void draw(Canvas canvas) {

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

    @Override
    public void takeDamage(int incomingDamage) {

    }

    @Override
    public void die() {

    }
}