package com.jackingaming.notesquirrel.learn_fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class FragmentParentActivity extends AppCompatActivity {

    private Bitmap imageSource;
    private ImageView imageView;
    private Button button;

    private Bitmap[][] spriteSheetItems;

    private int indexButtonX;
    private int indexButtonY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_parent);

        ////////////////////////////
        Assets.init(getResources());
        ////////////////////////////
        //spriteSheetItems = Assets.items;
        //spriteSheetItems = Assets.tiles;
        spriteSheetItems = Assets.entities;



        //imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
        //imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.pc_yoko_tileset1);
        Bitmap tooBigClippit = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
        Bitmap justRightClippit = Bitmap.createScaledBitmap(tooBigClippit, 500, 500, false);
        imageSource = justRightClippit;
        imageView = (ImageView) findViewById(R.id.imageview_fragment);
        imageView.setImageBitmap(imageSource);

        Log.d(MainActivity.DEBUG_TAG, "imageSource.getWidth(): " + imageSource.getWidth());
        Log.d(MainActivity.DEBUG_TAG, "imageSource.getHeight(): " + imageSource.getHeight());
        /*
        Drawable imageSource = getResources().getDrawable(R.drawable.gbc_hm2_spritesheet_items);
        imageView = (ImageView) findViewById(R.id.imageview_fragment);
        imageView.setImageDrawable(imageSource);
        Log.d(MainActivity.DEBUG_TAG, "imageSource.getInstrinsicWidth(): " + imageSource.getIntrinsicWidth());
        Log.d(MainActivity.DEBUG_TAG, "imageSource.getIntrinsicHeight(): " + imageSource.getIntrinsicHeight());
        */



        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "event.getX(), event.getY(): " + event.getX() + ", " + event.getY());

                return true;
            }
        });


        indexButtonX = 0;
        indexButtonY = 0;
        button = (Button) findViewById(R.id.button_fragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView.setMinimumWidth( (spriteSheet[indexButtonY][indexButtonX].getWidth() * 7) );
                //imageView.setMinimumHeight( (spriteSheet[indexButtonY][indexButtonX].getHeight() * 7) );
                //imageView.setMaxWidth( (spriteSheet[indexButtonY][indexButtonX].getWidth() * 7) );
                //imageView.setMaxHeight( (spriteSheet[indexButtonY][indexButtonX].getHeight() * 7) );
                //imageView.setAdjustViewBounds(true);
                Log.d(MainActivity.DEBUG_TAG, "button's OnClickListener.onClick(View) BEFORE calling spriteSheet's width and height.");
                //How to set by pixel:
                //imageView.getLayoutParams().width = (spriteSheetItems[indexButtonY][indexButtonX].getWidth() * 15);
                imageView.getLayoutParams().width = (spriteSheetItems[indexButtonY][indexButtonX].getWidth() * 8);
                //imageView.getLayoutParams().height = (spriteSheetItems[indexButtonY][indexButtonX].getHeight() * 15);
                imageView.getLayoutParams().height = (spriteSheetItems[indexButtonY][indexButtonX].getHeight() * 8);
                //imageView.setLayoutParams( new ViewGroup.LayoutParams(16*15, 16*15) );
                //////////////////////////
                imageView.requestLayout();
                //////////////////////////
                //How to set by dp:
                //int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());
                //imageView.getLayoutParams().height = dimensionInDp;
                //imageView.getLayoutParams().width = dimensionInDp;
                //////////////////////////
                //imageView.requestLayout();
                //////////////////////////
                Log.d(MainActivity.DEBUG_TAG, "button's OnClickListener.onClick(View) AFTER calling spriteSheet's width and height.");

                Bitmap newImage = Bitmap.createScaledBitmap(spriteSheetItems[indexButtonY][indexButtonX], 240, 240, false);

                imageView.setImageBitmap(newImage);

                indexButtonX++;
                if (indexButtonX == spriteSheetItems[indexButtonY].length) {
                    indexButtonX = 0;
                    indexButtonY++;

                    if (indexButtonY == spriteSheetItems.length) {
                        indexButtonY = 0;
                    }
                }

                /*
                int[][] test = {
                        {1, 2},
                        {3, 4},
                        {5, 6},
                        {7, 8}
                };
                Log.d(MainActivity.DEBUG_TAG, "test.length, test[0].length: " + test.length + ", " + test[0].length);
                */

                Log.d(MainActivity.DEBUG_TAG,
                        "imageView.getWidth(), imageView.getHeight(): " +
                                imageView.getWidth() + ", " + imageView.getHeight());

                Log.d(MainActivity.DEBUG_TAG,
                        "imageView.getDrawable().getIntrinsicWidth(), imageView.getDrawable().getIntrinsicHeight(): " +
                                imageView.getDrawable().getIntrinsicWidth() + ", " + imageView.getDrawable().getIntrinsicHeight());
            }
        });
    }

}