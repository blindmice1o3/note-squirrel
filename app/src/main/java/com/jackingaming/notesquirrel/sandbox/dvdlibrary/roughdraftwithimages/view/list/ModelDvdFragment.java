package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list;

import android.graphics.Bitmap;
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
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.Dvd;

public class ModelDvdFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ModelDvdFragment.onCreate(Bundle)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "ModelDvdFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.model_dvd_fragment, container, false);

        return view;
    }

    public void setDvd(Dvd dvd) {
        Log.d(MainActivity.DEBUG_TAG, "ModelDvdFragment.setDvd(Dvd)");

        View view = getView();

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview_dvd_cover_art);
        TextView textView = (TextView) view.findViewById(R.id.textview_dvd_title);

        imageView.setImageBitmap(dvd.getImage());
        textView.setText(dvd.getTitle());

        ///////////////////////////////////////////////////////////////////////
        /*
        imageView.getLayoutParams().height = (int) (icon.getHeight() * 0.5);
        imageView.setAdjustViewBounds(true);

        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        Log.d(MainActivity.DEBUG_TAG, "scaledDensity: " + scaledDensity);
        Log.d(MainActivity.DEBUG_TAG, "widthPixels: " + widthPixels);
        Log.d(MainActivity.DEBUG_TAG, "heightPixels: " + heightPixels);
        Log.d(MainActivity.DEBUG_TAG, "source.getHeight(): " + source.getHeight());
        Log.d(MainActivity.DEBUG_TAG, "icon.getHeight(): " + icon.getHeight());
        */
        ///////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "ModelDvdFragment.onPause()");

        // Save data if necessary.
    }

}