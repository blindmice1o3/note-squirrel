package com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.models;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.sandbox.autopilotoff.criminalintent.CriminalIntentJSONSerializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> crimes;
    private CriminalIntentJSONSerializer serializer;

    private static CrimeLab crimeLab;
    private Context appContext;

    private CrimeLab(Context appContext) {
        this.appContext = appContext;
        crimes = new ArrayList<Crime>();
        serializer = new CriminalIntentJSONSerializer(this.appContext, FILENAME);
    }

    public static CrimeLab get(Context c) {
        if (crimeLab == null) {
            crimeLab = new CrimeLab(c.getApplicationContext());
        }
        return crimeLab;
    }

    public void addCrime(Crime c) {
        crimes.add(c);
    }

    public boolean saveCrimes() {
        try {
            serializer.saveCrimes(crimes);
            Log.d(TAG, "crimes saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            return false;
        }
    }

    public ArrayList<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : crimes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }
}