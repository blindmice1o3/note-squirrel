package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.tiles;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.jackingaming.notesquirrel.gameboycolor.poohfarmer.sprites.Assets;

public class TileMap {

    public enum TileType { SOLID, WALKABLE, SIGN_POST, TRANSFER_POINT; }
    public static final int TILE_SIZE = 16;

    private Bitmap texture;
    private TileType[][] tiles;

    private int xSpawnIndex;
    private int ySpawnIndex;

    private int columns;
    private int rows;
    private int widthSceneMax;
    private int heightSceneMax;

    public TileMap() {
        xSpawnIndex = 4;
        ySpawnIndex = 4;

        initTexture();
        initTiles();
    }

    private void initTexture() {
        //SPRING
        texture = Assets.hm3Farm[0][0];
    }

    private void initTiles() {
        Bitmap rgbTileMap = Assets.rgbTileFarm;

        columns = rgbTileMap.getWidth();
        rows = rgbTileMap.getHeight();
        widthSceneMax = columns * TILE_SIZE;
        heightSceneMax = rows * TILE_SIZE;

        tiles = new TileType[rows][columns];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int pixel = rgbTileMap.getPixel(x, y);

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

    public Bitmap getTexture() {
        return texture;
    }

    public int getxSpawnIndex() {
        return xSpawnIndex;
    }

    public int getySpawnIndex() {
        return ySpawnIndex;
    }

    public int getWidthSceneMax() {
        return widthSceneMax;
    }

    public int getHeightSceneMax() {
        return heightSceneMax;
    }

}