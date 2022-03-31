package com.example.googlelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.googlelogin.Journal.UserHelperClass;
import com.example.googlelogin.database.User;
import com.example.googlelogin.database.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecSignupPage extends AppCompatActivity {
    private TextInputEditText nameInput, emailInput, phoneNumberInput, passwordInput;
    private Button secsignupButtonId;
    FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_signup_page);

        fireAuth=FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        passwordInput = findViewById(R.id.passwordInput);
        secsignupButtonId = findViewById(R.id.secsignupButtonId);

        secsignupButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SecSignupPage.this, LoginPage.class));
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String phone = phoneNumberInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();



                boolean register_validation_check=dataValidation(name,email,phone,password);
                if (register_validation_check==true){
                    signup();
                }
                else{
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void signup() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

         fireAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     userModel Model=new userModel(emailInput.getText().toString(),nameInput.getText().toString(),phoneNumberInput.getText().toString(),passwordInput.getText().toString(),fireAuth.getUid());
                     FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                     DatabaseReference databaseReference = firebaseDatabase.getReference("Registered");
                     databaseReference.push().setValue(Model);

                   Toast.makeText(SecSignupPage.this, "Registered Successfully, now please login to continue", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(SecSignupPage.this, LoginPage.class);
                     startActivity(intent);
                 }
                 else {
                     Toast.makeText(SecSignupPage.this, "Unsuccessful!!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                 }

             }
         });
    }
    private boolean dataValidation(String name, String email, String phone, String password) {
        if (name.isEmpty()) {
            nameInput.setError("Please enter your full name");
        } else if (email.isEmpty()) {
            emailInput.setError("Please enter your email address");
        } else if (phone.isEmpty()) {
            phoneNumberInput.setError("Please enter your number");
        } else if (password.isEmpty()) {
            passwordInput.setError("Please enter your password");
        } else {
            User user = new User(name, 12, email, phone, password);
        }
        return true;
    }
}