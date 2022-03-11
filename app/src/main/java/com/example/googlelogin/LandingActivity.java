package com.example.googlelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class LandingActivity extends AppCompatActivity {

    private MaterialButton loginBtn;
    private MaterialButton signupBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        signupBtn = findViewById(R.id.signupBtn);
        loginBtn = findViewById(R.id.loginBtn);



        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LandingActivity.this, LoginPage.class );
            startActivity(intent);
        });
        signupBtn.setOnClickListener(view -> {
            Intent signupIntent = new Intent(LandingActivity.this, SignupPage.class);
            startActivity(signupIntent);
        });
    }
}