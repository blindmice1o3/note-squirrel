package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.coarse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;

import java.util.List;

public class CoarseGrainControlsListAdapter extends BaseAdapter
        implements ListAdapter {

    private Context context;
    private List<String> menuItems;

    public CoarseGrainControlsListAdapter(Context context, List<String> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
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
        textView.setText(menuItems.get(position));

        final int positionFinal = position;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, menuItems.get(positionFinal), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
