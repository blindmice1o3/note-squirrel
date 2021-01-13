package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSearchByTitleFragmentListener} interface
 * to handle interaction events.
 */
public class SearchByTitleFragment extends Fragment {
    public static final String TAG = "SearchByTitleFragment";

    public interface OnSearchByTitleFragmentListener {
        void onSearchByTitleFragmentButtonCancelClick();
        void onSearchByTitleFragmentButtonOkClick(String title);

    }
    private OnSearchByTitleFragmentListener listener;

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
        Button buttonCancel = view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SearchByTitleFragment buttonCancel OnClickListener.onClick()", Toast.LENGTH_SHORT).show();

                // close keyboard
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                listener.onSearchByTitleFragmentButtonCancelClick();
            }
        });
        Button buttonOk = view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SearchByTitleFragment buttonOk OnClickListener.onClick()", Toast.LENGTH_SHORT).show();
                String title = editTextTitle.getText().toString();

                // close keyboard
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                listener.onSearchByTitleFragmentButtonOkClick(title);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchByTitleFragmentListener) {
            listener = (OnSearchByTitleFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchByTitleFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
