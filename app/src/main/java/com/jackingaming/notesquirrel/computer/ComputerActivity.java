package com.jackingaming.notesquirrel.computer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

public class ComputerActivity extends AppCompatActivity {

    private EditText editTextComputer;

    Bitmap ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        editTextComputer = (EditText) findViewById(R.id.editTextIDE);

        Bitmap aiSpriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
        ai = Bitmap.createBitmap(aiSpriteSheet, 0, 0, 124, 93);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_activity_computer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_computer_paperclip:
                Toast.makeText(this, "Paperclip", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "ComputerActivity.onOptionsItemSelected(MenuItem) switch's default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

    public void onButtonClearClicked(View view) {
        Log.d(MainActivity.DEBUG_TAG, "ComputerActivity.onButtonClearClicked(View)");

        String codeOfPlayer = editTextComputer.getText().toString();

        if (codeOfPlayer.length() > 0) {
            editTextComputer.getText().clear();
        } else {
            String toastMessage = "Ain't nothing need clearing, code-slinger.";

            ////////////////////////////////////////////////////////////////////////////////////////
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView message = (TextView) layout.findViewById(R.id.toast_message);
            message.setText(toastMessage);
            ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
            image.setImageBitmap(ai);

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
            ////////////////////////////////////////////////////////////////////////////////////////
        }
    }

    public void onButtonRunClicked(View view) {
        Log.d(MainActivity.DEBUG_TAG, "ComputerActivity.onButtonRunClicked(View)");

        String codeOfPlayer = editTextComputer.getText().toString();

        String toastMessage = null;
        if (codeOfPlayer.length() > 0) {
            toastMessage = "running: " + codeOfPlayer;
        } else {
            toastMessage = "Show at least a little effort, console-jockey.";
        }

        ////////////////////////////////////////////////////////////////////////////////////////
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView message = (TextView) layout.findViewById(R.id.toast_message);
        message.setText(toastMessage);
        ImageView image = (ImageView) layout.findViewById(R.id.toast_image);
        image.setImageBitmap(ai);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        ////////////////////////////////////////////////////////////////////////////////////////
    }

}