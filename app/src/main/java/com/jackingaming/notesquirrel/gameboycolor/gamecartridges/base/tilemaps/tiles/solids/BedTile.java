package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.stationary.CropEntity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableGroundTile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.growables.GrowableTile;

public class BedTile extends Tile {

    public BedTile(GameCartridge gameCartridge, int xIndex, int yIndex) {
        super(gameCartridge, xIndex, yIndex);

        walkability = Walkability.SOLID;
    }

    public void execute(final GameCartridge gameCartridge) {
        Scene sceneFarm = gameCartridge.getSceneManager().getScene(Scene.Id.FARM);
        int column = sceneFarm.getTileMap().getWidthSceneMax() / sceneFarm.getTileMap().getTileWidth();
        int row = sceneFarm.getTileMap().getHeightSceneMax() / sceneFarm.getTileMap().getTileHeight();

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                Log.d(MainActivity.DEBUG_TAG, "BedTile.execute(GameCartridge) inspecting tile at (xIndex, yIndex) for seeded-watering: (" + x + ", " + y + ").");

                Log.d(MainActivity.DEBUG_TAG, "BedTile.execute(GameCartridge) sceneFarm's tileMap: " + sceneFarm.getTileMap());
                Tile tile = sceneFarm.getTileMap().getTile(x, y);

                Log.d(MainActivity.DEBUG_TAG, "BedTile.execute(GameCartridge) tile (column, row): (" + column + ", " + row + ") " + tile.getClass().getSimpleName());

                if (tile instanceof GrowableGroundTile) {

                    Log.d(MainActivity.DEBUG_TAG, "GrowableGroundTile found at (xIndex, yIndex): (" + xIndex + ", " + yIndex + ").");

                    if ( (((GrowableGroundTile)tile).getState() == GrowableTile.State.SEEDED) &&
                            (((GrowableGroundTile)tile).getIsWatered()) ) {

                        final String message = "BedTile.execute(GameCartridge) FOUND SEEDED && WATERED TILE!!!";
                        Log.d(MainActivity.DEBUG_TAG, message);
                        /////////////////////////////////////////////////////////////////////////////////
                        ((JackInActivity) gameCartridge.getContext()).runOnUiThread(new Runnable() {
                            public void run() {
                                final Toast toast = Toast.makeText(gameCartridge.getContext(), message, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                            }
                        });
                        /////////////////////////////////////////////////////////////////////////////////

                        if ( ((GrowableGroundTile)tile).getType() == GrowableGroundTile.Type.CROP_SEEDED ) {
                            int tileWidth = sceneFarm.getTileMap().getTileWidth();
                            int tileHeight = sceneFarm.getTileMap().getTileHeight();

                            //TODO: currently default is POTATO CropEntity.
                            ///////////////////////////////////////////////////////////////////////////
                            CropEntity sproutling = new CropEntity(gameCartridge, CropEntity.Id.POTATO,
                                    (tile.getxIndex() * tileWidth), (tile.getyIndex() * tileHeight));
                            ///////////////////////////////////////////////////////////////////////////
                            //Tile's CROP_ENTITY
                            ((GrowableGroundTile)tile).setCropEntity(sproutling);
                            //Scene's ENTITY_MANAGER
                            sceneFarm.getEntityManager().addEntity(sproutling);

                            ((GrowableGroundTile)tile).setState(GrowableTile.State.INITIAL);
                            ((GrowableGroundTile)tile).setIsWatered(false);
                        }
                    } else {
                        //may be GrowableTile.State.PREPARED (tilled, unseeded) that had been watered.
                        ((GrowableGroundTile)tile).setIsWatered(false);
                    }
                }
            }
        }

    }

}