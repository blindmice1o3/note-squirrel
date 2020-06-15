package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tiles;

import android.graphics.Bitmap;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

import java.util.ArrayList;

/**
 * Copy directly from a previous IntelliJ project (desktop pc).
 *
 * Modifying for Android (e.g. Bitmap instead of BufferedImage).
 */
public class TileSpriteToRGBConverter {

    // CONSTANTS
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    // MEMBER VARIABLES
    private static ArrayList<Bitmap> nonWalkableTileSpriteTargets;
    private static ArrayList<Bitmap> walkableTileSpriteTargets;
    private static Bitmap worldBackground;

    public TileSpriteToRGBConverter() {

    } // **** end TileSpriteToRGBConverter() constructor ****

    public ArrayList<Bitmap> pullMultipleTiles(Bitmap spriteSheet, int xInit, int yInit, int numOfCols, int numOfRows) {
        ArrayList<Bitmap> returner = new ArrayList<Bitmap>();

        for (int yy = 0; yy < numOfRows; yy++) {
            int yOffset = yy * TILE_HEIGHT;
            for (int xx = 0; xx < numOfCols; xx++) {
                int xOffset = xx * TILE_WIDTH;
                returner.add(
                        Bitmap.createBitmap(spriteSheet, (xInit + xOffset), (yInit + yOffset), TILE_WIDTH, TILE_HEIGHT)
                );
            }
        }

        return returner;
    }

    public TileMap.TileType[][] generateTileMapForCollisionDetection(Bitmap worldBackground,
                                                                     ArrayList<Bitmap> nonWalkableTileSpriteTargets,
                                                                     ArrayList<Bitmap> walkableTileSpriteTargets) {
        //////////////////////////////////////////////////////////////////////////
        this.worldBackground = worldBackground;
        //Used in translateTileSpriteToRGBImage() for its final for-loop.
        this.nonWalkableTileSpriteTargets = nonWalkableTileSpriteTargets;
        //Used in translateTileSpriteToRGBImage() for its final for-loop.
        this.walkableTileSpriteTargets = walkableTileSpriteTargets;
        int[][][] rgbImage = translateTileSpriteToRGBImage();
        //////////////////////////////////////////////////////////////////////////
        int widthNumberOfTile = rgbImage[0].length;
        int heightNumberOfTile = rgbImage.length;

        System.out.println("TileSpriteToRGBConverter.generateTileMapForCollisionDetection(BufferedImage)'s widthWorld: " + widthNumberOfTile);
        System.out.println("TileSpriteToRGBConverter.generateTileMapForCollisionDetection(BufferedImage)'s heightWorld: " + heightNumberOfTile);

        TileMap.TileType[][] returner = new TileMap.TileType[heightNumberOfTile][widthNumberOfTile];

        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {
                if (rgbImage[y][x][0] == 1) {
                    returner[y][x] = TileMap.TileType.SOLID;
                } else if (rgbImage[y][x][0] == 0) {
                    returner[y][x] = TileMap.TileType.WALKABLE;
                } else if (rgbImage[y][x][0] == 2) {
                    //TODO: "2" might mean special TileType (MIXED!! some solid, others not!).
                    if (this.worldBackground == Assets.pokemonWorldMapPart1) {
                        returner[y][x] = TileMap.TileType.WALKABLE;
                        //returner[y][x] = new TallGrassTile(x, y);
                    } /* else if (this.worldBackground == Assets.homePlayer) {
                        returner[y][x] = new TelevisionTile(x, y);
                    } else if (this.worldBackground == Assets.roomPlayer) {
                        if (x == 0) {
                            returner[y][x] = new ComputerKeyboardTile(x, y);
                        } else {
                            returner[y][x] = new GameConsoleTile(x, y);
                        }
                    } else if (this.worldBackground == Assets.homeRival) {
                        //filler-space
                        returner[y][x] = new NonSolidTile(x, y);
                    }
                    */
                } else if (rgbImage[y][x][0] == 9) {
                    //TODO: "9" is probably the blank/white space in world map.
                    returner[y][x] = TileMap.TileType.SOLID;

                    /*
                    returner[y][x] = new NullTile(x, y);
                    */
                }
            }
        }

