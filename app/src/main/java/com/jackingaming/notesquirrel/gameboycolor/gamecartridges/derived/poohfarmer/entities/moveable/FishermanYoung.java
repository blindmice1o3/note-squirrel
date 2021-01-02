package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.entities.moveable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Creature;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FishermanYoung extends Creature {

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    transient private Map<Direction, Animation> animationWalk;

    public FishermanYoung(GameCartridge gameCartridge, float xCurrent, float yCurrent) {
        super(gameCartridge, xCurrent, yCurrent);

        init(gameCartridge);
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "FishermanYoung.init(GameCartridge)");
        this.gameCartridge = gameCartridge;
        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        initImage(gameCartridge.getContext().getResources());
        initBounds();
    }

    @Override
    public void initBounds() {
        Log.d(MainActivity.DEBUG_TAG, "FishermanYoung.initBounds()");
        bounds = new Rect(0, 0, width, height);
    }

    private void initImage(Resources resources) {
        Bitmap spriteSheet = BitmapFactory.decodeResource(resources, R.drawable.gbc_hm_will_and_bill);

        Bitmap[] walkUp = new Bitmap[2];
        Bitmap[] walkDown = new Bitmap[2];
        Bitmap[] walkLeft = new Bitmap[1];
        Bitmap[] walkRight = new Bitmap[1];

        int x = 41;
        int y = 157;
        int width = 16;
        int height = 24;
        walkUp[0] = Bitmap.createBitmap(spriteSheet, x, y, width, height);
        x += (width + 1);
        walkUp[1] = Bitmap.createBitmap(spriteSheet, x, y, width, height);
        x += (width + 1);
        walkDown[0] = Bitmap.createBitmap(spriteSheet, x, y, width, height);
        x += (width + 1);
        walkDown[1] = Bitmap.createBitmap(spriteSheet, x, y, width, height);
        x += (width + 1);
        walkLeft[0] = Bitmap.createBitmap(spriteSheet, x, y, (width - 1), height);
        walkRight[0] = Assets.flipHorizontally(walkLeft[0]);

        animationWalk = new HashMap<Direction, Animation>();
        animationWalk.put(Direction.UP, new Animation(500, walkUp));
        animationWalk.put(Direction.DOWN, new Animation(500, walkDown));
        animationWalk.put(Direction.LEFT, new Animation(500, walkLeft));
        animationWalk.put(Direction.RIGHT, new Animation(500, walkRight));
    }

    @Override
    public void update(long elapsed) {
        xMove = 0f;
        yMove = 0f;

        for (Animation anim : animationWalk.values()) {
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

        Rect rectOfImage = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCartridge.getGameCamera().getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCartridge.getGameCamera().getY()) + height) * heightPixelToViewportRatio ) );

        canvas.drawBitmap(currentFrame, rectOfImage, rectOnScreen, null);
    }

    private Bitmap determineCurrentAnimationFrame() {
        Bitmap currentFrame = null;

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
                currentFrame = animationWalk.get(Direction.DOWN).getCurrentFrame();
                break;

        }

        return currentFrame;
    }

}
