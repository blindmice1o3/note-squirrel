package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.SingleFragmentActivity;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}