package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheetVerifier2Activity extends AppCompatActivity {

    private Bitmap[][] mRobotRSeries;

    private List<Bitmap> userSelectedBitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_sheet_verifier2);
        Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity.onCreate(Bundle)");

        //Initialize mRobotRSeries (crop and store the top portion of R.drawable.snes_chrono_trigger_r_series)
        int rows = 22;
        final int columns = 11;
        mRobotRSeries = new Bitmap[rows][columns];
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

        List<Bitmap> dataSource = new ArrayList<>();
        for (int row = 0; row < mRobotRSeries.length; row++) {
            for (int column = 0; column < mRobotRSeries[row].length; column++) {
                dataSource.add(mRobotRSeries[row][column]);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview_sprite_sheet_verifier2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, columns));
        AdapterBitmapRecyclerView.ItemClickListener itemClickListener = new AdapterBitmapRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int row = position / columns;
                int column = position % columns;
                Toast.makeText(SpriteSheetVerifier2Activity.this, row + ", " + column, Toast.LENGTH_SHORT).show();

                userSelectedBitmaps.add(mRobotRSeries[row][column]);

                View viewContainingLinearLayout = getLayoutInflater().inflate(R.layout.review_user_selected_bitmaps_dialog, null);
                for (int i = 0; i < userSelectedBitmaps.size(); i++) {
                    ImageView imageView = new ImageView(SpriteSheetVerifier2Activity.this);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(userSelectedBitmaps.get(i));

                    LinearLayout linearLayout = viewContainingLinearLayout.findViewById((R.id.linearlayout_review_user_selected_bitmaps_dialog));
                    linearLayout.addView(imageView);
                }
                Dialog reviewUserSelectedBitmapsDialog = new AlertDialog.Builder(SpriteSheetVerifier2Activity.this)
                        .setView(viewContainingLinearLayout)
                        .create();
                reviewUserSelectedBitmapsDialog.show();
            }
        };
        AdapterBitmapRecyclerView adapterBitmapRecyclerView = new AdapterBitmapRecyclerView(this, dataSource, itemClickListener);
        recyclerView.setAdapter(adapterBitmapRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity.onStart()");
    }

}