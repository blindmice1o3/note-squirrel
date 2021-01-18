package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.jackingaming.notesquirrel.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VideoViewFragment.OnVideoViewFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class VideoViewFragment extends Fragment {

    public static final String TAG = "VideoViewFragment";
    private static final String FILE_NAME = "television_news_recording";

    private OnVideoViewFragmentInteractionListener listener;

    private VideoView videoView;
    private MediaController mediaController;

    public VideoViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videoView = (VideoView) view.findViewById(R.id.videoview_video_view_fragment);

        if (mediaController == null) {
            mediaController = new MediaController(getContext());
            mediaController.setAnchorView(videoView);
        }
        videoView.setMediaController(mediaController);

        Uri rawUri = getRawUri(FILE_NAME);
        videoView.setVideoURI(rawUri);
    }

    private Uri getRawUri(String fileName) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator + File.separator + getActivity().getPackageName() + File.separator + "raw/" + fileName);
    }

    @Override
    public void onStart() {
        super.onStart();

        videoView.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onVideoViewFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVideoViewFragmentInteractionListener) {
            listener = (OnVideoViewFragmentInteractionListener) context;
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
    public interface OnVideoViewFragmentInteractionListener {
        // TODO: Update argument type and name
        void onVideoViewFragmentInteraction(Uri uri);
    }
}
