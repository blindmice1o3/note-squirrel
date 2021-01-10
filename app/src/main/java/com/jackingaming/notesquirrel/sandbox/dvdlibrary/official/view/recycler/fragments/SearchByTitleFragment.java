package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSearchByTitleFragmentListener} interface
 * to handle interaction events.
 */
public class SearchByTitleFragment extends Fragment {

    private OnSearchByTitleFragmentListener mListener;
    public interface OnSearchByTitleFragmentListener {
        void onSearchByTitleFragmentButtonOkClick(String title);
    }

    private EditText editTextTitle;

    public SearchByTitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_title, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextTitle = view.findViewById(R.id.edittext_title);
    }

    public void onButtonCancelClick(View view) {
        Toast.makeText(getContext(), "SearchByTitleFragment.onButtonCancelClick()", Toast.LENGTH_SHORT).show();
        //TODO: fragment transaction
    }

    public void onButtonOkClick(View view) {
        Toast.makeText(getContext(), "SearchByTitleFragment.onButtonOkClick()", Toast.LENGTH_SHORT).show();
        String title = editTextTitle.getText().toString();
        editTextTitle.setText("");
        mListener.onSearchByTitleFragmentButtonOkClick(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchByTitleFragmentListener) {
            mListener = (OnSearchByTitleFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
