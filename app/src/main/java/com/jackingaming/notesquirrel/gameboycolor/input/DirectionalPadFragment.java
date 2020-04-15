package com.jackingaming.notesquirrel.gameboycolor.input;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.R;

public class DirectionalPadFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.directional_pad_fragment, container, false);

        ImageView topLeft = (ImageView) view.findViewById(R.id.topLeft);
        ImageView topCenter = (ImageView) view.findViewById(R.id.topCenter);
        ImageView topRight = (ImageView) view.findViewById(R.id.topRight);

        ImageView centerLeft = (ImageView) view.findViewById(R.id.centerLeft);
        ImageView center = (ImageView) view.findViewById(R.id.center);
        ImageView centerRight = (ImageView) view.findViewById(R.id.centerRight);

        ImageView bottomLeft = (ImageView) view.findViewById(R.id.bottomLeft);
        ImageView bottomCenter = (ImageView) view.findViewById(R.id.bottomCenter);
        ImageView bottomRight = (ImageView) view.findViewById(R.id.bottomRight);

        //do stuff

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
