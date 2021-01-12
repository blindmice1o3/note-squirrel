package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class RecyclerViewFragment extends Fragment {
    public interface RecyclerViewFragmentListener {
        void initDataSource();
    }
    private RecyclerViewFragmentListener listener;

    public enum Mode { GRID, LINEAR; }
    public static final String TAG = "RecyclerViewFragment";

    private Mode mode;
    private int scrollPosition;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecyclerViewFragmentListener) {
            listener = (RecyclerViewFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RecyclerViewFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewFragment.onCreate()");

        setHasOptionsMenu(true);

        mode = Mode.GRID;
        scrollPosition = 0;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_toggle_layout_manager, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_toggle_layout_manager:
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewFragment.onOptionsItemSelected() R.id.menu_item_toggle_layout_manager");

                performSwitchMode();

                // update icon
                int iconRes = (mode == Mode.GRID) ? (R.drawable.icon_listview) : (R.drawable.icon_gridview);
                item.setIcon(iconRes);
                return true;
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_fragment);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( instantiateLayoutManager() );
    }

    @Override
    public void onStart() {
        super.onStart();
        // Initialize data source
        // callback mechanism to inject RecyclerViewActivity.dvds into here
        // instantiate an adapter using [dvds], setup click listener, connect it to recyclerview
        listener.initDataSource();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void performSwitchMode() {
        recordScrollPosition();

        toggleMode();

        recyclerView.setLayoutManager( instantiateLayoutManager() );

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
                return new GridLayoutManager(getContext(), numberOfColumns);
            case LINEAR:
                return new LinearLayoutManager(getContext());
            default:
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.instantiateLayoutManager() switch (mode)'s default block.");
                return null;
        }
    }
}
