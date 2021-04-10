package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.jackingaming.notesquirrel.R;

import java.util.Date;

public class DateOrTimeFragment extends DialogFragment {
    public static final String EXTRA_DATE_OR_TIME = "com.jackingaming.notesquirrel.dateOrTime";
    private static final String DIALOG_DATE = "date";
    public static final int REQUEST_DATE = 0;
    private static final String DIALOG_TIME = "time";
    public static final int REQUEST_TIME = 1;

    private Date date;

    public static DateOrTimeFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE_OR_TIME, date);

        DateOrTimeFragment fragment = new DateOrTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        date = (Date) getArguments().getSerializable(EXTRA_DATE_OR_TIME);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date_or_time, null);

        Button dateButton = v.findViewById(R.id.crime_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                DatePickerFragment dialog = DatePickerFragment.newInstance(date);
                dialog.setTargetFragment(getTargetFragment(), REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
                getDialog().dismiss();
            }
        });

        Button timeButton = v.findViewById(R.id.crime_time);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                TimePickerFragment dialog = TimePickerFragment.newInstance(date);
                dialog.setTargetFragment(getTargetFragment(), REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
                getDialog().dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }
}