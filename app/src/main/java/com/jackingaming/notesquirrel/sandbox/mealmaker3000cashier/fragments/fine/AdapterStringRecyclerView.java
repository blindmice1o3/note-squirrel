package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.R;

import java.util.List;

public class AdapterStringRecyclerView extends RecyclerView.Adapter<AdapterStringRecyclerView.StringViewHolder> {

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    private ItemClickListener itemClickListener;

    private List<String> dataSource;

    class StringViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView textView;

        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(this);
        }

        public void bindData(String viewModel) {
            textView.setText(viewModel);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public AdapterStringRecyclerView(List<String> dataSource, ItemClickListener itemClickListener) {
        this.dataSource = dataSource;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = (position != 1) ? (R.layout.textview_adapter_recyclerview) : (R.layout.textview_bold_adapter_recyclerview);
        return viewType;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        StringViewHolder viewHolder = new StringViewHolder(textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        holder.bindData(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
