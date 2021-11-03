package com.jackingaming.notesquirrel.sandbox.ide;

import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.SingleFragmentActivity;

public class ProjectWizardActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ProjectWizardFragment();
    }
}