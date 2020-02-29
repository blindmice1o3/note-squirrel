package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class ImageActivity extends AppCompatActivity implements IPointCollectorListener {

    private PointCollector pointCollector = new PointCollector();
    private Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //MUST come after setContentView(int).
        addTouchListener();
        //displays a Dialog message to the device's screen.
        showPrompt();

        //REGISTER self to the SUBJECT as an interested SUBSCRIBER.
        pointCollector.setListener(this);
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

        image.setOnTouchListener(pointCollector);
    }

    //implementation of the method which PUSHED data from the SUBJECT.
    //this SUBSCRIBER class can now PULL relevant data from what the SUBJECT PUSHED.
    @Override
    public void pointsCollected(List<Point> points) {
        Log.d(MainActivity.DEBUG_TAG, "Collected points: " + points.size());

        //USING THE NEW Database.storePoints(List<Point>) METHOD TO ADD ENTRIES TO OUR DATABASE'S TABLE.
        database.storePoints(points);

        //TODO: remove this test/verification code later (verification using Logcat's monitor).
        //TESTING THE NEW Database.getPoints() METHOD TO READ VALUES FROM OUR DATABASE'S TABLE.
        List<Point> testReadFromDatabase = database.getPoints();
        for (Point point : testReadFromDatabase) {
            Log.d(MainActivity.DEBUG_TAG, String.format("Got point: (%d, %d)", point.x, point.y));
        }





        //TODO: remove this developer_helper.
        String message = String.format("Collection of coordinates: (%d, %d), (%d, %d), (%d, %d), (%d, %d)",
                points.get(0).x, points.get(0).y,
                points.get(1).x, points.get(1).y,
                points.get(2).x, points.get(2).y,
                points.get(3).x, points.get(3).y);
        //display message to device's screen via a Toast pop-up.
        //(discovered that Toast is slow, and it continues to show back-logged Toasts even after app shut down.)
        //TODO: remove this Toast-developer_helper.
        Toast toastCantSave = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toastCantSave.show();
    }

    @Override
    public void singlePointCollected(Point point) {
        String message = String.format("Single coordinate: (%d, %d)", point.x, point.y);
        //display message to device's screen via a Toast pop-up.
        //TODO: remove this Toast-developer_helper.
        Toast toastCantSave = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toastCantSave.show();
    }

}