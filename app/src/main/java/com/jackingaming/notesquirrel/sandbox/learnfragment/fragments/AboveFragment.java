package com.jackingaming.notesquirrel.sandbox.learnfragment.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentClickedListener} interface
 * to handle interaction events.
 * Use the {@link AboveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboveFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentClickedListener {
        // TODO: Update argument type and name
        void onFragmentClickedInteraction(View view);
    }
    private OnFragmentClickedListener fragmentClickedListener;
    //set within onAttach(Context)

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboveFragment() {
        Log.d(MainActivity.DEBUG_TAG, "AboveFragment() constructor (BUT SHOULD USE the \"newInstance(String, String)\" factory method to instantiate.");

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboveFragment newInstance(String param1, String param2) {
        Log.d(MainActivity.DEBUG_TAG, "AboveFragment.newInstance(String, String)");

        AboveFragment fragment = new AboveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(MainActivity.DEBUG_TAG, "AboveFragment.onAttach(Context)");

        if (context instanceof OnFragmentClickedListener) {
            fragmentClickedListener = (OnFragmentClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentClickedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.DEBUG_TAG, "AboveFragment.onCreate(Bundle)");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, "AboveFragment.onCreateView(LayoutInflater, ViewGroup, Bundle)");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_test, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentClickedListener != null) {
                    fragmentClickedListener.onFragmentClickedInteraction(v);
                } else {
                    Log.d(MainActivity.DEBUG_TAG, "fragmentClickedListener is null");
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(MainActivity.DEBUG_TAG, "AboveFragment.onDetach()");

        fragmentClickedListener = null;
    }

}