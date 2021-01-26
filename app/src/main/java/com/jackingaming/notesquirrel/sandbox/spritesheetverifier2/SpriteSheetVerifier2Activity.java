package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
            public void onItemClick(View view, int position, AdapterBitmapRecyclerView adapter) {
                int row = position / columns;
                int column = position % columns;
                Toast.makeText(SpriteSheetVerifier2Activity.this, row + ", " + column, Toast.LENGTH_SHORT).show();

                userSelectedBitmaps.add(mRobotRSeries[row][column]);

                View viewContainingRecyclerView = getLayoutInflater().inflate(R.layout.review_user_selected_bitmaps_dialog, null);

                Button buttonJackIn = viewContainingRecyclerView.findViewById(R.id.button_review_user_selected_bitmaps_dialog);
                buttonJackIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userSelectedBitmaps.size() > 0) {
                            displayAnimationAlertDialog();
                        } else {
                            Toast.makeText(SpriteSheetVerifier2Activity.this, "no images selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RecyclerView recyclerViewUserSelectedBitmaps = viewContainingRecyclerView.findViewById(R.id.recyclerview_review_user_selected_bitmaps_dialog);
                recyclerViewUserSelectedBitmaps.setLayoutManager(new LinearLayoutManager(SpriteSheetVerifier2Activity.this, LinearLayoutManager.HORIZONTAL, false));
                AdapterBitmapRecyclerView adapterUserSelectedBitmaps = new AdapterBitmapRecyclerView(
                        SpriteSheetVerifier2Activity.this,
                        userSelectedBitmaps,
                        new AdapterBitmapRecyclerView.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, AdapterBitmapRecyclerView adapter) {
                                userSelectedBitmaps.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        },
                        AdapterBitmapRecyclerView.BoundBy.HEIGHT);
                recyclerViewUserSelectedBitmaps.setAdapter(adapterUserSelectedBitmaps);

                final Dialog reviewUserSelectedBitmapsDialog = new AlertDialog.Builder(SpriteSheetVerifier2Activity.this)
                        .setView(viewContainingRecyclerView)
                        .create();

                reviewUserSelectedBitmapsDialog.show();
            }
        };
        AdapterBitmapRecyclerView adapterBitmapRecyclerView = new AdapterBitmapRecyclerView(this, dataSource, itemClickListener, AdapterBitmapRecyclerView.BoundBy.WIDTH);
        recyclerView.setAdapter(adapterBitmapRecyclerView);
    }

    private void displayAnimationAlertDialog() {
        Log.d(MainActivity.DEBUG_TAG, "displayAnimationAlertDialog()");
        View viewContainingImageView = getLayoutInflater().inflate(R.layout.animation_user_selected_bitmaps_dialog, null);
        ImageView imageView = viewContainingImageView.findViewById(R.id.imageview_animation);
        imageView.setImageBitmap(userSelectedBitmaps.get(0));

        AlertDialog animationDialog = new AlertDialog.Builder(this)
                .setView(viewContainingImageView)
                .create();
        animationDialog.show();
        Log.d(MainActivity.DEBUG_TAG, "displayAnimationAlertDialog()... after calling animationDialog's \"show()\"");

        runAnimation(imageView);
    }

    private void runAnimation(final ImageView imageView) {
        Log.d(MainActivity.DEBUG_TAG, "runAnimation(ImageView)");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int index = 0;
                int speed = 3_000;
                long lastTime = System.currentTimeMillis();
                long timer = 0L;

                while (true) {
                    long now = System.currentTimeMillis();
                    long elapsed = now - lastTime;

                    timer += elapsed;

                    Log.d(MainActivity.DEBUG_TAG, "timer: " + timer);
                    if (timer > speed) {
                        index++;
                        timer = 0;

                        if (index >= userSelectedBitmaps.size()) {
                            index = 0;
                        }

                        Log.d(MainActivity.DEBUG_TAG, "WHILE LOOP index: " + index);
                        imageView.setImageBitmap(userSelectedBitmaps.get(index));
                    }

                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "SpriteSheetVerifier2Activity.onStart()");
    }

}