package com.jackingaming.notesquirrel.learn_fragments2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.R;

public class SpriteSheetListFragment extends ListFragment {

    private SpriteSheetList spriteSheets = new SpriteSheetList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<SpriteSheet> adapter = new ArrayAdapter<SpriteSheet>(
                getActivity(),
                R.layout.list_item_sprite_sheet,
                spriteSheets);

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        SpriteSheet spriteSheet = spriteSheets.get(position);

        Toast.makeText(getActivity(), spriteSheet.getDescription(), Toast.LENGTH_LONG).show();
    }

}