package com.jackingaming.notesquirrel;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.passpoints.ImageActivity;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.AutoPilotOffActivity;
import com.jackingaming.notesquirrel.sandbox.countzero.CountZeroActivity;
import com.jackingaming.notesquirrel.sandbox.downloadhtml.DownloadHtmlFragmentParentActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.DvdLibraryActivity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.ListFragmentDvdParentActivity;
import com.jackingaming.notesquirrel.sandbox.ide.ProjectWizardActivity;
import com.jackingaming.notesquirrel.sandbox.ide.WorkbenchActivity;
import com.jackingaming.notesquirrel.sandbox.passingthrough.PassingThroughActivity;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.MealMaker3000CashierActivity;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier1.SpriteSheetVerifier1Activity;
import com.jackingaming.notesquirrel.sandbox.listviewemail.ListViewEmailActivity;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.SpriteSheetVerifier2Activity;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.grid.GridViewDvdActivity;
import com.jackingaming.notesquirrel.sandbox.worldofbox.WorldOfBoxActivity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "JJG";
    public static final String TEXT_FILE = "note_squirrel.txt";
    public static final String FILE_SAVED = "FileSaved";
    //to identify the Intent instance that was invoked (when a result is returned upon the
    //completion of that requesting intent), used with "startActivityForResult(Intent, int)"
    //and "onActivityResult(int, int, Intent)" (which is the call back method of
    //"startActivityForResult(Intent, int)").
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int BROWSE_GALLERY_REQUEST = 1;

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

    @Override
    protected void onStart() {
        super.onStart();

        String successMessage = "note/squirrel (hp edition!!!)";
        testHpEditionAsContent(successMessage);
    }

    private void testHpEditionAsContent(String successMessage) {
        EditText editText = (EditText) findViewById(R.id.text);
        editText.setText(successMessage);
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
        getMenuInflater().inflate(R.menu.options_menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_passpoints_reset:
                Toast.makeText(this, R.string.menu_passpoints_reset, Toast.LENGTH_SHORT).show();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(ImageActivity.PASSWORD_SET, false);
                editor.commit();

                finish();
                return true;
            case R.id.menu_camera:
                Toast.makeText(this, R.string.menu_camera, Toast.LENGTH_SHORT).show();

                //"The Android Camera application saves a full-size photo if you give it
                //a file to save into. You must provide a FULLY-QUALIFIED FILE NAME
                //where the camera app should save the photo."
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //"Ensure that there's a camera activity to handle the intent"
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //Get the default directory for where photos are stored onto the device's external storage.
                    File picturesDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    //File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                    //Create the File where the photo should go supplying a directory and name of the new file.
                    imageFile = new File(picturesDirectory, "passpoints_image");
                    imageFilePath = imageFile.getAbsolutePath();
                    Log.d(DEBUG_TAG, "imageFile.getAbsolutePath(): " + imageFilePath);

                    Log.d(DEBUG_TAG, "files-path: " + getFilesDir());
                    Log.d(DEBUG_TAG, "external-files-path: " + getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                    Log.d(DEBUG_TAG, "cache-path: " + getCacheDir());
                    Log.d(DEBUG_TAG, "external-cache-path: " + getExternalCacheDir());

                    //"Continue only if the File was successfully created"
                    if (imageFile != null) {
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null");

                        //Uri.fromFile(File) probably gets the FULLY-QUALIFIED FILE NAME of the file passed in.
                        Uri photoURI = Uri.fromFile(imageFile);
                        /*
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.jackingaming.notesquirrel.fileprovider", imageFile);
                        */

                        //Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null... AFTER FileProvider.getUriForFile(Context, String, File)");
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null... AFTER Uri.fromFile(File)");
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera... imageFile != null... PRE startActivityForResult(Intent, int)");
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    } else {
                        Log.d(DEBUG_TAG, "MainActivity.onOptionsItemSelected(), R.id.menu_camera, else-clause... imageFile == null");
                    }

                    /*
                    try {
                        Log.d(DEBUG_TAG, "BEFORE File.createTempFile(String, String, File)");
                        imageFile = File.createTempFile(
                                "passpoints_image",     // prefix
                                ".jpg",                 // suffix
                                picturesDirectory             // directory
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
                    */
                } else {
                    Log.d(DEBUG_TAG, "checking for camera app: package manager is null");
                    Toast.makeText(this, "checking for camera app: package manager is null", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menu_gallery:
                Toast.makeText(this, R.string.menu_gallery, Toast.LENGTH_SHORT).show();

                Intent browseGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Intent browseGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(browseGalleryIntent, BROWSE_GALLERY_REQUEST);
                return true;
            case R.id.menu_list_view_email:
                Toast.makeText(this, R.string.menu_list_view_email, Toast.LENGTH_SHORT).show();

                Intent listViewEmailIntent = new Intent(this, ListViewEmailActivity.class);
                startActivity(listViewEmailIntent);
                return true;
            case R.id.menu_download_html_fragment_parent:
                Toast.makeText(this, R.string.menu_download_html_fragment_parent, Toast.LENGTH_SHORT).show();

                Intent downloadHtmlFragmentParentIntent = new Intent(this, DownloadHtmlFragmentParentActivity.class);
                startActivity(downloadHtmlFragmentParentIntent);
                return true;
            case R.id.menu_grid_view_dvd:
                Toast.makeText(this, R.string.menu_grid_view_dvd, Toast.LENGTH_SHORT).show();

                Intent gridViewDvdIntent = new Intent(this, GridViewDvdActivity.class);
                startActivity(gridViewDvdIntent);
                return true;
            case R.id.menu_list_fragment_dvd:
                Toast.makeText(this, R.string.menu_list_fragment_dvd, Toast.LENGTH_SHORT).show();

                Intent listFragmentDvdParentIntent = new Intent(this, ListFragmentDvdParentActivity.class);
                startActivity(listFragmentDvdParentIntent);
                return true;
            case R.id.menu_dvd_library:
                Toast.makeText(this, R.string.menu_dvd_library, Toast.LENGTH_SHORT).show();

                Intent dvdLibraryIntent = new Intent(this, DvdLibraryActivity.class);
                startActivity(dvdLibraryIntent);
                return true;
            case R.id.menu_passing_through:
                Toast.makeText(this, R.string.menu_passing_through, Toast.LENGTH_SHORT).show();

                Intent passingThroughIntent = new Intent(this, PassingThroughActivity.class);
                startActivity(passingThroughIntent);
                return true;
            case R.id.menu_sprite_sheet_verifier1:
                Toast.makeText(this, R.string.menu_sprite_sheet_verifier1, Toast.LENGTH_SHORT).show();

                Intent spriteSheetVerifier1Intent = new Intent(this, SpriteSheetVerifier1Activity.class);
                startActivity(spriteSheetVerifier1Intent);
                return true;
            case R.id.menu_auto_pilot_off:
                Toast.makeText(this, R.string.menu_auto_pilot_off, Toast.LENGTH_SHORT).show();

                Intent autoPilotOffIntent = new Intent(this, AutoPilotOffActivity.class);
                startActivity(autoPilotOffIntent);
                return true;
            case R.id.menu_ide:
                Toast.makeText(this, R.string.menu_ide, Toast.LENGTH_SHORT).show();

                Intent workbenchIntent = new Intent(this, WorkbenchActivity.class);
                startActivity(workbenchIntent);
//                Intent projectWizardIntent = new Intent(this, ProjectWizardActivity.class);
//                startActivity(projectWizardIntent);
                return true;
            case R.id.menu_world_of_box:
                Toast.makeText(this, R.string.menu_world_of_box, Toast.LENGTH_SHORT).show();
                
                Intent worldOfBoxIntent = new Intent(this, WorldOfBoxActivity.class);
                startActivity(worldOfBoxIntent);
                return true;
            case R.id.menu_sprite_sheet_verifier2:
                Toast.makeText(this, R.string.menu_sprite_sheet_verifier2, Toast.LENGTH_SHORT).show();

                Intent spriteSheetVerifier2Intent = new Intent(this, SpriteSheetVerifier2Activity.class);
                startActivity(spriteSheetVerifier2Intent);
                return true;
            case R.id.menu_meal_maker_3000_cashier:
                Toast.makeText(this, R.string.menu_meal_maker_3000_cashier, Toast.LENGTH_SHORT).show();

                Intent mealMaker3000CashierIntent = new Intent(this, MealMaker3000CashierActivity.class);
                startActivity(mealMaker3000CashierIntent);
                return true;
            case R.id.menu_count_zero:
                Toast.makeText(this, R.string.menu_count_zero, Toast.LENGTH_SHORT).show();

                Intent countZeroIntent = new Intent(this, CountZeroActivity.class);
                startActivity(countZeroIntent);
                return true;
            case R.id.menu_jack_in:
                Toast.makeText(this, R.string.menu_jack_in, Toast.LENGTH_SHORT).show();

                Intent jackInIntent = new Intent(this, JackInActivity.class);
                startActivity(jackInIntent);
                return true;
            default:
                Toast.makeText(this, "MainActivity.onOptionsItemSelected(MenuItem) switch's default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    /**
     * This method is invoked upon returning from an activity invoked by
     * startActivityForResult. We check the requestCode to see which activity we
     * returned from.
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        // "The Android Camera application encodes the photo in the return Intent delivered to
        // onActivityResult() as a small Bitmap in the extras, under the key "data". (thumbnail)"
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //getting the image captured by the camera app (that was stored in a passed-in File
            //instance), getting its absolute (FULLY-QUALIFIED FILE NAME?) path.
            //TODO:
            Intent cameraResultIntent = new Intent(this, CameraResultActivity.class);
            cameraResultIntent.putExtra("imageAddress", imageFilePath);
            startActivity(cameraResultIntent);
        } else if (requestCode == BROWSE_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Log.d(DEBUG_TAG, "MainActivity.onActivityResult(int, int, Intent): Browse the Gallery");
            Log.d(DEBUG_TAG, "Gallery result: " + intent.getData());
            Toast.makeText(this, "Gallery result: " + intent.getData(), Toast.LENGTH_LONG).show();

            /*
            ////////////////////////////////////////////////////

            String[] columns = { MediaStore.Images.Media.DATA };

            Uri imageUri = intent.getData();

            Cursor cursor = getContentResolver().query(imageUri, columns, null, null, null);
            //Initially, cursor is pointing to immediately-BEFORE-the-first
            //item, SO MUST POINT IT TO THE FIRST ITEM.
            ////////////////////////////////////////////////////
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(columns[0]);
            String imagePath = cursor.getString(columnIndex);

            //IMPORTANT: remember to close the cursor.
            cursor.close();

            //TODO: 2020-03-27
            imageFilePath = Uri.parse(imagePath);
            ////////////////////////////////////////////////////

            ////////////////////////////////////////////////////
            */

            Uri pathUri = intent.getData();
            String pathAddress = intent.getData().getPath();

            Intent galleryResultIntent = new Intent(this, GalleryResultActivity.class);
            galleryResultIntent.putExtra("pathUri", pathUri);
            galleryResultIntent.putExtra("pathAddress", pathAddress);
            startActivity(galleryResultIntent);
            //TODO:
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

}