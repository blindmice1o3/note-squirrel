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

public class AdapterBitmapRecyclerViewMain extends RecyclerView.Adapter<AdapterBitmapRecyclerViewMain.FrameViewHolder> {
    public enum BoundBy { WIDTH, HEIGHT; }
    private BoundBy boundBy;

    public interface ItemClickListener {
        void onItemClick(View view, int position, AdapterBitmapRecyclerViewMain adapter);
    }
    private ItemClickListener itemClickListener;

    private Context context;
    private List<Bitmap> dataSource;

    class FrameViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView imageView;

        public FrameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition(), AdapterBitmapRecyclerViewMain.this);
            }
        }
    }

    public AdapterBitmapRecyclerViewMain(Context context, List<Bitmap> dataSource, ItemClickListener itemClickListener) {
        this.context = context;
        this.dataSource = dataSource;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageview_recycler_item_boundby_width, parent, false);
        FrameViewHolder frameViewHolder = new FrameViewHolder(view);
        return frameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder holder, int position) {
        Bitmap imageUserSelected = dataSource.get(position);
        holder.imageView.setImageBitmap(imageUserSelected);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}