package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;

public class FishForm
        implements Form {
    transient private Game game;
    private Player player;

    public FishForm(Player player) {
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
}