package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.R;

import java.util.List;

public class AdapterBitmapRecyclerView extends RecyclerView.Adapter<AdapterBitmapRecyclerView.BitmapViewHolder> {
    public enum BoundBy { WIDTH, HEIGHT; }
    private BoundBy boundBy;

    public interface ItemClickListener {
        void onItemClick(View view, int position, AdapterBitmapRecyclerView adapter);
    }
    private ItemClickListener itemClickListener;

    private Context context;
    private List<Bitmap> dataSource;

    class BitmapViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView imageView;

        public BitmapViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition(), AdapterBitmapRecyclerView.this);
            }
        }
    }

    public AdapterBitmapRecyclerView(Context context, List<Bitmap> dataSource, ItemClickListener itemClickListener, BoundBy boundBy) {
        this.context = context;
        this.dataSource = dataSource;
        this.itemClickListener = itemClickListener;
        this.boundBy = boundBy;
    }

    @NonNull
    @Override
    public BitmapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutBasedOnBoundBy = (boundBy == BoundBy.WIDTH) ? (R.layout.imageview_recycler_item_boundby_width) : (R.layout.imageview_recycler_item_boundby_height);

        View view = LayoutInflater.from(context).inflate(layoutBasedOnBoundBy, parent, false);
        BitmapViewHolder bitmapViewHolder = new BitmapViewHolder(view);
        return bitmapViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BitmapViewHolder holder, int position) {
        holder.imageView.setImageBitmap(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
