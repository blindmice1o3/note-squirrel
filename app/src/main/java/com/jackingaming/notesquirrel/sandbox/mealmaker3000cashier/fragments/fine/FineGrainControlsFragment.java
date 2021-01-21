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

import com.jackingaming.notesquirrel.R;

import java.util.ArrayList;
import java.util.List;

public class FineGrainControlsFragment extends Fragment {
    public static final String TAG = "FineGrainControlsFragment";

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView recyclerView4;

    private List<String> dataSourceQuadrant1;
    private List<String> dataSourceQuadrant2;
    private List<String> dataSourceQuadrant3;
    private List<String> dataSourceQuadrant4;

    AdapterStringRecyclerView adapterStringRecyclerView1;
    AdapterStringRecyclerView adapterStringRecyclerView2;
    AdapterStringRecyclerView adapterStringRecyclerView3;
    AdapterStringRecyclerView adapterStringRecyclerView4;

    public FineGrainControlsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // COMBOS
        dataSourceQuadrant1 = new ArrayList<String>();
        dataSourceQuadrant1.add("Suprm Crosnt #21");
        dataSourceQuadrant1.add("Ssg Crosnt #22");
        dataSourceQuadrant1.add("Grd Ssg Burrito #23");
        dataSourceQuadrant1.add("Meat Lvr Burrito #24");
        dataSourceQuadrant1.add("SEC Bisc #25");
        dataSourceQuadrant1.add("BEC Bisc #25");
        dataSourceQuadrant1.add("Loaded Bfst Sand #26");
        dataSourceQuadrant1.add("Extreme Ssg #27");
        dataSourceQuadrant1.add("Ult Bfst #28");
        dataSourceQuadrant1.add("Grl Srd Swiss #29");
        dataSourceQuadrant1.add("Bfst Platter #30");
        dataSourceQuadrant1.add("EMPTY");

        // ADDITIONAL ITEMS
        dataSourceQuadrant2 = new ArrayList<String>();
        dataSourceQuadrant2.add("Breakfast Jack");
        dataSourceQuadrant2.add("Sausage Bfst Jack");
        dataSourceQuadrant2.add("Bacon Bfst Jack");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("Srd Bfst Sand");
        dataSourceQuadrant2.add("Hmst Chk Bisc");
        dataSourceQuadrant2.add("Mini Pancakes");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("Donut Holes");
        dataSourceQuadrant2.add("EMPTY");
        dataSourceQuadrant2.add("EMPTY");

        // EXTRAS
        dataSourceQuadrant3 = new ArrayList<String>();
        dataSourceQuadrant3.add("Hash Browns");
        dataSourceQuadrant3.add("Bacon");
        dataSourceQuadrant3.add("Sausage");
        dataSourceQuadrant3.add("Ham");
        dataSourceQuadrant3.add("Croissant");
        dataSourceQuadrant3.add("Biscuit");
        dataSourceQuadrant3.add("Tortilla");
        dataSourceQuadrant3.add("EMPTY");
        dataSourceQuadrant3.add("Shell Egg");
        dataSourceQuadrant3.add("Scramble Egg");
        dataSourceQuadrant3.add("Egg White");
        dataSourceQuadrant3.add("EMPTY");

        // BREAKFAST DRINKS
        dataSourceQuadrant4 = new ArrayList<String>();
        dataSourceQuadrant4.add("Coffee");
        dataSourceQuadrant4.add("Lrg Coffee");
        dataSourceQuadrant4.add("Decaf Coffee");
        dataSourceQuadrant4.add("Lrg Decaf Coffee");
        dataSourceQuadrant4.add("Orange Juice");
        dataSourceQuadrant4.add("Apple Juice");
        dataSourceQuadrant4.add("Milk");
        dataSourceQuadrant4.add("EMPTY");


        adapterStringRecyclerView1 = new AdapterStringRecyclerView(dataSourceQuadrant1);
        adapterStringRecyclerView2 = new AdapterStringRecyclerView(dataSourceQuadrant2);
        adapterStringRecyclerView3 = new AdapterStringRecyclerView(dataSourceQuadrant3);
        adapterStringRecyclerView4 = new AdapterStringRecyclerView(dataSourceQuadrant4);
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

        recyclerView1 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_1);
        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView1.setAdapter(adapterStringRecyclerView1);

        recyclerView2 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_2);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView2.setAdapter(adapterStringRecyclerView2);

        recyclerView3 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_3);
        recyclerView3.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        recyclerView3.setAdapter(adapterStringRecyclerView3);

        recyclerView4 = view.findViewById(R.id.recyclerview_fine_grain_quadrant_4);
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
