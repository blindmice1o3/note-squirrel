package com.jackingaming.notesquirrel.sandbox.autopilotoff.photogallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.photogallery.models.GalleryItem;

import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";
    private static final int DEFAULT_PAGE = 1;

    GridView gridView;
    ArrayList<GalleryItem> items = new ArrayList<GalleryItem>();
    ThumbnailDownloader<ImageView> thumbnailThread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        // Retrieve XML from Flickr using an [AsyncTask] background thread and
        // parse the XML into an array of [GalleryItem]s (which contains a
        // URL for a thumbnail-size photo).
        new FetchItemsTask().execute(DEFAULT_PAGE);

        /*
         "[AsyncTask] is the easiest way to get a background thread, but it is
         fundamentally ill-suited for repetitive and long-running work."

         "It is intended for work that is short lived and not repeated too often."

         "Starting with Android 3.2, [AsyncTask] does not create a thread for each
         instance of [AsyncTask]. Instead, it uses something called an [Executor] to
         run background work for all [AsyncTask]s on a single background thread.
         That means that each [AsyncTask] will run one after the other. A long-running
         [AsyncTask] will poison the well, preventing other [AsyncTask]s from getting
         any CPU time."
         */

        // Create a [dedicated] background thread (as opposed to using an [AsyncTask]
        // background thread) that uses a [message queue] and a [Looper] to download
        // photos [on-demand/as-needed] (triggered in the adapter's [getView(...)]).
        //
        // Q. How will the [dedicated] thread work with the adapter to display downloaded
        // photos when it cannot directly access the main thread?
        // A. The [Handler] passed into [ThumbnailDownloader]'s constructor is associated
        // with the main/UI thread's [Looper] (the object that manages a thread's [message queue]).
        // The UI update code will be run on the main thread.
        thumbnailThread = new ThumbnailDownloader<ImageView>(new Handler());
        // The listener does UI work with the returning [Bitmap]s.
        thumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            @Override
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                // It is necessary to guard the call to [setImageBitmap(Bitmap)] to
                // ensure that you are not setting the image on a stale [ImageView].
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        thumbnailThread.start();
        // Safety note: [getLooper()] is called after calling [start()] on your
        // [ThumbnailDownloader]. This is a way to ensure that the thread's guts
        // are ready before proceeding.
        thumbnailThread.getLooper();
        Log.i(TAG, "Background thread started");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // If the user rotates the screen, [ThumbnailDownloader] may be hanging on to
        // invalid [ImageView]s. Bad things will happen if those [ImageView]s get pressed.
        // Clean all the requests out of your queue.
        thumbnailThread.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Safety note: The call to [quit()] is critical. If you do not quit your
        // [HandlerThread]s, they will never die.
        thumbnailThread.quit();
        Log.i(TAG, "Background thread destroyed");
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


    // Applied ViewHolder pattern (reduces the number of calls to [findViewById()])
    // to custom ArrayAdapter (displays photo downloaded from the [url] of a [GalleryItem])
    // (https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#using-a-custom-arrayadapter)
    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {
        // View lookup cache
        private class ViewHolder {
            ImageView imageView;
        }

        public GalleryItemAdapter(ArrayList<GalleryItem> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if an existing view is bring reused, otherwise inflate the view
            ViewHolder viewHolder;  // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
                Log.i(TAG, "getView(int, View, ViewGroup) for position: " + position + " INFLATED");

                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.gallery_item_imageView);

                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
                Log.i(TAG, "getView(int, View, ViewGroup) for position: " + position + " recycled");
            }

            // Populate the template view (via the viewHolder object) with a [placeholder] image.
            viewHolder.imageView.setImageResource(R.drawable.rsz_big_trouble_in_little_china);

            // Get the data item for this position
            GalleryItem item = getItem(position);
            // Create a message using the [Token] and [URL], and put that message on the
            // [ThumbnailDownloader]'s message queue.
            thumbnailThread.queueThumbnail(viewHolder.imageView, item.getUrl());

            return convertView;
        }
    }
}