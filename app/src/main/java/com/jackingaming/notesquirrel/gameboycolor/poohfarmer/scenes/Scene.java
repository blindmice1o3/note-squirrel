package com.jackingaming.notesquirrel.gameboycolor.poohfarmer.scenes;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Scene {

    //TODO: PoohFarmerCartridge should be in charge of sceneCurrent (and a collection of Scene instances).
    //TODO: a Scene should be composed of Tile[][] and ArrayList<Entity>

    //TODO: refactor to Tile
    public enum TileType { SOLID, WALKABLE, SIGN_POST, TRANSFER_POINT; }

    private TileType[][] tiles;
    private int columns;
    private int rows;

    private int tileSize;
    private int widthSceneMax;
    private int heightSceneMax;

    public Scene(Bitmap tileMap, int tileSize) {
        columns = tileMap.getWidth();
        rows = tileMap.getHeight();

        this.tileSize = tileSize;
        widthSceneMax = columns * tileSize;
        heightSceneMax = rows * tileSize;

        initTiles(tileMap);
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

    public boolean isSolid(int xPosition, int yPosition) {
        //CHECK BEYOND SCENE BOUND (e.g. moving off map)
        if ((xPosition < 0) ||(xPosition >= widthSceneMax) ||
                (yPosition < 0) || (yPosition >= heightSceneMax)) {
            return true;
        }

        int indexColumn = xPosition / tileSize;
        int indexRow = yPosition / tileSize;

        //CHECK FOR TileType.WALKABLE
        if (tiles[indexRow][indexColumn] == TileType.WALKABLE) {
            return false;
        }

        //TODO: handle TileType.TRANSFER_POINT

        //DEFAULT IS TileType.SOLID (not walkable)
        return true;
    }

}