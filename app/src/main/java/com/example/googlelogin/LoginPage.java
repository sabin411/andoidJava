package com.example.googlelogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.googlelogin.database.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginPage extends AppCompatActivity {
private MaterialButton loginButtonId;
private TextInputEditText emailInputField, passwordInputField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        emailInputField = findViewById(R.id.emailInputField);
        passwordInputField = findViewById(R.id.passwordInputField);

        loginButtonId = findViewById(R.id.loginButtonId);
        loginButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputField.getText().toString().trim();
                String password = passwordInputField.getText().toString().trim();

                // validating login input fields
                System.out.print(email+password);

                if (email.isEmpty()){
                    emailInputField.setError("Email cannot be empty");

                }
                else if ( password.isEmpty()){
                    passwordInputField.setError("Password cannot be empty");
                }
                else{
                    Intent intent = new Intent(LoginPage.this, Dashboard.class);
                    startActivity(intent);
                }

            }
        });


//        loginButtonId.setOnClickListener(view -> {
//            String email = emailInput.getText().toString().trim();
//            String password = passwordInput.getText().toString().trim();
//
//            // validating login input fields
//            System.out.print(email+password);
//
//            if (email.isEmpty()){
//                emailInput.setError("Email cannot be empty");
//
//            }
//            else if ( password.isEmpty()){
//                passwordInput.setError("Password cannot be empty");
//            }
//            else{
//                Intent intent = new Intent(LoginPage.this, Dashboard.class);
//                startActivity(intent);
//            }
//
//            });
    }
}