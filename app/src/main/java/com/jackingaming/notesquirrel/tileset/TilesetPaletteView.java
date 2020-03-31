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

    private Bitmap tilesetModel;

    private Rect modelRect;
    private Rect canvasRect;

    private int numberOfTilesAcross;
    private int numberOfTilesDown;
    private int xModelTileSize;
    private int yModelTileSize;

    private int widthCanvas;
    private int heightCanvas;
    private int xCanvasTileSize;
    private int yCanvasTileSize;

    private float xConversionFactor;
    private float yConversionFactor;

    private Paint paintYellowUnfilled;

    private int xSelected;
    private int ySelected;

    public int canvasToModel(int canvasCoordinate) {
        //TODO:

        return 0;
    }

    public TilesetPaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, "$$$$$$$$$$$ TilesetPaletteView(Context) constructor $$$$$$$$$$$");

        numberOfTilesAcross = 6;
        numberOfTilesDown = 20;

        tilesetModel = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_computer_yoko_tileset);
        modelRect = new Rect(0, 0, tilesetModel.getWidth(), tilesetModel.getHeight());


        Log.d(MainActivity.DEBUG_TAG, "xModelTileSize = modelRect.right / numberOfTilesAcross: " + modelRect.right + " / " + numberOfTilesAcross);
        Log.d(MainActivity.DEBUG_TAG, "yModelTileSize = modelRect.bottom / numberOfTilesDown: " + modelRect.bottom + " / " + numberOfTilesDown);
        xModelTileSize = modelRect.right / numberOfTilesAcross;
        yModelTileSize = modelRect.bottom / numberOfTilesDown;
        Log.d(MainActivity.DEBUG_TAG, "xModelTileSize: " + xModelTileSize);
        Log.d(MainActivity.DEBUG_TAG, "yModelTileSize: " + yModelTileSize);


        paintYellowUnfilled = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintYellowUnfilled.setColor(Color.YELLOW);
        paintYellowUnfilled.setStyle(Paint.Style.FILL);


        ///////////////////////////////////////////////////////////
        setBackgroundResource(R.drawable.pc_computer_yoko_tileset);
        ///////////////////////////////////////////////////////////
        //setImageBitmap(tilesetModel);


        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(),
                        "(x, y): " + event.getX() + ", " + event.getY(),
                        Toast.LENGTH_SHORT).show();

                Log.d(MainActivity.DEBUG_TAG, "event.getX(): " + event.getX());
                Log.d(MainActivity.DEBUG_TAG, "event.getY(): " + event.getY());
                //move by tile size (not one pixel at a time)
                xSelected = (((int)event.getX() / xCanvasTileSize)) * xCanvasTileSize;
                ySelected = (((int)event.getY() / yCanvasTileSize)) * yCanvasTileSize;
                Log.d(MainActivity.DEBUG_TAG, "xSelected: " + xSelected);
                Log.d(MainActivity.DEBUG_TAG, "ySelected: " + ySelected);

                /////////////
                invalidate();
                /////////////

                return true;
            }
        });

        Log.d(MainActivity.DEBUG_TAG, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
        Log.d(MainActivity.DEBUG_TAG, "#######TilesetPaletteView.onSizeChanged(int, int, int, int)#######");

        widthCanvas = w;
        heightCanvas = h;
        Log.d(MainActivity.DEBUG_TAG, "(widthCanvas, heightCanvas): " + widthCanvas + ", " + heightCanvas);

        ////////////////////////////////////////////
        canvasRect = new Rect(0, 0, w, h);
        ////////////////////////////////////////////

        Log.d(MainActivity.DEBUG_TAG, "xCanvasTileSize = canvasRect.right / numberOfTilesAcross: " + canvasRect.right + " / " + numberOfTilesAcross);
        Log.d(MainActivity.DEBUG_TAG, "yCanvasTileSize = canvasRect.bottom / numberOfTilesDown: " + canvasRect.bottom + " / " + numberOfTilesDown);
        xCanvasTileSize = canvasRect.right / numberOfTilesAcross;
        yCanvasTileSize = canvasRect.bottom / numberOfTilesDown;
        Log.d(MainActivity.DEBUG_TAG, "xCanvasTileSize: " + xCanvasTileSize);
        Log.d(MainActivity.DEBUG_TAG, "yCanvasTileSize: " + yCanvasTileSize);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        xConversionFactor = (float) modelRect.right / canvasRect.right;
        yConversionFactor = (float) modelRect.bottom / canvasRect.bottom;
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
        Log.d(MainActivity.DEBUG_TAG, "(xConversionFactor, yConversionFactor): " + xConversionFactor + ", " + yConversionFactor);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Log.d(MainActivity.DEBUG_TAG, "(canvas.getWidth(), canvas.getHeight()): " + canvas.getWidth() + ", " + canvas.getHeight());

        canvas.drawRect(xSelected, ySelected, (xSelected + xCanvasTileSize), (ySelected + yCanvasTileSize), paintYellowUnfilled);

        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

}