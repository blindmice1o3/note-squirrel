package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Crime;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.CrimeLab;

import java.text.DateFormat;
import java.util.ArrayList;

public class CrimeListFragment extends ListFragment {
    private static final String TAG = "CrimeListFragment";

    private ArrayList<Crime> crimes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(crimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getTitle() + " was clicked");

        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu(Menu, MenuInflater)");
        inflater.inflate(R.menu.fragment_crime_list, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu(Menu)");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_new_crime");
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }

            //Configure the view for this Crime
            Crime c = getItem(position);

            TextView titleTextView = convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            TextView dateTextView = convertView.findViewById(R.id.crime_list_item_dateTextView);
            String formattedDateTime = DateFormat
                    .getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT)
                    .format(c.getDate());
            dateTextView.setText(formattedDateTime);

            CheckBox solvedCheckBox = convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}