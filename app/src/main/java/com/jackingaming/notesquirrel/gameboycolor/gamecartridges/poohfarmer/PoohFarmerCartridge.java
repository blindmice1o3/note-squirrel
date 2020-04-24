package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.ButtonPadFragment;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;
import com.jackingaming.notesquirrel.sandbox.learnfragment.FragmentParentDvdActivity;

import static android.content.Context.MODE_PRIVATE;

public class PoohFarmerCartridge
        implements GameCartridge {

    private Context context;
    private SurfaceHolder holder;   //used to get Canvas
    private Resources resources;
    private InputManager inputManager;


    private int sideSquareScreen;
    private int sideSquareGameCameraInPixel;
    public float pixelToScreenRatio;


    private Player player;
    private GameCamera gameCamera;
    private Scene sceneCurrent;


    public PoohFarmerCartridge(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public void init(SurfaceHolder holder, int sideSquareScreen, InputManager inputManager) {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.init()");

        this.holder = holder;
        this.sideSquareScreen = sideSquareScreen;
        this.inputManager = inputManager;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareScreen: " + sideSquareScreen);


        sideSquareGameCameraInPixel = GameCamera.CLIP_NUMBER_OF_TILES * TileMap.TILE_SIZE;
        Log.d(MainActivity.DEBUG_TAG, "sideSquareGameCameraInPixel: " + sideSquareGameCameraInPixel);
        pixelToScreenRatio = ((float)sideSquareScreen) / sideSquareGameCameraInPixel;
        Log.d(MainActivity.DEBUG_TAG, "pixelToScreenRatio: " + pixelToScreenRatio);


        gameCamera = new GameCamera();
        player = new Player(gameCamera, sideSquareScreen, pixelToScreenRatio);
        sceneCurrent = new Scene(sideSquareScreen);


        Assets.init(resources);
        sceneCurrent.init(player, gameCamera);
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.savePresentState()");

        /////////////////////////////////////////////////////////////////////////////////
        //only THIS activity can get access to THIS preference file.
        SharedPreferences prefs = ((JackInActivity)context).getPreferences(MODE_PRIVATE);
        //Editor is an inner-class of the SharedPreferences class.
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("xCurrentPlayer", player.getxCurrent());
        editor.putFloat("yCurrentPlayer", player.getyCurrent());
        editor.putInt("directionOrdinalPlayer", player.getDirection().ordinal());
        editor.putFloat("xGameCamera", gameCamera.getX());
        editor.putFloat("yGameCamera", gameCamera.getY());

        //HAVE TO tell editor to actually save the values we'd put into it.
        editor.commit();
        /////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.loadSavedState()");

        /////////////////////////////////////////////////////////////////////////////////////
        //retrieving PERSISTENT data (values stored between "runs").
        SharedPreferences prefs = ((JackInActivity)context).getPreferences(MODE_PRIVATE);
        //checking if the key-value pair exists,
        //if does NOT exist (haven't done a put() and commit())...
        //it uses the default value (the second argument).
        float xCurrentPlayer = prefs.getFloat("xCurrentPlayer", 3f*16f);
        float yCurrentPlayer = prefs.getFloat("yCurrentPlayer", 10f*16f);
        int directionOrdinalPlayer = prefs.getInt("directionOrdinalPlayer", 1);
        float xGameCamera = prefs.getFloat("xGameCamera", 2f*16f);
        float yGameCamera = prefs.getFloat("yGameCamera", 8f*16f);
        /////////////////////////////////////////////////////////////////////////////////////

        ////////////////////maybe possible to remove if-checks////////////////////
        if (player != null) {
            player.setxCurrent(xCurrentPlayer);
            player.setyCurrent(yCurrentPlayer);
            player.setDirection(Player.Direction.values()[directionOrdinalPlayer]);
        }
        if (gameCamera != null) {
            gameCamera.setX(xGameCamera);
            gameCamera.setY(yGameCamera);
        }
        //////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void getInputScreen(MotionEvent event) {
        if (inputManager.isJustPressedViewport()) {
            //left
            if (inputManager.isLeftViewport()) {
                int xFuture = (int) (player.getxCurrent() - player.getMoveSpeed());
                int yFutureTop = (int) (player.getyCurrent());
                int yFutureBottom = (int) (player.getyCurrent() + player.getHeight() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFuture, yFutureTop) && !sceneCurrent.getTileMap().isSolid(xFuture, yFutureBottom)) {
                    player.moveLeft();
                    gameCamera.update(0L);
                }
            }
            //right
            else if (inputManager.isRightViewport()) {
                int xFuture = (int) ((player.getxCurrent() + player.getWidth()) + player.getMoveSpeed() - 1);
                int yFutureTop = (int) (player.getyCurrent());
                int yFutureBottom = (int) (player.getyCurrent() + player.getHeight() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFuture, yFutureTop) && !sceneCurrent.getTileMap().isSolid(xFuture, yFutureBottom)) {
                    player.moveRight();
                    gameCamera.update(0L);
                }
            }
            //up
            else if (inputManager.isUpViewport()) {
                int yFuture = (int) (player.getyCurrent() - player.getMoveSpeed());
                int xFutureLeft = (int) (player.getxCurrent());
                int xFutureRight = (int) (player.getxCurrent() + player.getWidth() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFutureLeft, yFuture) && !sceneCurrent.getTileMap().isSolid(xFutureRight, yFuture)) {
                    player.moveUp();
                    gameCamera.update(0L);
                }
            }
            //down
            else if (inputManager.isDownViewport()) {
                int yFuture = (int) ((player.getyCurrent() + player.getHeight()) + player.getMoveSpeed() - 1);
                int xFutureLeft = (int) (player.getxCurrent());
                int xFutureRight = (int) (player.getxCurrent() + player.getWidth() - 1);

                if (!sceneCurrent.getTileMap().isSolid(xFutureLeft, yFuture) && !sceneCurrent.getTileMap().isSolid(xFutureRight, yFuture)) {
                    player.moveDown();
                    gameCamera.update(0L);
                }
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        //TODO: refactor to player.move(direction) and gameCamera.move(direction).
        if (inputManager.isPressingDirectionalPad()) {
            //up
            if (inputManager.isUpDirectionalPad()) {
                player.moveUp();
                gameCamera.update(0L);
            }
            //down
            else if (inputManager.isDownDirectionalPad()) {
                player.moveDown();
                gameCamera.update(0L);
            }
            //left
            else if (inputManager.isLeftDirectionalPad()) {
                player.moveLeft();
                gameCamera.update(0L);
            }
            //right
            else if (inputManager.isRightDirectionalPad()) {
                player.moveRight();
                gameCamera.update(0L);
            }
        }
    }

    @Override
    public void getInputButtonPad(ButtonPadFragment.InputButton inputButton) {
        if (inputManager.isPressingButtonPad()) {
            switch (inputButton) {
                //menu button will launch FragmentParentDvdActivity
                case MENU_BUTTON:
                    Log.d(MainActivity.DEBUG_TAG, "menu-button");
                    Intent fragmentParentDvdIntent = new Intent(context, FragmentParentDvdActivity.class);
                    context.startActivity(fragmentParentDvdIntent);
                    break;
                case A_BUTTON:
                    Log.d(MainActivity.DEBUG_TAG, "a-button");
                    break;
                case B_BUTTON:
                    Log.d(MainActivity.DEBUG_TAG, "b-button");
                    break;
                default:
                    Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.onButtonPadInput(InputButton) switch's default block.");
            }
        }
    }

    @Override
    public void update(long elapsed) {
        ////////////////////////////////////////////////////
        getInputScreen(inputManager.getEvent());
        getInputDirectionalPad();
        getInputButtonPad(inputManager.getInputButton());
        ////////////////////////////////////////////////////

        sceneCurrent.update(elapsed);
    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = holder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            sceneCurrent.render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}