package com.jackingaming.notesquirrel.sandbox.learnfragment.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class BelowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "BelowFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_frame, container, false);
    }

}