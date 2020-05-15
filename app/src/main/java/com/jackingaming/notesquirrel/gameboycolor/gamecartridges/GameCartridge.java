package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import android.content.Context;
import android.view.SurfaceHolder;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public interface GameCartridge {

    enum Id { POOH_FARMER, PONG, POCKET_CRITTERS; }
    
    public void init(SurfaceHolder holder, InputManager inputManager, int widthScreen, int heightScreen);

    public void savePresentState();
    public void loadSavedState();

    public void getInputViewport();
    public void getInputDirectionalPad();
    public void getInputButtonPad();

    public Context getContext();
    public Id getIdGameCartridge();
    public Player getPlayer();
    public GameCamera getGameCamera();
    public SceneManager getSceneManager();
    public int getWidthViewport();
    public int getHeightViewport();

    public void update(long elapsed);
    public void render();
}
