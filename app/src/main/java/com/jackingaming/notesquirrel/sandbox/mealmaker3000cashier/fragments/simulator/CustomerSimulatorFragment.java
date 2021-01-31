package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.simulator;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackingaming.notesquirrel.R;

public class CustomerSimulatorFragment extends Fragment {
    public static final String TAG = "CustomerSimulatorFragment";

    public CustomerSimulatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_simulator, container, false);
    }
}