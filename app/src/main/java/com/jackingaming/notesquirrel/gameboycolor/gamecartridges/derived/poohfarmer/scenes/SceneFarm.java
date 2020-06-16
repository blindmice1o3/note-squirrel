package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.Robot;

public class SceneFarm extends Scene {

    public SceneFarm(Handler handler, Id sceneID) {
        super(handler, sceneID);
    }

    @Override
    public void initEntityManager(Player player) {
        super.initEntityManager(player);

        Entity robot = new Robot(handler, (7 * tileMap.getTileWidth()), (5 * tileMap.getTileHeight()));
        entityManager.addEntity(robot);
    }

}