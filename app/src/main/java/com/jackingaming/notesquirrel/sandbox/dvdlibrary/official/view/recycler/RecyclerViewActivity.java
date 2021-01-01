package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog.DisplayDvdDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.MyBottomSheetDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.Command;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.SearchByAvailableCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.SearchByTitleCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.ViewContentOfCartCommand;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity
        implements AdapterRecyclerView.ItemClickListener,
        DisplayDvdDialogFragment.DisplayDvdDialogTouchListener {

    public static final String IP_ADDRESS = "http://192.168.0.141:8080";
    public static final String CART_KEY = "CART";
    public static final String BUNDLE_KEY = "BUNDLE";
    public static final int VIEW_CART_ACTIVITY_REQUEST_CODE = 1;

    public enum Mode { GRID, LINEAR; }

    private final RestTemplate restTemplate = new RestTemplate();
    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private Mode mode;
    private int scrollPosition;
    private List<Dvd> dvds;
    private AdapterRecyclerView adapter;

    private List<Command> commandsForBottomSheet;
    private MyBottomSheetDialogFragment myBottomSheetDialogFragment;

    private List<Dvd> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        progressDialog = new ProgressDialog(RecyclerViewActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tinkering);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        mode = Mode.GRID;
        scrollPosition = 0;

        // DEFAULT data (not downloaded from database)
        dvds = loadCSVAsDvd();
        adapter = new AdapterRecyclerView(dvds);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager( instantiateLayoutManager() );

        String path = "/dvds";
        String urlGetAll = IP_ADDRESS + path;
        GetTask taskGetAll = new GetTask();
        taskGetAll.execute(urlGetAll);

        commandsForBottomSheet = new ArrayList<Command>();
        commandsForBottomSheet.add(new ViewContentOfCartCommand(this));
        commandsForBottomSheet.add(new SearchByAvailableCommand(this));
        commandsForBottomSheet.add(new SearchByTitleCommand(this));
        myBottomSheetDialogFragment = new MyBottomSheetDialogFragment(commandsForBottomSheet);

        cart = new ArrayList<Dvd>();
    }

    private class GetTask extends AsyncTask<String, Void, List<Dvd>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait... It is downloading");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<Dvd> doInBackground(String... strings) {
            String url = strings[0];

            ResponseEntity<List<Dvd>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Dvd>>(){});
            List<Dvd> dvdsUpdated = response.getBody();
            return dvdsUpdated;
        }

        @Override
        protected void onPostExecute(List<Dvd> dvdsUpdated) {
            super.onPostExecute(dvdsUpdated);
            dvds.clear();
            dvds.addAll(dvdsUpdated);
            adapter.notifyDataSetChanged();

            progressDialog.hide();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "position: " + position + " | available: " + dvds.get(position).isAvailable(), Toast.LENGTH_SHORT).show();

        DisplayDvdDialogFragment displayDvdDialogFragment = new DisplayDvdDialogFragment(dvds.get(position));
        displayDvdDialogFragment.setListener(this);
        displayDvdDialogFragment.show(getSupportFragmentManager(), DisplayDvdDialogFragment.TAG);
    }

    @Override
    public void onPositiveButtonClick(Dvd dvd) {
        Toast.makeText(this, "[Add to cart] was clicked", Toast.LENGTH_SHORT).show();
        cart.add(dvd);
    }

    @Override
    public void onNegativeButtonClick() {
        Toast.makeText(this, "[Cancel] was clicked", Toast.LENGTH_SHORT).show();
    }

    public void launchViewCartActivity() {
        Intent viewCartIntent = new Intent(this, ViewCartActivity.class);
        Bundle cartBundle = new Bundle();
        cartBundle.putSerializable(CART_KEY, (Serializable)cart);
        viewCartIntent.putExtra(BUNDLE_KEY, cartBundle);
        startActivityForResult(viewCartIntent, VIEW_CART_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == VIEW_CART_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            cart.clear();
            Toast.makeText(this, "cart.clear()", Toast.LENGTH_SHORT).show();

            // update local data
            String path = "/dvds";
            String url = IP_ADDRESS + path;
            GetTask taskGetAll = new GetTask();
            taskGetAll.execute(url);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onBottomSheetButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onBottomSheetButtonClick(View)");

        myBottomSheetDialogFragment.show(
                getSupportFragmentManager(),
                MyBottomSheetDialogFragment.TAG
        );
    }

    public void performGetTask(String path) {
        String urlGetByAvailable = IP_ADDRESS + path;

        GetTask taskGetByAvailable = new GetTask();
        taskGetByAvailable.execute(urlGetByAvailable);
    }

    boolean availableSwitcher = true;
    public void onAddDvdButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onAddDvdButtonClick(View)");

        availableSwitcher = !availableSwitcher;

        String path = "/dvds";
        String url = IP_ADDRESS + path;
        Dvd newDvd = new Dvd("Escape from Poverty", availableSwitcher);
        PostTaskParams params = new PostTaskParams(url, newDvd);

        PostTask taskPost = new PostTask();
        taskPost.execute(params);

        // update local data
        GetTask taskGetAll = new GetTask();
        taskGetAll.execute(url);
    }

    private class PostTask extends AsyncTask<PostTaskParams, Void, Void> {
        @Override
        protected Void doInBackground(PostTaskParams... postTaskParams) {
            String url = postTaskParams[0].url;
            Dvd newDvd = postTaskParams[0].dvd;

            restTemplate.postForObject(url, newDvd, Dvd.class);
            return null;
        }
    }

    private class PostTaskParams {
        String url;
        Dvd dvd;
        public PostTaskParams(String url, Dvd dvd) {
            this.url = url;
            this.dvd = dvd;
        }
    }

    public void onSwitchModeButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSwitchModeButtonClick(View)");

        recordScrollPosition();

        toggleMode();

        recyclerView.setLayoutManager( instantiateLayoutManager() );

        loadScrollPosition();
    }

    private void recordScrollPosition() {
        switch (mode) {
            case GRID:
                scrollPosition = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                break;
            case LINEAR:
                scrollPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.recordScrollPosition() switch (mode)'s default block.");
                break;
        }
    }

    private void loadScrollPosition() {
        recyclerView.scrollToPosition(scrollPosition);
    }

    /**
     * Alternate current mode.
     */
    private void toggleMode() {
        mode = (mode == Mode.GRID) ? (Mode.LINEAR) : (Mode.GRID);
    }

    private RecyclerView.LayoutManager instantiateLayoutManager() {
        switch (mode) {
            case GRID:
                int numberOfColumns = 4;
                return new GridLayoutManager(this, numberOfColumns);
            case LINEAR:
                return new LinearLayoutManager(this);
            default:
                Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.instantiateLayoutManager() switch (mode)'s default block.");
                return null;
        }
    }

    private List<Dvd> loadCSVAsDvd() {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        InputStream inputStream = getResources().openRawResource(R.raw.dvd_library_collection_unsorted_csv);

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] titles = stringBuilder.toString().split(",");
        Log.d(MainActivity.DEBUG_TAG, "number of titles (via array's length): " + titles.length);

        List<Dvd> dvds = new ArrayList<Dvd>();
        ///////////////////////////////////////////////////////////////////////////
        int counter = 0;
        for (int i = 0; i < titles.length; i++) {
            counter++;

            String title = titles[i];
            Log.d(MainActivity.DEBUG_TAG, String.format("%3d: %s", counter, title));

            Dvd dvd = new Dvd(title, true);
            dvds.add(dvd);
        }
        Toast.makeText(this, "number of dvds (via counter): " + counter, Toast.LENGTH_SHORT).show();
        ///////////////////////////////////////////////////////////////////////////

        return dvds;
    }

}