package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent;

import android.content.Context;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models.Crime;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer {
    private Context context;
    private String fileName;

    public CriminalIntentJSONSerializer(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public void saveCrimes(ArrayList<Crime> crimes)
            throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Crime c : crimes) {
            array.put(c.toJSON());

            // Write the file to disk
            Writer writer = null;
            try {
                OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
                writer.write(array.toString());
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }
}