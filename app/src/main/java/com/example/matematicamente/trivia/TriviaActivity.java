package com.example.matematicamente.trivia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.matematicamente.R;
import com.example.matematicamente.utils.JSONParser;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TriviaActivity extends AppCompatActivity {

    TextView triviaQuestion;
    Button firstOption;
    Button secondOption;
    Button thirdOption;
    Button fourthOption;

    JSONArray triviaData;
    int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        triviaQuestion = findViewById(R.id.trivia_question);
        firstOption = findViewById(R.id.option_a);
        secondOption = findViewById(R.id.option_b);
        thirdOption = findViewById(R.id.option_c);
        fourthOption = findViewById(R.id.option_d);

        try {
            JSONObject json = JSONParser.readJSONFromAsset(getApplicationContext(), "trivia.json");
            triviaData = json.getJSONArray("trivia");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        updateCurrentTrivia();
    }

    protected void updateCurrentTrivia(){
        try {
            JSONObject question = triviaData.getJSONObject(currentQuestion);
            updateQuestion(question.getString("question"));
            updateOptions(question.getJSONArray("options"));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    protected void updateQuestion(String question){
        triviaQuestion.setText(question);
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

    public void validateAnswer(View view){
        /**
        RadioButton answer = findViewById(options.getCheckedRadioButtonId());
        if (answer.getText().equals(getCurrentRightAnswer())) {
            currentQuestion++;
            updateCurrentTrivia();
        }
         */
    }
}
