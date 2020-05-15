package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Animation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Robot extends Creature {

    public enum State { OFF, WALK, RUN; }

    private GameCamera gameCamera;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private State state;

    private Bitmap image;
    private Map<Direction, Animation> animationWalk;
    private Map<Direction, Animation> animationRun;

    public Robot(Handler handler, float xCurrent, float yCurrent) {
        super(handler, xCurrent, yCurrent);

        gameCamera = handler.getGameCartridge().getGameCamera();
        widthPixelToViewportRatio = ((float) handler.getGameCartridge().getWidthViewport()) / gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) handler.getGameCartridge().getHeightViewport()) / gameCamera.getHeightClipInPixel();

        state = State.OFF;

        initImage(handler.getGameCartridge().getContext().getResources());
    }

    @Override
    public void init() {

    }

    private void initImage(Resources resources) {
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.snes_chrono_trigger_r_series);

        //crop all non-pink sprites////////////////////////////////////////////////////
        Bitmap[][] robotRSeries = new Bitmap[22][11];

        int widthSprite = 32;
        int heightSprite = 32;
        int offsetHorizontal = 8;
        int offsetVertical = 8;
        for (int row = 0; row < robotRSeries.length; row++) {
            for (int column = 0; column < robotRSeries[row].length; column++) {
                int xStart = column * (widthSprite + offsetHorizontal);
                int yStart = row * (heightSprite + offsetVertical);
                robotRSeries[row][column] = Bitmap.createBitmap(spriteSheet, xStart, yStart,
                        widthSprite, heightSprite);
            }
        }
        ///////////////////////////////////////////////////////////////////////////////

        //group sprites by animationWalk category//////////////////////////////////////////
        Bitmap[] walkDown = new Bitmap[5];
        Bitmap[] walkLeft = new Bitmap[5];
        Bitmap[] walkUp = new Bitmap[5];
        Bitmap[] walkRight = new Bitmap[5];
        Bitmap[] runDown = new Bitmap[5];
        Bitmap[] runLeft = new Bitmap[5];
        Bitmap[] runUp = new Bitmap[5];
        Bitmap[] runRight = new Bitmap[5];
        int xIndexStart = 3;
        int yIndexStart = 17;
        for (int xIndex = xIndexStart; xIndex < robotRSeries[0].length; xIndex++) {
            for (int yIndex = yIndexStart; yIndex < robotRSeries.length; yIndex++) {
                //walkDown
                if (xIndex == 3) {
                    walkDown[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //walkLeft
                else if (xIndex == 4) {
                    walkLeft[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //walkUp
                else if (xIndex == 5) {
                    walkUp[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //walkRight
                else if (xIndex == 6) {
                    walkRight[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //runDown
                else if (xIndex == 7) {
                    runDown[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //runLeft
                else if (xIndex == 8) {
                    runLeft[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //runUp
                else if (xIndex == 9) {
                    runUp[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
                //runRight
                else if (xIndex == 10) {
                    runRight[yIndex-yIndexStart] = robotRSeries[yIndex][xIndex];
                }
            }
        }

        image = robotRSeries[6][1];
        ///////////////////////////////////////////////////////////////////////////////

        //initialize the collection of animationWalk///////////////////////////////////////
        animationWalk = new HashMap<Direction, Animation>();
        animationWalk.put(Direction.DOWN, new Animation(500, walkDown));
        animationWalk.put(Direction.LEFT, new Animation(500, walkLeft));
        animationWalk.put(Direction.UP, new Animation(500, walkUp));
        animationWalk.put(Direction.RIGHT, new Animation(500, walkRight));

        animationRun = new HashMap<Direction, Animation>();
        animationRun.put(Direction.DOWN, new Animation(500, runDown));
        animationRun.put(Direction.LEFT, new Animation(500, runLeft));
        animationRun.put(Direction.UP, new Animation(500, runUp));
        animationRun.put(Direction.RIGHT, new Animation(500, runRight));
        ///////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void update(long elapsed) {
        xMove = 0f;
        yMove = 0f;

        for (Animation anim : animationWalk.values()) {
            anim.update();
        }
        for (Animation anim : animationRun.values()) {
            anim.update();
        }

        /////////////////////////////////
        if (state == State.WALK) {
            moveSpeed = 4f;
        } else if (state == State.RUN) {
            moveSpeed = 6f;
        } else if (state == State.OFF) {
            moveSpeed = 0f;
        }
        /////////////////////////////////

        decideWalkRandomDirection();
    }

    private void decideWalkRandomDirection() {
        Random random = new Random();

        if (random.nextInt(100) == 1) {
            int moveDir = random.nextInt(5);

            switch (moveDir) {
                case 0:
                    move(Direction.LEFT);
                    break;
                case 1:
                    move(Direction.RIGHT);
                    break;
                case 2:
                    move(Direction.UP);
                    break;
                case 3:
                    move(Direction.DOWN);
                    break;
                default:
                    //TODO: maybe set state to State.OFF
                    xMove = 0;
                    yMove = 0;
                    break;
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = currentAnimationFrame();

        Rect bounds = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect screenRect = new Rect(
                (int)( (xCurrent - gameCamera.getX()) * widthPixelToViewportRatio),
                (int)( (yCurrent - gameCamera.getY()) * heightPixelToViewportRatio),
                (int)( ((xCurrent - gameCamera.getX()) + width) * widthPixelToViewportRatio),
                (int)( ((yCurrent - gameCamera.getY()) + height) * heightPixelToViewportRatio) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, bounds, screenRect, null);
        //canvas.drawBitmap(image, bounds, screenRect, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    private Bitmap currentAnimationFrame() {
        Bitmap currentFrame = null;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//        if (xMove == 0f && yMove == 0f) { return image; }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        if (state == State.OFF) {
            currentFrame = image;
        } else if (state == State.WALK) {
            switch (direction) {
                case UP:
                    currentFrame = animationWalk.get(Direction.UP).getCurrentFrame();
                    break;
                case DOWN:
                    currentFrame = animationWalk.get(Direction.DOWN).getCurrentFrame();
                    break;
                case LEFT:
                    currentFrame = animationWalk.get(Direction.LEFT).getCurrentFrame();
                    break;
                case RIGHT:
                    currentFrame = animationWalk.get(Direction.RIGHT).getCurrentFrame();
                    break;
                default:
                    currentFrame = image;
                    break;
            }
        } else if (state == State.RUN) {
            switch (direction) {
                case UP:
                    currentFrame = animationRun.get(Direction.UP).getCurrentFrame();
                    break;
                case DOWN:
                    currentFrame = animationRun.get(Direction.DOWN).getCurrentFrame();
                    break;
                case LEFT:
                    currentFrame = animationRun.get(Direction.LEFT).getCurrentFrame();
                    break;
                case RIGHT:
                    currentFrame = animationRun.get(Direction.RIGHT).getCurrentFrame();
                    break;
                default:
                    currentFrame = image;
                    break;
            }
        }

        return currentFrame;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

}