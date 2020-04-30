package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public enum Id { FARM, PART_01, HOME_01, HOME_02, HOME_RIVAL, LAB; }

    private Context context;
    private int widthViewport;
    private int heightViewport;
    private Id sceneID;

    private TileMap tileMap;
    private List<Entity> entities;
    private Player player;
    private GameCamera gameCamera;

    public Scene(Context context, int widthViewport, int heightViewport, Id sceneID) {
        this.context = context;
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
        this.sceneID = sceneID;
    }

    public void init(Player player, GameCamera gameCamera) {
        initTileMap();
        initEntities(player);
        initGameCamera(gameCamera);
    }

    private void initTileMap() {
        tileMap = new TileMap(context, sceneID);
    }

    private void initEntities(Player player) {
        this.player = player;
        player.init();
        player.setTileMap(tileMap);
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
                (int)(gameCamera.getX() + gameCamera.getWidthClipInPixel()),
                (int)(gameCamera.getY() + gameCamera.getHeightClipInPixel())
        );
        Rect screenFarm = new Rect(0, 0, widthViewport, heightViewport);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(tileMap.getTexture(), boundsFarm, screenFarm, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //FOREGROUND
        for (Entity entity : entities) {
            entity.render(canvas);
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }

}