package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

public class Handler {

    private GameCartridge gameCartridge;

    public Handler(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;
    }

    public GameCartridge getGameCartridge() {
        return gameCartridge;
    }

}