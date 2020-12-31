package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jackingaming.notesquirrel.R;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "MyBottomSheetDialog";

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
        listViewBottomSheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] menuItems = getResources().getStringArray(R.array.bottom_sheet_menu_items);
                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), menuItems[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(), menuItems[position], Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), menuItems[position], Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getContext(),
                                "RecyclerViewActivity.onCreate() setting listviewBottomSheet's OnItemClickListener's switch's default block",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
