package com.jackingaming.notesquirrel.gameboycolor.input;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.GameView;
import com.jackingaming.notesquirrel.sandbox.listviewemail.Message;
import com.jackingaming.notesquirrel.sandbox.listviewemail.MessageToListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewportFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onAttach(Context)");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onCreate(Bundle)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.viewport_fragment, container, false);

        //TODO: do more stuff

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onActivityCreated(Bundle)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.DEBUG_TAG, "ViewportFragment.onDetach()");
    }

}