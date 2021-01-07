package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.R;

public class GameViewFragment extends Fragment {
    public interface OnGameViewFragmentClickListener {
        public void onGameViewClicked();
    }
    private OnGameViewFragmentClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnGameViewFragmentClickListener) {
            listener = (OnGameViewFragmentClickListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + "must implement GameViewFragment.OnGameViewFragmentClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_view, container, false);
        return view;
    }


}
