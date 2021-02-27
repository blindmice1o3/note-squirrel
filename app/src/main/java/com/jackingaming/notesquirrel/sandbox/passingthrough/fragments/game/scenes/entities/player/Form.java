package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;

import java.io.Serializable;

public interface Form extends Serializable {
    void init(Game game);
    void update(long elapsed);
    void interpretInput();
    void determineNextImage();

    void respondToTransferPointCollision(String key);
    boolean respondToEntityCollision(Entity e);
    void respondToItemCollisionViaClick(Item item);
    void respondToItemCollisionViaMove(Item item);
}
