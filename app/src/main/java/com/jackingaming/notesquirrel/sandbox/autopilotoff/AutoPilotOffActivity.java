package com.jackingaming.notesquirrel.sandbox.autopilotoff;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.CrimeListFragment;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz.QuizFragment;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.hellomoon.HelloMoonActivity;

public class AutoPilotOffActivity extends SingleFragmentActivity {
    private static final String TAG = "AutoPilotOffActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
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
        inflater.inflate(R.menu.options_menu_activity_auto_pilot_off, menu);
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
            case R.id.menu_item_new_crime:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_new_crime");
                return true;
            case R.id.menu_item_geo_quiz:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_geo_quiz");
                FragmentManager fm = getSupportFragmentManager();
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
            case R.id.menu_item_hello_moon:
                Log.i(TAG, "onOptionsItemSelected(MenuItem) R.id.menu_item_hello_moon");
                Intent intentHelloMoon = new Intent(this, HelloMoonActivity.class);
                startActivity(intentHelloMoon);
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