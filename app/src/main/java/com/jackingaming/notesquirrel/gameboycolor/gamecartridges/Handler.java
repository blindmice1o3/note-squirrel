package com.jackingaming.notesquirrel.gameboycolor.gamecartridges;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;

public class Handler {

    private GameCartridge gameCartridge;
    private Scene currentScene;

    public Handler(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
    }

    public GameCartridge getGameCartridge() {
        return gameCartridge;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

}