package com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.staging;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.MealMaker3000CashierActivity;
import com.jackingaming.notesquirrel.sandbox.mealmaker3000cashier.fragments.coarse.StringToTextViewListAdapter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MealStagingScreenFragment extends Fragment {
    public static final String TAG = "MealStagingScreenFragment";

    public interface OnFragmentInteractionListener {
        void onMealStagingScreenFragmentListViewItemClicked(String menuItem);
    }
    private OnFragmentInteractionListener listener;

    final private RestTemplate restTemplate = new RestTemplate();
    final private ProgressDialog progressDialog = new ProgressDialog(getContext());

    private List<String> listItems;
    private StringToTextViewListAdapter stringToTextViewListAdapter;

    public MealStagingScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listItems = new ArrayList<String>();
        listItems.add("Peanut Butter & Jelly Sandwich");

        stringToTextViewListAdapter = new StringToTextViewListAdapter(getContext(), listItems);
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
        listView.setAdapter(stringToTextViewListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), listItems.get(position), Toast.LENGTH_SHORT).show();
                listener.onMealStagingScreenFragmentListViewItemClicked(listItems.get(position));
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

    public void addMenuItem(String menuItem) {
        listItems.add(menuItem);
        stringToTextViewListAdapter.notifyDataSetChanged();
    }

    public void removeMenuItem(String menuItem) {
        listItems.remove(menuItem);
        stringToTextViewListAdapter.notifyDataSetChanged();
    }

    public void uploadMealToRemoteRepository() {
        AsyncTask<Void, Void, Void> uploadMealTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Please wait... It is uploading");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String path = "/meal-maker-3000/checkout";
                String url = MealMaker3000CashierActivity.IP_ADDRESS_REST_CONTROLLER + path;
                restTemplate.postForObject(url, listItems, ResponseEntity.class);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listItems.clear();
                stringToTextViewListAdapter.notifyDataSetChanged();
                progressDialog.hide();
            }
        };
        uploadMealTask.execute();
    }
}
