package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.DvdList;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.grid.DvdListToGridViewCellAdapter;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list.MyListFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list.ModelDvdFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListFragmentDvdParentActivity extends AppCompatActivity {

    private enum Mode { LIST, GRID; }

    private DvdList dvds;

    private Mode mode;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParentActivity.onCreate(Bundle)");

        dvds = new DvdList(getResources());

        initListMode();
        position = 0;
    }

    private void initListMode() {
        mode = Mode.LIST;

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        setContentView(R.layout.activity_list_fragment_dvd_parent);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        ////////////////////////////////////////////////////////////////////////////////////////////
        final ModelDvdFragment modelDvdFragment =
                (ModelDvdFragment) getSupportFragmentManager().findFragmentById(R.id.modelDvd);
        modelDvdFragment.setDvd(dvds.get(position));
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        MyListFragment myListFragment = (MyListFragment) getSupportFragmentManager().findFragmentById(R.id.listDvd);

        ArrayAdapter<Dvd> adapter = new ArrayAdapter<Dvd>(this,
                R.layout.array_adapter_my_list_fragment, dvds);

        myListFragment.setListAdapter(adapter);
        myListFragment.setOnListItemClickListener(new MyListFragment.OnListItemClickListener() {
            @Override
            public void onDvdItemClicked(int position) {
                //tracking last clicked position
                setPosition(position);

                Dvd dvd = dvds.get(position);

                modelDvdFragment.setDvd(dvd);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        //@@@@@@@CONTEXT_MENU@@@@@@@
        registerForContextMenu(myListFragment.getListView());
    }

    private void initGridMode() {
        mode = Mode.GRID;

        //FIND PREVIOUS POSITION
        MyListFragment myListFragment = (MyListFragment) getSupportFragmentManager().findFragmentById(R.id.listDvd);
        int firstVisiblePosition = myListFragment.getListView().getFirstVisiblePosition();
        Toast.makeText(this, "firstVisiblePosition: " + firstVisiblePosition, Toast.LENGTH_SHORT).show();

        ///////////////////////CLEAN UP///////////////////////
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment modelFragment = fragmentManager.findFragmentById(R.id.modelDvd);
        Fragment listFragment = fragmentManager.findFragmentById(R.id.listDvd);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(modelFragment);
        transaction.remove(listFragment);
        transaction.commit();
        //////////////////////////////////////////////////////

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        setContentView(R.layout.activity_grid_view_dvd);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        // Initialize the GridView (otherwise blank screen)
        GridView gridView = (GridView) findViewById(R.id.gridview);

        final DvdListToGridViewCellAdapter adapterForArrayList = new DvdListToGridViewCellAdapter(this, dvds);
        gridView.setAdapter(adapterForArrayList);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParent.onOptionItemSelected()'s gridView.OnItemClickListener.onItemClick(AdapterView<?>, View, int, long)");
                //tracking last clicked position
                setPosition(position);

                // Toggle the favorite-star's activeness image.
                Dvd dvd = dvds.get(position);
                //@@@@@@@@@@@@@@@@@@@
                dvd.toggleFavorite();
                //@@@@@@@@@@@@@@@@@@@
                adapterForArrayList.notifyDataSetChanged();
            }
        });

        ////////////////////////////////////////////
        gridView.setSelection(firstVisiblePosition);
        ////////////////////////////////////////////

        //@@@@@@@CONTEXT_MENU@@@@@@@
        registerForContextMenu(gridView);
    }

    private static final String FAVORITED_DVD_TITLES_KEY = "FAVORITED_DVD_TITLES_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private static final String MODE_INDEX_KEY = "MODE_INDEX_KEY";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParentActivity.onSavedInstanceState(Bundle)");

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Construct a list of favorited dvds.
        final ArrayList<String> favoritedDvdTitles = new ArrayList<String>();
        for (Dvd dvd : dvds) {
            if (dvd.getIsFavorite()) {
                favoritedDvdTitles.add(dvd.getTitle());
            }
        }
        //Save the list to outState for later.
        outState.putStringArrayList(FAVORITED_DVD_TITLES_KEY, favoritedDvdTitles);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        int modeIndex = mode.ordinal();
        outState.putInt(MODE_INDEX_KEY, modeIndex);
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        //!!!ONLY LIST HAS ModelDvdFragment!!!
        if (mode == Mode.LIST) {
            //Save position (determines which dvd for ModelDvdFragment to display) to outstate for later.
            outState.putInt(POSITION_KEY, position);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParentActivity.onRestoreInstanceState(Bundle)");

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Get the previously saved list of favorited dvds.
        final ArrayList<String> favoritedDvdTitles = savedInstanceState.getStringArrayList(FAVORITED_DVD_TITLES_KEY);
        //Iterate through all the dvds and set which was favorited.
        for (String dvdTitle : favoritedDvdTitles) {
            for (Dvd dvd : dvds) {
                if (dvd.getTitle().equals(dvdTitle)) {
                    dvd.setIsFavorite(true);
                    break;
                }
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        int modeIndex = savedInstanceState.getInt(MODE_INDEX_KEY);
        mode = Mode.values()[modeIndex];
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        //!!!ONLY LIST HAS ModelDvdFragment!!!
        if (mode == Mode.LIST) {
            //Get the previously saved position to set dvd to display in ModelDvdFragment.
            position = savedInstanceState.getInt(POSITION_KEY);

            //Set dvd for ModelDvdFragment to display, based on previously saved position.
            final ModelDvdFragment modelDvdFragment =
                    (ModelDvdFragment) getSupportFragmentManager().findFragmentById(R.id.modelDvd);
            modelDvdFragment.setDvd(dvds.get(position));
        } else if (mode == Mode.GRID) {
            //Activity's default starting mode (when re-created) is LIST.
            initGridMode();
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        Log.d(MainActivity.DEBUG_TAG, "JackInActivity.onCreateContextMenu(ContextMenu, View, ContextMenu.ContextMenuInfo)");
        MenuInflater inflater = getMenuInflater();
        //@@@@@@@CONTEXT_MENU@@@@@@@
        inflater.inflate(R.menu.context_menu_activity_list_fragment_dvd_parent, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //@@@@@@@CONTEXT_MENU@@@@@@@
        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParentActivity.onContextItemSelected(MenuItem)");
        switch (item.getItemId()) {
            case R.id.check_out:
                Toast.makeText(this, "Check out", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.cancel:
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_activity_list_fragment_dvd_parent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_listview:
                if (mode != Mode.LIST) {
                    Toast.makeText(this, "ListView", Toast.LENGTH_SHORT).show();

                    initListMode();
                } else {
                    Toast.makeText(this, "Already ListView", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_gridview:
                if (mode != Mode.GRID) {
                    Toast.makeText(this, "GridView", Toast.LENGTH_SHORT).show();

                    initGridMode();
                } else {
                    Toast.makeText(this, "Already GridView", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                Toast.makeText(this, "ListFragmentDvdParentActivity.onOptionsItemSelected(MenuItem) switch's default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
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
        Log.d(MainActivity.DEBUG_TAG, "number of dvds: " + dvds.length);

        /*
        int counter = 0;
        for (String dvd : dvds) {
            counter++;
            Log.d(MainActivity.DEBUG_TAG, String.format("%3d: %s", counter, dvd));
        }
        */

        return dvds;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}