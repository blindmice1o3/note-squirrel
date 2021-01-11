package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.RecyclerViewActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.fragments.SearchByTitleFragment;

public class SearchByTitleCommand
        implements Command {

    private RecyclerViewActivity recyclerViewActivity;

    public SearchByTitleCommand(RecyclerViewActivity recyclerViewActivity) {
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    public void execute() {
        Log.d(MainActivity.DEBUG_TAG, "SearchByTitleCommand.execute()");

        //TODO: fragment transaction
        SearchByTitleFragment searchByTitleFragment = new SearchByTitleFragment();
        recyclerViewActivity.performFragmentTransactionReplace(
                R.id.framelayout_placeholder_recyclerview,
                searchByTitleFragment,
                SearchByTitleFragment.TAG);
//        String path = "/foo?searchText=guy";
//        recyclerViewActivity.performGetTask(path);
    }

    @NonNull
    @Override
    public String toString() {
        return "Search By Title";
    }
}
