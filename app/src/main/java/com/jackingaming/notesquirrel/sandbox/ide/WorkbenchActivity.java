package com.jackingaming.notesquirrel.sandbox.ide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.ide.models.ExpandableListDataItems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class WorkbenchActivity extends AppCompatActivity {
    private static final String TAG = "WorkbenchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentLeft = fm.findFragmentById(R.id.fragmentContainerLeft);
        Fragment fragmentCenter = fm.findFragmentById(R.id.fragmentContainerCenter);
        Fragment fragmentRight = fm.findFragmentById(R.id.fragmentContainerRight);

        if (fragmentLeft == null) {
            fragmentLeft = new PackageExplorerFragment();
        }
        if (fragmentCenter == null) {
            File rootDirectory = new File(getExternalFilesDir(null), ExpandableListDataItems.ROOT_DIRECTORY_NAME);
            File gameboyColorFile = new File(rootDirectory, "GameboyColor.txt");
            if (gameboyColorFile.exists()) {
                Log.i(TAG, "gameboyColorFile does exist.");
                fragmentCenter = EditorFragment.newInstance(gameboyColorFile);
            } else {
                Log.i(TAG, "gameboyColorFile does NOT exist.");
            }
        }
        if (fragmentRight == null) {
            fragmentRight = new OutlineFragment();
        }

        fm.beginTransaction()
                .add(R.id.fragmentContainerLeft, fragmentLeft)
                .add(R.id.fragmentContainerCenter, fragmentCenter)
                .add(R.id.fragmentContainerRight, fragmentRight)
                .commit();
    }
}