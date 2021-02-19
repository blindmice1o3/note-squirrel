package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.buttonpad;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class ButtonPadFragment extends Fragment {
    public enum Button { BUTTON_MENU, BUTTON_A, BUTTON_B; }
    public interface TouchListener {
        void onButtonPadTouch(Button button, MotionEvent event);
    }
    private TouchListener listener;
    public void setTouchListener(TouchListener listener) {
        this.listener = listener;
    }

    private ConstraintLayout constraintLayout;
    private ImageView imageViewButtonMenu;
    private ImageView imageViewButtonA;
    private ImageView imageViewButtonB;

    private Rect boundsOfButtonMenu;
    private Rect boundsOfButtonA;
    private Rect boundsOfButtonB;

    public ButtonPadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button_pad, container, false);

        constraintLayout = view.findViewById(R.id.constraintlayout_button_pad_fragment);
        imageViewButtonMenu = view.findViewById(R.id.imageview_button_menu_button_pad_fragment);
        imageViewButtonA = view.findViewById(R.id.imageview_button_a_button_pad_fragment);
        imageViewButtonB = view.findViewById(R.id.imageview_button_b_button_pad_fragment);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);
        Bitmap imageButtonMenu = Bitmap.createBitmap(source, 172, 375, 136, 52);
        Bitmap imageButtonA = Bitmap.createBitmap(source, 172, 435, 64, 52);
        Bitmap imageButtonB = Bitmap.createBitmap(source, 244, 435, 64, 52);

        imageViewButtonMenu.setImageBitmap(imageButtonMenu);
        imageViewButtonA.setImageBitmap(imageButtonA);
        imageViewButtonB.setImageBitmap(imageButtonB);

        return view;
    }

    public void setupOnTouchListener() {
        boundsOfButtonMenu = new Rect(imageViewButtonMenu.getLeft(), imageViewButtonMenu.getTop(), imageViewButtonMenu.getRight(), imageViewButtonMenu.getBottom());
        Log.d(MainActivity.DEBUG_TAG, "<" + imageViewButtonMenu.getLeft() + ", " + imageViewButtonMenu.getTop() + ", " + imageViewButtonMenu.getRight() + ", " + imageViewButtonMenu.getBottom() + ">");
        boundsOfButtonA = new Rect(imageViewButtonA.getLeft(), imageViewButtonA.getTop(), imageViewButtonA.getRight(), imageViewButtonA.getBottom());
        boundsOfButtonB = new Rect(imageViewButtonB.getLeft(), imageViewButtonB.getTop(), imageViewButtonB.getRight(), imageViewButtonB.getBottom());

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            private boolean pressing;
            private boolean justPressed;
            private boolean cantPress;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onTouch(View v, MotionEvent event)");

//                // If this callback is being called, a MotionEvent was triggered...
//                // MotionEvent event may be: ACTION_DOWN, ACTION_MOVE, or ACTION_UP.
//                if ((event.getAction() == MotionEvent.ACTION_DOWN) ||
//                        (event.getAction() == MotionEvent.ACTION_MOVE ||
//                                (event.getAction() == MotionEvent.ACTION_POINTER_DOWN))) {
//                    pressing = true;
//                } else if ((event.getAction() == MotionEvent.ACTION_UP) ||
//                        (event.getAction() == MotionEvent.ACTION_POINTER_UP)) {
//                    pressing = false;
//                }
//
//                if (cantPress && !pressing) {
//                    cantPress = false;
//                } else if (justPressed) {
//                    cantPress = true;
//                    justPressed = false;
//                }
//                if (!cantPress && pressing) {
//                    justPressed = true;
//                }
//
//                if (justPressed) {
                    Button button = null;
                    // Determine if the touch event occurred within the bounds of a "button".
                    // If so, set direction to the corresponding "button", otherwise the
                    // touch event should NOT count as a "button" press.
                    if (boundsOfButtonMenu.contains((int) event.getX(), (int) event.getY())) {
                        button = Button.BUTTON_MENU;
                    } else if (boundsOfButtonA.contains((int) event.getX(), (int) event.getY())) {
                        button = Button.BUTTON_A;
                    } else if (boundsOfButtonB.contains((int) event.getX(), (int) event.getY())) {
                        button = Button.BUTTON_B;
                    }

                    if (button != null) {
                        ////////////////////////////////////////
                        listener.onButtonPadTouch(button, event);
                        ////////////////////////////////////////
                    }
//                }

                return true;
            }
        });
    }
}