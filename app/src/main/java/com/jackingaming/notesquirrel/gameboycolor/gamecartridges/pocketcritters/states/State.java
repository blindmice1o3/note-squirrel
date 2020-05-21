package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

public interface State {

    public enum Id { GAME, START_MENU; }

    public void getInputViewport();

    public void getInputDirectionalPad();

    public void getInputButtonPad();

    public void update(long elapsed);

    public void render();

    public void enter(Object[] args);

    public void exit();

    public void savePresentState();

    public void loadSavedState();

}