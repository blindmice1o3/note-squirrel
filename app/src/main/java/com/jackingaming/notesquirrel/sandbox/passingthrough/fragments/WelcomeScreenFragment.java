package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.PassingThroughActivity;

public class WelcomeScreenFragment extends Fragment {
    public static final String TAG = "WelcomeScreenFragment";

    public WelcomeScreenFragment() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + " constructor");
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onCreate(Bundle savedInstanceState)");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)");

        View view = inflater.inflate(R.layout.fragment_welcome_screen, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onViewCreated(View view, Bundle savedInstanceState)");

        final String[] gameTitles = getResources().getStringArray(R.array.game_titles);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, gameTitles);

        ListView listView = view.findViewById(R.id.listview_welcome_screen_fragment);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), gameTitles[position], Toast.LENGTH_SHORT).show();

                ((PassingThroughActivity) getActivity()).changeLayout(gameTitles[position]);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onPause()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onSaveInstanceState(Bundle outState)");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onAttach(Context context)");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onDetach()");
    }
}