package com.jackingaming.notesquirrel.tileset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class TilesetPaletteView extends ImageView {

    private int widthCanvas;
    private int heightCanvas;

    private Bitmap tileset;

    private Rect canvasScreenRect;
    private Rect tilesetRect;

    private int numberOfTilesAcross;
    private int tileSize;
    private int numberOfTilesDown;

    private int xSelected;
    private int ySelected;

    public TilesetPaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView(Context) PRE-BitmapFactory.decodeResource(Resources, int).");

        numberOfTilesAcross = 6;

        tileset = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_computer_yoko_tileset);
        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView(Context) POST-BitmapFactory.decodeResource(Resources, int).");
        //setImageBitmap(tileset);
        tilesetRect = new Rect(0, 0, tileset.getWidth(), tileset.getHeight());

        Log.d(MainActivity.DEBUG_TAG, "tileSize = tilesetRect.right / numberOfTilesAcross: " + tilesetRect.right + " / " + numberOfTilesAcross);
        tileSize = tilesetRect.right / numberOfTilesAcross;
        Log.d(MainActivity.DEBUG_TAG, "tileSize: " + tileSize);

        Log.d(MainActivity.DEBUG_TAG, "numberOfTilesDown = tilesetRect.bottom / tileSize: " + tilesetRect.bottom + " / " + tileSize);
        numberOfTilesDown = tilesetRect.bottom / tileSize;
        Log.d(MainActivity.DEBUG_TAG, "numberOfTilesDown: " + numberOfTilesDown);

        setBackgroundResource(R.drawable.pc_computer_yoko_tileset);

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(),
                        "(x, y): " + event.getX() + ", " + event.getY(),
                        Toast.LENGTH_SHORT).show();

                xSelected = (int) event.getX();
                ySelected = (int) event.getY();

                /////////////
                invalidate();
                /////////////

                return true;
            }
        });

        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView(Context) POST.setOnTouchListener(...).");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
        Log.d(MainActivity.DEBUG_TAG, "#######TilesetPaletteView.onSizeChanged(int, int, int, int)#######");

        widthCanvas = w;
        heightCanvas = h;

        canvasScreenRect = new Rect(0, 0, w, h);

        Log.d(MainActivity.DEBUG_TAG, "(widthCanvas, heightCanvas): " + widthCanvas + ", " + heightCanvas);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Log.d(MainActivity.DEBUG_TAG, "(canvas.getWidth(), canvas.getHeight()): " + canvas.getWidth() + ", " + canvas.getHeight());

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawRect(xSelected, ySelected, (xSelected + tileSize), (ySelected + tileSize), paint);

        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    public void setSelectedTileCoordinates(int xSelected, int ySelected) {
        this.xSelected = xSelected;
        this.ySelected = ySelected;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public void setxSelected(int xSelected) {
        this.xSelected = xSelected;
    }

    public void setySelected(int ySelected) {
        this.ySelected = ySelected;
    }

}