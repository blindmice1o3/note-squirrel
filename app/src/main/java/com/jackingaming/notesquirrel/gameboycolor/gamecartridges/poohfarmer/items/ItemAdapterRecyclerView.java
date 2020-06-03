package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.R;

import java.util.List;

public class ItemAdapterRecyclerView extends RecyclerView.Adapter<ItemAdapterRecyclerView.ItemViewHolder> {

    ///////////////////////////////////////////////////////////////////////////////////////
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private ItemClickListener itemClickListener;
    // register as an observer... allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    private List<Item> dataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;

        public ItemViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageview_item);

            textView = view.findViewById(R.id.textview_item);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdapterRecyclerView(List<Item> dataSet) {
        this.dataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        // do stuff.

        ItemViewHolder vh = new ItemViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText( dataSet.get(position).getId().name() );
        holder.imageView.setImageBitmap( dataSet.get(position).getImage() );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}