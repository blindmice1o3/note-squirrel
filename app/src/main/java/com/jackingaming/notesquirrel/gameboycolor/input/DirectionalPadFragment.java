package com.jackingaming.notesquirrel.gameboycolor.input;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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

    ///////////////////////////////////////////////////////////////////
    public enum Direction { UP, LEFT, RIGHT, DOWN; }
    public interface OnDirectionalPadTouchListener {
        public void onDirectionalPadTouched(Direction direction, boolean isPressed);
    }
    private OnDirectionalPadTouchListener onDirectionalPadTouchListener;
    public void setOnDirectionalPadTouchListener(OnDirectionalPadTouchListener onDirectionalPadTouchListener) {
        this.onDirectionalPadTouchListener = onDirectionalPadTouchListener;
    }
    ////////////////////////////////////////////////////////////////////

    private Map<String, Bitmap> dPad;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onAttach(Context)");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onCreate(Bundle)");
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

            private Rect rectBoundsOfView;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "topCenter.onTouch()");
                if (onDirectionalPadTouchListener != null) {
                    //TODO: Instead of GameCartridge... goto JackInActivity (compose w KeyManager).
                    //TODO: Pass MotionEvent in as a second parameter.
                    boolean isPressed = true;

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rectBoundsOfView = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {

                        if( !rectBoundsOfView.contains(
                                v.getLeft() + (int)event.getX(),
                                v.getTop() + (int)event.getY()) ) {
                            isPressed = false;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isPressed = false;
                    }

                    onDirectionalPadTouchListener.onDirectionalPadTouched(Direction.UP, isPressed);
                }
                return true;
            }
        });
        ImageView topRight = (ImageView) view.findViewById(R.id.topRight);

        ImageView centerLeft = (ImageView) view.findViewById(R.id.centerLeft);
        centerLeft.setImageBitmap(dPad.get("left"));
        centerLeft.setOnTouchListener(new View.OnTouchListener() {

            private Rect rectBoundsOfView;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "centerLeft.onTouch()");
                if (onDirectionalPadTouchListener != null) {
                    boolean isPressed = true;

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rectBoundsOfView = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {

                        if( !rectBoundsOfView.contains(
                                v.getLeft() + (int)event.getX(),
                                v.getTop() + (int)event.getY()) ) {
                            isPressed = false;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isPressed = false;
                    }

                    onDirectionalPadTouchListener.onDirectionalPadTouched(Direction.LEFT, isPressed);
                }
                return true;
            }
        });
        ImageView center = (ImageView) view.findViewById(R.id.center);
        center.setImageBitmap(dPad.get("center"));
        ImageView centerRight = (ImageView) view.findViewById(R.id.centerRight);
        centerRight.setImageBitmap(dPad.get("right"));
        centerRight.setOnTouchListener(new View.OnTouchListener() {

            private Rect rectBoundsOfView;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "centerRight.onTouch()");
                if (onDirectionalPadTouchListener != null) {
                    boolean isPressed = true;

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rectBoundsOfView = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {

                        if( !rectBoundsOfView.contains(
                                v.getLeft() + (int)event.getX(),
                                v.getTop() + (int)event.getY()) ) {
                            isPressed = false;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isPressed = false;
                    }

                    onDirectionalPadTouchListener.onDirectionalPadTouched(Direction.RIGHT, isPressed);
                }
                return true;
            }
        });

        ImageView bottomLeft = (ImageView) view.findViewById(R.id.bottomLeft);
        ImageView bottomCenter = (ImageView) view.findViewById(R.id.bottomCenter);
        bottomCenter.setImageBitmap(dPad.get("down"));
        bottomCenter.setOnTouchListener(new View.OnTouchListener() {

            private Rect rectBoundsOfView;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "bottomCenter.onTouch()");
                if (onDirectionalPadTouchListener != null) {
                    boolean isPressed = true;

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rectBoundsOfView = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                    }
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {

                        if( !rectBoundsOfView.contains(
                                v.getLeft() + (int)event.getX(),
                                v.getTop() + (int)event.getY()) ) {
                            isPressed = false;
                        }
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isPressed = false;
                    }

                    onDirectionalPadTouchListener.onDirectionalPadTouched(Direction.DOWN, isPressed);
                }
                return true;
            }
        });
        ImageView bottomRight = (ImageView) view.findViewById(R.id.bottomRight);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onActivityCreated(Bundle)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onDetach()");
    }

}