package com.example.zad1;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;

    private Button help_question_Button;
    private int currentIndex = 0;

    private int correctAnswerCount = 0;
    private boolean answerWasShown;

    private static final String TAG = "MainActivity";

    private static final String KEY_CURRENT_INDEX = "currentIndex";

    static final String KEY_EXTRA_ANSWER = "com.example.zad1.correctAnswer";
    private static final int REQUEST_CODE_PROMPT=0;
    private Question[] questions = new Question[]{
            new Question(R.string.q_activity,true),
            new Question(R.string.q_find_resources,false),
            new Question(R.string.q_listener,true),
            new Question(R.string.q_resources,true),
            new Question(R.string.q_version,false)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate wywołanie");

        if(savedInstanceState !=null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        help_question_Button = findViewById(R.id.answer_help_button);
        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_false);
        nextButton = findViewById(R.id.button_next);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex == questions.length - 1) {
                    // Wyświetl wynik
                    String resultMessage = getString(R.string.quiz_result, correctAnswerCount, questions.length);
                    Toast.makeText(MainActivity.this, resultMessage, Toast.LENGTH_LONG).show();
                    // Zresetuj quiz
                    currentIndex = 0;
                    correctAnswerCount = 0;
                } else {
                    currentIndex = (currentIndex + 1) % questions.length;
                }
                answerWasShown = false;
                setNextQuestion();
            }
        });

        setNextQuestion();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        help_question_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
                startActivityForResult(intent,REQUEST_CODE_PROMPT);
            }
        });

    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }else{
            if(userAnswer == correctAnswer){
                resultMessageId = R.string.correct_answer;
                correctAnswerCount++;
            }else{
                resultMessageId = R.string.inncorrect_answer;
            }
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop wywołanie");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart wywołanie");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy wywołanie");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause wywołanie");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume wywołanie");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }



}