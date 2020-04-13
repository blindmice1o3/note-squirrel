package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites.Assets;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public enum SceneId { FARM, HOME; }

    private Bitmap texture;
    private int sideSquareScreen;

    private TileType[][] tiles;
    private List<Entity> entities;
    private Player player;
    private GameCamera gameCamera;

    //TODO: refactor to Tile
    public enum TileType { SOLID, WALKABLE, SIGN_POST, TRANSFER_POINT; }
    public static final int TILE_SIZE = 16;

    private int xSpawnIndex = 4;
    private int ySpawnIndex = 4;

    private int columns;
    private int rows;
    private int widthSceneMax;
    private int heightSceneMax;

    public Scene(int sideSquareScreen) {
        this.sideSquareScreen = sideSquareScreen;
    }

    public void init(Player player, GameCamera gameCamera) {
        initTexture();
        initTiles();
        initEntities(player);
        initGameCamera(gameCamera);
    }

    private void initTexture() {
        //SPRING
        texture = Assets.hm3Farm[0][0];
    }

    private void initEntities(Player player) {
        this.player = player;
        player.init();
        player.setxCurrent((xSpawnIndex * TILE_SIZE));
        player.setyCurrent((ySpawnIndex * TILE_SIZE));

        entities = new ArrayList<Entity>();
        entities.add(player);
    }

    private void initGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
        gameCamera.init(player, widthSceneMax, heightSceneMax);
    }

    private void initTiles() {
        Bitmap tileMap = Assets.rgbTileFarm;

        columns = tileMap.getWidth();
        rows = tileMap.getHeight();
        widthSceneMax = columns * TILE_SIZE;
        heightSceneMax = rows * TILE_SIZE;

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
        //BACKGROUND
        Rect boundsFarm = new Rect(
                (int)gameCamera.getX(),
                (int)gameCamera.getY(),
                (int)(gameCamera.getX() + gameCamera.getWidthClip()),
                (int)(gameCamera.getY() + gameCamera.getHeightClip())
        );
        Rect screenFarm = new Rect(0, 0, sideSquareScreen, sideSquareScreen);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(texture, boundsFarm, screenFarm, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //FOREGROUND
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