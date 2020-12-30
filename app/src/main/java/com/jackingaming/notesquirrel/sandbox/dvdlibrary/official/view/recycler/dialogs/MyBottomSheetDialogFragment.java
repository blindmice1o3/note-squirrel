package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
}
