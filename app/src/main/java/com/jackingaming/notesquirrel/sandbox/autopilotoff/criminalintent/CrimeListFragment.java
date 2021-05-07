package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Crime;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.CrimeLab;

import java.text.DateFormat;
import java.util.ArrayList;

public class CrimeListFragment extends ListFragment {
    private static final String SUBTITLE_VISIBLE = "subtitleVisible";
    private final String TAG = "CrimeListFragment";

    private ArrayList<Crime> crimes;
    private boolean subtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");

        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(crimes);
        setListAdapter(adapter);

        setRetainInstance(true);
        subtitleVisible = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View v = super.onCreateView(inflater, container, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (subtitleVisible) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated(View, Bundle)");

        /*
            -By passing the parent group into inflate, the layout_* attributes of your
            empty view layout are respected.

            -By not attaching the view in inflate (the false parameter), the empty view is
            returned. Otherwise, inflate would return the parent requiring us to use
            parentGroup.findViewById(empty_view_id) to get the empty view for use with
            setEmptyView(). Here we avoid the extra lookup, the need for another id, and
            the need to expose that id in our code. If we didn't need to preserve the
            reference to empty, telling inflate to attach would be the correct action
            removing the need for the addView call.
        */
        ViewGroup parentGroup = (ViewGroup) getListView().getParent();
        View emptyView = getLayoutInflater().inflate(R.layout.fragment_crime_list_empty_view, parentGroup, false);
        parentGroup.addView(emptyView);

        getListView().setEmptyView(emptyView);

        Button emptyViewButtonNewCrime = emptyView.findViewById(R.id.empty_view_button_new_crime);
        emptyViewButtonNewCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCrime();
            }
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
        Log.d(TAG, c.getTitle() + " was clicked");

        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        ((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "onCreateOptionsMenu(Menu, MenuInflater)");
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (subtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu(Menu)");
        super.onPrepareOptionsMenu(menu);
    }

    private void createNewCrime() {
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(i, 0);
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_new_crime");
                createNewCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                if (((AppCompatActivity) getActivity()).getSupportActionBar().getSubtitle() == null) {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
                    subtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
                    subtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
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