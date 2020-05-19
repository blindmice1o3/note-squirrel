package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.frogger.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

public class Log extends Creature {

    public enum Size { LARGE, MEDIUM, SMALL; }

    private GameCamera gameCamera;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Size size;

    private Bitmap image;

    public Log(Handler handler, float xCurrent, float yCurrent,
               Direction direction, Size size) {
        super(handler, xCurrent, yCurrent);

        //Do NOT allow Direction.UP or Direction.DOWN (default is Direction.RIGHT).
        if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
            this.direction = direction;
        } else {
            this.direction = Direction.RIGHT;
        }

        this.size = size;

        gameCamera = handler.getGameCartridge().getGameCamera();
        widthPixelToViewportRatio = ((float) handler.getGameCartridge().getWidthViewport()) /
                gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) handler.getGameCartridge().getHeightViewport()) /
                gameCamera.getHeightClipInPixel();

        init();
    }

    @Override
    public void init() {
        android.util.Log.d(MainActivity.DEBUG_TAG, "Log.init()");

        initImage();
        adjustSizeAndBounds();
    }

    private void adjustSizeAndBounds() {
        android.util.Log.d(MainActivity.DEBUG_TAG, "Log.adjustSizeAndBounds()");

        //TODO: WORK-AROUND (FROGGER TILE_WIDTH and TILE_HEIGHT)
        if (handler.getGameCartridge().getIdGameCartridge() == GameCartridge.Id.FROGGER) {
            int tileWidthFrogger = 48;
            int tileHeightFrogger = 48;

            //ADJUST width, height, and bounds.
            switch (size) {
                case LARGE:
                    width = Assets.logLarge.getWidth();
                    break;
                case MEDIUM:
                    width = Assets.logMedium.getWidth();
                    break;
                case SMALL:
                    width = Assets.logSmall.getWidth();
                    break;
            }
            height = 1 * tileHeightFrogger;
            bounds = new Rect(0, 0, width, height);
        }
    }

    private void initImage() {
        if (direction == Direction.RIGHT) {
            switch (size) {
                case LARGE:
                    image = Assets.logLarge;
                    break;
                case MEDIUM:
                    image = Assets.logMedium;
                    break;
                case SMALL:
                    image = Assets.logSmall;
                    break;
            }
        } else if (direction == Direction.LEFT) {
            switch (size) {
                case LARGE:
                    image = Assets.flipHorizontally(Assets.logLarge);
                    break;
                case MEDIUM:
                    image = Assets.flipHorizontally(Assets.logMedium);
                    break;
                case SMALL:
                    image = Assets.flipHorizontally(Assets.logSmall);
                    break;
            }
        }
    }

    @Override
    public void update(long elapsed) {
        xMove = 0;
        yMove = 0;

        switch (direction) {
            case LEFT:
                if ( (xCurrent-moveSpeed) > 0 ) {
                    xMove = -moveSpeed;
                } else {
                    active = false;
                }

                break;
            case RIGHT:
                int widthSceneMax = handler.getGameCartridge().getSceneManager().getCurrentScene().getTileMap().getWidthSceneMax();
                if ( (xCurrent+width+moveSpeed) < widthSceneMax ) {
                    xMove = moveSpeed;
                } else {
                    active = false;
                }

                break;
            default:
                android.util.Log.d(MainActivity.DEBUG_TAG, "Log.update(long) switch (direction)'s default.");
                break;
        }

        move(direction);
    }

    @Override
    public void render(Canvas canvas) {
        Rect bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect screenRect = new Rect(
                (int)( (xCurrent - gameCamera.getX()) * widthPixelToViewportRatio),
                (int)( (yCurrent - gameCamera.getY()) * heightPixelToViewportRatio),
                (int)( ((xCurrent - gameCamera.getX()) + width) * widthPixelToViewportRatio),
                (int)( ((yCurrent - gameCamera.getY()) + height) * heightPixelToViewportRatio) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(image, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

}