package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Crime;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz.QuizFragment;

import java.text.DateFormat;

public class CrimeFragment extends Fragment {
    public static final String TAG = "CrimeFragment";

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;

    public CrimeFragment() {
        // Required empty public constructor
        Log.i(TAG, "constructor()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        setHasOptionsMenu(true);

        crime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        titleField = view.findViewById(R.id.crime_title);
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Intentionally blank.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Intentionally blank.
            }
        });

        dateButton = view.findViewById(R.id.crime_date);
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(crime.getDate());
        dateButton.setText(formattedDate);
        dateButton.setEnabled(false);

        solvedCheckBox = view.findViewById(R.id.crime_solved);
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated(View, Bundle)");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated(Bundle)");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored(Bundle)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu(Menu, MenuInflater)");
        inflater.inflate(R.menu.options_menu_fragment_crime, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu(Menu)");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Log.i(TAG, getClass().getSimpleName() +
                        "onOptionsItemSelected(MenuItem) R.id.menu_item_new_crime");
                return true;
            case R.id.menu_item_geo_quiz:
                Log.i(TAG, getClass().getSimpleName() +
                                "onOptionsItemSelected(MenuItem) R.id.menu_item_geo_quiz");
                FragmentManager fm = getFragmentManager();
                Fragment fragmentInFragmentContainer = fm.findFragmentById(R.id.fragmentContainer);
                if ( !(fragmentInFragmentContainer instanceof QuizFragment) ) {
                    Fragment quizFragment = fm.findFragmentByTag(QuizFragment.TAG);
                    if (quizFragment == null) {
                        quizFragment = new QuizFragment();
                    }
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainer, quizFragment, QuizFragment.TAG)
                            .addToBackStack(null)
                            .commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState(Bundle)");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");
    }
}