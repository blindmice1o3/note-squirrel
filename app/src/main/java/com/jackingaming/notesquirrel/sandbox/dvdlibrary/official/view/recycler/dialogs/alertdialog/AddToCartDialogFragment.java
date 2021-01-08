package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

public class AddToCartDialogFragment extends DialogFragment {
    public static final String TAG = "AddToCartDialog";

    public interface AddToCartAlertDialogListener {
        void onAddToCartAlertDialogPositiveClick(Dvd dvd);
        void onAddToCartAlertDialogNegativeClick();
    }
    private AddToCartAlertDialogListener listener;

    private Dvd dvd;

    public AddToCartDialogFragment(Dvd dvd) {
        this.dvd = dvd;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddToCartAlertDialogListener) {
            listener = (AddToCartAlertDialogListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement AddToCartDialogFragment.AddToCartAlertDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String message = dvd.getTitle() + " | available:" + dvd.isAvailable();
        Dialog addToCartDialog = new AlertDialog.Builder(requireContext())
                .setTitle("Add to cart?")
                .setMessage(message)
                .setPositiveButton("Add to cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAddToCartAlertDialogPositiveClick(dvd);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAddToCartAlertDialogNegativeClick();
                    }
                })
                .create();
        return addToCartDialog;
    }
}
