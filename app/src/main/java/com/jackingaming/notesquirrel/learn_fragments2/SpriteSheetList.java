package com.jackingaming.notesquirrel.learn_fragments2;

import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;

public class SpriteSheetList extends ArrayList<SpriteSheet> {

    public SpriteSheetList() {
        add(new SpriteSheet(
                R.drawable.corgi_crusade,
                600, 680,
                "Corgi Crusade - entity sprite",
                "animation: down, up, left"
        ));
        add(new SpriteSheet(
                R.drawable.arcade_bubble_bobble_general_sprites,
                645, 1730,
                "Bubble Bobble - general sprites",
                "entities, items, projectiles, effects, font"
        ));
        add(new SpriteSheet(
                R.drawable.gbc_hm2_font_and_message_box,
                167, 172,
                "Harvest Moon 2 - font and message box",
                "character table and textbox background"
        ));
    }

}