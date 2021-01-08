package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.RecyclerViewActivity;

public class AddNewDvdToDBCommand implements Command {

    private RecyclerViewActivity recyclerViewActivity;

    public AddNewDvdToDBCommand(RecyclerViewActivity recyclerViewActivity) {
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    public void execute() {
        Log.d(MainActivity.DEBUG_TAG, "AddNewDvdToDBCommand.execute()");

        recyclerViewActivity.performAddNewDvdToDB();
    }

    @NonNull
    @Override
    public String toString() {
        return "Add New DVD To DB";
    }
}
