package com.jackingaming.notesquirrel.learn_fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class FragmentParentActivity extends AppCompatActivity {

    private Bitmap imageSource;
    private ImageView imageView;
    private Button button;

    private Bitmap[][] spriteSheet;

    private void initSpriteSheet() {
        int column = 9;
        int row = 9;
        spriteSheet = new Bitmap[row][column];

        int margin = 1;
        int tileWidth = 16;
        int tileHeight = 16;

        int xCurrent = margin;
        int yCurrent = margin;

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < column; x++) {
                spriteSheet[y][x] = Bitmap.createBitmap(imageSource, xCurrent, yCurrent, tileWidth, tileHeight);
                xCurrent += (tileWidth + margin);
            }
            xCurrent = margin;
            yCurrent += (tileHeight + margin);
        }

        /*
        int[] animal = {1, 2, 3, 4};
        int[] booger = {9, 8, 7, 6};
        int[][] practice = new int[2][4];
        practice[0] = animal;
        practice[1] = booger;
        Log.d(MainActivity.DEBUG_TAG, "practice.length: " + practice.length);
        Log.d(MainActivity.DEBUG_TAG, "practice[0].length: " + practice[0].length);
        for (int i = 0; i < practice.length; i++) {
            Log.d(MainActivity.DEBUG_TAG,
                    practice[i][0] + " " + practice[i][1] + " " + practice[i][2] + " " + practice[i][3] + " ");
        }
        */
    }

    private int indexButtonX;
    private int indexButtonY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_parent);

        imageSource = BitmapFactory.decodeResource(getResources(), R.drawable.gbc_hm2_spritesheet_items);
        initSpriteSheet();
        imageView = (ImageView) findViewById(R.id.imageview_fragment);
        imageView.setImageBitmap(imageSource);



        Log.d(MainActivity.DEBUG_TAG,
                "imageView.getWidth(), imageView.getHeight(): " +
                        imageView.getWidth() + ", " + imageView.getHeight());
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG,
                        "event.getX(), event.getY(): " +
                                event.getX() + ", " + event.getY());

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
                imageView.getLayoutParams().width = (spriteSheet[indexButtonY][indexButtonX].getWidth() * 15);
                imageView.getLayoutParams().height = (spriteSheet[indexButtonY][indexButtonX].getHeight() * 15);

                Bitmap newImage = Bitmap.createScaledBitmap(spriteSheet[indexButtonY][indexButtonX], 240, 240, false);

                imageView.setImageBitmap(newImage);

                indexButtonX++;
                if ((indexButtonX%9) == 0) {
                    indexButtonX = 0;
                    indexButtonY++;
                }




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