        return returner;
    }

    public int[][][] translateTileSpriteToRGBImage() {
        int widthNumberOfTile = (worldBackground.getWidth() / TILE_WIDTH);
        int heightNumberOfTile = (worldBackground.getHeight() / TILE_HEIGHT);

        System.out.println("TileSpriteToRGBConverter.translateTileSpriteToRGBImage()'s widthNumberOfTile: " + widthNumberOfTile);
        System.out.println("TileSpriteToRGBConverter.translateTileSpriteToRGBImage()'s heightNumberOfTile: " + heightNumberOfTile);

        int[][][] returner = new int[heightNumberOfTile][widthNumberOfTile][1];

        //checking each tile sprite image (16x16) within the entire world map (widthNumberOfTile by heightNumberOfTile).
        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {

                int xOffset = (x * TILE_WIDTH);
                int yOffset = (y * TILE_HEIGHT);

                boolean blankTile = false;

                //ONLY FOR WORLDMAP (pokemon-gsc-kanto.png)
                if (worldBackground == Assets.pokemonWorldMapPart1) {   //TODO:
//                if (worldBackground == Assets.world) {

                    blankTile = true;
                    //!!!CHECK TILE - BLANK VERSUS FILLED!!!
                    //check each individual pixel within each of the 64x64-pixeled tile.
                    //@@@@@
                    //label for outer-loop in case I can break out early.
                    //@@@@@
                    outerloop:
                    for (int yy = 0; yy < TILE_HEIGHT; yy++) {
                        for (int xx = 0; xx < TILE_WIDTH; xx++) {

                            int pixel = worldBackground.getPixel((xOffset + xx), (yOffset + yy));
                            int red = (pixel >> 16) & 0xff;
                            int green = (pixel >> 8) & 0xff;
                            int blue = (pixel) & 0xff;

                            //if non-blank tile
                            if (!((red == 255) && (green == 255) && (blue == 255)) &&
                                    //!!!THERE WERE OTHER RGB values FOR BLANK!!!
                                    !((red == 254) && (green == 254) && (blue == 254))) {
                                blankTile = false;
                                //@@@@@@@@@@@@@@
                                break outerloop;
                                //@@@@@@@@@@@@@@
                            }
                        }
                    }

                }

                // non-blank tile will be set to have a value of 0.
                if (!blankTile) {
                    returner[y][x][0] = 0;
                }
                // blank tile will be set to have a value of 9.
                else {
                    returner[y][x][0] = 9;
                }

            }
        }


        //CHECK TARGET TILE IMAGE TO DETERMINE IWALKABLE.
        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {

                int xOffset = (x * TILE_WIDTH);
                int yOffset = (y * TILE_HEIGHT);

                //if non-blank (equals 0)...
                if (returner[y][x][0] == 0) {
                    if (nonWalkableTileSpriteTargets.size() > 0) {
                        for (Bitmap tileSpriteTarget : nonWalkableTileSpriteTargets) {

                            //it is the same as one of the target.
                            if (compareTile(Bitmap.createBitmap(worldBackground, xOffset, yOffset, TILE_WIDTH, TILE_HEIGHT),
                                    tileSpriteTarget)) {
                                returner[y][x][0] = 1;
                                break;
                            }
                        }
                    }
                    if (walkableTileSpriteTargets.size() > 0) {
                        for (Bitmap tileSpriteTarget : walkableTileSpriteTargets) {
                            if (compareTile(Bitmap.createBitmap(worldBackground, xOffset, yOffset, TILE_WIDTH, TILE_HEIGHT),
                                    tileSpriteTarget)) {
                                returner[y][x][0] = 2;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return returner;
    } // **** end BufferedImage translateTileSpriteToRGBImage() method ****

    private boolean compareTile(Bitmap tileSpriteImage, Bitmap tileSpriteTarget) {
        boolean sameImage = true;

        outerloop:
        for (int yy = 0; yy < TILE_HEIGHT; yy++) {
            for (int xx = 0; xx < TILE_WIDTH; xx++) {
                int pixelImage = tileSpriteImage.getPixel(xx, yy);
                int redImage = (pixelImage >> 16) & 0xff;
                int greenImage = (pixelImage >> 8) & 0xff;
                int blueImage = (pixelImage) & 0xff;

                int pixelTarget = tileSpriteTarget.getPixel(xx, yy);
                int redTarget = (pixelTarget >> 16) & 0xff;
                int greenTarget = (pixelTarget >> 8) & 0xff;
                int blueTarget = (pixelTarget) & 0xff;

                //if one pixel is not the same... sameImage set to false.
                if ( !((redImage == redTarget) && (greenImage == greenTarget) && (blueImage == blueTarget)) ) {
                    sameImage = false;
                    break outerloop;
                }
            }
        }

        return sameImage;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  Load world by reading RGB values from BufferedImage object and writing to int[][][].
     *
     * @param image the BufferedImage object from which the game world (map/level/tile-layout) is modeled.
     *              Since the actual RGB values (e.g. red == 255, green == 0, and blue == 0) are largely
     *              arbitrary, some of the values can be utilized as meta-data.
     * @return a multi-dimensional array of int values which represent the game's world (map/level) as
     *          if it was a two-dimensional array of int (i.e. int[widthInTiles][heightInTiles]),
     *          where each element of the two-dimensional array is a reference to a third array of int
     *          values (i.e. int[3]), an array of the RGB values of each individual pixels from
     *          BufferedImage image. The values inside the array of int[] representing RGB will be
     *          parsed and translated to their corresponding Tile type using IWorld class's
     *          translateTileFromRGB(int[][][] rgbArrayRelativeToMap) method.
     */
    public static int[][][] transcribeRGBFromImage(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[][][] rgbArrayRelativeToMap = new int[width][height][3];

        int pixel;
        int red;
        int green;
        int blue;

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                pixel = image.getPixel(xx, yy);
                red = (pixel >> 16) & 0xff;
                green = (pixel >> 8) & 0xff;
                blue = (pixel) & 0xff;

                rgbArrayRelativeToMap[xx][yy][0] = red;
                rgbArrayRelativeToMap[xx][yy][1] = green;
                rgbArrayRelativeToMap[xx][yy][2] = blue;
            }
        }

        return rgbArrayRelativeToMap;
    } // **** end int[][][] transcribeRGBFromImage(BufferedImage) method ****

    public void testConsoleOutput(int[][][] rgbImage) {
        for (int y = 0; y < rgbImage.length; y++) {
            for (int x = 0; x <rgbImage[y].length; x++) {
                //figure out if NOT last element in its group of columns,
                // otherwise it's the last element in its group of columns.
                if (x != (rgbImage[y].length-1)) {
                    System.out.print(Integer.toString(rgbImage[y][x][0]) + " ");
                } else {
                    System.out.println(Integer.toString(rgbImage[y][x][0]));
                }
            }
        }
    }

} // **** end TileSpriteToRGBConverter class ****
