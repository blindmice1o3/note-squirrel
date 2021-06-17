package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.SingleFragmentActivity;

public class CrimeCameraActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        // Hide the window title.
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // Hide the status bar and other OS-level chrome
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // The calls to [requestWindowFeature(...)] and [addFlags(...)] must be made
        // before the activity's view is created in [Activity.setContentView(...)],
        // which, in [CrimeCameraActivity], is called in the superclass's implementation
        // of [onCreate(Bundle)].
        super.onCreate(savedInstanceState);
    }
}