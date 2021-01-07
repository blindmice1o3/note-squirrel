package com.jackingaming.notesquirrel.sandbox.passingthrough;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.GameViewFragment;

public class PassingThroughActivity extends AppCompatActivity
        implements GameViewFragment.OnGameViewFragmentClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing_through);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_placeholder_viewport, new GameViewFragment())
                .setReorderingAllowed(true)
                .addToBackStack("framelayoutviewport-to-gameviewfragment")
                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.framelayout_placeholder_input, new GameViewFragment())
                .setReorderingAllowed(true)
                .addToBackStack("framelayoutinput-to-gameviewfragment")
                .commit();
    }

    @Override
    public void onGameViewClicked() {
        Toast.makeText(this, "PassingThroughActivity.onGameViewClicked()", Toast.LENGTH_SHORT).show();
    }

}
