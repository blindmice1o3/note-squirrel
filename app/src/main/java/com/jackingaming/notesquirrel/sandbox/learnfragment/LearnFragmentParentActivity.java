package com.jackingaming.notesquirrel.sandbox.learnfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.learnfragment.fragments.AboveFragment;

public class LearnFragmentParentActivity extends AppCompatActivity
        implements AboveFragment.OnFragmentClickedListener {

    private boolean isBlue = true;
    @Override
    public void onFragmentClickedInteraction(View v) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity's fragment's interface... AboveFragment.OnFragmentClickedListener.onFragmentClickedInteraction(View)");

        if (isBlue) {
            v.setBackgroundColor(Color.BLUE);
        } else {
            v.setBackgroundColor(Color.YELLOW);
        }

        isBlue = !isBlue;
    }

    public void onLeftButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)");

        FragmentManager fragmentManager = getSupportFragmentManager();

        AboveFragment aboveFragmentInLayoutBottom =
                (AboveFragment) fragmentManager.findFragmentById(R.id.framelayout_bottom);

        if (aboveFragmentInLayoutBottom != null) {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... aboveFragmentInLayoutBottom remove(Fragment)");

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(aboveFragmentInLayoutBottom);

            transaction.commit();
        } else {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... aboveFragmentInLayoutBottom NOT remove(Fragment)");
        }
    }

    public void onRightButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onRightButtonClick(View)");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        AboveFragment aboveFragment = AboveFragment.newInstance("one", "two");
        transaction.replace(R.id.framelayout_bottom, aboveFragment);
        //transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_fragment_parent);

        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onCreate(Bundle)");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onDestroy()");
    }

}