package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.RecyclerViewActivity;

public class SearchByAvailableCommand
        implements Command {

    private RecyclerViewActivity recyclerViewActivity;

    public SearchByAvailableCommand(RecyclerViewActivity recyclerViewActivity) {
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    public void execute() {
        Log.d(MainActivity.DEBUG_TAG, "SearchByAvailableCommand.execute()");

        String path = "/foo?available=false";
        recyclerViewActivity.performGetTask(path);
    }

    @NonNull
    @Override
    public String toString() {
        return "Search By Available";
    }
}
