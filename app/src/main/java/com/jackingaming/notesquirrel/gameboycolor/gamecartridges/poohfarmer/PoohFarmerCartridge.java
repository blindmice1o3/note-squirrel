package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;
import com.jackingaming.notesquirrel.gameboycolor.sprites.Assets;
import com.jackingaming.notesquirrel.sandbox.learnfragment.FragmentParentDvdActivity;

import static android.content.Context.MODE_PRIVATE;

public class PoohFarmerCartridge
        implements GameCartridge {

    private Context context;

    private SurfaceHolder holder;   //used to get Canvas
    private InputManager inputManager;
    private int widthScreen;
    private int heightScreen;
    private int widthViewport;
    private int heightViewport;

    private Id idGameCartridge;
    private Player player;
    private GameCamera gameCamera;

    //TODO: Map<GameCartridge.Id, SceneManager>...
    // Each SceneManager will have their own Map<Scene.Id, Scene>...
    // Each Scene will have tile index initialization values used for both TileMap and texture.
    private SceneManager sceneManager;
    //private Scene sceneCurrent;

    public PoohFarmerCartridge(Context context, Id idGameCartridge) {
        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.holder = holder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        Assets.init(context);

        gameCamera = new GameCamera();
        player = new Player(gameCamera, widthViewport, heightViewport);
        sceneManager = new SceneManager(context, widthViewport, heightViewport, idGameCartridge);
        sceneManager.init(player, gameCamera);
        //sceneCurrent = sceneManager.getCurrentScene();
        //sceneCurrent.init(player, gameCamera);
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.savePresentState()");

        if (idGameCartridge == Id.POOH_FARMER) {
            /////////////////////////////////////////////////////////////////////////////////
            //only THIS activity can get access to THIS preference file.
            SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
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
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "PoohFarmerCartridge.loadSavedState()");

        if (idGameCartridge == Id.POOH_FARMER) {
            /////////////////////////////////////////////////////////////////////////////////////
            //retrieving PERSISTENT data (values stored between "runs").
            SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
            //checking if the key-value pair exists,
            //if does NOT exist (haven't done a put() and commit())...
            //it uses the default value (the second argument).
            float xCurrentPlayer = prefs.getFloat("xCurrentPlayer", 3f * 16f);
            float yCurrentPlayer = prefs.getFloat("yCurrentPlayer", 10f * 16f);
            int directionOrdinalPlayer = prefs.getInt("directionOrdinalPlayer", 1);
            float xGameCamera = prefs.getFloat("xGameCamera", 2f * 16f);
            float yGameCamera = prefs.getFloat("yGameCamera", 8f * 16f);
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
        if (inputManager.isPressingButtonPad()) {
            //menu button (will launch FragmentParentDvdActivity)
            if (inputManager.isMenuButtonPad()) {
                Log.d(MainActivity.DEBUG_TAG, "menu-button");
                Intent fragmentParentDvdIntent = new Intent(context, FragmentParentDvdActivity.class);
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

        sceneManager.update(elapsed);
        //sceneCurrent.update(elapsed);
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
            sceneManager.render(canvas);
            //sceneCurrent.render(canvas);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            holder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

}