package com.jackingaming.notesquirrel.file;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

import java.io.File;
import java.io.IOException;

public class FileSandboxActivity extends AppCompatActivity {

    private String filePath, parentDirectory;
    private File randomDir, randomFile, randomFile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sandbox);

        randomDir = new File("/files/FileSandbox");
        randomDir.mkdir();

        Log.d(MainActivity.DEBUG_TAG, getFilesDir().toString());

        randomFile = new File("random.txt");
        randomFile2 = new File("/files/FileSandbox/random2.txt");

        try {
            Log.d(MainActivity.DEBUG_TAG, "try: before randomFile.createNewFile();");
            randomFile.createNewFile();
            Log.d(MainActivity.DEBUG_TAG, "try: before randomFile2.createNewFile();");
            randomFile2.createNewFile();
            Log.d(MainActivity.DEBUG_TAG, "try: after randomFile2.createNewFile();");

            filePath = randomFile.getCanonicalPath();
            Log.d(MainActivity.DEBUG_TAG, "try: after randomFile.getCanonicalPath();");
        } catch (IOException e) {
            Log.d(MainActivity.DEBUG_TAG, "FileSandboxActivity.onCreate(Bundle): catch-block IOException");
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView textView = (TextView) findViewById(R.id.text_view_file_sandbox);
        textView.setText("Hello world!\n");
        textView.append("Two, four, six, eight!");
        textView.append("No space or new line.\n");

        if (randomFile.exists()) {
            textView.append(filePath);
        }

        textView.append("\n\nafter randomFile.exists() block");
    }



}