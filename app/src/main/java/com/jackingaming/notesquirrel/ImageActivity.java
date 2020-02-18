package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //MUST come after setContentView(int).
        addTouchListener();
        //displays a Dialog message to the device's screen.
        showPrompt();
    }

    private void showPrompt() {
        //Builder is probably an inner-class of the AlertDialog class.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting up buttons on the builder (to customize the dialog that it will build).
        //setNegativeButton(CharSequence, OnClickListener) would be like the "cancel" button.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //intentionally left blank.
            }
        });

        builder.setTitle("Create Your Passpoint Sequence");
        builder.setMessage("Touch four points on the image to set the passpoint sequence. You must click the same points in future to gain access to your notes.");

        //use the Builder to create the dialog (set various options on the builder and then call create()).
        //this will return an AlertDialog object (it can have an "Ok" and/or "Cancel" button[s]).
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addTouchListener() {
        ImageView image = (ImageView) findViewById(R.id.touch_image);

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                String message = String.format("Coordinates: (%.2f, %.2f)", x, y);

                //display message to Logcat monitor.
                Log.d(MainActivity.DEBUG_TAG, message);

                //display message to device's screen via a Toast pop-up.
                //(discovered that Toast is slow, and it continues to show back-logged Toasts even after app shut down.)
                //TODO: remove this Toast-developer_helper.
                Toast toastCantSave = Toast.makeText(ImageActivity.this, message, Toast.LENGTH_SHORT);
                toastCantSave.show();

                return false;
            }
        });
    }

}
