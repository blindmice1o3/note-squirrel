package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.television.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;

/**
 * Following: https://i-rant.arnaudbos.com/2d-transformations-android-java/
 */
public class MatrixTransformationFragment extends Fragment {
    public static final String TAG = "MatrixTransformationFragment";

    private enum MatrixConcatenation {
        PRE, POST
    }

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
        return inflater.inflate(R.layout.fragment_matrix_transformation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Display clippitSprite
        imageView = view.findViewById(R.id.imageview_matrix_transformation_fragment);
        Bitmap clippitSpriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.pc_ms_office_clippit);
        Bitmap clippitSprite = Bitmap.createBitmap(clippitSpriteSheet, 0, 0, 124, 93);
        imageView.setImageBitmap(clippitSprite);

        // Setup for rotation
        final Matrix matrix = new Matrix();
        final float pivotX = ((BitmapDrawable)imageView.getDrawable()).getBitmap().getWidth() / 2f;
        final float pivotY = ((BitmapDrawable)imageView.getDrawable()).getBitmap().getHeight() / 2f;
        Toast.makeText(getContext(), "pivotX, pivotY: " + pivotX + ", " + pivotY, Toast.LENGTH_SHORT).show();
        imageView.setScaleType(ImageView.ScaleType.MATRIX);


//        Point outSize = new Point();
//        view.getDisplay().getSize(outSize);
//        int width = outSize.x;
//        int height = outSize.y;
//        final Matrix matrix = center(width, height, new BitmapDrawable(getResources(), clippitSprite));
//        imageView.setImageMatrix(matrix);

        Button rotateButton = view.findViewById(R.id.button_rotate_matrix_transformation_fragment);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rotateClippit(imageView, 0, 0, matrix, MatrixConcatenation.POST);
                matrix.postRotate(THETA, pivotX, pivotY);
                imageView.setImageMatrix(matrix);
            }
        });
    }

    private static Matrix center(float width, float height, Drawable d) {
        final float drawableWidth = d.getIntrinsicWidth();
        final float drawableHeight = d.getIntrinsicHeight();
        final float widthScale = width / drawableWidth;
        final float heightScale = height / drawableHeight;
        final float scale = Math.min(1.0f, Math.min(widthScale, heightScale));
        Matrix m = new Matrix();
        m.postScale(scale, scale);
        m.postTranslate((width - drawableWidth * scale) / 2F,
                (height - drawableHeight * scale) / 2F);
        return m;
    }

    private static void rotateClippit(ImageView view, float x, float y,
                                        Matrix matrix, MatrixConcatenation p) {
        switch (p) {
            case PRE:
                matrix.preTranslate(-x, -y);
                matrix.preRotate(THETA);
                matrix.preTranslate(x, y);
                break;
            case POST:
                matrix.postTranslate(-x, -y);
                matrix.postRotate(THETA);
                matrix.postTranslate(x, y);
                break;
        }
        view.setImageMatrix(matrix);
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
