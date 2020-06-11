package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;

import java.util.ArrayList;
import java.util.List;

public class BackpackActivity extends AppCompatActivity
        implements ItemAdapterRecyclerView.ItemClickListener {

    public enum Mode { GRID, LINEAR; }

    public static final String SELECTED_ITEM = "SELECTED_ITEM";

    private RecyclerView recyclerView;
    private List<Item> dataSet;
    private ItemAdapterRecyclerView adapter;
    private int scrollPosition;
    private Mode mode = Mode.GRID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_backpack_activity);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        scrollPosition = 0;
        mode = Mode.GRID;

        dataSet = new ArrayList<Item>();
        dataSet.add(new Item(getResources(), Item.Id.BUG_NET));
        dataSet.add(new Item(getResources(), Item.Id.SHOVEL));
        dataSet.add(new Item(getResources(), Item.Id.SICKLE));
        dataSet.add(new Item(getResources(), Item.Id.FISHING_POLE));

        dataSet.add(new Item(getResources(), Item.Id.AX));
        dataSet.add(new Item(getResources(), Item.Id.HAMMER));
        dataSet.add(new Item(getResources(), Item.Id.BUG_NET));
        dataSet.add(new Item(getResources(), Item.Id.WATERING_CAN));

        dataSet.add(new Item(getResources(), Item.Id.BUG_NET));
        dataSet.add(new Item(getResources(), Item.Id.SHOVEL));
        dataSet.add(new Item(getResources(), Item.Id.SICKLE));
        dataSet.add(new Item(getResources(), Item.Id.FISHING_POLE));

        dataSet.add(new Item(getResources(), Item.Id.AX));
        dataSet.add(new Item(getResources(), Item.Id.HAMMER));
        dataSet.add(new Item(getResources(), Item.Id.BUG_NET));
        dataSet.add(new Item(getResources(), Item.Id.WATERING_CAN));

        /////////////////////////////////////////////////////////////////////////
        if (getIntent().getSerializableExtra(JackInActivity.INVENTORY) != null) {
            dataSet = (ArrayList<Item>) getIntent().getSerializableExtra(JackInActivity.INVENTORY);
            for (Item item : dataSet) {
                item.initImage(getResources());
            }
        }
        /////////////////////////////////////////////////////////////////////////

        initRecyclerView();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();

        /////////////////////////////////////////////////////////////////////////
        Intent intent = new Intent();
        intent.putExtra(SELECTED_ITEM, position);
        setResult(RESULT_OK, intent);
        Log.d(MainActivity.DEBUG_TAG, "BackpackActivity.onItemClick(View, int) Intent.putExtra(String, int) has put position in as extra data.");
        finish();
        /////////////////////////////////////////////////////////////////////////
    }

    private void initRecyclerView() {
        // specify an adapter (based on Mode.GRID or Mode.LINEAR)
        adapter = new ItemAdapterRecyclerView(dataSet, mode);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // specify a layout manager
        recyclerView.setLayoutManager( instantiateLayoutManager() );
    }

    public void onSwitchModeButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "BackpackActivity.onSwitchModeButtonClick(View)");

        recordScrollPosition();

        toggleMode();

        initRecyclerView();

        loadScrollPosition();
    }

    private void recordScrollPosition() {
        switch (mode) {
            case GRID:
                scrollPosition = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                break;
            case LINEAR:
                scrollPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "BackpackActivity.recordScrollPosition() switch (mode)'s default block.");
                break;
        }
    }

    private void loadScrollPosition() {
        recyclerView.scrollToPosition(scrollPosition);
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
                int numberOfColumns = 4;
                return new GridLayoutManager(this, numberOfColumns);
            case LINEAR:
                return new LinearLayoutManager(this);
            default:
                Log.d(MainActivity.DEBUG_TAG, "BackpackActivity.instantiateLayoutManager() switch (mode)'s default block.");
                return null;
        }
    }

}