package com.example.matematicamente.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matematicamente.R;
import com.example.matematicamente.home.HomeActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actity_result);
        updateResult();
    }

    protected void updateResult(){
        TextView result = findViewById(R.id.result);
        result.setText("Respondiste " + getIntent().getExtras().getInt("result") + "/" + getIntent().getExtras().getInt("qty") + " preguntas correctas");
    }

    public void goBackHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
