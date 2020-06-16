package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;

public class FroggerCartridge
        implements GameCartridge {

    private Context context;
    private Id idGameCartridge;

    private SurfaceHolder surfaceHolder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Handler handler;
    private Player player;
    private GameCamera gameCamera;
    private SceneManager sceneManager;

    public FroggerCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        //Assets.init(context);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        handler = new Handler(this);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        gameCamera = new GameCamera(widthViewport, heightViewport);
        int clipWidthInTile = 20;
        int clipHeightInTile = 15;
        int tileWidthFrogger = (int)((48f) * (15f/20f));
        int tileHeightFrogger = 48;
        gameCamera.setWidthClipInPixel(clipWidthInTile * tileWidthFrogger);
        gameCamera.setHeightClipInPixel(clipHeightInTile * tileHeightFrogger);

        player = new Player(handler);
        sceneManager = new SceneManager(handler);
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.savePresentState()");
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.loadSavedState()");
    }

    @Override
    public void getInputViewport() {
        if (inputManager.isJustPressedViewport()) {
            //left
            if (inputManager.isLeftViewport()) {
                player.move(Player.Direction.LEFT);
            }
            //right
            else if (inputManager.isRightViewport()) {
                player.move(Player.Direction.RIGHT);
            }
            //up
            else if (inputManager.isUpViewport()) {
                player.move(Player.Direction.UP);
            }
            //down
            else if (inputManager.isDownViewport()) {
                player.move(Player.Direction.DOWN);
            }
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            //up
            if (inputManager.isUpDirectionalPad()) {
                player.move(Player.Direction.UP);
            }
            //down
            else if (inputManager.isDownDirectionalPad()) {
                player.move(Player.Direction.DOWN);
            }
            //left
            else if (inputManager.isLeftDirectionalPad()) {
                player.move(Player.Direction.LEFT);
            }
            //right
            else if (inputManager.isRightDirectionalPad()) {
                player.move(Player.Direction.RIGHT);
            }
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isJustPressedButtonPad()) {
            //menu button (will launch ListFragmentDvdParentActivity)
            if (inputManager.isMenuButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "menu-button");
                Intent fragmentParentDvdIntent = new Intent(context, ListFragmentDvdParentActivity.class);
                context.startActivity(fragmentParentDvdIntent);
            }
            //a button
            else if (inputManager.isaButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "a-button");
            }
            //b button
            else if (inputManager.isbButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "b-button");
            }
        }
    }

    @Override
    public void update(long elapsed) {
        ////////////////////////////////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        ////////////////////////////////////////////////////

        sceneManager.getCurrentScene().update(elapsed);
    }

    @Override
    public void render() {
        //synchronize?
        ////////////////////////////////////
        Canvas canvas = surfaceHolder.lockCanvas();
        ////////////////////////////////////

        if (canvas != null) {
            //Clear the canvas by painting the background white.
            canvas.drawColor(Color.WHITE);

            //@@@@@@@@@@@@@@@@@@@@@@@@@@
            sceneManager.getCurrentScene().render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            surfaceHolder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    @Override
    public Context getContext() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getContext()");
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getIdGameCartridge()");
        return idGameCartridge;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getSurfaceHolder()");
        return surfaceHolder;
    }

    @Override
    public InputManager getInputManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getInputManager()");
        return inputManager;
    }

    @Override
    public int getWidthViewport() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getWidthViewport()");
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getHeightViewport()");
        return heightViewport;
    }

    @Override
    public Handler getHandler() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getHandler()");
        return handler;
    }

    @Override
    public Player getPlayer() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getPlayer()");
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.setPlayer(Player)");
        this.player = player;
    }

    @Override
    public GameCamera getGameCamera() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getGameCamera()");
        return gameCamera;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.setGameCamera(GameCamera)");
        this.gameCamera = gameCamera;
    }

    @Override
    public SceneManager getSceneManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getSceneManager()");
        return sceneManager;
    }

    @Override
    public StateManager getStateManager() {
        Log.d(MainActivity.DEBUG_TAG, "FroggerCartridge.getStateManager()... currently returning \"null\".");
        return null;
    }

}