package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.PocketCrittersCartridge;

public class RecyclerViewActivity extends AppCompatActivity
        implements AdapterRecyclerView.ItemClickListener {

    public enum Mode { GRID, LINEAR; }

    private Mode mode = Mode.GRID;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tinkering);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // use a grid layout manager
        int numberOfColumns = 4;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        //TODO: placeholder dataSet.
        String[] dataSet = { "Here ", "is ", "a ", "data ", "set ", "of ", "String ", "objects." };
        // specify an adapter
        AdapterRecyclerView adapter = new AdapterRecyclerView(dataSet);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    public void onSwitchModeButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSwitchModeButtonClick(View)");

        // Alternate current mode.
        mode = (mode == Mode.GRID) ? (Mode.LINEAR) : (Mode.GRID);

        RecyclerView.LayoutManager layoutManager = null;
        switch (mode) {
            case GRID:
                // use a grid layout manager
                int numberOfColumns = 4;
                layoutManager = new GridLayoutManager(this, numberOfColumns);
                break;
            case LINEAR:
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(this);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSwitchModeButtonClick(View) switch (mode)'s default block.");
                break;
        }

        recyclerView.setLayoutManager(layoutManager);
    }

}