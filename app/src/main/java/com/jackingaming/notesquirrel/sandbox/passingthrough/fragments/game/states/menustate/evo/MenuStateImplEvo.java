package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.Assets;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo.SceneEvo;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.MenuStateImpl;

public class MenuStateImplEvo extends MenuStateImpl {
    interface MenuItem {
        void init(Game game);
        void enter(Object[] args);
        void exit();
        void update(long elapsed);
        void render(Canvas canvas);
    }

    private static final int MARGIN_SIZE_HORIZONTAL = Tile.WIDTH;
    private static final int MARGIN_SIZE_VERTICAL = Tile.HEIGHT;
    private static MenuStateImplEvo uniqueInstance;

    transient private Game game;

    transient private Bitmap imageBackground;
    private float xScaleFactor;
    private float yScaleFactor;
    transient private Rect rectSourceImageBackground;
    transient private Rect rectDestinationImageBackground;


    transient private Bitmap imageCursor;
    private float xCursor;
    private float yCursor;

    private MenuStateImplEvo() {
        xCursor = 3;
        yCursor = 11;
    }

    public static MenuStateImplEvo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuStateImplEvo();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        initImage();

        int y0BelowLabelHp = ((SceneEvo)game.getSceneManager().getCurrentScene()).getHeadUpDisplay().calculateHpLabelY1();
        int x0BelowLabelHp = y0BelowLabelHp;
        int widthHalfScreen = game.getWidthViewport() / 2;
        xScaleFactor = (float)(widthHalfScreen - (2 * x0BelowLabelHp)) / (float)(imageBackground.getWidth());
        yScaleFactor = xScaleFactor;

        rectSourceImageBackground = new Rect(0, 0,
                0 + imageBackground.getWidth(),
                0 + imageBackground.getHeight());
        rectDestinationImageBackground = new Rect(x0BelowLabelHp, y0BelowLabelHp,
                (int)(x0BelowLabelHp + (imageBackground.getWidth() * xScaleFactor)),
                (int)(y0BelowLabelHp + (imageBackground.getHeight() * yScaleFactor)));
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
        canvas.drawBitmap(imageBackground,
                rectSourceImageBackground, rectDestinationImageBackground, null);
    }
}