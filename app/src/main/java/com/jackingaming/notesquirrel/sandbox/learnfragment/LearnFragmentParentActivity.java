package com.jackingaming.notesquirrel.sandbox.learnfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.learnfragment.fragments.FirstTestFragment;

public class LearnFragmentParentActivity extends AppCompatActivity
        implements FirstTestFragment.OnFragmentInteractionListener {

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "LearnFragmentParentActivity's fragment's interface... FirstTestFragment.OnFragmentInteractionListener.onFragmentInteraction(Uri)", Toast.LENGTH_SHORT).show();
    }

    public void onLeftButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)");
    }

    public void onRightButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onRightButtonClick(View)");

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