package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;

import java.util.HashMap;
import java.util.Map;

public class Player extends Creature {

    private GameCartridge gameCartridge;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Map<Direction, Animation> animation;

    public Player(Handler handler) {
        super(handler,0f, 0f);

        gameCartridge = handler.getGameCartridge();
        widthPixelToViewportRatio = ((float) handler.getGameCartridge().getWidthViewport()) /
                handler.getGameCartridge().getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) handler.getGameCartridge().getHeightViewport()) /
                handler.getGameCartridge().getGameCamera().getHeightClipInPixel();
    }

    @Override
    public void init() {
        Log.d(MainActivity.DEBUG_TAG, "Player.init()");

        initAnimation();
    }

    private void initAnimation() {
        Log.d(MainActivity.DEBUG_TAG, "Player.initAnimation()");

        animation = new HashMap<Direction, Animation>();

        animation.put(Direction.DOWN, new Animation(420, Assets.corgiCrusade[0]));
        animation.put(Direction.UP, new Animation(420, Assets.corgiCrusade[1]));
        animation.put(Direction.LEFT, new Animation(420, Assets.corgiCrusade[2]));
        animation.put(Direction.RIGHT, new Animation(420, Assets.corgiCrusade[3]));
    }

    @Override
    public void update(long elapsed) {
        xMove = 0f;
        yMove = 0f;

        for (Animation anim : animation.values()) {
            anim.update();
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = currentAnimationFrame();

        Rect bounds = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());

//        Rect screenRect = new Rect(
//                (int)(64 * pixelToScreenRatio),
//                (int)(64 * pixelToScreenRatio),
//                (int)((64 + width)* pixelToScreenRatio),
//                (int)((64 + height) * pixelToScreenRatio)
//        );

        Rect screenRect = new Rect(
                (int)( (xCurrent - handler.getGameCartridge().getGameCamera().getX()) *
                        widthPixelToViewportRatio ),
                (int)( (yCurrent - handler.getGameCartridge().getGameCamera().getY()) *
                        heightPixelToViewportRatio ),
                (int)( ((xCurrent - handler.getGameCartridge().getGameCamera().getX()) + width) *
                        widthPixelToViewportRatio ),
                (int)( ((yCurrent - handler.getGameCartridge().getGameCamera().getY()) + height) *
                        heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    private Bitmap currentAnimationFrame() {
        Bitmap currentFrame = null;

        switch (direction) {
            case UP:
                currentFrame = animation.get(Direction.UP).getCurrentFrame();
                break;
            case DOWN:
                currentFrame = animation.get(Direction.DOWN).getCurrentFrame();
                break;
            case LEFT:
                currentFrame = animation.get(Direction.LEFT).getCurrentFrame();
                break;
            case RIGHT:
                currentFrame = animation.get(Direction.RIGHT).getCurrentFrame();
                break;
            default:
                currentFrame = Assets.corgiCrusade[0][0];
                break;
        }

        return currentFrame;
    }

    public void checkTileFacing() {
        Log.d(MainActivity.DEBUG_TAG, "Player.checkTileFacing()");

        TileMap tileMap = gameCartridge.getSceneManager().getCurrentScene().getTileMap();

        /////////////////////////////////////////////////////
        if (gameCartridge instanceof PocketCrittersCartridge) {
        /////////////////////////////////////////////////////
            float xPlayerCenter = xCurrent + (width / 2);
            float yPlayerCenter = yCurrent + (height / 2);

            int xInspect = 0;
            int yInspect = 0;
            switch (direction) {
                case UP:
                    xInspect = (int) (xPlayerCenter);
                    yInspect = (int) (yPlayerCenter - ((height / 2) + 1));
                    break;
                case DOWN:
                    xInspect = (int) (xPlayerCenter);
                    yInspect = (int) (yPlayerCenter + ((height / 2) + 1));
                    break;
                case LEFT:
                    xInspect = (int) (xPlayerCenter - ((width / 2) + 1));
                    yInspect = (int) (yPlayerCenter);
                    break;
                case RIGHT:
                    xInspect = (int) (xPlayerCenter + ((width / 2) + 1));
                    yInspect = (int) (yPlayerCenter);
                    break;
                default:
                    Log.d(MainActivity.DEBUG_TAG, "Player.checkTileFacing() switch construct's default block.");
                    break;
            }

            Log.d(MainActivity.DEBUG_TAG, "checkTileFacing at (currently in pixel values): (" + xInspect + ", " + yInspect + ").");
            ////////////////////////////////////////////////////////////////////
            TileMap.TileType tileFacing = tileMap.checkTile(xInspect, yInspect);
            ////////////////////////////////////////////////////////////////////
            if (tileFacing != null) {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing: " + tileFacing.name());

                final String message = tileFacing.name();
                final Context context = ((PocketCrittersCartridge)gameCartridge).getContext();
                /////////////////////////////////////////////////////////////////////////////////
                ((JackInActivity)context).runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }
                });
                /////////////////////////////////////////////////////////////////////////////////
                
                /*
                if ( (tileFacing == TileMap.TileType.COMPUTER) ||
                        (tileFacing == TileMap.TileType.GAME_CONSOLE) ||
                        (tileFacing == TileMap.TileType.TELEVISION) ) {

                    final String message = tileFacing.name();
                    final Context context = ((PocketCrittersCartridge)gameCartridge).getContext();
                    /////////////////////////////////////////////////////////////////////////////////
                    ((JackInActivity)context).runOnUiThread(new Runnable() {
                        public void run() {
                            final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    });
                    /////////////////////////////////////////////////////////////////////////////////
                }
                */
            } else {
                Log.d(MainActivity.DEBUG_TAG, "tileFacing is null");
            }
        }
    }

}