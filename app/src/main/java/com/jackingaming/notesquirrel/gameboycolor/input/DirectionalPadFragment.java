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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.util.HashMap;
import java.util.Map;

public class DirectionalPadFragment extends Fragment {

    public enum Direction { UP, LEFT, CENTER, RIGHT, DOWN; }

    ////////////////////////////////////////////////////////////////////////////////
    public interface OnDirectionalPadTouchListener {
        public void onDirectionalPadTouched(Direction direction, boolean isPressed);
    }
    private OnDirectionalPadTouchListener onDirectionalPadTouchListener;
    public void setOnDirectionalPadTouchListener(OnDirectionalPadTouchListener onDirectionalPadTouchListener) {
        this.onDirectionalPadTouchListener = onDirectionalPadTouchListener;
    }
    ////////////////////////////////////////////////////////////////////////////////

    private Map<Direction, Bitmap> texture;

    private ImageView imageViewLeft;
    private ImageView imageViewUp;
    private ImageView imageViewRight;
    private ImageView imageViewDown;

    private Rect boundsOfLeft;
    private Rect boundsOfUp;
    private Rect boundsOfRight;
    private Rect boundsOfDown;

    private ConstraintLayout constraintLayout;

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

    public void initBounds() {
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.initBounds()");

        boundsOfUp = new Rect(imageViewUp.getLeft(), imageViewUp.getTop(), imageViewUp.getRight(), imageViewUp.getBottom());
        boundsOfLeft = new Rect(imageViewLeft.getLeft(), imageViewLeft.getTop(), imageViewLeft.getRight(), imageViewLeft.getBottom());
        boundsOfRight = new Rect(imageViewRight.getLeft(), imageViewRight.getTop(), imageViewRight.getRight(), imageViewRight.getBottom());
        boundsOfDown = new Rect(imageViewDown.getLeft(), imageViewDown.getTop(), imageViewDown.getRight(), imageViewDown.getBottom());

        Log.d("DirectionalPadFragment", " constraintLayout (LTRB): " + constraintLayout.getLeft() + ", " + constraintLayout.getTop() + ", " + constraintLayout.getRight() + ", " + constraintLayout.getBottom());

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d(MainActivity.DEBUG_TAG, "constraintLayout.onTouch()");
                if (onDirectionalPadTouchListener != null) {

                    // If this callback is being called, a MotionEvent was triggered...
                    // MotionEvent event may be: ACTION_DOWN, ACTION_MOVE, or ACTION_UP.
                    boolean isPressed = true;
                    Direction direction = null;

//                    Log.d(MainActivity.DEBUG_TAG, "CONSTRAINTLAYOUT ONTOUCHLISTENER v.getLeft(), v.getTop(): " + v.getLeft() + ", " + v.getTop());
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfUp: " + boundsOfUp.left + ", " + boundsOfUp.top + ", " + boundsOfUp.right + ", " + boundsOfUp.bottom);
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfLeft: " + boundsOfLeft.left + ", " + boundsOfLeft.top + ", " + boundsOfLeft.right + ", " + boundsOfLeft.bottom);
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfRight: " + boundsOfRight.left + ", " + boundsOfRight.top + ", " + boundsOfRight.right + ", " + boundsOfRight.bottom);
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfDown: " + boundsOfDown.left + ", " + boundsOfDown.top + ", " + boundsOfDown.right + ", " + boundsOfDown.bottom);

                    // Determine if the touch event occurred within the bounds of a "button".
                    // If so, set direction to the corresponding "button", otherwise the
                    // touch event should NOT count as a "button" press.
                    if (boundsOfUp.contains( (int) event.getX(), (int) event.getY() )) {
                        direction = Direction.UP;
                    } else if (boundsOfDown.contains( (int) event.getX(), (int) event.getY() )) {
                        direction = Direction.DOWN;
                    } else if (boundsOfLeft.contains( (int) event.getX(), (int) event.getY() )) {
                        direction = Direction.LEFT;
                    } else if (boundsOfRight.contains( (int) event.getX(), (int) event.getY() )) {
                        direction = Direction.RIGHT;
                    } else {
                        isPressed = false;
                    }

                    // ACTION_UP means a "button" was released, and is NOT a "button" press.
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isPressed = false;
                    }

                    // Inform listener/subscriber (InputManager) that a touch event occurred
                    // and the interpreted results are: (1) direction and (2) isPressed.
                    onDirectionalPadTouchListener.onDirectionalPadTouched(direction, isPressed);
                }
                return true;
            }
        });
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment's ConstraintLayout's OnTouchListener is defined.");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "DirectionalPadFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.directional_pad_fragment, container, false);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);

        texture = new HashMap<Direction, Bitmap>();

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
        Bitmap leftButton = Bitmap.createBitmap(source, 22, 405, 40, 52);
        Bitmap centerButton = Bitmap.createBitmap(source, 62, 405, 52, 52);
        Bitmap rightButton = Bitmap.createBitmap(source, 114, 405, 40, 52);
        Bitmap downButton = Bitmap.createBitmap(source, 62, 457, 52, 40);

        Bitmap scaledUpButton = Bitmap.createScaledBitmap(upButton, upButton.getWidth() * scaleFactor, upButton.getHeight() * scaleFactor, false);
        Bitmap scaledLeftButton = Bitmap.createScaledBitmap(leftButton, leftButton.getWidth() * scaleFactor, leftButton.getHeight() * scaleFactor, false);
        Bitmap scaledCenterButton = Bitmap.createScaledBitmap(centerButton, centerButton.getWidth() * scaleFactor, centerButton.getHeight() * scaleFactor, false);
        Bitmap scaledRightButton = Bitmap.createScaledBitmap(rightButton, rightButton.getWidth() * scaleFactor, rightButton.getHeight() * scaleFactor, false);
        Bitmap scaledDownButton = Bitmap.createScaledBitmap(downButton, downButton.getWidth() * scaleFactor, downButton.getHeight() * scaleFactor, false);

        texture.put(Direction.UP, scaledUpButton);
        texture.put(Direction.LEFT, scaledLeftButton);
        texture.put(Direction.CENTER, scaledCenterButton);
        texture.put(Direction.RIGHT, scaledRightButton);
        texture.put(Direction.DOWN, scaledDownButton);


        ////////////////////////////////////////////////////////////////////////////////////////////
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayoutDirectionalPad);
        ////////////////////////////////////////////////////////////////////////////////////////////


        ImageView imageViewUpLeft = (ImageView) view.findViewById(R.id.imageViewUpLeft);
        ///////////////////////////////////////////////////////////
        imageViewUp = (ImageView) view.findViewById(R.id.imageViewUp);
        imageViewUp.setImageBitmap(texture.get(Direction.UP));
        ///////////////////////////////////////////////////////////
        ImageView imageViewUpRight = (ImageView) view.findViewById(R.id.imageViewUpRight);



        ///////////////////////////////////////////////////////////
        imageViewLeft = (ImageView) view.findViewById(R.id.imageViewLeft);
        imageViewLeft.setImageBitmap(texture.get(Direction.LEFT));
        ///////////////////////////////////////////////////////////
        ImageView imageViewCenter = (ImageView) view.findViewById(R.id.imageViewCenter);
        imageViewCenter.setImageBitmap(texture.get(Direction.CENTER));
        ///////////////////////////////////////////////////////////
        imageViewRight = (ImageView) view.findViewById(R.id.imageViewRight);
        imageViewRight.setImageBitmap(texture.get(Direction.RIGHT));
        ///////////////////////////////////////////////////////////



        ImageView imageViewDownLeft = (ImageView) view.findViewById(R.id.imageViewDownLeft);
        ///////////////////////////////////////////////////////////
        imageViewDown = (ImageView) view.findViewById(R.id.imageViewDown);
        imageViewDown.setImageBitmap(texture.get(Direction.DOWN));
        ///////////////////////////////////////////////////////////
        ImageView imageViewDownRight = (ImageView) view.findViewById(R.id.imageViewDownRight);



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