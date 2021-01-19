package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BreakingNewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BreakingNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BreakingNewsFragment extends Fragment {
    public static final String TAG = "BreakingNewsFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BreakingNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BreakingNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BreakingNewsFragment newInstance(String param1, String param2) {
        BreakingNewsFragment fragment = new BreakingNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breaking_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintlayout_breaking_news_fragment);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoViewFragment videoViewFragment = new VideoViewFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.framelayout_television_activity, videoViewFragment, VideoViewFragment.TAG);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        Button matrixTransformationButton = view.findViewById(R.id.button_matrix_transformation_breaking_news_fragment);
        matrixTransformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatrixTransformationFragment matrixTransformationFragment = new MatrixTransformationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.framelayout_television_activity, matrixTransformationFragment, MatrixTransformationFragment.TAG);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });

        final TextView textViewTagHD = view.findViewById(R.id.tag_hd_breaking_news_fragment);
        Button removeViewButton = view.findViewById(R.id.button_remove_view_breaking_news_fragment);
        removeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout.indexOfChild(textViewTagHD) != -1) {
                    Fade fadeOutEffect = new Fade(Fade.OUT);
                    TransitionManager.beginDelayedTransition(constraintLayout, fadeOutEffect);
                    constraintLayout.removeView(textViewTagHD);
                } else {
                    Toast.makeText(getContext(), "view NOT in layout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button addViewButton = view.findViewById(R.id.button_add_view_breaking_news_fragment);
        addViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout.indexOfChild(textViewTagHD) == -1) {
                    Fade fadeInEffect = new Fade(Fade.IN);
                    TransitionManager.beginDelayedTransition(constraintLayout, fadeInEffect);
                    constraintLayout.addView(textViewTagHD);
                } else {
                    Toast.makeText(getContext(), "view already in layout", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
