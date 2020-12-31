package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.RecyclerViewActivity;

import java.util.List;

public class ViewContentOfCartCommand
        implements Command {

    private RecyclerViewActivity recyclerViewActivity;

    public ViewContentOfCartCommand(RecyclerViewActivity recyclerViewActivity) {
        this.recyclerViewActivity = recyclerViewActivity;
    }

    @Override
    public void execute() {
        Log.d(MainActivity.DEBUG_TAG, "ViewContentOfCartCommand.execute()");

        List<Dvd> cart = recyclerViewActivity.getCart();
        Toast.makeText(recyclerViewActivity.getBaseContext(),
                "number of dvd in cart: " + cart.size(),
                Toast.LENGTH_SHORT).show();

        //TODO: launch new activity? dialog with list?
        //TODO: develop checkout-cart feature.
    }

    @NonNull
    @Override
    public String toString() {
        return "View Content Of Cart";
    }
}
