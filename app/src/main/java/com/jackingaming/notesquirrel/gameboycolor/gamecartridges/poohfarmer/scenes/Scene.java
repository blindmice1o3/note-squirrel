package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public enum SceneId { FARM, HOME; }

    private int sideSquareScreen;

    private TileMap tileMap;
    private List<Entity> entities;
    private Player player;
    private GameCamera gameCamera;

    public Scene(int sideSquareScreen) {
        this.sideSquareScreen = sideSquareScreen;
    }

    public void init(Player player, GameCamera gameCamera) {
        initTileMap();
        initEntities(player);
        initGameCamera(gameCamera);
    }

    private void initTileMap() {
        tileMap = new TileMap();
    }

    private void initEntities(Player player) {
        this.player = player;
        player.init();
        player.setxCurrent((tileMap.getxSpawnIndex() * TileMap.TILE_SIZE));
        player.setyCurrent((tileMap.getySpawnIndex() * TileMap.TILE_SIZE));

        entities = new ArrayList<Entity>();
        entities.add(player);
    }

    private void initGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
        gameCamera.init(player, tileMap.getWidthSceneMax(), tileMap.getHeightSceneMax());
    }

    public void update(long elapsed) {
        for (Entity entity : entities) {
            entity.update(elapsed);
        }
    }

    public void render(Canvas canvas) {
        //BACKGROUND
        Rect boundsFarm = new Rect(
                (int)gameCamera.getX(),
                (int)gameCamera.getY(),
                (int)(gameCamera.getX() + gameCamera.getWidthClip()),
                (int)(gameCamera.getY() + gameCamera.getHeightClip())
        );
        Rect screenFarm = new Rect(0, 0, sideSquareScreen, sideSquareScreen);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(tileMap.getTexture(), boundsFarm, screenFarm, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //FOREGROUND
        for (Entity entity : entities) {
            entity.render(canvas);
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }

}