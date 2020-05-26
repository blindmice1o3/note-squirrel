package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.MainActivity;

public class MyListFragment extends ListFragment {

    ///////////////////////////////////////////////////////////////////////////////////////
    public interface OnListItemClickListener {
        public void onDvdItemClicked(int position);
    }
    private OnListItemClickListener onListItemClickListener;
    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "MyListFragment.onCreate(Bundle)");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(MainActivity.DEBUG_TAG, "MyListFragment.onListItemClick(ListView, View, int, long)");

        if (onListItemClickListener != null) {
            onListItemClickListener.onDvdItemClicked(position);
        }
    }

}