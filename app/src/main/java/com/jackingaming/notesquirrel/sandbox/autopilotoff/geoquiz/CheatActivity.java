package com.jackingaming.notesquirrel.sandbox.autopilotoff.geoquiz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jackingaming.notesquirrel.R;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "CheatActivity";
    // Key to starting-data-attached-by-activity-that-launched-CheatActivity.
    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.jackingaming.notesquirrel.answer_is_true";
    // Key to results-to-be-returned-by-CheatActivity.
    public static final String EXTRA_ANSWER_SHOWN =
            "com.jackingaming.notesquirrel.answer_shown";
    private static final String KEY_ANSWER_SHOWN = "answer_shown";

    private boolean answerIsTrue;
    private TextView answerTextView;
    private Button showAnswerButton;
    private TextView apiLevelTextView;

    private boolean answerShown;

    private void showAnswer() {
        if (answerIsTrue) {
            answerTextView.setText(R.string.true_button);
        } else {
            answerTextView.setText(R.string.false_button);
        }
    }

    private void updateAnswerShownResult(boolean isAnswerShown) {
        Log.d(TAG, "updateAnswerShownResult(boolean) answerShown: " + answerShown);
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @TargetApi(11)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate(Bundle)");
        setContentView(R.layout.activity_cheat);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }
        apiLevelTextView = findViewById(R.id.api_level_text_view);
        apiLevelTextView.setText("API level " + Build.VERSION.SDK_INT);

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        answerTextView = findViewById(R.id.answer_text_view);
        showAnswerButton = findViewById(R.id.show_answer_button);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();

                answerShown = true;
                updateAnswerShownResult(answerShown);
            }
        });

        // Answer will not be shown until the user presses the showAnswerButton.
        if (savedInstanceState != null) {
            answerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);
            if (answerShown) {
                showAnswer();
            }
        }
        updateAnswerShownResult(answerShown);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState(Bundle)");
        outState.putBoolean(KEY_ANSWER_SHOWN, answerShown);
    }
}