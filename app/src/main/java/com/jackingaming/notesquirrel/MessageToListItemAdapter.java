package com.jackingaming.notesquirrel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageToListItemAdapter extends BaseAdapter
        implements ListAdapter {

    private Context context;
    private List<Message> messages;

    public MessageToListItemAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //the View-component that we're inflating the xml into (e.g. a list item's GUI)
        //R.layout.list_item_message2 uses RelativeLayout (instead of nested LinearLayout ViewGroups)
        View view = inflater.inflate(R.layout.list_item_message2, null);

        Message message = messages.get(position);

        String title = message.getTitle();
        String sender = message.getSender();

        //the TextView components of the list item's GUI
        TextView titleView = (TextView) view.findViewById(R.id.list_message_title);
        TextView senderView = (TextView) view.findViewById(R.id.list_message_sender);
        //set text on the TextView components dynamically (not hard-coded/statically)
        titleView.setText(title);
        senderView.setText(sender);

        //the ImageView component of the list item's GUI
        ImageView imageView = (ImageView) view.findViewById(R.id.list_message_icon);
        boolean isRead = message.isRead();
        int iconId = R.drawable.btn_radio_on_focused_holo_dark;
        if (isRead) {
            iconId = R.drawable.btn_radio_on_disabled_focused_holo_light;
        }
        Drawable icon = context.getResources().getDrawable(iconId);
        //set image's source on the ImageView component base on state of Message instances
        imageView.setImageDrawable(icon);

        return view;
    }

}