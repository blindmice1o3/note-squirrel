package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.Command;

import java.util.List;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "MyBottomSheetDialog";

    private List<Command> commands;

    public MyBottomSheetDialogFragment(List<Command> commands) {
        this.commands = commands;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listViewBottomSheet = (ListView) view.findViewById(R.id.listview_bottom_sheet);
        listViewBottomSheet.setAdapter(new CommandToListItemAdapter(getContext(),
                this, commands));
    }
}
