package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.RecyclerViewActivity;

public class ViewContentOfCartCommand
        implements Command {

    private RecyclerViewActivity recyclerViewActivity;

    public ViewContentOfCartCommand(RecyclerViewActivity recyclerViewActivity) {
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    public void execute() {
        Log.d(MainActivity.DEBUG_TAG, "ViewContentOfCartCommand.execute()");

        recyclerViewActivity.onViewCartButtonClick();
        //TODO: launch new activity? dialog with list?
        //TODO: develop checkout-cart feature.
    }

    @NonNull
    @Override
    public String toString() {
        return "View Content Of Cart";
    }
}
