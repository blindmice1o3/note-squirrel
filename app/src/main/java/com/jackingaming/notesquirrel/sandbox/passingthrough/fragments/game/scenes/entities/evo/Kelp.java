package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.evo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Animation;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Entity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

public class Kelp extends Entity {
    private static final int WIDTH_IMAGE = 12;
    private static final int HEIGHT_IMAGE = 32;
    private static final int ANIMATION_SPEED_DEFAULT = 300;
    transient private static Bitmap[] kelpFrames;

    transient private Animation animation;

    public Kelp(int xSpawn, int ySpawn) {
        super(xSpawn, ySpawn);
        width = Tile.WIDTH / 2;
        height = Tile.HEIGHT;
    }

    @Override
    public void init(Game game) {
        super.init(game);

        if (kelpFrames == null) {
            Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.snes_evo_search_for_eden_chapter1_creatures);

            kelpFrames = new Bitmap[6];
            kelpFrames[0] = Bitmap.createBitmap(spriteSheet, 557, 718, WIDTH_IMAGE, HEIGHT_IMAGE);
            kelpFrames[1] = Bitmap.createBitmap(spriteSheet, 569, 718, WIDTH_IMAGE, HEIGHT_IMAGE);
            kelpFrames[2] = Bitmap.createBitmap(spriteSheet, 582, 718, WIDTH_IMAGE, HEIGHT_IMAGE);
            kelpFrames[3] = Bitmap.createBitmap(spriteSheet, 594, 718, WIDTH_IMAGE, HEIGHT_IMAGE);
            kelpFrames[4] = Bitmap.createBitmap(spriteSheet, 582, 718, WIDTH_IMAGE, HEIGHT_IMAGE);
            kelpFrames[5] = Bitmap.createBitmap(spriteSheet, 569, 718, WIDTH_IMAGE, HEIGHT_IMAGE);
        }
        animation = new Animation(kelpFrames, ANIMATION_SPEED_DEFAULT);
    }

    @Override
    public void update(long elapsed) {
        animation.update(elapsed);
        image = animation.getCurrentFrame();
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