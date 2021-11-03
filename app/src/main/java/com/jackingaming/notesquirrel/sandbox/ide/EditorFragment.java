package com.jackingaming.notesquirrel.sandbox.ide;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class EditorFragment extends Fragment {
    private static final String TAG = "EditorFragment";
    public static final String EXTRA_FILE =
            "com.jackingaming.notesquirrel.sandbox.ide.file";

    public static EditorFragment newInstance(File file) {
        Log.d(TAG, "newInstance(File)");
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_FILE, file);

        EditorFragment fragment = new EditorFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editor, container, false);

        TextView textView = (TextView) v.findViewById(R.id.fragment_editor_textView);
        File file = (File) getArguments().getSerializable(EXTRA_FILE);
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine() + "\n");
            }
            String fileContent = sb.toString();
            textView.setText("File name: " + file.getName() + "\n" +
                    "File path: " + file.getPath() + "\n" +
                    "Is file: " + file.isFile() + "\n" +
                    "Is directory: " + file.isDirectory() + "\n" +
                    "Exists: " + file.exists() + "\n" +
                    "Parent path: " + file.getParent() + "\n" +
                    "File content: " + "\n" +
                    fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return v;
    }
}