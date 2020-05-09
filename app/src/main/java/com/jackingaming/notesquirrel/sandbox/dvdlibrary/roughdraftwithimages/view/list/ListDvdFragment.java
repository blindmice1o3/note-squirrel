package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.MainActivity;

public class ListDvdFragment extends ListFragment {

    ///////////////////////////////////////////////////////////////////////////////////////
    public interface OnDvdItemClickListener {
        public void onDvdItemClicked(int position);
    }
    private OnDvdItemClickListener onDvdItemClickListener;
    public void setOnDvdItemClickListener(OnDvdItemClickListener onDvdItemClickListener) {
        this.onDvdItemClickListener = onDvdItemClickListener;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ListDvdFragment.onCreate(Bundle)");
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(MainActivity.DEBUG_TAG, "ListDvdFragment.onListItemClick(ListView, View, int, long)");

        if (onDvdItemClickListener != null) {
            onDvdItemClickListener.onDvdItemClicked(position);
        }
    }

}