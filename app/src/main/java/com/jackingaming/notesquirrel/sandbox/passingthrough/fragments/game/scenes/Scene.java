package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes;

import android.graphics.Canvas;
import android.graphics.Color;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.GameCamera;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.EntityManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.ItemManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.TileManager;

import java.io.Serializable;

public abstract class Scene
        implements Serializable {
    transient protected Game game;

    protected TileManager tileManager;
    protected ItemManager itemManager;
    protected EntityManager entityManager;

    protected float xLastKnown;
    protected float yLastKnown;

    public Scene() {
        tileManager = new TileManager();
        itemManager = new ItemManager();
        entityManager = new EntityManager();
        xLastKnown = 0f;
        yLastKnown = 0f;
    }

    public abstract void init(Game game);

    public void enter() {
        GameCamera.getInstance().init(Player.getInstance(),
                game.getWidthViewport(), game.getHeightViewport(),
                tileManager.getWidthScene(), tileManager.getHeightScene());
    }

    public void exit() {
        xLastKnown = Player.getInstance().getX();
        yLastKnown = Player.getInstance().getY();
    }

    public void update(long elapsed) {
        entityManager.update(elapsed);
    }

    public void drawCurrentFrame(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        tileManager.draw(canvas);
        itemManager.draw(canvas);
        entityManager.draw(canvas);
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}