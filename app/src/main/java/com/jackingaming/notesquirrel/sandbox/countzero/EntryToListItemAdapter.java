package com.jackingaming.notesquirrel.sandbox.countzero;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;

import java.util.List;

public class EntryToListItemAdapter extends BaseAdapter
        implements ListAdapter {

    private Context context;
    private List<Entry> dates;

    public EntryToListItemAdapter(Context context, List<Entry> dates) {
        this.context = context;
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_entry, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toggle isActive, update associated ImageView.
                boolean isActive = !dates.get(position).isActive();
                ////////////////////////////////////////
                dates.get(position).setActive(isActive);
                ////////////////////////////////////////

                ImageView imageView = v.findViewById(R.id.imageview_count_zero_entry);
                int iconId = R.drawable.btn_radio_on_disabled_focused_holo_light;
                if (isActive) {
                    iconId = R.drawable.btn_radio_on_focused_holo_dark;
                }
                Drawable icon = context.getResources().getDrawable(iconId);
                /////////////////////////////////
                imageView.setImageDrawable(icon);
                /////////////////////////////////
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.textview_count_zero_entry);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview_count_zero_entry);

        String date = dates.get(position).getDate();
        ///////////////////////
        textView.setText(date);
        ///////////////////////

        boolean isActive = dates.get(position).isActive();
        int iconId = R.drawable.btn_radio_on_disabled_focused_holo_light;
        if (isActive) {
            iconId = R.drawable.btn_radio_on_focused_holo_dark;
        }
        Drawable icon = context.getResources().getDrawable(iconId);
        /////////////////////////////////
        imageView.setImageDrawable(icon);
        /////////////////////////////////

        return view;
    }

}