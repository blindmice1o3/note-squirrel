package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Chicken extends Creature {

    public enum Stage { BABY, ADULT; }

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    private Stage stage;

    transient private Map<Direction, Animation> animationWalkBaby;
    transient private Map<Direction, Animation> animationWalkAdult;

    public Chicken(GameCartridge gameCartridge, float xCurrent, float yCurrent, Stage stage) {
        super(gameCartridge, xCurrent, yCurrent);

        this.stage = stage;

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "Chicken.init(GameCartridge)");
        this.gameCartridge = gameCartridge;

        GameCamera gameCamera = gameCartridge.getGameCamera();
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCamera.getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCamera.getHeightClipInPixel();

        initImage(gameCartridge.getContext().getResources());
        initBounds();
    }

    @Override
    public void initBounds() {
        Log.d(MainActivity.DEBUG_TAG, "Chicken.initBounds()");
        bounds = new Rect(0, 0, width, height);
    }

    private void initImage(Resources resources) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        Bitmap spriteSheetBaby = BitmapFactory.decodeResource(resources, R.drawable.snes_hm_chicken);
        ////////////////////////////////////////////////////////////////////////////////////////////

        Bitmap[] walkUpBaby = new Bitmap[2];
        walkUpBaby[0] = Bitmap.createBitmap(spriteSheetBaby, 300, 0, 12, 16);
        walkUpBaby[1] = Bitmap.createBitmap(spriteSheetBaby, 300, 30, 12, 16);

        Bitmap[] walkDownBaby = new Bitmap[2];
        walkDownBaby[0] = Bitmap.createBitmap(spriteSheetBaby, 270, 0, 16, 16);
        walkDownBaby[1] = Bitmap.createBitmap(spriteSheetBaby, 270, 30, 16, 16);

        Bitmap[] walkLeftBaby = new Bitmap[2];
        walkLeftBaby[0] = Bitmap.createBitmap(spriteSheetBaby, 240, 0, 16, 16);
        walkLeftBaby[1] = Bitmap.createBitmap(spriteSheetBaby, 240, 30, 16, 16);

        Bitmap[] walkRightBaby = new Bitmap[2];
        walkRightBaby[0] = Assets.flipHorizontally( walkLeftBaby[0] );
        walkRightBaby[1] = Assets.flipHorizontally( walkLeftBaby[1] );

        animationWalkBaby = new HashMap<Direction, Animation>();
        animationWalkBaby.put(Direction.UP, new Animation(500, walkUpBaby));
        animationWalkBaby.put(Direction.DOWN, new Animation(500, walkDownBaby));
        animationWalkBaby.put(Direction.LEFT, new Animation(500, walkLeftBaby));
        animationWalkBaby.put(Direction.RIGHT, new Animation(500, walkRightBaby));

        ////////////////////////////////////////////////////////////////////////////////////////////
        Bitmap spriteSheetAdult = BitmapFactory.decodeResource(resources, R.drawable.snes_hm_creatures);
        ////////////////////////////////////////////////////////////////////////////////////////////

        Bitmap[] walkUpAdult = new Bitmap[3];
        walkUpAdult[0] = Bitmap.createBitmap(spriteSheetAdult, 147, 144, 16, 16);
        walkUpAdult[1] = Bitmap.createBitmap(spriteSheetAdult, 172, 145, 16, 16);
        walkUpAdult[2] = Bitmap.createBitmap(spriteSheetAdult, 195, 144, 16, 16);

        Bitmap[] walkDownAdult = new Bitmap[3];
        walkDownAdult[0] = Bitmap.createBitmap(spriteSheetAdult, 147, 207, 16, 16);
        walkDownAdult[1] = Bitmap.createBitmap(spriteSheetAdult, 171, 208, 16, 16);
        walkDownAdult[2] = Bitmap.createBitmap(spriteSheetAdult, 195, 207, 16, 16);

        Bitmap[] walkLeftAdult = new Bitmap[3];
        walkLeftAdult[0] = Bitmap.createBitmap(spriteSheetAdult, 147, 239, 16, 16);
        walkLeftAdult[1] = Bitmap.createBitmap(spriteSheetAdult, 171, 240, 16, 16);
        walkLeftAdult[2] = Bitmap.createBitmap(spriteSheetAdult, 195, 239, 16, 16);

        Bitmap[] walkRightAdult = new Bitmap[3];
        walkRightAdult[0] = Bitmap.createBitmap(spriteSheetAdult, 147, 175, 16, 16);
        walkRightAdult[1] = Bitmap.createBitmap(spriteSheetAdult, 171, 176, 16, 16);
        walkRightAdult[2] = Bitmap.createBitmap(spriteSheetAdult, 195, 175, 16, 16);

        animationWalkAdult = new HashMap<Direction, Animation>();
        animationWalkAdult.put(Direction.UP, new Animation(500, walkUpAdult));
        animationWalkAdult.put(Direction.DOWN, new Animation(500, walkDownAdult));
        animationWalkAdult.put(Direction.LEFT, new Animation(500, walkLeftAdult));
        animationWalkAdult.put(Direction.RIGHT, new Animation(500, walkRightAdult));
    }

    @Override
    public void update(long elapsed) {
        xMove = 0f;
        yMove = 0f;

        for (Animation anim : animationWalkBaby.values()) {
            anim.update(elapsed);
        }
        for (Animation anim : animationWalkAdult.values()) {
            anim.update(elapsed);
        }

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
                    xMove = 0;
                    yMove = 0;
                    break;
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = determineCurrentAnimationFrame();
        GameCamera gameCamera = gameCartridge.getGameCamera();

        Rect rectOfImage = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCamera.getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCamera.getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCamera.getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCamera.getY()) + height) * heightPixelToViewportRatio ) );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, rectOfImage, rectOnScreen, null);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    }

    private Bitmap determineCurrentAnimationFrame() {
        Bitmap currentFrame = null;

        if (stage == Stage.BABY) {
            switch (direction) {
                case UP:
                    currentFrame = animationWalkBaby.get(Direction.UP).getCurrentFrame();
                    break;
                case DOWN:
                    currentFrame = animationWalkBaby.get(Direction.DOWN).getCurrentFrame();
                    break;
                case LEFT:
                    currentFrame = animationWalkBaby.get(Direction.LEFT).getCurrentFrame();
                    break;
                case RIGHT:
                    currentFrame = animationWalkBaby.get(Direction.RIGHT).getCurrentFrame();
                    break;
            }

        } else if (stage == Stage.ADULT) {
            switch (direction) {
                case UP:
                    currentFrame = animationWalkAdult.get(Direction.UP).getCurrentFrame();
                    break;
                case DOWN:
                    currentFrame = animationWalkAdult.get(Direction.DOWN).getCurrentFrame();
                    break;
                case LEFT:
                    currentFrame = animationWalkAdult.get(Direction.LEFT).getCurrentFrame();
                    break;
                case RIGHT:
                    currentFrame = animationWalkAdult.get(Direction.RIGHT).getCurrentFrame();
                    break;
            }
        }

        return currentFrame;
    }

}