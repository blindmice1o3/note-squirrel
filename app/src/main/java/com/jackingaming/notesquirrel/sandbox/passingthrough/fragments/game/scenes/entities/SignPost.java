package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class SignPost extends Entity {
    public SignPost(int xSpawn, int ySpawn) {
        super(xSpawn, ySpawn);
    }

    @Override
    public void init(Game game) {
        super.init(game);
        Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.custom_hm_tile_sprites_sheet);
        image = Bitmap.createBitmap(spriteSheet, 1 * Tile.WIDTH, 4 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void respondToEntityCollision(Entity e) {

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