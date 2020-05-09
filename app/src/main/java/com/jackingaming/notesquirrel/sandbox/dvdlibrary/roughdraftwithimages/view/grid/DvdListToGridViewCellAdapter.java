package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.grid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.DvdList;

public class DvdListToGridViewCellAdapter extends BaseAdapter {

    private Context context;
    private DvdList dvdList;

    public DvdListToGridViewCellAdapter(Context context, DvdList dvdList) {
        this.context = context;
        this.dvdList = dvdList;
    }

    @Override
    public int getCount() {
        return dvdList.size();
    }

    @Override
    public Object getItem(int position) {
        return dvdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: use ModelDvdFragment's layout.
        //View view = inflater.inflate(R.layout.model_dvd_fragment, container, false);

        return null;
    }

}