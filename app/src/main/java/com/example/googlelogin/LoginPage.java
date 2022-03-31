package com.example.googlelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.googlelogin.database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {
private MaterialButton loginButtonId;
private TextInputEditText emailInputField, passwordInputField;
FirebaseAuth fireAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        fireAuth=FirebaseAuth.getInstance();

//        initializing through id's defined above
        emailInputField = findViewById(R.id.emailInputField);
        passwordInputField = findViewById(R.id.passwordInputField);
        loginButtonId = findViewById(R.id.loginButtonId);

        loginButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInputField.getText().toString().trim();
                String password = passwordInputField.getText().toString().trim();


                boolean check_login_validation=loginValidation(email,password);
                if (check_login_validation==true){
                      login();
                }
                else{
                    emailInputField.requestFocus();
                    Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void login() {
        String email = emailInputField.getText().toString().trim();
        String password = passwordInputField.getText().toString().trim();

        fireAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginPage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginPage.this, Dashboard.class)
                                    .putExtra("uid", fireAuth.getUid());
                            startActivity(intent);
                            finish();

                        } else {
                            emailInputField.setText("");
                            passwordInputField.setText("");
                            Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }


                });
    }
    private boolean loginValidation(String email,String password){
//                        System.out.print(email+password);

                if (email.isEmpty()){
                    emailInputField.setError("Email cannot be empty");
                }
                else if ( password.isEmpty()){
                    passwordInputField.setError("Password cannot be empty");
                    return false;
                }
                return true;
    }

}