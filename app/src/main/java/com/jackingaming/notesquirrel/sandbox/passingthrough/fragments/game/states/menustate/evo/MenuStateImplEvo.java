package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.evo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.Assets;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.State;

public class MenuStateEvoImpl
        implements State {
    private static final int MARGIN_SIZE_HORIZONTAL = Tile.WIDTH;
    private static final int MARGIN_SIZE_VERTICAL = Tile.HEIGHT;
    private static MenuStateEvoImpl uniqueInstance;

    private Game game;

    private Bitmap imageBackground;
    private float xScaleFactor;
    private float yScaleFactor;


    private Bitmap imageCursor;
    private float xCursor;
    private float yCursor;

    private MenuStateEvoImpl() {
        xCursor = 3;
        yCursor = 11;
    }

    public static MenuStateEvoImpl getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuStateEvoImpl();
        }
        return uniqueInstance;
    }

    public void init(Game game) {
        this.game = game;

        initImage();
        int widthQuadrant = game.getWidthViewport() / 4;
        xScaleFactor = (float)widthQuadrant / (float)((imageBackground.getWidth() + (2 * MARGIN_SIZE_HORIZONTAL)));
        yScaleFactor = xScaleFactor;
    }

    private void initImage() {
        imageBackground = Assets.mainMenu;
        imageCursor = Assets.leftOverworld0;
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(long elapsed) {

    }

    @Override
    public void render(Canvas canvas) {

    }
}