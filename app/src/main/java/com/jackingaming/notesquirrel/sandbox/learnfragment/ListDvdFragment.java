package com.jackingaming.notesquirrel.sandbox.learnfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class ListDvdFragment extends ListFragment {

    private OnDvdItemClickListener onDvdItemClickListener;

    public interface OnDvdItemClickListener {
        public void onDvdItemClicked(int position);
    }

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

        /*
        Dvd dvd = dvds.get(position);
        Toast.makeText(getActivity(), dvd.getTitle(), Toast.LENGTH_LONG).show();
        */
    }

    public void setOnDvdItemClickListener(OnDvdItemClickListener onDvdItemClickListener) {
        this.onDvdItemClickListener = onDvdItemClickListener;
    }

}