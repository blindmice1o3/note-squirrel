package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.buttonpad.ButtonPadFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.gamepad.directionpad.DirectionPadFragment;

public class GamePadFragment extends Fragment {

    private DirectionPadFragment directionPadFragment;
    private ButtonPadFragment buttonPadFragment;

    public GamePadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_pad, container, false);
        directionPadFragment = (DirectionPadFragment) getChildFragmentManager().findFragmentById(R.id.directionpadfragment_game_pad_fragment);
        buttonPadFragment = (ButtonPadFragment) getChildFragmentManager().findFragmentById(R.id.buttonpadfragment_game_pad_fragment);
        return view;
    }
}