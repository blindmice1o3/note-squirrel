package com.jackingaming.notesquirrel.sandbox.autopilotoff.draganddraw;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.SingleFragmentActivity;

public class DragAndDrawActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new DragAndDrawFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}