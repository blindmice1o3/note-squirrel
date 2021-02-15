package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class HoneyPot extends Item {
    public HoneyPot() {
        super();
        name = "Honey Pot";
    }

    @Override
    public void init(Game game) {
        super.init(game);
        Bitmap spriteSheet = BitmapFactory.decodeResource(game.getContext().getResources(), R.drawable.gba_kingdom_hearts_chain_of_memories_winnie_the_pooh);
        image = Bitmap.createBitmap(spriteSheet, 318, 1556, 38, 37);
    }
}