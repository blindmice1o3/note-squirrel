package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.grid.ArrayDataSourceToGridViewCellAdapter;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.grid.DvdListToGridViewCellAdapter;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list.ListDvdFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list.ModelDvdFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ListFragmentDvdParentActivity extends AppCompatActivity {

    private DvdList dvds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fragment_dvd_parent);

        dvds = new DvdList(getResources());

        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParentActivity.onCreate(Bundle)");

        ////////////////////////////////////////////////////////////////////////////////////////////
        final ModelDvdFragment modelDvdFragment = (ModelDvdFragment) getSupportFragmentManager().findFragmentById(R.id.modelDvd);
        modelDvdFragment.setDvd(dvds.get(0));
        ////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        ListDvdFragment listDvdFragment = (ListDvdFragment) getSupportFragmentManager().findFragmentById(R.id.listDvd);

        ArrayAdapter<Dvd> adapter = new ArrayAdapter<Dvd>(this,
                R.layout.list_item_dvd, dvds);

        listDvdFragment.setListAdapter(adapter);
        listDvdFragment.setOnDvdItemClickListener(new ListDvdFragment.OnDvdItemClickListener() {
            @Override
            public void onDvdItemClicked(int position) {
                Dvd dvd = dvds.get(position);

                modelDvdFragment.setDvd(dvd);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_fragment_dvd_parent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        ListDvdFragment listDvdFragment;

        switch (item.getItemId()) {
            case R.id.menu_listview:
                //TODO: implement menu_listview
                Toast.makeText(this, "ListView", Toast.LENGTH_SHORT).show();

                //TODO: 2020_05_09

                /*
                setContentView(R.layout.activity_list_fragment_dvd_parent);

                ////////////////////////////////////////////////////////////////////////////////////////////
                final ModelDvdFragment modelDvdFragment = (ModelDvdFragment) getSupportFragmentManager().findFragmentById(R.id.modelDvd);
                modelDvdFragment.setDvd(dvds.get(0));
                ////////////////////////////////////////////////////////////////////////////////////////////

                ////////////////////////////////////////////////////////////////////////////////////////////
                listDvdFragment = (ListDvdFragment) getSupportFragmentManager().findFragmentById(R.id.listDvd);

                ArrayAdapter<Dvd> adapterForArray = new ArrayAdapter<Dvd>(this,
                        R.layout.list_item_dvd, dvds);

                listDvdFragment.setListAdapter(adapterForArray);
                listDvdFragment.setOnDvdItemClickListener(new ListDvdFragment.OnDvdItemClickListener() {
                    @Override
                    public void onDvdItemClicked(int position) {
                        Dvd dvd = dvds.get(position);

                        modelDvdFragment.setDvd(dvd);
                    }
                });
                ////////////////////////////////////////////////////////////////////////////////////////////
                */

                return true;
            case R.id.menu_gridview:
                //TODO: implement menu_gridview
                Toast.makeText(this, "GridView", Toast.LENGTH_SHORT).show();

                //////////////////////////////////////////////////////
                listDvdFragment = (ListDvdFragment) getSupportFragmentManager().findFragmentById(R.id.listDvd);
                int firstVisiblePosition = listDvdFragment.getListView().getFirstVisiblePosition();
                Toast.makeText(this, "firstVisiblePosition: " + firstVisiblePosition, Toast.LENGTH_SHORT).show();
                //////////////////////////////////////////////////////

                //TODO: 2020_05_08 USE ModelDvdFragment (it has image resources).

                setContentView(R.layout.activity_grid_view_dvd);

                // Initialize the GridView (otherwise blank screen)
                GridView gridView = (GridView) findViewById(R.id.gridview);
                /*
                String[] dataSourceDvd = loadCSV();
                ArrayDataSourceToGridViewCellAdapter adapter = new ArrayDataSourceToGridViewCellAdapter(this, dataSourceDvd);
                */
                final DvdListToGridViewCellAdapter adapterForArrayList = new DvdListToGridViewCellAdapter(this, dvds);
                gridView.setAdapter(adapterForArrayList);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParent.onOptionItemSelected()'s gridView.OnItemClickListener.onItemClick(AdapterView<?>, View, int, long)");
                        //TODO: toggle the favorite-star's activeness image.

                        Dvd dvd = dvds.get(position);
                        dvd.toggleFavorite();

                        adapterForArrayList.notifyDataSetChanged();
                    }
                });

                ////////////////////////////////////////////
                gridView.setSelection(firstVisiblePosition);
                ////////////////////////////////////////////

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

}