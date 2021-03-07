package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.InputManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

public class MenuItemEvolution
        implements MenuStateImplEvo.MenuItem {
    private static MenuItemEvolution uniqueInstance;
    transient private Game game;
    private String name;

    transient private Bitmap imageBackground;
    private float xScaleFactor;
    private float yScaleFactor;
    transient private Rect rectSource;
    transient private Rect rectDestination;

    transient private Paint paintYellowBorder;
    transient private Rect rectJaws;
    transient private Rect rectBody;
    transient private Rect rectHandsAndFeet;
    transient private Rect rectTail;
    transient private Rect rectDoNotEvolve;

    private int index;

    private MenuItemEvolution() {
        name = "Evolution";
        index = 0;
    }

    public static MenuItemEvolution getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuItemEvolution();
        }
        return uniqueInstance;
    }

    @Override
    public void init(Game game) {
        this.game = game;

        initImage(game.getContext().getResources());

        xScaleFactor = (float)game.getWidthViewport() / (float)imageBackground.getWidth();
        yScaleFactor = xScaleFactor;

        rectSource = new Rect(0, 0, imageBackground.getWidth(), imageBackground.getHeight());
        rectDestination = new Rect(0, 0, game.getWidthViewport(),
                (int)(imageBackground.getHeight() * yScaleFactor));

        paintYellowBorder = new Paint();
        paintYellowBorder.setStyle(Paint.Style.STROKE);
        paintYellowBorder.setStrokeWidth(10f);
        paintYellowBorder.setColor(Color.YELLOW);

        rectJaws = new Rect((int)(12 * xScaleFactor), (int)(61 * yScaleFactor),
                (int)(106 * xScaleFactor), (int)(107 * yScaleFactor));
        rectBody = new Rect((int)(352 * xScaleFactor), (int)(61 * yScaleFactor),
                (int)(446 * xScaleFactor), (int)(107 * yScaleFactor));
        rectHandsAndFeet = new Rect((int)(452 * xScaleFactor), (int)(61 * yScaleFactor),
                (int)(586 * xScaleFactor), (int)(107 * yScaleFactor));
        rectTail = new Rect((int)(152 * xScaleFactor), (int)(113 * yScaleFactor),
                (int)(246 * xScaleFactor), (int)(159 * yScaleFactor));
        rectDoNotEvolve = new Rect((int)(432 * xScaleFactor), (int)(113 * yScaleFactor),
                (int)(586 * xScaleFactor), (int)(159 * yScaleFactor));
    }

    private void initImage(Resources resources) {
        imageBackground = BitmapFactory.decodeResource(resources, R.drawable.snes_evo_search_for_eden_mainmenustate_menulist_evolution);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void update(long elapsed) {
        if (game.getInputManager().isJustPressed(InputManager.Button.RIGHT)) {
            index++;
            if (index > 4) {
                index = 0;
            }
        } else if (game.getInputManager().isJustPressed(InputManager.Button.LEFT)) {
            index--;
            if (index < 0) {
                index = 4;
            }
        } else if (game.getInputManager().isJustPressed(InputManager.Button.B)) {
            MenuStateImplEvo.getInstance().getMenuItemManager().popMenuItemStack();
        }
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(imageBackground, rectSource, rectDestination, null);

        if (index == 0) {
            canvas.drawRect(rectJaws, paintYellowBorder);
        } else if (index == 1) {
            canvas.drawRect(rectBody, paintYellowBorder);
        } else if (index == 2) {
            canvas.drawRect(rectHandsAndFeet, paintYellowBorder);
        } else if (index == 3) {
            canvas.drawRect(rectTail, paintYellowBorder);
        } else if (index == 4) {
            canvas.drawRect(rectDoNotEvolve, paintYellowBorder);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Game getGame() {
        return game;
    }
}