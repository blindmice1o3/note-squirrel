package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource.BreakfastItems;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource.DataSourceRepository;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource.LunchAndDinnerItems;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.fine.datasource.SnacksAndSidesItems;

import java.util.ArrayList;
import java.util.List;

public class FineGrainControlsFragment extends Fragment {
    public static final String TAG = "FineGrainControlsFragment";

    private List<String> dataSourceQuadrant1;
    private List<String> dataSourceQuadrant2;
    private List<String> dataSourceQuadrant3;
    private List<String> dataSourceQuadrant4;

    private AdapterStringRecyclerView adapterStringRecyclerView1;
    private AdapterStringRecyclerView adapterStringRecyclerView2;
    private AdapterStringRecyclerView adapterStringRecyclerView3;
    private AdapterStringRecyclerView adapterStringRecyclerView4;

    public FineGrainControlsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSourceQuadrant1 = new ArrayList<String>();
        adapterStringRecyclerView1 = new AdapterStringRecyclerView(dataSourceQuadrant1);
        adapterStringRecyclerView1.setClickListener(new AdapterStringRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), dataSourceQuadrant1.get(position), Toast.LENGTH_SHORT).show();
                // TODO:
            }
        });

        dataSourceQuadrant2 = new ArrayList<String>();
        adapterStringRecyclerView2 = new AdapterStringRecyclerView(dataSourceQuadrant2);
        adapterStringRecyclerView2.setClickListener(new AdapterStringRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), dataSourceQuadrant2.get(position), Toast.LENGTH_SHORT).show();
                // TODO:
            }
        });

        dataSourceQuadrant3 = new ArrayList<String>();
        adapterStringRecyclerView3 = new AdapterStringRecyclerView(dataSourceQuadrant3);
        adapterStringRecyclerView3.setClickListener(new AdapterStringRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), dataSourceQuadrant3.get(position), Toast.LENGTH_SHORT).show();
                // TODO:
            }
        });

        dataSourceQuadrant4 = new ArrayList<String>();
        adapterStringRecyclerView4 = new AdapterStringRecyclerView(dataSourceQuadrant4);
        adapterStringRecyclerView4.setClickListener(new AdapterStringRecyclerView.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), dataSourceQuadrant4.get(position), Toast.LENGTH_SHORT).show();
                // TODO:
            }
        });

        switchDataSource("Breakfast");
    }

    public void switchDataSource(String menuCategory) {
        DataSourceRepository dataSourceRepository = null;
        switch (menuCategory) {
            case "Breakfast":
                dataSourceRepository = new BreakfastItems();
                break;
            case "Lunch & Dinner":
                dataSourceRepository = new LunchAndDinnerItems();
                break;
            case "Snacks & Sides":
                dataSourceRepository = new SnacksAndSidesItems();
                break;
            case "Drinks":
                // TODO: finish implementation of other DataSourceRepository classes.
                break;
            case "Kids & Extras":
                // TODO: finish implementation of other DataSourceRepository classes.
                break;
            case "Late Night Menu":
                // TODO: finish implementation of other DataSourceRepository classes.
                break;
            case "Brunch":
                // TODO: finish implementation of other DataSourceRepository classes.
                break;
        }

        dataSourceQuadrant1.clear();
        dataSourceQuadrant1.addAll(dataSourceRepository.retrieveDataSourceForQuadrant1());
        adapterStringRecyclerView1.notifyDataSetChanged();

        dataSourceQuadrant2.clear();
        dataSourceQuadrant2.addAll(dataSourceRepository.retrieveDataSourceForQuadrant2());
        adapterStringRecyclerView2.notifyDataSetChanged();

        dataSourceQuadrant3.clear();
        dataSourceQuadrant3.addAll(dataSourceRepository.retrieveDataSourceForQuadrant3());
        adapterStringRecyclerView3.notifyDataSetChanged();

        dataSourceQuadrant4.clear();
        dataSourceQuadrant4.addAll(dataSourceRepository.retrieveDataSourceForQuadrant4());
        adapterStringRecyclerView4.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fine_grain_controls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int numberOfColumns = 4;

        RecyclerView recyclerView1 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_1);
        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView1.setAdapter(adapterStringRecyclerView1);

        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_2);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView2.setAdapter(adapterStringRecyclerView2);

        RecyclerView recyclerView3 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_3);
        recyclerView3.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView3.setAdapter(adapterStringRecyclerView3);

        RecyclerView recyclerView4 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_4);
        recyclerView4.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView4.setAdapter(adapterStringRecyclerView4);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
