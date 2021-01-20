package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;

/**
 * Following: https://i-rant.arnaudbos.com/2d-transformations-android-java/
 */
public class MatrixTransformationFragment extends Fragment {
    public static final String TAG = "MatrixTransformationFragment";

    private OnFragmentInteractionListener mListener;

    private ImageView imageView;
    private static final float THETA = 30;

    public MatrixTransformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matrix_transformation, container, false);

        imageView = view.findViewById(R.id.imageview_matrix_transformation_fragment);
        Log.d(MainActivity.DEBUG_TAG, "imageView.getScaleType(): " + imageView.getScaleType().name());
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        Log.d(MainActivity.DEBUG_TAG, "imageView.getScaleType(): " + imageView.getScaleType().name());

        // Display clippitSprite
        Bitmap clippitSpriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
        Bitmap clippitSprite = Bitmap.createBitmap(clippitSpriteSheet, 0, 0, 124, 93);
        imageView.setImageBitmap(clippitSprite);

        return view;
    }

    float pivotX;
    float pivotY;
    final Matrix matrix = new Matrix();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // This method is called after all of ImageView's lifecycle methods are finished
        // (e.g. after it is measured and laid out... has width and height values).
        imageView.post(new Runnable() {
            @Override
            public void run() {
                int widthImageView = imageView.getWidth();
                int heightImageView = imageView.getHeight();
                Log.d(MainActivity.DEBUG_TAG, "widthImageView, heightImageView: " + widthImageView + ", " + heightImageView);
                int widthBitmapImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap().getWidth();
                int heightBitmapImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap().getHeight();
                Log.d(MainActivity.DEBUG_TAG, "widthBitmapImage, heightBitmapImage: " + widthBitmapImage + ", " + heightBitmapImage);

                // Scale and center
                RectF imageRectF = new RectF(0, 0, widthBitmapImage, heightBitmapImage);
                RectF viewRectF = new RectF(0, 0, widthImageView, heightImageView);
                matrix.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER);
                imageView.setImageMatrix(matrix);

                // Setup for rotation
                pivotX = widthImageView / 2f;
                pivotY = heightImageView / 2f;
                Log.d(MainActivity.DEBUG_TAG, "pivotX, pivotY: " + pivotX + ", " + pivotY);
            }
        });

        Button rotateButton = view.findViewById(R.id.button_rotate_matrix_transformation_fragment);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rotate
                matrix.postRotate(THETA, pivotX, pivotY);
                imageView.setImageMatrix(matrix);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMatrixTransformationFragmentInteraction(uri);
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
        void onMatrixTransformationFragmentInteraction(Uri uri);
    }
}
