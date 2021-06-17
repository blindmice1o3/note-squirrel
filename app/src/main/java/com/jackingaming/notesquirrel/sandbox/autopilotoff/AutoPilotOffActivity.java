package com.jackingaming.notesquirrel.sandbox.autopilotoff;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.CrimeFragment;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.CrimeListFragment;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.CrimePagerActivity;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Crime;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz.QuizFragment;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.hellomoon.HelloMoonActivity;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.remotecontrol.RemoteControlActivity;

public class AutoPilotOffActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    private static final String TAG = "AutoPilotOffActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        // This [alias resource], a resource that points to another resource,
        // is defined in res/values/refs.xml
        return R.layout.activity_listdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            // Start an instance of CrimePagerActivity
            Intent i = new Intent(this, CrimePagerActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
            startActivity(i);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        FragmentManager fm = getSupportFragmentManager();
        CrimeListFragment listFragment = (CrimeListFragment) fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu(Menu)");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_auto_pilot_off, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu(Menu)");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_geo_quiz:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_geo_quiz");
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragmentInFragmentContainer = fm.findFragmentById(R.id.fragmentContainer);
                if (!(fragmentInFragmentContainer instanceof QuizFragment)) {
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
            case R.id.menu_item_hello_moon:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_hello_moon");
                Intent intentHelloMoon = new Intent(this, HelloMoonActivity.class);
                startActivity(intentHelloMoon);
                return true;
            case R.id.menu_item_remote_control:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_remote_control");
                Intent intentRemoteControl = new Intent(this, RemoteControlActivity.class);
                startActivity(intentRemoteControl);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}