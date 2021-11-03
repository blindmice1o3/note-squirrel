package com.jackingaming.notesquirrel.sandbox.ide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.R;

public class ProjectWizardFragment extends Fragment {
    private static final String TAG = "ProjectWizardFragment";
    private static final int REQUEST_CODE_CHOOSE_DIRECTORY = 9999;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_wizard, container, false);

        Button newProjectButton = (Button) v.findViewById(R.id.projectwizard_new_project_button);
        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(Intent.createChooser(i, "Choose directory"), REQUEST_CODE_CHOOSE_DIRECTORY);
                } else {
                    Log.d(TAG, "Cannot startActivityForResult(...) to choose a directory: Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_CHOOSE_DIRECTORY:
                Log.i(TAG, "Result URI " + data.getData());
                break;
        }
    }
}