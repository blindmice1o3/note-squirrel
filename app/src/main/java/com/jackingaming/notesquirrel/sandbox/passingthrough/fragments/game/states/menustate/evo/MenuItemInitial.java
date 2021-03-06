package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states.menustate.evo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.fish.Assets;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo.SceneEvo;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class MenuItemInitial
        implements MenuStateImplEvo.MenuItem {
    private static MenuItemInitial uniqueInstance;
    transient private Game game;
    private String name;

    private List<MenuStateImplEvo.MenuItem> menuItems;

    transient private Bitmap imageCursor;
    private float xCursor;
    private float yCursor;

    private MenuItemInitial() {
        name = "Initial";

        menuItems = new ArrayList<MenuStateImplEvo.MenuItem>();
        menuItems.add(MenuItemEvolution.getInstance());
        menuItems.add(MenuItemCapability.getInstance());
        menuItems.add(MenuItemRecordOfEvolution.getInstance());

        xCursor = 3;
        yCursor = 11;
    }

    public static MenuItemInitial getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MenuItemInitial();
        }
        return uniqueInstance;
    }

    transient private Paint paintBorder;
    transient private Paint paintBackgroundPanel;
    transient private Paint paintFont;
    private int x0BelowLabelHp;
    private int y0BelowLabelHp;
    private int widthBackgroundPanel;
    private int heightBackgroundPanel;
    private int heightLine;
    private int padding = Tile.WIDTH;
    private float xScaleFactorCursor;
    private float yScaleFactorCursor;
    @Override
    public void init(Game game) {
        this.game = game;

        initImage();

        paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setColor(Color.WHITE);

        paintBackgroundPanel = new Paint();
        paintBackgroundPanel.setAntiAlias(true);
        paintBackgroundPanel.setColor(Color.BLUE);

        paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.WHITE);
        paintFont.setTextSize(40f);

        int widthMaxText = 0;
        Rect boundsText = new Rect();
        for (MenuStateImplEvo.MenuItem menuItem : menuItems) {
            String nameMenuItem = menuItem.getName();
            paintFont.getTextBounds(nameMenuItem, 0, nameMenuItem.length(), boundsText);
            if (boundsText.width() > widthMaxText) {
                widthMaxText = boundsText.width();
            }
            if (boundsText.height() > heightLine) {
                heightLine = boundsText.height();
            }
        }

        widthBackgroundPanel = widthMaxText + imageCursor.getWidth() + (2 * padding);
        heightBackgroundPanel = (menuItems.size() * heightLine) + (2 * padding);

        y0BelowLabelHp = ((SceneEvo)game.getSceneManager().getCurrentScene()).getHeadUpDisplay().calculateHpLabelY1();
        x0BelowLabelHp = y0BelowLabelHp;

        int widthHalfScreen = game.getWidthViewport() / 2;
        xScaleFactorCursor = (float)(widthHalfScreen - (2 * x0BelowLabelHp) - widthMaxText - (2 * padding)) / (float)(imageCursor.getWidth());
        yScaleFactorCursor = xScaleFactorCursor;
    }

    private void initImage() {
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

    private int sizeBorder = 3;
    private float roundnessBorder = 10f;
    @Override
    public void render(Canvas canvas) {
        // BORDER
        RectF rectBorder = new RectF(x0BelowLabelHp - sizeBorder, y0BelowLabelHp - sizeBorder,
                x0BelowLabelHp + widthBackgroundPanel + (2 * sizeBorder) - 3,
                y0BelowLabelHp + heightBackgroundPanel + (2 * sizeBorder) - 3);
        canvas.drawRoundRect(rectBorder, roundnessBorder+1, roundnessBorder+1, paintBorder);

        // BACKGROUND PANEL
        RectF rectBackgroundPanel = new RectF(x0BelowLabelHp, y0BelowLabelHp,
                x0BelowLabelHp + widthBackgroundPanel,
                y0BelowLabelHp + heightBackgroundPanel);
        canvas.drawRoundRect(rectBackgroundPanel, roundnessBorder, roundnessBorder, paintBackgroundPanel);

        // TEXT
        int xText = x0BelowLabelHp + padding + imageCursor.getWidth();
        int yText = y0BelowLabelHp + padding + heightLine;
        for (MenuStateImplEvo.MenuItem menuItem : menuItems) {
            canvas.drawText(menuItem.getName(), xText, yText, paintFont);
            yText += heightLine;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}