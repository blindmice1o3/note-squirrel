package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

public class RemoveFromCartConfirmationDialogFragment extends DialogFragment {
    public static final String TAG = "RemoveFromCartConfirmationDialog";

    public interface RemoveFromCartConfirmationDialogTouchListener {
        void onPositiveButtonClick(Dvd dvd);
        void onNegativeButtonClick();
    }
    private RemoveFromCartConfirmationDialogTouchListener listener;
    public void setListener(RemoveFromCartConfirmationDialogTouchListener listener) {
        this.listener = listener;
    }

    private Dvd dvd;

    public RemoveFromCartConfirmationDialogFragment(Dvd dvd) {
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
                        listener.onPositiveButtonClick(dvd);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onNegativeButtonClick();
                    }
                })
                .create();
        return removeFromCartConfirmationDialog;
    }
}
