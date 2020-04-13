package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private TileType[][] tiles;
    private List<Entity> entities;
    //TODO: items will go here
    private GameCamera gameCamera;

    //TODO: PoohFarmerCartridge should be in charge of sceneCurrent (and a collection of Scene instances).
    //TODO: a Scene should be composed of Tile[][] and ArrayList<Entity>

    //TODO: refactor to Tile
    public enum TileType { SOLID, WALKABLE, SIGN_POST, TRANSFER_POINT; }
    public static final int TILE_SIZE = 16;

    private int xSpawnIndex = 4;
    private int ySpawnIndex = 4;

    private int columns;
    private int rows;
    private int widthSceneMax;
    private int heightSceneMax;

    public Scene(Bitmap tileMap, Player player) {
        columns = tileMap.getWidth();
        rows = tileMap.getHeight();
        widthSceneMax = columns * TILE_SIZE;
        heightSceneMax = rows * TILE_SIZE;

        player.setxCurrent((xSpawnIndex * TILE_SIZE));
        player.setyCurrent((ySpawnIndex * TILE_SIZE));

        initTiles(tileMap);
        initEntities(player);

        gameCamera = new GameCamera(player, widthSceneMax, heightSceneMax);
        gameCamera.update(0L);
    }

    private void initEntities(Player player) {
        entities = new ArrayList<Entity>();
        entities.add(player);
    }

    private void initTiles(Bitmap tileMap) {
        tiles = new TileType[rows][columns];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                int pixel = tileMap.getPixel(x, y);

                if (pixel == Color.BLACK) {
                    tiles[y][x] = TileType.SOLID;
                } else if (pixel == Color.WHITE) {
                    tiles[y][x] = TileType.WALKABLE;
                } else if (pixel == Color.RED) {
                    tiles[y][x] = TileType.SIGN_POST;
                } else if (pixel == Color.GREEN) {
                    tiles[y][x] = TileType.TRANSFER_POINT;
                }
                //TODO: handle special tiles (stashWood, flowerPlot, hotSpring)
                else if (pixel == Color.BLUE) {
                    tiles[y][x] = TileType.SOLID;
                }

            }
        }
    }

    public void update(long elapsed) {
        gameCamera.update(elapsed);
    }

    public void render(Canvas canvas) {
        for (Entity entity : entities) {
            entity.render(canvas);
        }
    }

    public boolean isSolid(int xPosition, int yPosition) {
        //CHECK BEYOND SCENE BOUND (e.g. moving off map)
        if ((xPosition < 0) ||(xPosition >= widthSceneMax) ||
                (yPosition < 0) || (yPosition >= heightSceneMax)) {
            return true;
        }

        int indexColumn = xPosition / TILE_SIZE;
        int indexRow = yPosition / TILE_SIZE;

        //CHECK FOR TileType.WALKABLE
        if (tiles[indexRow][indexColumn] == TileType.WALKABLE) {
            return false;
        }

        //TODO: handle TileType.TRANSFER_POINT

        //DEFAULT IS TileType.SOLID (not walkable)
        return true;
    }

}