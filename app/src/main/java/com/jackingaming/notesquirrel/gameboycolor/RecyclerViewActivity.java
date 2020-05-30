package com.jackingaming.notesquirrel.gameboycolor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class RecyclerViewActivity extends AppCompatActivity
        implements AdapterRecyclerView.ItemClickListener {

    public enum Mode { GRID, LINEAR; }

    private Mode mode = Mode.GRID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initRecyclerView();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tinkering);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // specify a layout manager
        recyclerView.setLayoutManager( instantiateLayoutManager() );

        //TODO: placeholder dataSet.
        String[] dataSet = { "Here ", "is ", "a ", "data ", "set ", "of ", "String ", "objects." };
        AdapterRecyclerView adapter = new AdapterRecyclerView(dataSet);
        adapter.setClickListener(this);

        // specify an adapter
        recyclerView.setAdapter(adapter);
    }

    public void onSwitchModeButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSwitchModeButtonClick(View)");

        toggleMode();

        initRecyclerView();
    }

    /**
     * Alternate current mode.
     */
    private void toggleMode() {
        mode = (mode == Mode.GRID) ? (Mode.LINEAR) : (Mode.GRID);
    }

    private RecyclerView.LayoutManager instantiateLayoutManager() {
        switch (mode) {
            case GRID:
                // use a grid layout manager
                int numberOfColumns = 4;
                return new GridLayoutManager(this, numberOfColumns);
            case LINEAR:
                // use a linear layout manager
                return new LinearLayoutManager(this);
            default:
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSwitchModeButtonClick(View) switch (mode)'s default block.");
                return null;
        }
    }

}