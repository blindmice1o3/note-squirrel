package com.jackingaming.notesquirrel.sandbox.autopilotoff.remotecontrol;


import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jackingaming.notesquirrel.R;

public class RemoteControlFragment extends Fragment {

    private TextView selectedTextView;
    private TextView workingTextView;

    public RemoteControlFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remote_control, container, false);

        selectedTextView = (TextView) v.findViewById(R.id.fragment_remote_control_selectedTextView);
        workingTextView = (TextView) v.findViewById(R.id.fragment_remote_control_workingTextView);

        View.OnClickListener numberButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                String working = workingTextView.getText().toString();
                String text = textView.getText().toString();
                if (working.equals("0")) {
                    workingTextView.setText(text);
                } else {
                    workingTextView.setText(working + text);
                }
            }
        };

        TableLayout tableLayout = (TableLayout) v.findViewById(R.id.fragment_remote_control_tableLayout);
        int number = 1;
        // Start at index 2 to skip the two text views.
        for (int i = 2; i < tableLayout.getChildCount() - 1; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                Button button = (Button) row.getChildAt(j);
                button.setText("" + number);
                button.setOnClickListener(numberButtonListener);
                number++;
            }
        }

        TableRow bottomRow = (TableRow) tableLayout.getChildAt(tableLayout.getChildCount() - 1);

        Button deleteButton = (Button) bottomRow.getChildAt(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            deleteButton.setTextAppearance(R.style.RemoteButtonBolded);
        }
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workingTextView.setText("0");
            }
        });

        Button zeroButton = (Button) bottomRow.getChildAt(1);
        zeroButton.setText("0");
        zeroButton.setOnClickListener(numberButtonListener);

        Button enterButton = (Button) bottomRow.getChildAt(2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            enterButton.setTextAppearance(R.style.RemoteButtonBolded);
        }
        enterButton.setText("Enter");
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence working = workingTextView.getText();
                if (working.length() > 0) {
                    selectedTextView.setText(working);
                }
                workingTextView.setText("0");
            }
        });

        return v;
    }

}