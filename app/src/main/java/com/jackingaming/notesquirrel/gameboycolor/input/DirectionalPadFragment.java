package com.jackingaming.notesquirrel.gameboycolor.input;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.util.HashMap;
import java.util.Map;

public class DirectionalPadFragment extends Fragment {

    private Map<String, Bitmap> dPad;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onCreate(Bundle)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onStart()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.directional_pad_fragment, container, false);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);

        dPad = new HashMap<String, Bitmap>();

        ///////////////////////////////////////////////////////////////////////
        int currentOrientation = getResources().getConfiguration().orientation;
        int scaleFactor = 1;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            scaleFactor = 4;
        } else if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            scaleFactor = 2;
        }
        ///////////////////////////////////////////////////////////////////////

        Bitmap upButton = Bitmap.createBitmap(source, 62, 365, 52, 40);
        Bitmap scaledUpButton = Bitmap.createScaledBitmap(upButton, upButton.getWidth() * scaleFactor, upButton.getHeight() * scaleFactor, false);
        Bitmap leftButton = Bitmap.createBitmap(source, 22, 405, 40, 52);
        Bitmap scaledLeftButton = Bitmap.createScaledBitmap(leftButton, leftButton.getWidth() * scaleFactor, leftButton.getHeight() * scaleFactor, false);
        Bitmap centerButton = Bitmap.createBitmap(source, 62, 405, 52, 52);
        Bitmap scaledCenterButton = Bitmap.createScaledBitmap(centerButton, centerButton.getWidth() * scaleFactor, centerButton.getHeight() * scaleFactor, false);
        Bitmap rightButton = Bitmap.createBitmap(source, 114, 405, 40, 52);
        Bitmap scaledRightButton = Bitmap.createScaledBitmap(rightButton, rightButton.getWidth() * scaleFactor, rightButton.getHeight() * scaleFactor, false);
        Bitmap downButton = Bitmap.createBitmap(source, 62, 457, 52, 40);
        Bitmap scaledDownButton = Bitmap.createScaledBitmap(downButton, downButton.getWidth() * scaleFactor, downButton.getHeight() * scaleFactor, false);

        dPad.put("up", scaledUpButton);
        dPad.put("left", scaledLeftButton);
        dPad.put("center", scaledCenterButton);
        dPad.put("right", scaledRightButton);
        dPad.put("down", scaledDownButton);

        ImageView topLeft = (ImageView) view.findViewById(R.id.topLeft);
        ImageView topCenter = (ImageView) view.findViewById(R.id.topCenter);
        topCenter.setImageBitmap(dPad.get("up"));
        topCenter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "topCenter.onTouch()");

                return true;
            }
        });
        ImageView topRight = (ImageView) view.findViewById(R.id.topRight);

        ImageView centerLeft = (ImageView) view.findViewById(R.id.centerLeft);
        centerLeft.setImageBitmap(dPad.get("left"));
        centerLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "centerLeft.onTouch()");

                return true;
            }
        });
        ImageView center = (ImageView) view.findViewById(R.id.center);
        center.setImageBitmap(dPad.get("center"));
        ImageView centerRight = (ImageView) view.findViewById(R.id.centerRight);
        centerRight.setImageBitmap(dPad.get("right"));
        centerRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "centerRight.onTouch()");

                return true;
            }
        });

        ImageView bottomLeft = (ImageView) view.findViewById(R.id.bottomLeft);
        ImageView bottomCenter = (ImageView) view.findViewById(R.id.bottomCenter);
        bottomCenter.setImageBitmap(dPad.get("down"));
        bottomCenter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "bottomCenter.onTouch()");

                return true;
            }
        });
        ImageView bottomRight = (ImageView) view.findViewById(R.id.bottomRight);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onPause()");
    }
}
