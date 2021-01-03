package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

public class RemoveFromCartDialogFragment extends DialogFragment {
    public static final String TAG = "RemoveFromCartDialog";

    public interface RemoveFromCartAlertDialogListener {
        void onRemoveFromCartAlertDialogPositiveClick(Dvd dvd);
        void onRemoveFromCartAlertDialogNegativeClick();
    }
    private RemoveFromCartAlertDialogListener listener;
    public void setListener(RemoveFromCartAlertDialogListener listener) {
        this.listener = listener;
    }

    private Dvd dvd;

    public RemoveFromCartDialogFragment(Dvd dvd) {
        this.dvd = dvd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog removeFromCartConfirmationDialog = new AlertDialog.Builder(requireContext())
                .setMessage("Remove " + dvd.getTitle() + " from cart?")
                .setPositiveButton("Remove from cart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onRemoveFromCartAlertDialogPositiveClick(dvd);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onRemoveFromCartAlertDialogNegativeClick();
                    }
                })
                .create();
        return removeFromCartConfirmationDialog;
    }
}
