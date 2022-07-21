package com.example.loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
 //this activity is a splash activity which pops up as soon the user starts the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,SignUpActivity.class)); //after the splash screen the user will be directed to sign up activity
                finish();
            }
        },2000);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}
