package com.example.matematicamente.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.matematicamente.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText nameInput;
    TextInputEditText ageInput;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameInput = findViewById(R.id.name_input);
        ageInput = findViewById(R.id.age_input);
        button = findViewById(R.id.continue_button);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(getString(R.string.name_input_text), nameInput.getText().toString());
                editor.putString(getString(R.string.age_input_text), ageInput.getText().toString());
                editor.commit();
            }
        });
    }
}
