package com.jackingaming.notesquirrel.tileset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private int tileSize;
    private Bitmap tileset;

    private int xSelected;
    private int ySelected;

    public TilesetPaletteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView(Context) PRE-BitmapFactory.decodeResource(Resources, int).");

        tileSize = 40;
        tileset = BitmapFactory.decodeResource(context.getResources(), R.drawable.pc_computer_yoko_tileset);
        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView(Context) POST-BitmapFactory.decodeResource(Resources, int).");
        setImageBitmap(tileset);
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(),
                        "(x, y): " + event.getX() + ", " + event.getY(),
                        Toast.LENGTH_SHORT).show();

                xSelected = (int) event.getX();
                ySelected = (int) event.getY();

                return true;
            }
        });
        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView(Context) POST.setOnTouchListener(...).");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(MainActivity.DEBUG_TAG, "TilesetPaletteView.onDraw(Canvas).");

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawRect(xSelected, ySelected, (xSelected + tileSize), (ySelected + tileSize), paint);
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