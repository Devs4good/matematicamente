package com.example.matematicamente.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matematicamente.R;
import com.example.matematicamente.home.HomeActivity;
import com.example.matematicamente.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private final long DELAY = 1500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Activity activity = this;
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        SharedPreferences prefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                        String name = prefs.getString(getString(R.string.name_input_text), "");
                        Intent intent = new Intent(activity, name.isEmpty() ? LoginActivity.class : HomeActivity.class);
                        startActivity(intent);
                    }
                },
                DELAY
        );
    }
}