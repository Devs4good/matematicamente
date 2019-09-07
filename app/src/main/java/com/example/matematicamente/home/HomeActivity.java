package com.example.matematicamente.home;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matematicamente.R;
import com.example.matematicamente.contact.ContactActivity;
import com.example.matematicamente.curiosities.CuriositiesActivity;
import com.example.matematicamente.graphics.GraphicsActivity;
import com.example.matematicamente.trivia.TriviaActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void onCuriositiesClick(View button) {
        Intent intent = new Intent(this, CuriositiesActivity.class);
        startActivity(intent);
    }

    public void onGraphicClick(View button) {
        Intent intent = new Intent(this, GraphicsActivity.class);
        startActivity(intent);

    }

    public void onTriviaClick(View button) {
        Intent intent = new Intent(this, TriviaActivity.class);
        startActivity(intent);

    }

    public void onContactClick(View button) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);

    }

}
