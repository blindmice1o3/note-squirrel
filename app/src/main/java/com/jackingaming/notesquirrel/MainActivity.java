package com.jackingaming.notesquirrel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "JJG";
    public static final String TEXT_FILE = "note_squirrel.txt";
    public static final String FILE_SAVED = "FileSaved";
    //to identify the Intent instance that was invoked (when a result is returned upon the
    //completion of that requesting intent), used with "startActivityForResult(Intent, int)"
    //and "onActivityResult(int, int, Intent)" (which is the call back method of
    //"startActivityForResult(Intent, int)").
    private static final int PHOTO_TAKEN = 0;

    private File imageFile;
    private String imageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        addSaveButtonListener();
        addLockButtonListener();

        //retrieving PERSISTENT data (values stored between "runs").
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        //checking if the key-value pair exists,
        //if does NOT exist (haven't done a put() and commit())...
        //it uses the default value (the second argument).
        boolean fileSaved = prefs.getBoolean(FILE_SAVED, false);

        if (fileSaved) {
            loadSavedFile();
        }
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

    private void addLockButtonListener() {
        Button lockBtn = (Button) findViewById(R.id.lock);

        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "Lock button clicked.");

                finish();
            }
        });
    }

    private void addSaveButtonListener() {
        Button saveBtn = (Button) findViewById(R.id.save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.text);
                String text = editText.getText().toString();

                //was used to verify the button was actually "doing stuff"
                //when we clicked it (before we supplied implementation).
                Log.d(DEBUG_TAG, "Save button clicked: " + text);

                try {
                    FileOutputStream fos = openFileOutput(TEXT_FILE, MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.close();

                    Log.d(DEBUG_TAG, "Saved file to internal storage.");

                    //the following uses PERSISTENT data (it's kept in between "runs" of our app).

                    //this is to DISTINGUISH BETWEEN user never actually pressing "save" button
                    //(e.g. they're using the app for the very first time) VERSUS the app had tried
                    //to save but there was a hiccup and ended up in the catch block.
                    //getPreferences(int) will get the preference file with lots of different
                    //values that we want to persist in between app "runs".
                    //only THIS activity can get access to THIS preference file.
                    SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    //Editor is an inner-class of the SharedPreferences class.
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(FILE_SAVED, true);
                    //HAVE TO tell editor to actually save the values we'd put into it.
                    editor.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(DEBUG_TAG, "Unable to save file to internal storage.");

                    //not using "this" because we're in an anonymous class rather than MainActivity.
                    //"getString()" is a method of the Activity, you can pass it the int id of the String.
                    Toast toastCantSave = Toast.makeText(MainActivity.this, getString(R.string.toast_cant_save), Toast.LENGTH_LONG);
                    //to display the Toast object, we have to call show().
                    toastCantSave.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_passpoints_reset:
                //Toast.makeText(this, "Passpoints Reset", Toast.LENGTH_LONG).show();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(ImageActivity.PASSWORD_SET, false);
                editor.commit();

                finish();

                return true;
            case R.id.menu_camera:
                //TODO: implement menu_camera
                Toast.makeText(this, "Camera", Toast.LENGTH_LONG).show();

                //"The Android Camera application saves a full-size photo if you give it
                //a file to save into. You must provide a FULLY-QUALIFIED FILE NAME
                //where the camera app should save the photo."
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //"Ensure that there's a camera activity to handle the intent"
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //"Create the File where the photo should go"
                    //get the default directory for where photos are stored onto the device's external storage.
                    File picturesDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    //supplying a directory and name of the new file.
                    //imageFile = new File(picturesDirectory, "passpoints_image.jpg");
                    try {
                        Log.d(DEBUG_TAG, "BEFORE File.createTempFile(String, String, File)");
                        imageFile = File.createTempFile(
                                "passpoints_image",     /* prefix */
                                ".jpg",                 /* suffix */
                                picturesDirectory             /* directory */
                        );
                        Log.d(DEBUG_TAG, "AFTER File.createTempFile(String, String, File)");

                        ///////////////////////////////////////////////////////////////////////////
                        Log.d(DEBUG_TAG, "@@@@@ imageFile: " + picturesDirectory.toString() + " <passpoints_image.jpg>? @@@@@");
                        ///////////////////////////////////////////////////////////////////////////

                        imageFilePath = imageFile.getAbsolutePath();
                        Log.d(DEBUG_TAG, "imageFile.getAbsolutePath(): " + imageFilePath);
                    } catch (IOException e) {
                        //"Error occurred while creating the File"
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemsSelected(), R.id.menu_camera... catch-block... File.createTempFile(String, String, File)");
                        e.printStackTrace();
                    }
                    //"Continue only if the File was successfully created"
                    if (imageFile != null) {
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null");

                        Log.d(DEBUG_TAG, "BuildConfig.APPLICATION_ID + \".provider\": " + BuildConfig.APPLICATION_ID + ".provider");
                        //TODO:
                        Uri photoURI = FileProvider.getUriForFile(this,
                                BuildConfig.APPLICATION_ID + ".fileprovider", imageFile);
                        //Uri.fromFile(File) probably gets the FULLY-QUALIFIED FILE NAME of the file passed in.
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null... AFTER FileProvider.getUriForFile(Context, String, File)");
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                        //startActivity(takePictureIntent);

                        // "The Android Camera application encodes the photo in the return Intent delivered to
                        // onActivityResult() as a small Bitmap in the extras, under the key "data"."
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null... PRE startActivityForResult(Intent, int)");
                        startActivityForResult(takePictureIntent, PHOTO_TAKEN);
                    } else {
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera, else-clause... imageFile == null");
                    }

                } else {
                    Log.d(DEBUG_TAG, "checking for camera app: package manager is null");
                    Toast.makeText(this, "checking for camera app: package manager is null", Toast.LENGTH_LONG).show();
                }

                return true;
            case R.id.menu_cancel:
                //TODO: implement menu_cancel
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(this, "MainActivity.onOptionsItemSelected(MenuItem) switch's default", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    // "The Android Camera application encodes the photo in the return Intent delivered to
    // onActivityResult() as a small Bitmap in the extras, under the key "data"."
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PHOTO_TAKEN && resultCode == RESULT_OK) {
            //getting the image captured by the camera app (that was stored in a passed in File
            //instance), getting its absolute (FULLY-QUALIFIED FILE NAME?) path.
            //TODO:
            Intent photoViewerIntent = new Intent(this, PhotoViewerActivity.class);
            photoViewerIntent.putExtra("imageAddress", imageFilePath);
            startActivity(photoViewerIntent);

            /*
                In order to READ (e.g. when we invoke "decodeFile(String)") from the
                device's external storage, we need to request PERMISSION (this is done
                within the "AndroidManifest.xml" file).
             */
            /*
            Bitmap photo = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            if (photo != null) {
                Intent photoViewerIntent = new Intent(this, PhotoViewerActivity.class);
                //TODO:
                //photoViewerIntent.putExtra("photoTaken", photo);

                startActivity(photoViewerIntent);
            }
            else {
                Toast.makeText(this, R.string.unable_to_save_photo_file, Toast.LENGTH_LONG).show();
            }
            */
        }
    }

}