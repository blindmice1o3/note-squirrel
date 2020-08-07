package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;

public class SceneRetroGrid extends Scene {

    transient private Paint paintLine;
    private int speedLine;
    private float ySpawn;
    private float yCurrent;

    public SceneRetroGrid(GameCartridge gameCartridge, Id sceneID) {
        super(gameCartridge, sceneID);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setStrokeWidth(2f);
        paintLine.setColor(Color.BLACK);

        speedLine = 1;
        ySpawn = gameCartridge.getHeightViewport()/2;
        yCurrent = ySpawn;
    }

    @Override
    public void init(GameCartridge gameCartridge, Player player, GameCamera gameCamera, SceneManager sceneManager) {
        this.gameCartridge = gameCartridge;

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setStrokeWidth(2f);
        paintLine.setColor(Color.BLACK);
    }

    @Override
    public void initTileMap() {
        //intentionally blank.
    }

    @Override
    public void enter() {
        inputManager = gameCartridge.getInputManager();

        gameCartridge.getHeadUpDisplay().setIsVisible(false);
    }

    @Override
    public void exit(Object[] extra) {
        //intentionally blank.
    }

    @Override
    public void update(long elapsed) {
        yCurrent += speedLine;
        if (yCurrent >= gameCartridge.getHeightViewport()) {
            yCurrent = ySpawn;
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawLine(0, yCurrent,
                gameCartridge.getWidthViewport(), yCurrent,
                paintLine);
    }

    @Override
    public void getInputViewport() {
        //intentionally blank.
    }

    @Override
    public void getInputDirectionalPad() {
        //intentionally blank.
    }

    @Override
    protected void doButtonJustPressedA() {
        //intentionally blank.
    }

    @Override
    protected void doButtonJustPressedB() {
        gameCartridge.getSceneManager().pop(null);
    }

    @Override
    public void getInputSelectButton() {
        //intentionally blank.
    }

    @Override
    public void getInputStartButton() {
        //intentionally blank.
    }

}