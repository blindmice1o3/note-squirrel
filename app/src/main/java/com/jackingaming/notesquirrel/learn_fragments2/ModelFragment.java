package com.jackingaming.notesquirrel.learn_fragments2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.R;

public class ModelFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialization here.
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.model_fragment, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.title);

        Drawable icon = getResources().getDrawable(R.drawable.rsz_big_trouble_in_little_china);

        imageView.setImageDrawable(icon);
        imageView.getLayoutParams().width = (int) (icon.getIntrinsicWidth() * 0.8);
        imageView.getLayoutParams().height = (int) (icon.getIntrinsicHeight() * 0.8);
        imageView.setAdjustViewBounds(true);

        textView.setText("They told Jack Burton to go to hell... and that's exactly where he's going!");

        return view;
    }

    @Override
    public void onPause() {
        // Save data if necessary.
        super.onPause();
    }

}