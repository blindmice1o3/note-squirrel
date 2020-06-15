package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.computer;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource.DvdList;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.view.list.MyListFragment;

public class ComputerActivity extends AppCompatActivity {

    //FRAGMENT
    private MyListFragment projectToolWindowFragment;
    private EditorPanelFragment editorPanelFragment;
    //VIEW
    private EditText editTextEditorPanel;
    //TOAST (CUSTOM VIEW)
    private Bitmap ai;

    //DATA SOURCE (for ListFragment's ArrayAdapter)
    //TODO: placeholder for project tool window.
    private DvdList dvds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer);

        projectToolWindowFragment =
                (MyListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment_project_tool_window);
        editorPanelFragment =
                (EditorPanelFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_editor_panel);
        editTextEditorPanel = (EditText) findViewById(R.id.editTextEditorPanel);

        Bitmap aiSpriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
        ai = Bitmap.createBitmap(aiSpriteSheet, 0, 0, 124, 93);

        //TODO: placeholder for project tool window.
        dvds = new DvdList(getResources());
        ArrayAdapter<Dvd> adapter = new ArrayAdapter<Dvd>(this,
                R.layout.array_adapter_my_list_fragment, dvds);
        projectToolWindowFragment.setListAdapter(adapter);

        projectToolWindowFragment.setOnListItemClickListener(new MyListFragment.OnListItemClickListener() {
            @Override
            public void onDvdItemClicked(int position) {
                Toast.makeText(ComputerActivity.this, "List item clicked", Toast.LENGTH_SHORT).show();
                editTextEditorPanel.setText(dvds.get(position).getTitle());
            }
        });
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

        String codeOfPlayer = editTextEditorPanel.getText().toString();

        if (codeOfPlayer.length() > 0) {
            editTextEditorPanel.getText().clear();
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

        String codeOfPlayer = editTextEditorPanel.getText().toString();

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