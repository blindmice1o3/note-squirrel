package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.coarse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;

import java.util.List;

public class StringToTextViewListAdapter extends BaseAdapter
        implements ListAdapter {

    private Context context;
    private List<String> listItems;

    public StringToTextViewListAdapter(Context context, List<String> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.array_adapter_my_list_fragment, null);
        TextView textView = view.findViewById(R.id.text1);
        textView.setText(listItems.get(position));

        return view;
    }
}
