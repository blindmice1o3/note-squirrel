package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.ContactsContract;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Crime;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.CrimeLab;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Photo;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID =
            "com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.crime_id";
    private static final String DIALOG_DATE_OR_TIME = "dateOrTime";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE_OR_TIME = 3000;
    private static final int REQUEST_PHOTO = 5;
    private static final int REQUEST_CONTACT = 6;

    private Crime crime;
    private ImageButton photoButton;
    private ImageView photoView;
    private EditText titleField;
    private Button dateOrTimeButton;
    private CheckBox solvedCheckBox;
    private Button suspectButton;

    public CrimeFragment() {
        // Required empty public constructor
        Log.i(TAG, "constructor()");
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Log.i(TAG, "newInstance(UUID)");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        setHasOptionsMenu(true);

        UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    private void updateDateAndTime() {
        String formattedDateTime = DateFormat
                .getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT)
                .format(crime.getDate());
        dateOrTimeButton.setText(formattedDateTime);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        titleField = (EditText) view.findViewById(R.id.crime_title);
        titleField.setText(crime.getTitle());
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

        dateOrTimeButton = (Button) view.findViewById(R.id.crime_date_or_time);
        dateOrTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                DateOrTimeFragment dialog = DateOrTimeFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE_OR_TIME);
                dialog.show(fm, DIALOG_DATE_OR_TIME);
            }
        });

        updateDateAndTime();

        solvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        solvedCheckBox.setChecked(crime.isSolved());
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        photoButton = (ImageButton) view.findViewById(R.id.crime_imageButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        photoView = (ImageView) view.findViewById(R.id.crime_imageView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo p = crime.getPhoto();
                if (p == null) {
                    return;
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
            }
        });

        // If camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD &&
                        Camera.getNumberOfCameras() > 0);
        if (!hasACamera) {
            photoButton.setEnabled(false);
        }

        Button reportButton = (Button) view.findViewById(R.id.crime_reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        suspectButton = (Button) view.findViewById(R.id.crime_suspectButton);
        suspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });

        if (crime.getSuspect() != null) {
            suspectButton.setText(crime.getSuspect());
        }

        return view;
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (crime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = android.text.format.DateFormat.format(dateFormat, crime.getDate()).toString();

        String suspect = crime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report,
                crime.getTitle(), dateString, solvedString, suspect);

        return report;
    }

    private void showPhoto() {
        // (Re)set the image button's image based on our photo
        Photo p = crime.getPhoto();
        BitmapDrawable b = null;
        if (p != null) {
            String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
            b = PictureUtils.getScaledDrawable(getActivity(), path);
        }
        photoView.setImageDrawable(b);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == DateOrTimeFragment.REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);
            updateDateAndTime();
        } else if (requestCode == DateOrTimeFragment.REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            crime.setDate(date);
            updateDateAndTime();
        } else if (requestCode == REQUEST_PHOTO) {
            // Create a new Photo object and attach it to the crime
            String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Log.i(TAG, "filename: " + filename);

                Photo p = new Photo(filename);
                crime.setPhoto(p);
                showPhoto();
                Log.i(TAG, "Crime: " + crime.getTitle() + " has a photo");
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();

            // Specify which fields you want your query to return values for.
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            // Perform your query - the contactUri is like a "where" clause here
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            // Double-check that you actually got results
            if (c.getCount() == 0) {
                c.close();
                return;
            }

            // Pull out the first column of the first row of data -
            // that is your suspect's name.
            c.moveToFirst();
            String suspect = c.getString(0);
            crime.setSuspect(suspect);
            suspectButton.setText(suspect);
            c.close();
        }
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
        showPhoto();
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
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu(Menu)");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) android.R.id.home");
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            case R.id.menu_item_delete_crime_in_detail_view:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_delete_crime_in_detail_view");
                CrimeLab.get(getActivity()).deleteCrime(crime);
                getActivity().finish();
                return true;
            case R.id.menu_item_summon_clippy:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_summon_clippy");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        CrimeLab.get(getActivity()).saveCrimes();
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
        PictureUtils.cleanImageView(photoView);
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