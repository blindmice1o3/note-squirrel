package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.fragments.BreakingNewsFragment;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.fragments.VideoViewFragment;

public class TelevisionActivity extends AppCompatActivity
        implements BreakingNewsFragment.OnFragmentInteractionListener,
        VideoViewFragment.OnVideoViewFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_television);

        BreakingNewsFragment breakingNewsFragment = new BreakingNewsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.framelayout_television_activity, breakingNewsFragment, BreakingNewsFragment.TAG);
        fragmentTransaction.commitNow();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "TelevisionActivity.onFragmentInteration() BreakingNewsFragment.OnFragmentInteractionListener", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVideoViewFragmentInteraction(Uri uri) {
        Toast.makeText(this, "TelevisionActivity.onVideoViewFragmentInteration() VideoViewFragment.OnVideoViewFragmentInteractionListener", Toast.LENGTH_SHORT).show();
    }
}