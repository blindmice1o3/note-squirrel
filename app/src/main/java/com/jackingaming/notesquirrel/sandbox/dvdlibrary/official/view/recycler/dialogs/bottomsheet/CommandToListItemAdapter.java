package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.Command;

import java.util.List;

public class CommandToListItemAdapter extends BaseAdapter
        implements ListAdapter {

    private Context context;
    private MyBottomSheetDialogFragment myBottomSheetDialogFragment;
    private List<Command> commands;

    public CommandToListItemAdapter(Context context, MyBottomSheetDialogFragment myBottomSheetDialogFragment,
                                    List<Command> commands) {
        this.context = context;
        this.myBottomSheetDialogFragment = myBottomSheetDialogFragment;
        this.commands = commands;
    }

    @Override
    public int getCount() {
        return commands.size();
    }

    @Override
    public Object getItem(int position) {
        return commands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_item_bottom_sheet, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_listitem_bottom_sheet);
        final Command command = commands.get(position);
        textView.setText(command.toString());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.execute();
                myBottomSheetDialogFragment.dismiss();
            }
        });

        return view;
    }
}
