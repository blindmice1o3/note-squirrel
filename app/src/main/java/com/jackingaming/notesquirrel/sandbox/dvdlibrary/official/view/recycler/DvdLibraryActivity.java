package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog.AddToCartDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.alertdialog.RemoveFromCartDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.MyBottomSheetDialogFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.AddNewDvdToDBCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.Command;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.SearchByAvailableCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.dialogs.bottomsheet.commands.SearchByTitleCommand;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.fragments.RecyclerViewFragment;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.fragments.SearchByTitleFragment;
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

public class DvdLibraryActivity extends AppCompatActivity
        implements AddToCartDialogFragment.AddToCartAlertDialogListener,
        RemoveFromCartDialogFragment.RemoveFromCartAlertDialogListener,
        SearchByTitleFragment.OnSearchByTitleFragmentListener,
        RecyclerViewFragment.RecyclerViewFragmentListener {

    public static final String IP_ADDRESS_REST_CONTROLLER = "http://192.168.1.121:8080";

    private final RestTemplate restTemplate = new RestTemplate();
    private ProgressDialog progressDialog;

    private RecyclerViewFragment recyclerViewFragment;

    private List<Dvd> dvds;
    private AdapterRecyclerView adapterLibrary;

    private List<Command> commandsForBottomSheet;
    private MyBottomSheetDialogFragment myBottomSheetDialogFragment;

    private List<Dvd> cart;
    private AdapterRecyclerView adapterCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd_library);

        progressDialog = new ProgressDialog(this);

        commandsForBottomSheet = new ArrayList<Command>();
        commandsForBottomSheet.add(new AddNewDvdToDBCommand(this));
        commandsForBottomSheet.add(new SearchByAvailableCommand(this));
        commandsForBottomSheet.add(new SearchByTitleCommand(this));
        myBottomSheetDialogFragment = new MyBottomSheetDialogFragment(commandsForBottomSheet);

        // DEFAULT data (not downloaded from database)
        dvds = loadCSVAsDvd();
        cart = new ArrayList<Dvd>();

        recyclerViewFragment = new RecyclerViewFragment();
        performFragmentTransactionAdd(
                R.id.framelayout_placeholder_recyclerview,
                recyclerViewFragment,
                RecyclerViewFragment.TAG);
    }

    public void performFragmentTransactionAdd(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        fragmentTransaction.add(containerViewId, fragment, tag);

        fragmentTransaction.commitNow();
    }

    public void performFragmentTransactionReplace(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        fragmentTransaction.replace(containerViewId, fragment, tag);

        fragmentTransaction.commitNow();
    }

    @Override
    public void initDataSource() {
        adapterLibrary = new AdapterRecyclerView(dvds);
        AdapterRecyclerView.ItemClickListener addToCartItemClickListener = new AdapterRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(DvdLibraryActivity.this, "position: " + position + " | available: " + dvds.get(position).isAvailable(), Toast.LENGTH_SHORT).show();

                AddToCartDialogFragment addToCartDialogFragment = new AddToCartDialogFragment(dvds.get(position));
                addToCartDialogFragment.show(getSupportFragmentManager(), AddToCartDialogFragment.TAG);
            }
        };
        adapterLibrary.setClickListener(addToCartItemClickListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_fragment);
        recyclerView.setAdapter(adapterLibrary);

        String path = "/dvds";
        performGetTask(path);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return recyclerViewFragment.onOptionsItemSelected(item);
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

    public void onViewCartButtonClick(View view) {
        View viewContainingRecyclerView = getLayoutInflater().inflate(R.layout.view_cart_recyclerview, null);
        RecyclerView recyclerView = (RecyclerView) viewContainingRecyclerView.findViewById(R.id.recyclerview_view_cart);
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
                .setView(viewContainingRecyclerView)
                .setPositiveButton("Check out?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DvdLibraryActivity.this, "[Check out] button", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(DvdLibraryActivity.this, "[Cancel] button", Toast.LENGTH_SHORT).show();
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
    public void performAddNewDvdToDB() {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.performAddNewDvdToDB()");

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

    public void onHomeButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onHomeButtonClick(View)");

        // update local data
        String path = "/dvds";
        performGetTask(path);
    }

    @Override
    public void onSearchByTitleFragmentButtonCancelClick() {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSearchByTitleFragmentButtonCancelClick() recyclerViewFragment: " + recyclerViewFragment);
        performFragmentTransactionReplace(
                R.id.framelayout_placeholder_recyclerview,
                recyclerViewFragment,
                RecyclerViewFragment.TAG);
    }

    @Override
    public void onSearchByTitleFragmentButtonOkClick(String title) {
        Log.d(MainActivity.DEBUG_TAG, "RecyclerViewActivity.onSearchByTitleFragmentButtonOkClick(String) recyclerViewFragment: " + recyclerViewFragment);
        performFragmentTransactionReplace(
                R.id.framelayout_placeholder_recyclerview,
                recyclerViewFragment,
                RecyclerViewFragment.TAG);

        String path = "/foo?searchText=" + title;
        performGetTask(path);
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