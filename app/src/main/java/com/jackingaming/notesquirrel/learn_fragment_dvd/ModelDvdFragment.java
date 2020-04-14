package com.jackingaming.notesquirrel.learn_fragment_dvd;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class ModelDvdFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialization here.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.model_dvd_fragment, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.title);

        Drawable icon = getResources().getDrawable(R.drawable.stoner_movies_on_netflix);

        imageView.setImageDrawable(icon);
        //imageView.getLayoutParams().height = (int) (icon.getIntrinsicHeight() * 0.5);
        //imageView.setAdjustViewBounds(true);
        ///////////////////////////////////////////////////////////////////////
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        Log.d(MainActivity.DEBUG_TAG, "scaledDensity: " + scaledDensity);
        Log.d(MainActivity.DEBUG_TAG, "widthPixels: " + widthPixels);
        Log.d(MainActivity.DEBUG_TAG, "heightPixels: " + heightPixels);
        Log.d(MainActivity.DEBUG_TAG, "icon.getInstrinsicHeight(): " + icon.getIntrinsicHeight());
        Log.d(MainActivity.DEBUG_TAG, "icon.getIntrinsicHeight() * 0.5: " + (icon.getIntrinsicHeight() * 0.5));
        ///////////////////////////////////////////////////////////////////////
        textView.setText("stoner movies on netflix COMPILATION IMAGE");

        return view;
    }

    @Override
    public void onPause() {
        // Save data if necessary.
        super.onPause();
    }

}