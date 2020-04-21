package com.jackingaming.notesquirrel.gameboycolor.input;

import android.content.Context;
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

public class ButtonPadFragment extends Fragment {

    ///////////////////////////////////////////////////////////////////
    public enum InputButton { A_BUTTON, B_BUTTON, MENU_BUTTON; }
    public interface OnButtonPadTouchListener {
        public void onButtonPadTouched(InputButton inputButton, MotionEvent event);
    }
    private OnButtonPadTouchListener onButtonPadTouchListener;
    public void setOnButtonPadTouchListener(OnButtonPadTouchListener onButtonPadTouchListener) {
        this.onButtonPadTouchListener = onButtonPadTouchListener;
    }
    ////////////////////////////////////////////////////////////////////

    private Map<String, Bitmap> buttonPad;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "ButtonPadFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.button_pad_fragment, container, false);

        Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.d_pad);

        buttonPad = new HashMap<String, Bitmap>();

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
        Bitmap scaledMenuButton = Bitmap.createScaledBitmap(menuButton, menuButton.getWidth() * scaleFactor, menuButton.getHeight() * scaleFactor, false);
        Bitmap aButton = Bitmap.createBitmap(source, 172, 435, 64, 52);
        Bitmap scaledAButton = Bitmap.createScaledBitmap(aButton, aButton.getWidth() * scaleFactor, aButton.getHeight() * scaleFactor, false);
        Bitmap bButton = Bitmap.createBitmap(source, 244, 435, 64, 52);
        Bitmap scaledBButton = Bitmap.createScaledBitmap(bButton, bButton.getWidth() * scaleFactor, bButton.getHeight() * scaleFactor, false);

        buttonPad.put("menu", scaledMenuButton);
        buttonPad.put("a", scaledAButton);
        buttonPad.put("b", scaledBButton);

        ImageView menuButtonPad = (ImageView) view.findViewById(R.id.menu_button_pad);
        menuButtonPad.setImageBitmap(buttonPad.get("menu"));
        menuButtonPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "menuButtonPad.onTouch()");
                if (onButtonPadTouchListener != null) {
                    //TODO: Instead of GameCartridge... goto JackInActivity (compose w KeyManager).
                    //TODO: Pass MotionEvent in as a second parameter.
                    onButtonPadTouchListener.onButtonPadTouched(InputButton.MENU_BUTTON, event);
                }
                return true;
            }
        });

        ImageView aButtonPad = (ImageView) view.findViewById(R.id.a_button_pad);
        aButtonPad.setImageBitmap(buttonPad.get("a"));
        aButtonPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "aButtonPad.onTouch()");
                if (onButtonPadTouchListener != null) {
                    onButtonPadTouchListener.onButtonPadTouched(InputButton.A_BUTTON, event);
                }
                return true;
            }
        });

        ImageView bButtonPad = (ImageView) view.findViewById(R.id.b_button_pad);
        bButtonPad.setImageBitmap(buttonPad.get("b"));
        bButtonPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(MainActivity.DEBUG_TAG, "bButtonPad.onTouch()");
                if (onButtonPadTouchListener != null) {
                    onButtonPadTouchListener.onButtonPadTouched(InputButton.B_BUTTON, event);
                }
                return true;
            }
        });

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