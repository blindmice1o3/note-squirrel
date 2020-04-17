package com.jackingaming.notesquirrel;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_result);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView textView = (TextView) findViewById(R.id.text_view_file_path);
        ImageView imageView = (ImageView) findViewById(R.id.image_view_file_path);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imagePath = extras.getString("pathAddress");
            Uri pathUri = (Uri) extras.get("pathUri");

            if (imagePath != null) {
                textView.setText(imagePath);

                //Uri imageUri = Uri.fromFile(new File("imagePath"));
                imageView.setImageURI(pathUri);
            } else {
                Log.d(MainActivity.DEBUG_TAG, "imagePath is null");
            }
        } else {
            Log.d(MainActivity.DEBUG_TAG, "extras is null");
        }
    }

}