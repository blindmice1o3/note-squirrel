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

public class ButtonPadFragment extends Fragment {

    public enum InputButton { A_BUTTON, B_BUTTON, MENU_BUTTON; }

    ///////////////////////////////////////////////////////////////////////////////
    public interface OnButtonPadTouchListener {
        public void onButtonPadTouched(InputButton inputButton, boolean isPressed);
    }
    private OnButtonPadTouchListener onButtonPadTouchListener;
    public void setOnButtonPadTouchListener(OnButtonPadTouchListener onButtonPadTouchListener) {
        this.onButtonPadTouchListener = onButtonPadTouchListener;
    }
    ///////////////////////////////////////////////////////////////////////////////

    private Map<InputButton, Bitmap> texture;

    private ImageView imageViewMenu;
    private ImageView imageViewA;
    private ImageView imageViewB;

    private Rect boundsOfMenu;
    private Rect boundsOfA;
    private Rect boundsOfB;

    private ConstraintLayout constraintLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onAttach(Context)");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onCreate(Bundle)");
    }

    public void initBounds() {
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.initBounds()");

        boundsOfMenu = new Rect(imageViewMenu.getLeft(), imageViewMenu.getTop(), imageViewMenu.getRight(), imageViewMenu.getBottom());
        boundsOfA = new Rect(imageViewA.getLeft(), imageViewA.getTop(), imageViewA.getRight(), imageViewA.getBottom());
        boundsOfB = new Rect(imageViewB.getLeft(), imageViewB.getTop(), imageViewB.getRight(), imageViewB.getBottom());

        Log.d("ButtonPadFragment", " constraintLayout (LTRB): " + constraintLayout.getLeft() + ", " + constraintLayout.getTop() + ", " + constraintLayout.getRight() + ", " + constraintLayout.getBottom());

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d(MainActivity.DEBUG_TAG, "constraintLayout.onTouch()");
                if (onButtonPadTouchListener != null) {

                    // If this callback is being called, a MotionEvent was triggered...
                    // MotionEvent event may be: ACTION_DOWN, ACTION_MOVE, or ACTION_UP.
                    boolean isPressed = true;
                    InputButton inputButton = null;

//                    Log.d(MainActivity.DEBUG_TAG, "CONSTRAINTLAYOUT ONTOUCHLISTENER v.getLeft(), v.getTop(): " + v.getLeft() + ", " + v.getTop());
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfMenu: " + boundsOfMenu.left + ", " + boundsOfMenu.top + ", " + boundsOfMenu.right + ", " + boundsOfMenu.bottom);
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfA: " + boundsOfA.left + ", " + boundsOfA.top + ", " + boundsOfA.right + ", " + boundsOfA.bottom);
//                    Log.d(MainActivity.DEBUG_TAG, "boundsOfB: " + boundsOfB.left + ", " + boundsOfB.top + ", " + boundsOfB.right + ", " + boundsOfB.bottom);

                    // Determine if the touch event occurred within the bounds of a "button".
                    // If so, set inputButton to the corresponding "button", otherwise the
                    // touch event should NOT count as a "button" press.
                    if (boundsOfMenu.contains( (int) event.getX(), (int) event.getY() )) {
                        inputButton = InputButton.MENU_BUTTON;
                    } else if (boundsOfA.contains( (int) event.getX(), (int) event.getY() )) {
                        inputButton = InputButton.A_BUTTON;
                    } else if (boundsOfB.contains( (int) event.getX(), (int) event.getY() )) {
                        inputButton = InputButton.B_BUTTON;
                    } else {
                        isPressed = false;
                    }

                    // ACTION_UP means a "button" was released, and is NOT a "button" press.
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isPressed = false;
                    }

                    // Inform listener/subscriber (InputManager) that a touch event occurred
                    // and the interpreted results are: (1) inputButton and (2) isPressed.
                    onButtonPadTouchListener.onButtonPadTouched(inputButton, isPressed);
                }
                return true;
            }
        });



        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment's ConstraintLayout's OnTouchListener is defined.");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.button_pad_fragment, container, false);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);

        texture = new HashMap<InputButton, Bitmap>();

        ///////////////////////////////////////////////////////////////////////
        int currentOrientation = getResources().getConfiguration().orientation;
        int scaleFactor = 1;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            scaleFactor = 3;
        } else if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            scaleFactor = 2;
        }
        ///////////////////////////////////////////////////////////////////////

        Bitmap menuButton = Bitmap.createBitmap(source, 172, 375, 136, 52);
        Bitmap aButton = Bitmap.createBitmap(source, 172, 435, 64, 52);
        Bitmap bButton = Bitmap.createBitmap(source, 244, 435, 64, 52);

        Bitmap scaledMenuButton = Bitmap.createScaledBitmap(menuButton, menuButton.getWidth() * scaleFactor, menuButton.getHeight() * scaleFactor, false);
        Bitmap scaledAButton = Bitmap.createScaledBitmap(aButton, aButton.getWidth() * scaleFactor, aButton.getHeight() * scaleFactor, false);
        Bitmap scaledBButton = Bitmap.createScaledBitmap(bButton, bButton.getWidth() * scaleFactor, bButton.getHeight() * scaleFactor, false);

        texture.put(InputButton.MENU_BUTTON, scaledMenuButton);
        texture.put(InputButton.A_BUTTON, scaledAButton);
        texture.put(InputButton.B_BUTTON, scaledBButton);


        ////////////////////////////////////////////////////////////////////////////////////////
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintLayoutButtonPad);
        ////////////////////////////////////////////////////////////////////////////////////////


        imageViewMenu = (ImageView) view.findViewById(R.id.imageViewMenu);
        imageViewMenu.setImageBitmap(texture.get(InputButton.MENU_BUTTON));

        imageViewA = (ImageView) view.findViewById(R.id.imageViewA);
        imageViewA.setImageBitmap(texture.get(InputButton.A_BUTTON));

        imageViewB = (ImageView) view.findViewById(R.id.imageViewB);
        imageViewB.setImageBitmap(texture.get(InputButton.B_BUTTON));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onActivityCreated(Bundle)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onDetach()");
    }

}