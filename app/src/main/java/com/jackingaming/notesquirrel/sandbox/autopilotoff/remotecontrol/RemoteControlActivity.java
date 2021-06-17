package com.jackingaming.notesquirrel.sandbox.autopilotoff.remotecontrol;

import android.os.Bundle;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.SingleFragmentActivity;

public class RemoteControlActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RemoteControlFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}