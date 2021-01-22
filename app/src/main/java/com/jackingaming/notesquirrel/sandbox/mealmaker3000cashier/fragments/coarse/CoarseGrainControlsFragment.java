package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.coarse;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;
import java.util.List;

public class CoarseGrainControlsFragment extends Fragment {
    public static final String TAG = "CoarseGrainControlsFragment";

    public interface OnFragmentInteractionListener {
        void onCoarseGrainControlsFragmentListViewItemClicked(String menuCategory);
    }
    private OnFragmentInteractionListener listener;

    private List<String> menuCategories;
    private CoarseGrainControlsListAdapter coarseGrainControlsListAdapter;

    public CoarseGrainControlsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuCategories = new ArrayList<String>();
        menuCategories.add("Breakfast");
        menuCategories.add("Lunch & Dinner");
        menuCategories.add("Snacks & Sides");
        menuCategories.add("Drinks");
        menuCategories.add("Kids & Extras");
        menuCategories.add("Late Night Menu");
        menuCategories.add("Brunch");

        coarseGrainControlsListAdapter = new CoarseGrainControlsListAdapter(getContext(), menuCategories);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coarse_grain_controls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listview_coarse_grain_controls);
        listView.setAdapter(coarseGrainControlsListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), menuCategories.get(position), Toast.LENGTH_SHORT).show();
                listener.onCoarseGrainControlsFragmentListViewItemClicked(menuCategories.get(position));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
