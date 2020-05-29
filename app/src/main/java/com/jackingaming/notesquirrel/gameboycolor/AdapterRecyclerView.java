package com.jackingaming.notesquirrel.gameboycolor;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.R;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ItemViewHolder> {

    private String[] dataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView textView;

        public ItemViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerView(String[] dataSet) {
        this.dataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adapter_recyclerview, parent, false);

        // do stuff.

        ItemViewHolder vh = new ItemViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText( dataSet[position] );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.length;
    }

}