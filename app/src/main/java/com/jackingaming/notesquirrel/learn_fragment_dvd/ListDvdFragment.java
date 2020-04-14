package com.jackingaming.notesquirrel.learn_fragment_dvd;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.R;

public class ListDvdFragment extends ListFragment {

    private DvdList dvds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dvds = new DvdList(getResources());

        ArrayAdapter<Dvd> adapter = new ArrayAdapter<Dvd>(
                getActivity(),
                R.layout.list_item_dvd,
                dvds);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Dvd dvd = dvds.get(position);

        Toast.makeText(getActivity(), dvd.getTitle(), Toast.LENGTH_LONG).show();
    }

}