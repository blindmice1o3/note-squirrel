package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class SpriteSheetVerifier2Activity extends AppCompatActivity {

    private int mIndexColumn;
    private int mIndexRow;

    private Bitmap[][] mRobotRSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_sheet_verifier2);
        Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity.onCreate(Bundle)");

        final ImageView imageView = (ImageView) findViewById(R.id.iv_sprite_sheet_verifier2);
        Button buttonColumnIncrement = (Button) findViewById(R.id.button_column_increment);
        Button buttonColumnDecrement = (Button) findViewById(R.id.button_column_decrement);
        Button buttonRowIncrement = (Button) findViewById(R.id.button_row_increment);
        Button buttonRowDecrement = (Button) findViewById(R.id.button_row_decrement);

        //Initialize mRobotRSeries (crop and store the top portion of R.drawable.snes_chrono_trigger_r_series)
        this.mRobotRSeries = new Bitmap[22][11];
        Bitmap spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.snes_chrono_trigger_r_series);
        int widthSprite = 32;
        int heightSprite = 32;
        int offsetHorizontal = 8;
        int offsetVertical = 8;
        for (int row = 0; row < mRobotRSeries.length; row++) {
            for (int column = 0; column < mRobotRSeries[row].length; column++) {
                int xStart = column * (widthSprite + offsetHorizontal);
                int yStart = row * (heightSprite + offsetVertical);
                mRobotRSeries[row][column] = Bitmap.createBitmap(spriteSheet, xStart, yStart,
                        widthSprite, heightSprite);
            }
        }
        //Might be redundant since local variable.
        ///////////////////
        spriteSheet = null;
        ///////////////////

        //Set the ImageView to the first cropped sprite.
        this.mIndexColumn = 0;
        this.mIndexRow = 0;
        /////////////////////////////////////////////////////////////////
        imageView.setImageBitmap(mRobotRSeries[mIndexRow][mIndexColumn]);
        /////////////////////////////////////////////////////////////////

        buttonColumnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity OnClickListener button column increment");
                //TODO:
                mIndexColumn++;
                if (mIndexColumn >= mRobotRSeries[mIndexRow].length) {
                    mIndexColumn = mRobotRSeries[mIndexRow].length-1;
                }

                /////////////////////////////////////////////////////////////////
                imageView.setImageBitmap(mRobotRSeries[mIndexRow][mIndexColumn]);
                /////////////////////////////////////////////////////////////////
            }
        });

        buttonColumnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity OnClickListener button column decrement");
                //TODO:
                mIndexColumn--;
                if (mIndexColumn < 0) {
                    mIndexColumn = 0;
                }

                /////////////////////////////////////////////////////////////////
                imageView.setImageBitmap(mRobotRSeries[mIndexRow][mIndexColumn]);
                /////////////////////////////////////////////////////////////////
            }
        });

        buttonRowIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity OnClickListener button row increment");
                //TODO:
                mIndexRow++;
                if (mIndexRow >= mRobotRSeries.length) {
                    mIndexRow = mRobotRSeries.length-1;
                }

                /////////////////////////////////////////////////////////////////
                imageView.setImageBitmap(mRobotRSeries[mIndexRow][mIndexColumn]);
                /////////////////////////////////////////////////////////////////
            }
        });

        buttonRowDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity OnClickListener button row decrement");
                //TODO:
                mIndexRow--;
                if (mIndexRow < 0) {
                    mIndexRow = 0;
                }

                /////////////////////////////////////////////////////////////////
                imageView.setImageBitmap(mRobotRSeries[mIndexRow][mIndexColumn]);
                /////////////////////////////////////////////////////////////////
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity.onStart()");
    }

}