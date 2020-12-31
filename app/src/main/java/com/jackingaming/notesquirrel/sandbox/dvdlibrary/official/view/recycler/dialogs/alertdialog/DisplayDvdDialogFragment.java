package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

public class DisplayDvdDialogFragment extends DialogFragment {
    public static final String TAG = "DisplayDvdDialog";

    private Dvd dvd;

    public DisplayDvdDialogFragment(Dvd dvd) {
        this.dvd = dvd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String message = dvd.getTitle() + " | available:" + dvd.isAvailable();
        Dialog displayDvdDialog = new AlertDialog.Builder(requireContext())
                .setTitle("Add to cart?")
                .setMessage(message)
                .setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),
                                "\"Add to cart\" feature is still in development",
                                Toast.LENGTH_SHORT).show();

                        // TODO: add to [cart]
                    }
                })
                .create();
        return displayDvdDialog;
    }
}
