package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.util.List;

public class AdapterStringRecyclerView extends RecyclerView.Adapter<AdapterStringRecyclerView.StringViewHolder> {

    private List<String> dataSource;

    public class StringViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView textView;

        public StringViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView;
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(MainActivity.DEBUG_TAG, "StringViewHolder's onClick(View)");
        }
    }

    public AdapterStringRecyclerView(List<String> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adapter_recyclerview, parent, false);
        StringViewHolder viewHolder = new StringViewHolder(textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        holder.textView.setText(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
