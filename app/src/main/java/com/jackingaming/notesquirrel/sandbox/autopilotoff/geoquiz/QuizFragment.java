package com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz.models.TrueFalseQuestion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QuizFragment extends Fragment {
    public static final String TAG = "QuizFragment";
    private static final String KEY_CURRENT_INDEX = "current_index";
    private static final String KEY_HAS_CHEATED = "has_cheated";

    private Button trueButton;
    private Button falseButton;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private Button cheatButton;
    private TextView questionTextView;

    private TrueFalseQuestion[] questionBank = new TrueFalseQuestion[] {
            new TrueFalseQuestion(R.string.question_oceans, true),
            new TrueFalseQuestion(R.string.question_mideast, false),
            new TrueFalseQuestion(R.string.question_africa, false),
            new TrueFalseQuestion(R.string.question_americas, true),
            new TrueFalseQuestion(R.string.question_asia, true),
    };

    private int currentIndex = 0;
    private Map<Integer, Boolean> hasCheated = new HashMap<Integer, Boolean>();

    public QuizFragment() {
        // Required empty public constructor
        Log.i(TAG, "constructor()");
    }

    private void updateQuestion() {
        Log.i(TAG, "updateQuestion()");
        int question = questionBank[currentIndex].getQuestion();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        Log.i(TAG, "checkAnswer(boolean)");
        boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
        boolean isCheater = false;
        if (hasCheated.containsKey(currentIndex)) {
            isCheater = hasCheated.get(currentIndex);
        }

        int messageResId = 0;

        if (isCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach(Context)");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(LayoutInflater, ViewGroup, Bundle)");

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionTextView = view.findViewById(R.id.question_text_view);
        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        trueButton = view.findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton = view.findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        previousButton = view.findViewById(R.id.prev_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex -= 1;
                if (currentIndex < 0) {
                    currentIndex = questionBank.length - 1;
                }
                updateQuestion();
            }
        });

        nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        cheatButton = view.findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CheatActivity.class);
                boolean answerIsTrue = questionBank[currentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated(View, Bundle)");

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
            hasCheated = (HashMap<Integer, Boolean>) savedInstanceState.getSerializable(KEY_HAS_CHEATED);
        }
    }

    @TargetApi(11)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated(Bundle)");

        getActivity().setTitle(R.string.geo_quiz_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored(Bundle)");

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState(Bundle)");

        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putSerializable(KEY_HAS_CHEATED, (Serializable) hasCheated);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @TargetApi(11)
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach()");

        getActivity().setTitle(R.string.crimes_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setSubtitle(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i(TAG, "onActivityResult(int, int, Intent)");

        if (data == null) {
            return;
        }

        boolean isCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        // At first, assume not-cheat. Instead of recording both cheat and not-cheat,
        // only record cheat (prevents bug: cheat in CheatActivity,
        // press the back-button to return to AutoPilotOffActivity [QuizFragment],
        // press the cheat-button again to go back to CheatActivity [but don't cheat
        // this second-time-around], press back-button to return HERE...
        // overwriting this index with a did-not-cheat value).
        if (isCheater) {
            hasCheated.put(currentIndex, isCheater);
        }
    }
}