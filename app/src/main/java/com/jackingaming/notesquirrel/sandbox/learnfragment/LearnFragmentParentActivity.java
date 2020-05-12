package com.jackingaming.notesquirrel.sandbox.learnfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.learnfragment.fragments.FirstTestFragment;
import com.jackingaming.notesquirrel.sandbox.learnfragment.fragments.NoFrameFragment;

public class LearnFragmentParentActivity extends AppCompatActivity
        implements FirstTestFragment.OnFragmentClickedListener {

    private boolean isBlue = true;
    @Override
    public void onFragmentClickedInteraction(View v) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity's fragment's interface... FirstTestFragment.OnFragmentClickedListener.onFragmentClickedInteraction(View)");

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

        FirstTestFragment framelayoutFirstTestFragment = (FirstTestFragment) fragmentManager.findFragmentById(R.id.framelayout_first_test_fragment);
        FirstTestFragment textviewFirstTestFragment = (FirstTestFragment) fragmentManager.findFragmentById(R.id.textview_first_test_fragment);
        //FirstTestFragment framelayouttopFirstTestFragment = (FirstTestFragment) fragmentManager.findFragmentById(R.id.framelayout_top);
        NoFrameFragment noFrameFragment = (NoFrameFragment) fragmentManager.findFragmentById(R.id.textview_no_frame_fragment);
        NoFrameFragment framelayoutNoFrameFragment = (NoFrameFragment) fragmentManager.findFragmentById(R.id.framelayout_top);
        FirstTestFragment firstTestFragmentInsideFrameLayoutBottom = (FirstTestFragment) fragmentManager.findFragmentById(R.id.framelayout_bottom);
        if (framelayoutFirstTestFragment == null) {
            Log.d(MainActivity.DEBUG_TAG, "framelayoutFirstTestFragment is null");
        }
        if (textviewFirstTestFragment == null) {
            Log.d(MainActivity.DEBUG_TAG, "textviewFirstTestFragment is null");
        }
        /*
        if (framelayouttopFirstTestFragment == null) {
            Log.d(MainActivity.DEBUG_TAG, "framelayouttopFirstTestFragment is null");
        } else {
            Log.d(MainActivity.DEBUG_TAG, "framelayouttopFirstTestFragment is NOT null");
        }
        */
        if (noFrameFragment == null) {
            Log.d(MainActivity.DEBUG_TAG, "noFrameFragment is null");
        } else {
            Log.d(MainActivity.DEBUG_TAG, "noFrameFragment is NOT null");
        }

        /*
        if (framelayouttopFirstTestFragment != null ) {
            // && framelayouttopFirstTestFragment.isInLayout()
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... framelayouttopFirstTestFragment remove(Fragment)");

            //fragmentManager.popBackStack();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(framelayouttopFirstTestFragment);

            transaction.commit();
        } else {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... framelayouttopFirstTestFragment NOT remove(Fragment)");
        }
        */
        if (noFrameFragment != null ) {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... noFrameFragment remove(Fragment)");

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(noFrameFragment);

            transaction.commit();
        } else {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... noFrameFragment NOT remove(Fragment)");
        }

        if (framelayoutNoFrameFragment != null ) {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... framelayoutNoFrameFragment remove(Fragment)");

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(framelayoutNoFrameFragment);

            transaction.commit();
        } else {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... framelayoutNoFrameFragment NOT remove(Fragment)");
        }

        if (firstTestFragmentInsideFrameLayoutBottom != null ) {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... firstTestFragmentInsideFrameLayoutBottom remove(Fragment)");

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(firstTestFragmentInsideFrameLayoutBottom);

            transaction.commit();
        } else {
            Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onLeftButtonClick(View)... firstTestFragmentInsideFrameLayoutBottom NOT remove(Fragment)");
        }
    }

    public void onRightButtonClick(View view) {
        Log.d(MainActivity.DEBUG_TAG, "LearnFragmentParentActivity.onRightButtonClick(View)");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        FirstTestFragment fragment = FirstTestFragment.newInstance("one", "two");
        ////////////////////////////////////////////////////////
        //NoFrameFragment noFrameFragment = new NoFrameFragment();
        ////////////////////////////////////////////////////////
        transaction.replace(R.id.framelayout_bottom, fragment);
        transaction.addToBackStack(null);

        transaction.commit();

        //////////////////////////////////////////////////////////////////////////////////
        //Log.d(MainActivity.DEBUG_TAG, "NoFrameFragment's id: " + noFrameFragment.getId());
        //////////////////////////////////////////////////////////////////////////////////
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