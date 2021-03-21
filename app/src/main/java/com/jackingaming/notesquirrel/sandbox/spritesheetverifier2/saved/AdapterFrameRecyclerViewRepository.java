package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.saved;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.Frame;

import java.util.List;

public class AdapterFrameRecyclerViewRepository extends RecyclerView.Adapter<AdapterFrameRecyclerViewRepository.FrameViewHolder> {
    public interface ItemClickListener {
        void onItemClick(View view, int position, AdapterFrameRecyclerViewRepository adapter);
    }
    private ItemClickListener itemClickListener;

    private Context context;
    private List<Frame> savedList;

    class FrameViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ImageView imageView;
        public TextView textViewIndexColumn;
        public TextView textViewIndexRow;

        public FrameViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.imageview_recycler_item_repository_for_saved_list);
            textViewIndexColumn = itemView.findViewById(R.id.textview_index_column_recycler_item_repository_for_saved_list);
            textViewIndexRow = itemView.findViewById(R.id.textview_index_row_recycler_item_repository_for_saved_list);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition(), AdapterFrameRecyclerViewRepository.this);
            }
        }
    }

    public AdapterFrameRecyclerViewRepository(Context context, List<Frame> savedList, AdapterFrameRecyclerViewRepository.ItemClickListener itemClickListener) {
        this.context = context;
        this.savedList = savedList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_repository_for_saved_list, parent, false);
        FrameViewHolder frameViewHolder = new FrameViewHolder(view);
        return frameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder holder, int position) {
        Frame frame = savedList.get(position);

        Bitmap imageUserSelected = frame.getImageUserSelected();
        int indexColumn = frame.getIndexColumn();
        int indexRow = frame.getIndexRow();

        holder.imageView.setImageBitmap(imageUserSelected);
        holder.textViewIndexColumn.setText(Integer.toString(indexColumn));
        holder.textViewIndexRow.setText(Integer.toString(indexRow));
    }

    @Override
    public int getItemCount() {
        return savedList.size();
    }
}