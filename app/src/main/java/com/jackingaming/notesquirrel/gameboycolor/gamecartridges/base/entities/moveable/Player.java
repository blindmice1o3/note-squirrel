package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.Item;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.FlowerSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.AxeItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.BugNetItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.FlowerPotItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.GlovedHandsItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.FishingPoleItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.HammerItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.CropSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.ScissorsItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.ShovelItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.ScytheItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.tools.WateringCanItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Animation;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.Holdable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Creature {

    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;
    transient private Map<Direction, Animation> animation;

    private String name = "EeyoreDefault";
    private ArrayList<Item> inventory;
    private int indexSelectedItem;

    private Holdable holdable;

    private int currencyNuggets;
    private int fodderQuantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Holdable getHoldable() {
        return holdable;
    }

    public void setHoldable(Holdable holdable) {
        this.holdable = holdable;
    }

    public int getCurrencyNuggets() {
        return currencyNuggets;
    }

    public void setCurrencyNuggets(int currencyNuggets) {
        this.currencyNuggets = currencyNuggets;
    }

    public int getFodderQuantity() {
        return fodderQuantity;
    }

    public void setFodderQuantity(int fodderQuantity) {
        this.fodderQuantity = fodderQuantity;
    }

    public void dropHoldable(Tile tile) {
        //Holdable.drop(Tile) WILL CHECK FOR ACCEPTABLE TILE.
        if (holdable.drop(tile)) {
            ////////////////
            holdable = null;
            Log.d(MainActivity.DEBUG_TAG, "Player.dropHolderable(Tile) holdable is null.");
            ////////////////
        }
    }

    public Player(GameCartridge gameCartridge) {
        super(gameCartridge,0f, 0f);

        //////////////////////
        indexSelectedItem = 0;
        holdable = null;
        currencyNuggets = 9000;
        fodderQuantity = 0;
        //////////////////////

        /////////////////////////////////////////////////////////////////////////////////////////
        inventory = new ArrayList<Item>();
        //INDOORS_CROP
        inventory.add(new FlowerPotItem(gameCartridge));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.MYSTERY));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.GERANIUM));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.PRIMROSE));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.LAVENDER));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.ORCHID));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.SAGE));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.SAFFRON));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.ROSEMARY));
        inventory.add(new FlowerSeedItem(gameCartridge, FlowerSeedItem.SeedType.CHAMOMILE));
        inventory.add(new WateringCanItem(gameCartridge));
        inventory.add(new ScissorsItem(gameCartridge));
        //OUTDOORS_CROP
        inventory.add(new ShovelItem(gameCartridge));
        inventory.add(new ScytheItem(gameCartridge));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.GRASS));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.TURNIP));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.POTATO));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.TOMATO));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.CORN));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.EGGPLANT));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.PEANUT));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.CARROT));
        inventory.add(new CropSeedItem(gameCartridge, CropSeedItem.SeedType.BROCCOLI));
        inventory.add(new WateringCanItem(gameCartridge));
        inventory.add(new GlovedHandsItem(gameCartridge));
        //UNIMPLEMENTED
        inventory.add(new AxeItem(gameCartridge));
        inventory.add(new HammerItem(gameCartridge));
        inventory.add(new BugNetItem(gameCartridge));
        inventory.add(new FishingPoleItem(gameCartridge));
        /////////////////////////////////////////////////////////////////////////////////////////

        init(gameCartridge);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    @Override
    public void init(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "Player.init(GameCartridge)");
        this.gameCartridge = gameCartridge;

        widthPixelToViewportRatio = ((float) gameCartridge.getWidthViewport()) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) gameCartridge.getHeightViewport()) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        initAnimation();
        initBounds();

        initInventory();

        if (holdable != null) {
            holdable.init(gameCartridge);
        }
    }

    private void initInventory() {
        for (Item item : inventory) {
            item.init(gameCartridge);
        }
    }

    @Override
    public void initBounds() {
        Log.d(MainActivity.DEBUG_TAG, "Player.initBounds()");

        //TODO: WORK-AROUND (FROGGER TILE_WIDTH and TILE_HEIGHT)
        if (gameCartridge.getIdGameCartridge() == GameCartridge.Id.FROGGER) {
            int tileWidthFrogger = 48;
            int tileHeightFrogger = 48;

            //ADJUST width, height, and bounds.
            width = 1 * tileWidthFrogger;
            height = 1 * tileHeightFrogger;
        }

        bounds = new Rect(0, 0, width, height);
    }

    private void initAnimation() {
        Log.d(MainActivity.DEBUG_TAG, "Player.initAnimation()");

        animation = new HashMap<Direction, Animation>();

        Bitmap[][] corgiCrusade = Assets.cropCorgiCrusade(gameCartridge.getContext().getResources());
        animation.put(Direction.DOWN, new Animation(420, corgiCrusade[0]));
        animation.put(Direction.UP, new Animation(420, corgiCrusade[1]));
        animation.put(Direction.LEFT, new Animation(420, corgiCrusade[2]));
        animation.put(Direction.RIGHT, new Animation(420, corgiCrusade[3]));
    }

    @Override
    protected void moveX() {
        TileMap tileMap = gameCartridge.getSceneManager().getCurrentScene().getTileMap();

        //LEFT
        if (xMove < 0) {
            int xFutureLeft = (int) (xCurrent + xMove);
            int xFutureRight = (int) ((xCurrent + width) + xMove - 1);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureTop) && !tileMap.isSolid(xFutureLeft, yFutureBottom)) {

                //////////////////
                xCurrent += xMove;
                //////////////////

                //CHECKING transfer point collision
                Rect collisionBounds = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                tileMap.checkTransferPointsCollision(collisionBounds);
            }
        }
        //RIGHT
        else if (xMove > 0) {
            int xFutureLeft = (int) (xCurrent + xMove);
            int xFutureRight = (int) ((xCurrent + width) + xMove - 1);
            int yFutureTop = (int) (yCurrent);
            int yFutureBottom = (int) (yCurrent + height - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureRight, yFutureTop) && !tileMap.isSolid(xFutureRight, yFutureBottom)) {

                //////////////////
                xCurrent += xMove;
                //////////////////

                //CHECKING transfer point collision
                Rect collisionBounds = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                tileMap.checkTransferPointsCollision(collisionBounds);
            }
        }
    }

    @Override
    protected void moveY() {
        TileMap tileMap = gameCartridge.getSceneManager().getCurrentScene().getTileMap();

        //UP
        if (yMove < 0) {
            int yFutureTop = (int) (yCurrent + yMove);
            int yFutureBottom = (int) ((yCurrent + height) + yMove - 1);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureTop) && !tileMap.isSolid(xFutureRight, yFutureTop)) {

                //////////////////
                yCurrent += yMove;
                //////////////////

                //CHECKING transfer point collision
                Rect collisionBounds = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                tileMap.checkTransferPointsCollision(collisionBounds);
            }
        }
        //DOWN
        else if (yMove > 0) {
            int yFutureTop = (int) (yCurrent + yMove);
            int yFutureBottom = (int) ((yCurrent + height) + yMove - 1);
            int xFutureLeft = (int) (xCurrent);
            int xFutureRight = (int) (xCurrent + width - 1);

            //CHECKING tile collision
            if (!tileMap.isSolid(xFutureLeft, yFutureBottom) && !tileMap.isSolid(xFutureRight, yFutureBottom)) {

                //////////////////
                yCurrent += yMove;
                //////////////////

                //CHECKING transfer point collision
                Rect collisionBounds = new Rect(xFutureLeft, yFutureTop, xFutureRight, yFutureBottom);
                tileMap.checkTransferPointsCollision(collisionBounds);
            }
        }
    }

    //TODO: have FroggerCartridge use a subclass of Player.
    @Override
    public boolean checkEntityCollision(float xOffset, float yOffset) {
        for (Entity e : gameCartridge.getSceneManager().getCurrentScene().getEntityManager().getEntities()) {
            //if the entity calling checkEntityCollision(float, float) finds ITSELF in the collection, skip by continue.
            if (e.equals(this)) {
                continue;
            }
            //player's HOLDABLE, do NOT count as collision
            else if (holdable != null) {
                if (e.equals(holdable)) {
                    continue;
                }
            }

            //check EACH entity to see if their collision bounds INTERSECTS with yours.
            if (e.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xOffset, yOffset))) {
                //Frog can walk on Log instances.
                ////////////////////////
                if (e instanceof com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.entities.moveable.Log) {
                    return false;
                } else {
                    return true;
                }
                ////////////////////////
            }
        }

        return false;
    }

    @Override
    public void update(long elapsed) {
        xMove = 0f;
        yMove = 0f;

        if (holdable != null) {
            holdable.updatePosition(xCurrent-(width/2), yCurrent-(height/2));
        }

        for (Animation anim : animation.values()) {
            anim.update(elapsed);
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap currentFrame = currentAnimationFrame();

        Rect rectOfImage = new Rect(0, 0, currentFrame.getWidth(), currentFrame.getHeight());
        Rect rectOnScreen = new Rect(
                (int)( (xCurrent - gameCartridge.getGameCamera().getX()) * widthPixelToViewportRatio ),
                (int)( (yCurrent - gameCartridge.getGameCamera().getY()) * heightPixelToViewportRatio ),
                (int)( ((xCurrent - gameCartridge.getGameCamera().getX()) + width) * widthPixelToViewportRatio ),
                (int)( ((yCurrent - gameCartridge.getGameCamera().getY()) + height) * heightPixelToViewportRatio ) );

//        Rect rectOnScreen = new Rect(
//                (int)(64 * pixelToScreenRatio),
//                (int)(64 * pixelToScreenRatio),
//                (int)((64 + width)* pixelToScreenRatio),
//                (int)((64 + height) * pixelToScreenRatio)
//        );

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        canvas.drawBitmap(currentFrame, rectOfImage, rectOnScreen, null);
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
                Log.d(MainActivity.DEBUG_TAG, "@@@@@Player.currentAnimationFrame() switch (direction) construct's default block.@@@@@");
                currentFrame = animation.get(Direction.DOWN).getCurrentFrame();
                break;
        }

        return currentFrame;
    }

    public Entity getEntityCurrentlyFacing() {
        Log.d(MainActivity.DEBUG_TAG, "Player.getEntityCurrentlyFacing()");

        Entity tempEntityReturner = null;

        int creatureCenterX = (int)(xCurrent + (width / 2));
        int creatureCenterY = (int)(yCurrent + (height / 2));

        Rect entityCollisionBox = new Rect();
        switch (direction) {
            case DOWN:
                entityCollisionBox.left = (creatureCenterX-(TileMap.TILE_WIDTH/4));
                entityCollisionBox.top = (creatureCenterY+(TileMap.TILE_HEIGHT/2)+((int)(0.3)*TileMap.TILE_HEIGHT));
                entityCollisionBox.right = (creatureCenterX-(TileMap.TILE_WIDTH/4)) +
                        (TileMap.TILE_WIDTH/2);
                entityCollisionBox.bottom = (creatureCenterY+(TileMap.TILE_HEIGHT/2)+((int)(0.3)*TileMap.TILE_HEIGHT)) +
                        (TileMap.TILE_HEIGHT/2);
                break;
            case UP:
                entityCollisionBox.left = (creatureCenterX-(TileMap.TILE_WIDTH/4));
                entityCollisionBox.top = (creatureCenterY-((int)(1.4)*TileMap.TILE_HEIGHT));
                entityCollisionBox.right = (creatureCenterX-(TileMap.TILE_WIDTH/4)) +
                        (TileMap.TILE_WIDTH/2);
                entityCollisionBox.bottom = (creatureCenterY-((int)(1.4)*TileMap.TILE_HEIGHT)) +
                        (TileMap.TILE_HEIGHT/2);
                break;
            case LEFT:
                entityCollisionBox.left = (creatureCenterX-((int)(1.4)*TileMap.TILE_WIDTH));
                entityCollisionBox.top = (creatureCenterY-(TileMap.TILE_HEIGHT/4));
                entityCollisionBox.right = (creatureCenterX-((int)(1.4)*TileMap.TILE_WIDTH)) +
                        (TileMap.TILE_WIDTH/2);
                entityCollisionBox.bottom = (creatureCenterY-(TileMap.TILE_HEIGHT/4)) +
                        (TileMap.TILE_HEIGHT/2);
                break;
            case RIGHT:
                entityCollisionBox.left = (creatureCenterX+(TileMap.TILE_WIDTH/2)+((int)(0.3)*TileMap.TILE_WIDTH));
                entityCollisionBox.top = (creatureCenterY-(TileMap.TILE_HEIGHT/4));
                entityCollisionBox.right = (creatureCenterX+(TileMap.TILE_WIDTH/2)+((int)(0.3)*TileMap.TILE_WIDTH)) +
                        (TileMap.TILE_WIDTH/2);
                entityCollisionBox.bottom = (creatureCenterY-(TileMap.TILE_HEIGHT/4)) +
                        (TileMap.TILE_HEIGHT/2);
                break;
            default:
                break;
        }

        for (Entity e : gameCartridge.getSceneManager().getCurrentScene().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }
            //player's HOLDABLE, do NOT count as collision
            else if (holdable != null) {
                if (e.equals(holdable)) {
                    continue;
                }
            }

            if (entityCollisionBox.intersect(e.getCollisionBounds(0, 0))) {
                tempEntityReturner = e;
            }
        }

        if (tempEntityReturner != null) {
            Log.d(MainActivity.DEBUG_TAG, "Player.getEntityCurrentlyFacing() entity: " + tempEntityReturner.toString());
        } else {
            Log.d(MainActivity.DEBUG_TAG, "Player.getEntityCurrentlyFacing() entity is null");
        }

        return tempEntityReturner;
    }

    public Tile getTileCurrentlyFacing() {
        TileMap tileMap = gameCartridge.getSceneManager().getCurrentScene().getTileMap();
        int tileWidth = tileMap.getTileWidth();
        int tileHeight = tileMap.getTileHeight();

        float xPlayerCenter = xCurrent + (width / 2);
        float yPlayerCenter = yCurrent + (height / 2);

        Tile tileFacing = null;
        int xInspectIndex = 0;
        int yInspectIndex = 0;
        switch (direction) {
            case UP:
                xInspectIndex = (int) (xPlayerCenter / tileWidth);
                yInspectIndex = (int) ((yPlayerCenter - tileHeight) / tileHeight);
                break;
            case DOWN:
                xInspectIndex = (int) (xPlayerCenter / tileWidth);
                yInspectIndex = (int) ((yPlayerCenter + tileHeight) / tileHeight);
                break;
            case LEFT:
                xInspectIndex = (int) ((xPlayerCenter - tileWidth) / tileWidth);
                yInspectIndex = (int) (yPlayerCenter / tileHeight);
                break;
            case RIGHT:
                xInspectIndex = (int) ((xPlayerCenter + tileWidth) / tileWidth);
                yInspectIndex = (int) (yPlayerCenter / tileHeight);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "@@@@@Player.getTileCurrentlyFacing() switch construct's default block.@@@@@");
                break;
        }

        ///////////////////////////////////////////////////////////
        tileFacing = tileMap.getTile(xInspectIndex, yInspectIndex);
        ///////////////////////////////////////////////////////////

        Log.d(MainActivity.DEBUG_TAG, "Player.getTileCurrentlyFacing at: (" + xInspectIndex + ", " + yInspectIndex + ").");
        if (tileFacing != null) {
            final String message = tileFacing.getClass().getSimpleName();
            Log.d(MainActivity.DEBUG_TAG, "tileFacing is: " + message);
            /////////////////////////////////////////////////////////////////////////////////
            ((JackInActivity) gameCartridge.getContext()).runOnUiThread(new Runnable() {
                public void run() {
                    final Toast toast = Toast.makeText(gameCartridge.getContext(), message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            });
            /////////////////////////////////////////////////////////////////////////////////
        } else {
            final String message = "tileFacing is null";
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
        }

        return tileFacing;
    }

    public void incrementIndexSelectedItem() {
        indexSelectedItem++;

        if (indexSelectedItem >= inventory.size()) {
            indexSelectedItem = 0;
        }
    }

    public void setIndexSelectedItem(int indexSelectedItem) {
        this.indexSelectedItem = indexSelectedItem;
    }

    public Item getSelectedItem() {
        return inventory.get(indexSelectedItem);
    }

}