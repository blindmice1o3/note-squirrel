package com.jackingaming.notesquirrel.sandbox.gridviewdvd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArrayDataSourceToGridViewCellAdapter extends BaseAdapter {

    private Context context;
    private String[] dvds;

    public ArrayDataSourceToGridViewCellAdapter(Context context, String[] dvds) {
        this.context = context;
        this.dvds = dvds;
    }

    @Override
    public int getCount() {
        return dvds.length;
    }

    @Override
    public Object getItem(int position) {
        return dvds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(dvds[position]);
        return textView;
    }

}