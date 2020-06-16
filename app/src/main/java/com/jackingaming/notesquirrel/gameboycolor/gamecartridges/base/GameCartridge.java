package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.content.Context;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StateManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface GameCartridge {

    enum Id { POCKET_CRITTERS, POOH_FARMER, PONG, FROGGER; }

    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen);

    public void savePresentState();
    public void loadSavedState();

    public void getInputViewport();
    public void getInputDirectionalPad();
    public void getInputButtonPad();

    public void update(long elapsed);
    public void render();

    public Context getContext();
    public Id getIdGameCartridge();
    public SurfaceHolder getSurfaceHolder();
    public InputManager getInputManager();
    public int getWidthViewport();
    public int getHeightViewport();

    public Handler getHandler();
    public Player getPlayer();
    public void setPlayer(Player player);
    public GameCamera getGameCamera();
    public void setGameCamera(GameCamera gameCamera);
    public SceneManager getSceneManager();
    public StateManager getStateManager();

}
