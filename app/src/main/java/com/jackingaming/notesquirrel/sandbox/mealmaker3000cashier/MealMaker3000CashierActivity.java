package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.coarse.CoarseGrainControlsFragment;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.FineGrainControlsFragment;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.simulator.CustomerSimulatorFragment;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.staging.MealStagingScreenFragment;

public class MealMaker3000CashierActivity extends AppCompatActivity
        implements CoarseGrainControlsFragment.OnFragmentInteractionListener,
        FineGrainControlsFragment.OnFragmentInteractionListener,
        MealStagingScreenFragment.OnFragmentInteractionListener {

    public static final String IP_ADDRESS_REST_CONTROLLER = "http://143.110.230.163:8080";
    private static final String KEY_COUNTER_SAVED_INSTANCE_STATE = "counter";

    private CustomerSimulatorFragment customerSimulatorFragment;
    private FineGrainControlsFragment fineGrainControlsFragment;
    private CoarseGrainControlsFragment coarseGrainControlsFragment;
    private MealStagingScreenFragment mealStagingScreenFragment;
    private Button buttonSendMealRequest;

    private int counterSavedInstanceState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "MealMaker3000CashierActivity.onCreate(Bundle)");
        setContentView(R.layout.activity_meal_maker_3000_cashier);

        customerSimulatorFragment = new CustomerSimulatorFragment();
        fineGrainControlsFragment = new FineGrainControlsFragment();
        coarseGrainControlsFragment = new CoarseGrainControlsFragment();
        mealStagingScreenFragment = new MealStagingScreenFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.framelayout_mealmaker3000cashier_customer_simulator, customerSimulatorFragment, CustomerSimulatorFragment.TAG);
        fragmentTransaction.add(R.id.framelayout_mealmaker3000cashier_fine_grain_controls, fineGrainControlsFragment, FineGrainControlsFragment.TAG);
        fragmentTransaction.add(R.id.framelayout_mealmaker3000cashier_course_grain_controls, coarseGrainControlsFragment, CoarseGrainControlsFragment.TAG);
        fragmentTransaction.add(R.id.framelayout_mealmaker3000cashier_meal_staging_screen, mealStagingScreenFragment, MealStagingScreenFragment.TAG);
        fragmentTransaction.commitNow();

        buttonSendMealRequest = findViewById(R.id.button_mealmaker3000cashier_send_meal_request);
        buttonSendMealRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MealMaker3000CashierActivity.this, "MealMaker3000CashierActivity's buttonSendMealRequest clicked", Toast.LENGTH_SHORT).show();
                // TODO: upload to the server's database.
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "MealMaker3000CashierActivity.onStart()");
    }

    //Called after onStart(). It is only called if there is a saved state to restore
    // (do NOT need to check if savedInstanceState is-not null).
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "MealMaker3000CashierActivity.onRestoreInstanceState(Bundle)");
        int counterSavedInstanceState = savedInstanceState.getInt(KEY_COUNTER_SAVED_INSTANCE_STATE);
        this.counterSavedInstanceState = counterSavedInstanceState;
        Toast.makeText(this, String.valueOf(counterSavedInstanceState), Toast.LENGTH_LONG).show();
    }

    //Called before the activity is paused.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(MainActivity.DEBUG_TAG, "MealMaker3000CashierActivity.onSavedInstanceState(Bundle)");
        counterSavedInstanceState++;
        outState.putInt(KEY_COUNTER_SAVED_INSTANCE_STATE, counterSavedInstanceState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCoarseGrainControlsFragmentListViewItemClicked(String menuCategory) {
        fineGrainControlsFragment.switchDataSource(menuCategory);
    }

    @Override
    public void onFineGrainControlsFragmentRecyclerViewItemClicked(String menuItem) {
        if (!menuItem.equals("EMPTY")) {
            mealStagingScreenFragment.addMenuItem(menuItem);
        }
    }

    @Override
    public void onMealStagingScreenFragmentListViewItemClicked(String menuItem) {
        mealStagingScreenFragment.removeMenuItem(menuItem);
    }
}