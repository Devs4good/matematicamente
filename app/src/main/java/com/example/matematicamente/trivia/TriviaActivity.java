package com.example.matematicamente.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matematicamente.R;
import com.example.matematicamente.home.HomeActivity;
import com.example.matematicamente.result.ResultActivity;
import com.example.matematicamente.utils.JSONParser;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TriviaActivity extends AppCompatActivity {

    TextView questionIndex;
    TextView triviaFormulation;
    TextView triviaQuestion;
    TextView explanation;
    TextView showExplanation;
    TextView result;
    Button firstOption;
    Button secondOption;
    Button thirdOption;
    Button fourthOption;
    Button nextQuestion;

    JSONArray triviaData;
    int currentQuestion = 0;
    int questionsQty = 0;
    int rightAnswers = 0;
    boolean canAnswer = true;
    boolean guessed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        questionIndex = findViewById(R.id.question_index);
        triviaFormulation = findViewById(R.id.trivia_formulation);
        triviaQuestion = findViewById(R.id.trivia_question);
        firstOption = findViewById(R.id.option_a);
        secondOption = findViewById(R.id.option_b);
        thirdOption = findViewById(R.id.option_c);
        fourthOption = findViewById(R.id.option_d);
        explanation = findViewById(R.id.explanation);
        showExplanation = findViewById(R.id.show_explanation);
        nextQuestion = findViewById(R.id.next_question);
        result = findViewById(R.id.result);

        try {
            JSONObject json = JSONParser.readJSONFromAsset(getApplicationContext(), "trivia.json");
            triviaData = json.getJSONArray("trivia");
            questionsQty = triviaData.length();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        updateCurrentTrivia();
    }

    protected void updateCurrentTrivia(){
        try {
            JSONObject question = triviaData.getJSONObject(currentQuestion);
            updateFormulation(question.getString("formulation"));
            updateQuestion(question.getString("question"));
            updateOptions(question.getJSONArray("options"));
            updateExplanation(question.getString("explanation"));
            updateQuestionIndex();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    protected void updateQuestionIndex(){
        questionIndex.setText(( currentQuestion + 1) + "/" + questionsQty);
    }

    protected void updateFormulation(String formulation){
        triviaFormulation.setText(formulation);
    }

    protected void updateQuestion(String question){
        triviaQuestion.setText(question);
    }

    protected void updateExplanation(String text) {
        explanation.setText(text);
    }

    protected void updateOptions(JSONArray options){
        try {
            firstOption.setText(options.getString(0));
            secondOption.setText(options.getString(1));
            thirdOption.setText(options.getString(2));
            fourthOption.setText(options.getString(3));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    protected String getCurrentRightAnswer() {
        try {
            JSONObject question = triviaData.getJSONObject(currentQuestion);
            return question.getString("answer");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void validateAnswer(View button){
        Button answer = (Button) button;
        canAnswer = false;
        answer.setBackgroundResource(R.drawable.btn_rounded_corner);
        if (answer.getText().equals(getCurrentRightAnswer())) {
            answer.setBackgroundTintList(getResources().getColorStateList(R.color.colorRightAnswer));
            answer.setTextColor(Color.parseColor("#FFFFFF"));
            answer.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_checked, 0, 0, 0);
            rightAnswers++;
            guessed = true;
        } else {
            answer.setBackgroundTintList(getResources().getColorStateList(R.color.colorWrongAnswer));
            answer.setTextColor(Color.parseColor("#FFFFFF"));
            answer.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_cancel, 0, 0, 0);
            guessed = false;
        }
        showExplanation.setVisibility(View.VISIBLE);
    }

    public void getNextQuestion(View view){
        currentQuestion++;

        if (currentQuestion == questionsQty) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("result", rightAnswers);
            intent.putExtra("qty", questionsQty);
            startActivity(intent);
        } else {
            canAnswer = true;
            hideExplanation();
            toggleButtons(View.VISIBLE);
            resetOptionStyles();
            updateCurrentTrivia();
        }
    }

    public void resetOptionStyles() {
        Button[] buttons = new Button[]{firstOption, secondOption, thirdOption, fourthOption};

        for (Button button : buttons) {
            button.setBackgroundResource(R.drawable.btn_rounded_corner_outline);
            button.setTextColor(getResources().getColor(R.color.colorPrimary));
            button.setBackgroundTintList(null);
            button.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
        }
    }

    protected void toggleButtons(int visibility) {
        Button[] buttons = new Button[]{firstOption, secondOption, thirdOption, fourthOption};

        for (Button button : buttons) {
            button.setVisibility(visibility);
        }
    }

    public void showExplanation(View view) {
        toggleButtons(View.GONE);
        result.setText(guessed ? "Tu respuesta fue correcta porque" : "Tu respuesta fue incorrecta porque");
        result.setVisibility(View.VISIBLE);
        showExplanation.setVisibility(View.GONE);
        explanation.setVisibility(View.VISIBLE);
        nextQuestion.setVisibility(View.VISIBLE);
    }

    protected void hideExplanation() {
        result.setVisibility(View.GONE);
        explanation.setVisibility(View.GONE);
        nextQuestion.setVisibility(View.GONE);
    }
}
