package com.example.matematicamente.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.matematicamente.R;
import com.example.matematicamente.home.HomeActivity;
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

        button.setEnabled(false);
        nameInput.addTextChangedListener(createInputValidator());
        ageInput.addTextChangedListener(createInputValidator());
    }

    @SuppressLint("ApplySharedPref")
    public void onLogInClick(View view) {
        SharedPreferences pref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(getString(R.string.name_input_text), getText(nameInput));
        Editable age = ageInput.getText();
        editor.putString(getString(R.string.age_input_text), getText(ageInput));
        editor.commit();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private String getText(TextInputEditText input) {
        Editable textEdit = input.getText();
        return textEdit == null ? "" : textEdit.toString();
    }

    private TextWatcher createInputValidator() {
        return new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                boolean isNameEmpty = getText(nameInput).equals("");
                boolean isAgeEmpty = getText(ageInput).equals("");
                button.setEnabled(!isNameEmpty && !isAgeEmpty);
            }
        };
    }

}
