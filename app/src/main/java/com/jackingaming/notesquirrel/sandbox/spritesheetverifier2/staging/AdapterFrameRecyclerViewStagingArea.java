package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.staging;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.Frame;

import java.util.List;

public class AdapterFrameRecyclerViewStagingArea extends RecyclerView.Adapter<AdapterFrameRecyclerViewStagingArea.FrameViewHolder> {
    public interface ItemClickListener {
        void onItemClick(View view, int position, AdapterFrameRecyclerViewStagingArea adapter);
    }
    private ItemClickListener itemClickListener;

    private Context context;
    private List<Frame> framesSelectedByUser;

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
                itemClickListener.onItemClick(v, getAdapterPosition(), AdapterFrameRecyclerViewStagingArea.this);
            }
        }
    }

    public AdapterFrameRecyclerViewStagingArea(Context context, List<Frame> framesSelectedByUser, AdapterFrameRecyclerViewStagingArea.ItemClickListener itemClickListener) {
        this.context = context;
        this.framesSelectedByUser = framesSelectedByUser;
        this.itemClickListener = itemClickListener;
        Log.d(MainActivity.DEBUG_TAG, "AdapterFrameRecyclerViewStagingArea() constructor framesSelectedByUser.size(): " + framesSelectedByUser.size());
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageview_recycler_item_boundby_height, parent, false);
        FrameViewHolder frameViewHolder = new FrameViewHolder(view);
        return frameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder holder, int position) {
        Frame frame = framesSelectedByUser.get(position);
        Bitmap imageUserSelected = frame.getImageUserSelected();
        holder.imageView.setImageBitmap(imageUserSelected);
    }

    @Override
    public int getItemCount() {
        return framesSelectedByUser.size();
    }
}
