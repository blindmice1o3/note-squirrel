package com.jackingaming.notesquirrel.sandbox.listfragmentdvd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class ListFragmentDvdParentActivity extends AppCompatActivity {

    private DvdList dvds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fragment_dvd_parent);

        Log.d(MainActivity.DEBUG_TAG, "ListFragmentDvdParentActivity.onCreate(Bundle)");

        dvds = new DvdList(getResources());

        //////////////////////////////////////////////////
        ListDvdFragment listDvdFragment = (ListDvdFragment) getSupportFragmentManager().findFragmentById(R.id.listDvd);

        ArrayAdapter<Dvd> adapter = new ArrayAdapter<Dvd>(this,
                R.layout.list_item_dvd, dvds);

        listDvdFragment.setListAdapter(adapter);
        //////////////////////////////////////////////////

        //////////////////////////////////////////////////
        final ModelDvdFragment modelDvdFragment = (ModelDvdFragment) getSupportFragmentManager().findFragmentById(R.id.modelDvd);

        modelDvdFragment.setDvd(dvds.get(0));
        //////////////////////////////////////////////////

        //SUBSCRIBE TO ListDvdFragment's onListItemClick() events.
        listDvdFragment.setOnDvdItemClickListener(new ListDvdFragment.OnDvdItemClickListener() {
            @Override
            public void onDvdItemClicked(int position) {
                Dvd dvd = dvds.get(position);

                modelDvdFragment.setDvd(dvd);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_fragment_dvd_parent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_listview:
                //TODO: implement menu_listview
                Toast.makeText(this, "ListView", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_gridview:
                //TODO: implement menu_gridview
                Toast.makeText(this, "GridView", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "ListFragmentDvdParentActivity.onOptionsItemSelected(MenuItem) switch's default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }
}