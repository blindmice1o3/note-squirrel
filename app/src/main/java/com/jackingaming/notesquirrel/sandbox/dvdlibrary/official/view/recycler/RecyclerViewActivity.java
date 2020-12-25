package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler;

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

import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RecyclerViewActivity extends AppCompatActivity
        implements AdapterRecyclerView.ItemClickListener {

    public enum Mode { GRID, LINEAR; }

    private RecyclerView recyclerView;
    private String[] dataSet;
    private AdapterRecyclerView adapter;
    private int scrollPosition;
    private Mode mode = Mode.GRID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tinkering);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        scrollPosition = 0;
        mode = Mode.GRID;

        dataSet = loadCSV();

        String url = "192.168.0.141:8080/dvds";
        RestTemplate restTemplate = new RestTemplate();
        // TODO: getForObject() (the returned data will be used to replace dataSet).

        adapter = new AdapterRecyclerView(dataSet);
        adapter.setClickListener(this);

        initRecyclerView();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        // specify a layout manager
        recyclerView.setLayoutManager( instantiateLayoutManager() );

        // specify an adapter
        recyclerView.setAdapter(adapter);
    }

    public void onSwitchModeButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSwitchModeButtonClick(View)");

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
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.recordScrollPosition() switch (mode)'s default block.");
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
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.instantiateLayoutManager() switch (mode)'s default block.");
                return null;
        }
    }

    private String[] loadCSV() {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        InputStream inputStream = getResources().openRawResource(R.raw.dvd_library_collection_unsorted_csv);

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] dvds = stringBuilder.toString().split(",");
        Log.d(MainActivity.DEBUG_TAG, "number of dvds (via array's length): " + dvds.length);

        ///////////////////////////////////////////////////////////////////////////
        int counter = 0;
        for (String dvd : dvds) {
            counter++;
            Log.d(MainActivity.DEBUG_TAG, String.format("%3d: %s", counter, dvd));
        }
        Toast.makeText(this, "number of dvds (via counter): " + counter, Toast.LENGTH_SHORT).show();
        ///////////////////////////////////////////////////////////////////////////

        return dvds;
    }

}