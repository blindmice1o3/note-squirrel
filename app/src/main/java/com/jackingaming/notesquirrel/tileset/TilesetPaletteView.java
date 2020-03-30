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
    private int modelTileSize;
    private int numberOfTilesDown;

    private int widthCanvas;
    private int canvasTileSize;
    private int canvasNumberOfTilesDown;
    private int heightCanvas;

    private float xConversionFactor;
    private float yConversionFactor;

    private int xSelected;
    private int ySelected;

    //private float aspectRatio;
    /*
    public int canvasToModel(int canvasCoordinate) {

    }
    */

    public TilesetPaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, "$$$$$$$$$$$ TilesetPaletteView(Context) constructor $$$$$$$$$$$");

        numberOfTilesAcross = 6;

        tilesetModel = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_computer_yoko_tileset);
        modelRect = new Rect(0, 0, tilesetModel.getWidth(), tilesetModel.getHeight());

        Log.d(MainActivity.DEBUG_TAG, "modelTileSize = modelRect.right / numberOfTilesAcross: " + modelRect.right + " / " + numberOfTilesAcross);
        modelTileSize = modelRect.right / numberOfTilesAcross;
        Log.d(MainActivity.DEBUG_TAG, "modelTileSize: " + modelTileSize);

        Log.d(MainActivity.DEBUG_TAG, "numberOfTilesDown = modelRect.bottom / modelTileSize: " + modelRect.bottom + " / " + modelTileSize);
        numberOfTilesDown = modelRect.bottom / modelTileSize;
        Log.d(MainActivity.DEBUG_TAG, "numberOfTilesDown: " + numberOfTilesDown);

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

                xSelected = (int) event.getX();
                ySelected = (int) event.getY();

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

        canvasRect = new Rect(0, 0, w, h);

        Log.d(MainActivity.DEBUG_TAG, "canvasTileSize = canvasRect.right / numberOfTilesAcross: " + canvasRect.right + " / " + numberOfTilesAcross);
        canvasTileSize = canvasRect.right / numberOfTilesAcross;
        Log.d(MainActivity.DEBUG_TAG, "canvasTileSize: " + canvasTileSize);

        Log.d(MainActivity.DEBUG_TAG, "canvasNumberOfTilesDown = canvasRect.bottom / canvasTileSize: " + canvasRect.bottom + " / " + canvasTileSize);
        canvasNumberOfTilesDown = canvasRect.bottom / canvasTileSize;
        Log.d(MainActivity.DEBUG_TAG, "canvasNumberOfTilesDown: " + canvasNumberOfTilesDown);

        Log.d(MainActivity.DEBUG_TAG, "(widthCanvas, heightCanvas): " + widthCanvas + ", " + heightCanvas);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");

        /////////////////////////////////////////////////////////////////
        xConversionFactor = (float) modelRect.right / canvasRect.right;
        yConversionFactor = (float) modelRect.bottom / canvasRect.bottom;
        /////////////////////////////////////////////////////////////////

        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
        Log.d(MainActivity.DEBUG_TAG, "(xConversionFactor, yConversionFactor): " + xConversionFactor + ", " + yConversionFactor);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Log.d(MainActivity.DEBUG_TAG, "(canvas.getWidth(), canvas.getHeight()): " + canvas.getWidth() + ", " + canvas.getHeight());

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawRect(xSelected, ySelected, (xSelected + modelTileSize), (ySelected + modelTileSize), paint);

        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

}