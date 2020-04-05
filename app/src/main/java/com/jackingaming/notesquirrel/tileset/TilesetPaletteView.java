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

    ////////////////////////////////////////
    private int xModelTileSize;
    private int yModelTileSize;
    // PRE y pixel 461
    private int xModelTileSizePreYPixel461;
    private int yModelTileSizePreYPixel461;
    // POST y pixel 461
    private int xModelTileSizePostYPixel461;
    private int yModelTileSizePostYPixel461;
    ////////////////////////////////////////

    private int widthCanvas;
    private int heightCanvas;
    private int xCanvasTileSize;
    private int yCanvasTileSize;

    private float xConversionFactor;
    private float yConversionFactor;

    private Paint paintYellowUnfilled;

    private int xSelected;
    private int ySelected;

    public Bitmap tile00x00;

    public int canvasToModel(int canvasCoordinate) {
        //TODO:

        return 0;
    }

    private int converterModelToPixel(int modelValue) {
        int pixel = 0;



        return pixel;
    }
    private float xConversionFactorModelToPixel;
    private float yConversionFactoryModelToPixel;
    private int marginSize = 1;
    private int numberOfPixelAcross;
    private int numberOfPixelDown;
    public TilesetPaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, "$$$$$$$$$$$ TilesetPaletteView(Context) constructor $$$$$$$$$$$");

        numberOfTilesAcross = 6;
        numberOfTilesDown = 20;

        numberOfPixelAcross = 242;
        numberOfPixelDown = 834;
        //TODO: THE RETURNED VALUES OF tilesetModel's getWidth() and getHeight() is 3x the actual number of pixel.
        //TODO: so 40px by 40px tile size will be given as 120 by 120 (for tilesetModel [Bitmap]).
        //TODO: SOLUTION: moved source image to drawable-nodpi directory to prevent SCALING.
        ////////////////////////////////////////////////////////////////////////////////////////////
        tilesetModel = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_yoko_tileset1);
        tile00x00 = tilesetModel.createBitmap(tilesetModel, 1, 1, 200, 200);
        Log.d(MainActivity.DEBUG_TAG, "tile00x00 (width, height): " + tile00x00.getWidth() + ", " + tile00x00.getHeight());
        Log.d(MainActivity.DEBUG_TAG, "tilesetModel (width, height): " + tilesetModel.getWidth() + ", " + tilesetModel.getHeight());
        modelRect = new Rect(0, 0, tilesetModel.getWidth(), tilesetModel.getHeight());
        Log.d(MainActivity.DEBUG_TAG, "modelRect.right, modelRect.bottom: " + modelRect.right + ", " + modelRect.bottom);
        ////////////////////////////////////////////////////////////////////////////////////////////
        //xConversionFactorModelToPixel =


        xModelTileSizePreYPixel461 = modelRect.right / numberOfTilesAcross;
        //yModelTileSizePreYPixel461 = (modelRect.bottom - ())

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
        //"setImageResource() automatically scale your image"
        setBackgroundResource(R.drawable.pc_yoko_tileset1); //drawable-nodpi (NOT re-scaled)
        //setImageResource(R.drawable.pc_yoko_tileset1);
        //setBackgroundResource(R.drawable.pc_yoko_tileset0); //drawable (automatically scaled)
        //setImageBitmap(tilesetModel); //does NOT fill the View automatically (see blue background)
        Log.d(MainActivity.DEBUG_TAG, "tilesetModel (width, height): " + tilesetModel.getWidth() + ", " + tilesetModel.getHeight());
        ///////////////////////////////////////////////////////////



        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //TODO: SHOULD BE USING SCREEN COORDINATE (converted using the conversion factors)
                //not pixel of 641.
                if (event.getY() < 641) {
                    Log.d(MainActivity.DEBUG_TAG, "event.getX(): " + event.getX());
                    Log.d(MainActivity.DEBUG_TAG, "event.getY(): " + event.getY());
                    //move by tile size (not one pixel at a time)
                    xSelected = (((int) event.getX() / xCanvasTileSize)) * xCanvasTileSize;
                    ySelected = (((int) event.getY() / yCanvasTileSize)) * yCanvasTileSize;
                    Log.d(MainActivity.DEBUG_TAG, "xSelected: " + xSelected);
                    Log.d(MainActivity.DEBUG_TAG, "ySelected: " + ySelected);
                } else {
                    xSelected = (((int) event.getX() / xCanvasTileSize)) * xCanvasTileSize;
                    //TODO: SHOULD BE USING SCREEN COORDINATE (converted using the conversion factors)
                    //not pixel of 641.
                    int numberOf48pxTileSprite = ((((int)event.getY() - 640) / (yCanvasTileSize+8) ) * (yCanvasTileSize+8)) / (yCanvasTileSize+8);
                    Log.d(MainActivity.DEBUG_TAG, "numberOf48pxTileSprite: " + numberOf48pxTileSprite);
                    ySelected = ((((int) event.getY() / yCanvasTileSize)) * yCanvasTileSize) + numberOf48pxTileSprite;
                }

                Toast.makeText(getContext(),
                        "(xSelected, ySelected): " + (xSelected / xCanvasTileSize) +
                                ", " + (ySelected / yCanvasTileSize),
                        Toast.LENGTH_SHORT).show();

                //https://stuff.mit.edu/afs/sipb/project/android/docs/guide/topics/graphics/2d-graphics.html
                //Canvas and Drawables:
                //Applications such as video games should be drawing to the Canvas on its own.
                //However, there's more than one way to do this:
                //-In the same thread as your UI Activity, wherein you create a custom View component
                //in your layout, call invalidate() and then handle the onDraw() callback.
                //-Or, in a separate thread, wherein you manage a SurfaceView and perform draws to the
                //Canvas as fast as your thread is capable (you do not need to request invalidate()).
                /////////////
                //invalidate();
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
        ////////////////////////////////////////////
        widthCanvas = w;
        heightCanvas = h;
        Log.d(MainActivity.DEBUG_TAG, "(widthCanvas, heightCanvas): " + widthCanvas + ", " + heightCanvas);
        canvasRect = new Rect(0, 0, widthCanvas, heightCanvas);
        ////////////////////////////////////////////
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        xConversionFactor = (float) modelRect.right / canvasRect.right;
        yConversionFactor = (float) modelRect.bottom / canvasRect.bottom;
        Log.d(MainActivity.DEBUG_TAG, "modelRect.right / canvasRect.right: " + modelRect.right + " / " + canvasRect.right + " = " + (modelRect.right / canvasRect.right));
        Log.d(MainActivity.DEBUG_TAG, "yCanvasTileSize: " + yCanvasTileSize);
        Log.d(MainActivity.DEBUG_TAG, "xCanvasTileSize: " + xCanvasTileSize);
        Log.d(MainActivity.DEBUG_TAG, "yCanvasTileSize: " + yCanvasTileSize);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");
        Log.d(MainActivity.DEBUG_TAG, "(xConversionFactor, yConversionFactor): " + xConversionFactor + ", " + yConversionFactor);
        Log.d(MainActivity.DEBUG_TAG, "###################################################################");




        Log.d(MainActivity.DEBUG_TAG, "xCanvasTileSize = widthCanvas / numberOfTilesAcross: " + widthCanvas + " / " + numberOfTilesAcross);
        Log.d(MainActivity.DEBUG_TAG, "yCanvasTileSize = heightCanvas / numberOfTilesDown: " + heightCanvas + " / " + numberOfTilesDown);
        xCanvasTileSize = widthCanvas / numberOfTilesAcross;
        yCanvasTileSize = heightCanvas / numberOfTilesDown;
        Log.d(MainActivity.DEBUG_TAG, "xCanvasTileSize: " + xCanvasTileSize);
        Log.d(MainActivity.DEBUG_TAG, "yCanvasTileSize: " + yCanvasTileSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Log.d(MainActivity.DEBUG_TAG, "(canvas.getWidth(), canvas.getHeight()): " + canvas.getWidth() + ", " + canvas.getHeight());



        //TODO:
        /*
        Bitmap test0 = BitmapFactory.decodeResource(getResources(), R.drawable.arcade_bubble_bobble_general_sprites);
        Log.d(MainActivity.DEBUG_TAG, "test0: " + test0.getWidth() + ", " + test0.getHeight());
        Log.d(MainActivity.DEBUG_TAG, "test0: " + test0.getScaledWidth(canvas) + ", " + test0.getScaledHeight(canvas));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap test1 = BitmapFactory.decodeResource(getResources(), R.drawable.arcade_bubble_bobble_general_sprites, options);
        Log.d(MainActivity.DEBUG_TAG, "test1: " + test1.getWidth() + ", " + test1.getHeight());
        Log.d(MainActivity.DEBUG_TAG, "test1: " + test1.getScaledWidth(canvas) + ", " + test1.getScaledHeight(canvas));
        */

        canvas.drawRect(xSelected, ySelected, (xSelected + xCanvasTileSize), (ySelected + yCanvasTileSize), paintYellowUnfilled);

        Log.d(MainActivity.DEBUG_TAG, "@@@@@@@@@@@@@@@@@@@@@@@@@@TilesetPaletteView.onDraw(Canvas).@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

}