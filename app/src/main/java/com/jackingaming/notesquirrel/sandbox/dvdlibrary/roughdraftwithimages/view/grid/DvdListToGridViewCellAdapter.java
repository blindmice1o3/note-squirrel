package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.DvdList;

public class DvdListToGridViewCellAdapter extends BaseAdapter {

    private Context context;
    private DvdList dvdList;

    public DvdListToGridViewCellAdapter(Context context, DvdList dvdList) {
        Log.d(MainActivity.DEBUG_TAG, "DvdListToGridViewCellAdapter(Context, DvdList) constructor");

        this.context = context;
        this.dvdList = dvdList;
    }

    @Override
    public int getCount() {
        Log.d(MainActivity.DEBUG_TAG, "DvdListToGridViewCellAdapter.getCount()");

        return dvdList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(MainActivity.DEBUG_TAG, "DvdListToGridViewCellAdapter.getItem(int)");

        return dvdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(MainActivity.DEBUG_TAG, "DvdListToGridViewCellAdapter.getItemId(int)");

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(MainActivity.DEBUG_TAG, "DvdListToGridViewCellAdapter.getView(int, View, ViewGroup)");
        //TODO: use ModelDvdFragment's layout.

        final Dvd dvd = dvdList.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.model_dvd_fragment, null);
        }

        final ImageView imageViewDvdCoverArt = (ImageView) convertView.findViewById(R.id.imageview_dvd_cover_art);
        final TextView textViewDvdTitle = (TextView) convertView.findViewById(R.id.textview_dvd_title);
        final ImageView imageViewFavorite = (ImageView) convertView.findViewById(R.id.imageview_favorite);

        imageViewDvdCoverArt.setImageBitmap(dvd.getImage());
        textViewDvdTitle.setText(dvd.getTitle());
        imageViewFavorite.setImageResource(
            dvd.getIsFavorite() ? R.drawable.btn_star_on_normal_holo_light : R.drawable.btn_star_off_normal_holo_light
        );

        return convertView;
    }

}