package com.example.googlelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {

    private MaterialButton loginBtn;
    private Button signupBtn;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mAuth = FirebaseAuth.getInstance();



        signupBtn = (Button) findViewById(R.id.signupBtn);
        loginBtn = findViewById(R.id.loginBtn);



        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LandingActivity.this, LoginPage.class );
            startActivity(intent);
        });
        signupBtn.setOnClickListener(view -> {
            Intent signupIntent = new Intent(LandingActivity.this, SecSignupPage.class);
        startActivity(signupIntent);


        });
    }

    @Override
    public void onStart(){
        super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
        startActivity(new Intent(LandingActivity.this, LoginPage.class));
    }
        else {
        startActivity(new Intent(LandingActivity.this, Dashboard.class));
    }
}
}