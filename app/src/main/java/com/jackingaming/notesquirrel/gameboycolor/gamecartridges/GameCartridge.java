package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.content.Context;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface GameCartridge {

    enum Id { POCKET_CRITTERS, POOH_FARMER, PONG, FROGGER; }
    
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen);

    public void savePresentState();
    public void loadSavedState();

    public void getInputViewport();
    public void getInputDirectionalPad();
    public void getInputButtonPad();

    public Context getContext();
    public Id getIdGameCartridge();
    public void setGameCamera(GameCamera gameCamera);
    public Player getPlayer();
    public void setPlayer(Player player);
    public GameCamera getGameCamera();
    public SceneManager getSceneManager();
    public int getWidthViewport();
    public int getHeightViewport();

    public void update(long elapsed);
    public void render();
}
