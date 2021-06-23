package com.jackingaming.notesquirrel.sandbox.autopilotoff.photogallery;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Photo;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.photogallery.models.GalleryItem;

import java.io.IOException;
import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    GridView gridView;
    ArrayList<GalleryItem> items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        items = new ArrayList<GalleryItem>();

        int defaultPage = 1;
        new FetchItemsTask().execute(defaultPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        gridView = (GridView) v.findViewById(R.id.gridView);
        gridView.setOnScrollListener(new EndlessScrollListener() {
            // Triggered only when new data needs to be appended to the list
            // Add whatever code is needed to append new items to your AdapterView
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (gridView.getAdapter() != null) {
                    loadNextDataFromApi(page);  // or loadNextDataFromApi(totalItemsCount);
                    return true; // ONLY if more data is actually being loaded; false otherwise.
                } else {
                    return false;
                }
            }
        });

        setupAdapter();

        return v;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // TODO: start a different (but similar) FetchItemsTask on its own thread.
        Log.i(TAG, ".loadNextDataFromApi(int) called by gridView's OnScrollListener (an EndlessScrollListener).");
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyDataSetChanged()`
        new FetchItemsTask().execute(offset);
    }

    void setupAdapter() {
        if (getActivity() == null || gridView == null) {
            Log.i(TAG, "setupAdapter(): either getActivity() or gridView is null!!!!!");
            return;
        }

        gridView.setAdapter(new GalleryItemAdapter(items));
//        gridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
//                android.R.layout.simple_gallery_item, items));
    }

    private class FetchItemsTask extends AsyncTask<Integer, Void, ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Integer... params) {
            int page = params[0];
            return new FlickrFetchr().fetchItems(page);
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> items) {
            PhotoGalleryFragment.this.items.addAll(items);
            ((GalleryItemAdapter)gridView.getAdapter()).notifyDataSetChanged();
        }
    }

    private class GalleryItemAdapter extends BaseAdapter {

        private ArrayList<GalleryItem> items;

        public GalleryItemAdapter(ArrayList<GalleryItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_gallery_item, null);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(items.get(position).toString());
            Log.i(TAG, "getView(int, View, ViewGroup) for position: " + position);

            return view;
        }
    }
}