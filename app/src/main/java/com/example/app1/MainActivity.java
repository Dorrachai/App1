package com.example.app1;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView responseText, questionText;
    private ImageButton option1, option2, option3;
    private List<Question> questions;
    private int correctAnswers = 0; // Correct answers counter
    private int currentQuestionIndex = 0;
    private Handler handler = new Handler();
    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, using findViewById(int) to programmatically interact with widgets
     * in the UI, calling managedQuery(Uri, String[], String, String[], String) to retrieve
     * cursors for data being displayed, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *        shut down then this Bundle contains the data it most recently
     *   supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText = findViewById(R.id.responseText);
        questionText = findViewById(R.id.question1).findViewById(R.id.questionText);
        option1 = findViewById(R.id.answer3);
        option2 = findViewById(R.id.answer4);
        option3 = findViewById(R.id.answer6);

        questions = loadQuestions();
        setQuestionView();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });

        findViewById(R.id.scoreButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Antal rätt: " + correctAnswers, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setQuestionView() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionText.setText(currentQuestion.getQuestionText());
            option1.setImageResource(currentQuestion.getOptionImageId(1));
            option2.setImageResource(currentQuestion.getOptionImageId(2));
            option3.setImageResource(currentQuestion.getOptionImageId(3));
        } else {
            responseText.setText("Finito!");
            option1.setVisibility(View.INVISIBLE);
            option2.setVisibility(View.INVISIBLE);
            option3.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * Checks the answer selected by the user against the correct answer of the current question.
     * Updates the UI to show whether the answer was correct and increments the correct answers
     * counter if the answer is correct.
     *
     * @param selectedOptionIndex The index of the option selected by the user.
     */
    private void checkAnswer(int selectedOptionIndex) {
        if (selectedOptionIndex == questions.get(currentQuestionIndex).getCorrectAnswer()) {
            responseText.setText("Rätt svar!");
            responseText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            correctAnswers++; // Increment the score for correct answer
        } else {
            responseText.setText("Fel svar!");
            responseText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    setQuestionView();
                } else {
                    responseText.setText("Finito!");
                    option1.setVisibility(View.INVISIBLE);
                    option2.setVisibility(View.INVISIBLE);
                    option3.setVisibility(View.INVISIBLE);
                }
            }
        }, 1000); // Delay to show the next question
    }

    private List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("3 + 3 =", 3, R.drawable.answer3, R.drawable.answer4, R.drawable.answer6));
        questions.add(new Question("4 + 3 =", 2, R.drawable.answer6, R.drawable.answer7, R.drawable.answer5));
        questions.add(new Question("6 - 1 =", 2, R.drawable.answer4, R.drawable.answer5, R.drawable.answer3));
        questions.add(new Question("12 / 3 =", 1, R.drawable.answer4, R.drawable.answer6, R.drawable.answer3));

        return questions;
    }

    class Question {
        private String questionText;
        private int correctAnswer;
        private int[] optionImageIds;

        public Question(String questionText, int correctAnswer, int... optionImageIds) {
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
            this.optionImageIds = optionImageIds;
        }

        public String getQuestionText() {
            return questionText;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }
        /**
         * Gets the resource ID for the image of the specified option index.
         *
         * @param index The index of the option (1-based).
         * @return The resource ID of the image.
         */
        public int getOptionImageId(int index) {
            return optionImageIds[index - 1];
        }
    }
}