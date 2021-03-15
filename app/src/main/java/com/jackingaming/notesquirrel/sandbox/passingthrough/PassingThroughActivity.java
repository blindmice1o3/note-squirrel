package com.jackingaming.notesquirrel.sandbox.passingthrough;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.GameConsoleFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.WelcomeScreenFragment;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneFarm;

public class PassingThroughActivity extends AppCompatActivity {
    public static final String KEY_IN_SEED_SHOP_DIALOG_STATE = "IN_SEED_SHOP_DIALOG_STATE";
    private boolean inSeedShopDialogState;
    public boolean isInSeedShopDialogState() {
        return inSeedShopDialogState;
    }
    public void setInSeedShopDialogState(boolean inSeedShopDialogState) {
        this.inSeedShopDialogState = inSeedShopDialogState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing_through);

        // "Add/replace your fragment as you would do the first time."
        if (savedInstanceState == null) {
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onCreate(Bundle savedInstanceState) savedInstanceState == null... yes fragment transaction add");
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.framelayout_passing_through, new WelcomeScreenFragment(), WelcomeScreenFragment.TAG)
                    .setReorderingAllowed(true)
                    .commitNow();
        }
        // "Activity is recreated due to any configuration change.
        // Here the fragment will be automatically attached by the fragment manager.
        // You can fetch that fragment by findFragmentByTag(String tag) and then use it."
        else {
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onCreate(Bundle savedInstanceState) savedInstanceState NOT null... no fragment transaction add");

            inSeedShopDialogState = savedInstanceState.getBoolean(KEY_IN_SEED_SHOP_DIALOG_STATE);
            savedInstanceState.putBoolean(KEY_IN_SEED_SHOP_DIALOG_STATE, false);
        }
    }

    public void changeLayout(String gameTitle) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".changeLayout(String gameTitle)");
        GameConsoleFragment gameFragment = new GameConsoleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("game", gameTitle);
        gameFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_passing_through, gameFragment, GameConsoleFragment.TAG)
                .addToBackStack(GameConsoleFragment.TAG)
                .setReorderingAllowed(true)
                .commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onRestoreInstanceState(Bundle savedInstanceState)");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onPause()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (getSupportFragmentManager().findFragmentByTag(GameConsoleFragment.TAG) != null) {
            GameConsoleFragment gameConsoleFragment = (GameConsoleFragment) getSupportFragmentManager().findFragmentByTag(GameConsoleFragment.TAG);
            if (gameConsoleFragment.getGame().getSceneManager().getCurrentScene() instanceof SceneFarm) {

                // Need to dismiss dialog before [super.onSaveInstanceState(outState)],
                // and re-open after everything is done reloading.
                if (SceneFarm.getInstance().isInSeedShopDialogState()) {
                    outState.putBoolean(KEY_IN_SEED_SHOP_DIALOG_STATE, SceneFarm.getInstance().isInSeedShopDialogState());
                    SceneFarm.getInstance().getSeedShopDialogFragment().dismiss();
                }
            }
        }
        super.onSaveInstanceState(outState);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onSaveInstanceState(Bundle outState)");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onDestroy()");
    }
}