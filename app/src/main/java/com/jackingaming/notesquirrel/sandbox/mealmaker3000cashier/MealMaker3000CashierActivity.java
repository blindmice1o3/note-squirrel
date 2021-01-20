package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.coarse.CoarseGrainControlsFragment;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.FineGrainControlsFragment;

public class MealMaker3000CashierActivity extends AppCompatActivity
        implements FineGrainControlsFragment.OnFragmentInteractionListener,
        CoarseGrainControlsFragment.OnFragmentInteractionListener {

    private static final String KEY_COUNTER_SAVED_INSTANCE_STATE = "counter";

    private int counterSavedInstanceState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "MealMaker3000CashierActivity.onCreate(Bundle)");
        setContentView(R.layout.activity_meal_maker_3000_cashier);

        FineGrainControlsFragment fineGrainControlsFragment = new FineGrainControlsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.framelayout_mealmaker3000cashier_fine_grain_controls, fineGrainControlsFragment, FineGrainControlsFragment.TAG);
        fragmentTransaction.commitNow();

        CoarseGrainControlsFragment coarseGrainControlsFragment = new CoarseGrainControlsFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.framelayout_mealmaker3000cashier_course_grain_controls, coarseGrainControlsFragment, CoarseGrainControlsFragment.TAG);
        fragmentTransaction.commitNow();
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
    public void onFineGrainControlsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCoarseGrainControlsFragmentInteraction(Uri uri) {

    }
}