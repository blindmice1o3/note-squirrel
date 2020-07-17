package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.CropSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.items.seeds.FlowerSeedItem;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.TileMap;

import java.io.Serializable;

public class HeadUpDisplay
        implements Serializable {

    public static final int MARGIN = 8;
    public static final int BORDER = 3;

    transient private GameCartridge gameCartridge;
    private boolean isVisible;

    private int widthViewport;
    private int heightViewport;
    private float widthPixelToViewportRatio;
    private float heightPixelToViewportRatio;

    transient private Bitmap selectedItem;

    public HeadUpDisplay(GameCartridge gameCartridge) {
        init(gameCartridge);
    }

    public void init(GameCartridge gameCartridge) {
        this.gameCartridge = gameCartridge;

        isVisible = true;
        widthViewport = gameCartridge.getWidthViewport();
        heightViewport = gameCartridge.getHeightViewport();
        widthPixelToViewportRatio = ((float) widthViewport) /
                gameCartridge.getGameCamera().getWidthClipInPixel();
        heightPixelToViewportRatio = ((float) heightViewport) /
                gameCartridge.getGameCamera().getHeightClipInPixel();

        selectedItem = gameCartridge.getPlayer().getSelectedItem().getImage();
    }

    public void update(long elapsed) {
        selectedItem = gameCartridge.getPlayer().getSelectedItem().getImage();
    }

    public void render(Canvas canvas) {
        //selectedItem's IMAGE position
        Rect selectedItemClip = new Rect(0, 0, selectedItem.getWidth(), selectedItem.getHeight());
        int x0 = widthViewport - (int)(MARGIN * widthPixelToViewportRatio) - (int)(TileMap.TILE_WIDTH * widthPixelToViewportRatio);
        int y0 = (int)(MARGIN * heightPixelToViewportRatio);
        int x1 = widthViewport - (int)(MARGIN * widthPixelToViewportRatio);
        int y1 = (int)(MARGIN * heightPixelToViewportRatio) + (int)(TileMap.TILE_HEIGHT * heightPixelToViewportRatio);
        Rect screenPosition = new Rect(x0, y0, x1, y1);

        //border paint (BACKGROUND COLOR)
        Paint paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setColor(Color.BLUE);

        //border position
        int x0Border = x0 - BORDER;
        int y0Border = y0 - BORDER;
        int x1Border = x1 + BORDER;
        int y1Border = y1 + BORDER;
        //Rect border = new Rect(x0Border, y0Border, x1Border, y1Border);
        RectF border = new RectF(x0Border, y0Border, x1Border, y1Border);


        //Paint (BACKGROUND)
        Paint paintBackground = new Paint();
        paintBackground.setAntiAlias(true);
        paintBackground.setColor(Color.WHITE);
        paintBackground.setAlpha(230);

        //Paint (FONT)
        Paint paintFont = new Paint();
        paintFont.setAntiAlias(true);
        paintFont.setColor(Color.GREEN);
        paintFont.setAlpha(230);
        paintFont.setTextSize(40f);
        paintFont.setTypeface(Typeface.SANS_SERIF);

        Paint.FontMetrics fm = paintFont.getFontMetrics();
        int heightLine = (int) (fm.bottom - fm.top + fm.leading);

        //////////////////////////////////////////////////////////////////////////////
        //border's IMAGE (ROUNDED CORNERS)
        //canvas.drawRect(border, paintBorder);
        canvas.drawRoundRect(border, (16f+BORDER), (16f+BORDER), paintBorder);

        //selectedItem's IMAGE (ROUNDED CORNERS)
        //canvas.drawBitmap(selectedItem, selectedItemClip, screenPosition, null);
        RoundedBitmapDrawable roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(gameCartridge.getContext().getResources(), selectedItem);
        roundedBitmapDrawable.setCornerRadius(16f);
        roundedBitmapDrawable.setBounds(screenPosition);
        roundedBitmapDrawable.draw(canvas);

        //CropSeedItem.SeedType
        if (gameCartridge.getPlayer().getSelectedItem() instanceof CropSeedItem) {
            String seedType = ((CropSeedItem)gameCartridge.getPlayer().getSelectedItem()).getSeedType().name();
            int xSeedType = x0;
            int ySeedType = y1 - heightLine;
            canvas.drawText(seedType, xSeedType, ySeedType, paintFont);
        }
        //FlowerSeedItem.SeedType
        else if (gameCartridge.getPlayer().getSelectedItem() instanceof FlowerSeedItem) {
            String seedType = ((FlowerSeedItem)gameCartridge.getPlayer().getSelectedItem()).getSeedType().name();
            int xSeedType = x0;
            int ySeedType = y1 - heightLine;
            canvas.drawText(seedType, xSeedType, ySeedType, paintFont);
        }
        //////////////////////////////////////////////////////////////////////////////



        //WHITE BACKGROUND
        Rect rectBackground = new Rect(x0-8, y1Border+20, x1+20+20, y1Border+heightLine+heightLine+16);
        /////////////////////////////////////////////////
        canvas.drawRect(rectBackground, paintBackground);
        /////////////////////////////////////////////////

        //@@@@@@@@@@@@@@@@@@@@@@
        //player.currencyNuggets
        //@@@@@@@@@@@@@@@@@@@@@@
        String playerCurrencyNuggets = "$" + String.valueOf(gameCartridge.getPlayer().getCurrencyNuggets());
        int xCurrencyNuggets = x0Border;
        int yCurrencyNuggets = y1Border + 6 + heightLine;
        canvas.drawText(playerCurrencyNuggets, xCurrencyNuggets, yCurrencyNuggets, paintFont);

        //@@@@@@@@@@@@@@@@@@@@@
        //player.fodderQuantity
        //@@@@@@@@@@@@@@@@@@@@@
        String playerFodderQuantity = String.valueOf(gameCartridge.getPlayer().getFodderQuantity()) + " fodder";
        int xFodderQuantity = x0Border;
        int yFodderQuantity = y1Border + 6 + heightLine + heightLine;
        canvas.drawText(playerFodderQuantity, xFodderQuantity, yFodderQuantity, paintFont);
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}