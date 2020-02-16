package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "JJG";
    public static final String TEXT_FILE = "note_squirrel.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSaveButtonListener();

        loadSavedFile();
    }

    private void loadSavedFile() {
        try {
            FileInputStream fis = (FileInputStream) openFileInput(TEXT_FILE);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new DataInputStream(fis)));

            EditText editText = (EditText) findViewById(R.id.text);

            String line;

            while ((line = reader.readLine()) != null) {
                editText.append(line);
                editText.append("\n");
            }

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(DEBUG_TAG, "Unable to read file to internal storage.");
        }
    }

    private void addSaveButtonListener() {
        Button saveBtn = (Button) findViewById(R.id.save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(DEBUG_TAG, "Save button clicked: " + text);
                //was used to verify the button was actually "doing stuff"
                //when we clicked it (before we supplied implementation).

                EditText editText = (EditText) findViewById(R.id.text);

                String text = editText.getText().toString();

                try {
                    FileOutputStream fos = openFileOutput(TEXT_FILE, Context.MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.close();

                    Log.d(DEBUG_TAG, "Saved file to internal storage.");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(DEBUG_TAG, "Unable to save file to internal storage.");
                }

            }
        });
    }

}