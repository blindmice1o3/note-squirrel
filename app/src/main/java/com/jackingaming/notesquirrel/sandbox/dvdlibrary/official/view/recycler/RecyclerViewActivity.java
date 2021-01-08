package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog.AddToCartDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog.RemoveFromCartDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.MyBottomSheetDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.Command;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.SearchByAvailableCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.SearchByTitleCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.ViewContentOfCartCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods.GetDvdTask;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods.GetDvdTaskParams;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods.PostDvdTask;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods.PostDvdTaskParams;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods.PostListOfDvdsTask;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods.PostListOfDvdsTaskParams;

import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity
        implements AddToCartDialogFragment.AddToCartAlertDialogListener,
        RemoveFromCartDialogFragment.RemoveFromCartAlertDialogListener {

    public static final String IP_ADDRESS_REST_CONTROLLER = "http://192.168.1.121:8080";

    public enum Mode { GRID, LINEAR; }

    private final RestTemplate restTemplate = new RestTemplate();
    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;
    private Mode mode;
    private int scrollPosition;
    private List<Dvd> dvds;
    private AdapterRecyclerView adapterLibrary;

    private List<Command> commandsForBottomSheet;
    private MyBottomSheetDialogFragment myBottomSheetDialogFragment;

    private List<Dvd> cart;
    private AdapterRecyclerView adapterCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        progressDialog = new ProgressDialog(this);

        mode = Mode.GRID;
        scrollPosition = 0;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tinkering);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( instantiateLayoutManager() );

        // DEFAULT data (not downloaded from database)
        dvds = loadCSVAsDvd();
        adapterLibrary = new AdapterRecyclerView(dvds);
        AdapterRecyclerView.ItemClickListener addToCartItemClickListener = new AdapterRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "position: " + position + " | available: " + dvds.get(position).isAvailable(), Toast.LENGTH_SHORT).show();

                AddToCartDialogFragment addToCartDialogFragment = new AddToCartDialogFragment(dvds.get(position));
                addToCartDialogFragment.show(getSupportFragmentManager(), AddToCartDialogFragment.TAG);
            }
        };
        adapterLibrary.setClickListener(addToCartItemClickListener);
        recyclerView.setAdapter(adapterLibrary);

        String path = "/dvds";
        performGetTask(path);

        commandsForBottomSheet = new ArrayList<Command>();
        commandsForBottomSheet.add(new ViewContentOfCartCommand(this));
        commandsForBottomSheet.add(new SearchByAvailableCommand(this));
        commandsForBottomSheet.add(new SearchByTitleCommand(this));
        myBottomSheetDialogFragment = new MyBottomSheetDialogFragment(commandsForBottomSheet);

        cart = new ArrayList<Dvd>();
    }

    @Override
    public void onAddToCartAlertDialogPositiveClick(Dvd dvd) {
        Toast.makeText(this, "RecyclerViewActivity.onAddToCartAlertDialogPositiveClick(Dvd): " + dvd.toString(), Toast.LENGTH_SHORT).show();
        cart.add(dvd);
    }

    @Override
    public void onAddToCartAlertDialogNegativeClick() {
        Toast.makeText(this, "RecyclerViewActivity.onAddToCartAlertDialogNegativeClick()", Toast.LENGTH_SHORT).show();
        // Intentionally blank.
    }

    public void onViewCartButtonClick() {
        View view = getLayoutInflater().inflate(R.layout.view_cart_recyclerview, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_view_cart);
        adapterCart = new AdapterRecyclerView(cart);
        AdapterRecyclerView.ItemClickListener viewCartItemClickListener = new AdapterRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RemoveFromCartDialogFragment removeFromCartDialogFragment = new RemoveFromCartDialogFragment(cart.get(position));
                removeFromCartDialogFragment.show(getSupportFragmentManager(), RemoveFromCartDialogFragment.TAG);
            }
        };
        adapterCart.setClickListener(viewCartItemClickListener);
        recyclerView.setAdapter(adapterCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Dialog viewCartDialog = new AlertDialog.Builder(this)
                .setTitle("View cart")
                .setView(view)
                .setPositiveButton("Check out?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RecyclerViewActivity.this, "[Check out] button", Toast.LENGTH_SHORT).show();

                        String path = "/dvds/checkout";
                        String url = IP_ADDRESS_REST_CONTROLLER + path;
                        PostListOfDvdsTaskParams postListOfDvdsTaskParams = new PostListOfDvdsTaskParams(restTemplate, url, cart);

                        PostListOfDvdsTask postListOfDvdsTask = new PostListOfDvdsTask() {
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                // After successfully checking out, clear() the cart.
                                cart.clear();
                            }
                        };
                        postListOfDvdsTask.execute(postListOfDvdsTaskParams);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RecyclerViewActivity.this, "[Cancel] button", Toast.LENGTH_SHORT).show();
                        // Intentionally blank.
                    }
                })
                .create();
        viewCartDialog.show();
    }

    @Override
    public void onRemoveFromCartAlertDialogPositiveClick(Dvd dvd) {
        Toast.makeText(this, "RecyclerViewActivity.onRemoveFromCartAlertDialogPositiveClick() [Remove from cart] was clicked", Toast.LENGTH_SHORT).show();
        cart.remove(dvd);
        adapterCart.notifyDataSetChanged();
    }

    @Override
    public void onRemoveFromCartAlertDialogNegativeClick() {
        Toast.makeText(this, "RecyclerViewActivity.onRemoveFromCartAlertDialogNegativeClick() [Cancel] was clicked", Toast.LENGTH_SHORT).show();
        // Intentionally blank.
    }

    public void onBottomSheetButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onBottomSheetButtonClick(View)");

        myBottomSheetDialogFragment.show(
                getSupportFragmentManager(),
                MyBottomSheetDialogFragment.TAG
        );
    }

    public void performGetTask(String path) {
        String url = IP_ADDRESS_REST_CONTROLLER + path;
        GetDvdTaskParams getDvdTaskParams = new GetDvdTaskParams(restTemplate, url);

        GetDvdTask getDvdTask = new GetDvdTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog.setMessage("Please wait... It is downloading");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(List<Dvd> dvdsUpdated) {
                super.onPostExecute(dvdsUpdated);
                dvds.clear();
                dvds.addAll(dvdsUpdated);
                adapterLibrary.notifyDataSetChanged();

                progressDialog.hide();
            }
        };

        getDvdTask.execute(getDvdTaskParams);
    }

    boolean availableSwitcher = true;
    public void onAddDvdButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onAddDvdButtonClick(View)");

        availableSwitcher = !availableSwitcher;

        String path = "/dvds";
        String url = IP_ADDRESS_REST_CONTROLLER + path;
        Dvd newDvd = new Dvd("Escape from Poverty", availableSwitcher);
        PostDvdTaskParams postDvdTaskParams = new PostDvdTaskParams(restTemplate, url, newDvd);

        PostDvdTask postDvdTask = new PostDvdTask();
        postDvdTask.execute(postDvdTaskParams);

        // update local data
        performGetTask(path);
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