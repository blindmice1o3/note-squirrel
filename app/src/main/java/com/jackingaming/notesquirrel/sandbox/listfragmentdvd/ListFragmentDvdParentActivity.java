package com.jackingaming.notesquirrel.sandbox.listfragmentdvd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

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

}