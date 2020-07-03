package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

import static android.content.Context.MODE_PRIVATE;

public class GameCartridge
        implements IGameCartridge {

    protected Context context;
    protected Id idGameCartridge;

    protected SurfaceHolder surfaceHolder;   //used to get Canvas
    protected InputManager inputManager;
    protected int widthScreen;
    protected int heightScreen;
    protected int widthViewport;
    protected int heightViewport;

    protected Player player;
    protected GameCamera gameCamera;
    private HeadUpDisplay headUpDisplay;
    protected StateManager stateManager;

    public GameCartridge(Context context, Id idGameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "GameCartridge(Context, Id) constructor");

        this.context = context;
        this.idGameCartridge = idGameCartridge;
    }

    @Override
    public void init(SurfaceHolder surfaceHolder, InputManager inputManager, int widthScreen, int heightScreen) {
        Log.d(MainActivity.DEBUG_TAG, "GameCartridge.init(SurfaceHolder, InputManager, int, int)");

        this.surfaceHolder = surfaceHolder;
        this.inputManager = inputManager;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        ////////////////////////////////////////////////////
        widthViewport = Math.min(widthScreen, heightScreen);
        heightViewport = widthViewport; //SQUARE VIEWPORT!!!
        ////////////////////////////////////////////////////

        gameCamera = new GameCamera(widthViewport, heightViewport);
        player = new Player(this);
        headUpDisplay = new HeadUpDisplay(this);
        ///////////////////////////////////////////////////
        stateManager = new StateManager(this);
        ///////////////////////////////////////////////////

        //TODO: if isReturningFromActivity... then load
        if ( ((JackInActivity)context).isReturningFromActivity() ) {
            loadSavedState();

            //TODO: if isReturningFromBackpackActivity... then set player's indexSelectedItem
            if ( ((JackInActivity)context).isReturningFromBackpackActivity() ) {
                //////////////////////////////////////
                int indexSelectedItem = ((JackInActivity)context).getIndexSelectedItem();
                Log.d(MainActivity.DEBUG_TAG, "GameCartridge.init() returning from BackpackActivity... indexSelectedItem: " + indexSelectedItem);
                player.setIndexSelectedItem(indexSelectedItem);
                //////////////////////////////////////
                ((JackInActivity)context).setIsReturningFromBackpackActivity(false);
            }

            ((JackInActivity)context).setIsReturningFromActivity(false);
        }
    }

    @Override
    public void savePresentState() {
        Log.d(MainActivity.DEBUG_TAG, "GameCartridge.savePresentState()");

        //HANDLES JackInActivity.gameCartridge/////////////////////////////////////////
        //only THIS activity can get access to THIS preference file.
        SharedPreferences prefs = ((JackInActivity) context).getPreferences(MODE_PRIVATE);
        //Editor is an inner-class of the SharedPreferences class.
        SharedPreferences.Editor editor = prefs.edit();
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //Save enum as integer (its index value).
        //!!!Used in JackInActivity's constructor (orientation change)!!!
        editor.putInt("idGameCartridge", idGameCartridge.ordinal());
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //HAVE TO tell editor to actually save the values we'd put into it.
        editor.commit();
        /////////////////////////////////////////////////////////////////////////////////

        SerializationDoer.saveViaOS(this);
    }

    @Override
    public void loadSavedState() {
        Log.d(MainActivity.DEBUG_TAG, "GameCartridge.loadSavedState()");

        // !!!THIS CHECKING FOR NULL IS NECESSARY!!!
        //if (handler != null) {
            SerializationDoer.loadViaOS(this);
        //}
    }

    @Override
    public void getInputViewport() {
        if (inputManager.isJustPressedViewport()) {
            stateManager.getCurrentState().getInputViewport();
        }
    }

    @Override
    public void getInputDirectionalPad() {
        if (inputManager.isPressingDirectionalPad()) {
            stateManager.getCurrentState().getInputDirectionalPad();
        }
    }

    @Override
    public void getInputButtonPad() {
        if (inputManager.isJustPressedButtonPad()) {
            stateManager.getCurrentState().getInputButtonPad();
        }
    }

    @Override
    public void update(long elapsed) {
        //////////////////////////
        getInputViewport();
        getInputDirectionalPad();
        getInputButtonPad();
        //////////////////////////

        stateManager.getCurrentState().update(elapsed);

        if (headUpDisplay.getIsVisible()) {
            headUpDisplay.update(elapsed);
        }
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

            stateManager.getCurrentState().render(canvas);
            if (headUpDisplay.getIsVisible()) {
                headUpDisplay.render(canvas);
            }

            //unlock it and post our updated drawing to it.
            ///////////////////////////////////
            surfaceHolder.unlockCanvasAndPost(canvas);
            ///////////////////////////////////
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Id getIdGameCartridge() {
        return idGameCartridge;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public int getWidthViewport() {
        return widthViewport;
    }

    @Override
    public int getHeightViewport() {
        return heightViewport;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public GameCamera getGameCamera() {
        return gameCamera;
    }

    @Override
    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    @Override
    public HeadUpDisplay getHeadUpDisplay() {
        return headUpDisplay;
    }

    @Override
    public void setHeadUpDisplay(HeadUpDisplay headUpDisplay) {
        this.headUpDisplay= headUpDisplay;
    }

    @Override
    public SceneManager getSceneManager() {
        return ((GameState)stateManager.getState(State.Id.GAME)).getSceneManager();
    }

    @Override
    public StateManager getStateManager() {
        return stateManager;
    }

}