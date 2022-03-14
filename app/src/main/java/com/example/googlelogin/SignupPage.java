package com.example.googlelogin;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.googlelogin.database.User;
import com.example.googlelogin.database.UserRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignupPage extends AppCompatActivity {
    private TextInputEditText nameInput, emailInput, phoneNumberInput, passwordInput;
    private MaterialButton signupButtonId;
    private UserRepository userRepository;


//    public static boolean isNull(EditText textValue, String error_message){
//        textValue.setError(null);
//        if(textValue.length() == 0){
//            textValue.setError(error_message);
//            return true;
//        }
//        return false;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        nameInput = findViewById(R.id.nameInput);
        emailInput =findViewById(R.id.emailInput);
        phoneNumberInput =findViewById(R.id.phoneNumberInput);
        passwordInput = findViewById(R.id.passwordInput);
        signupButtonId = findViewById(R.id.signupButtonId);
        signupButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String phone = phoneNumberInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (name.isEmpty()){
                    nameInput.setError("Please enter your full name");
                }
                else if (email.isEmpty()){
                    emailInput.setError("Please enter your email address");
                }
                else if (phone.isEmpty()){
                    phoneNumberInput.setError("Please enter your number");
                }
                else if (password.isEmpty()){
                    passwordInput.setError("Please enter your password");
                }
                else{
                    User user = new User(name, 12, email, phone, password);
                    long value = userRepository.insertUser(user);
                    if (value != -1 ){
                        Intent intent = new Intent(SignupPage.this, LoginPage.class);
                        intent.putExtra("email", email);
                        intent.putExtra("name", name);
                        intent.putExtra("password", password);
                        startActivity(intent);

                    }}

            }
        });

//        signupButtonId.setOnClickListener(view -> {
//            String name = nameInput.getText().toString().trim();
//            String email = emailInput.getText().toString().trim();
//            String phone = phoneNumberInput.getText().toString().trim();
//            String password = passwordInput.getText().toString().trim();
//
//            if (name.isEmpty()){
//                nameInput.setError("Please enter your full name");
//            }
//            else if (email.isEmpty()){
//                emailInput.setError("Please enter your email address");
//            }
//            else if (phone.isEmpty()){
//                phoneNumberInput.setError("Please enter your number");
//            }
//            else if (password.isEmpty()){
//                passwordInput.setError("Please enter your password");
//            }
//            else{
//                User user = new User(name, 1, email, phone, password);
//                long value = userRepository.insertUser(user);
//                if (value != -1 ){
//                    Intent intent = new Intent(SignupPage.this, LoginPage.class);
//                    intent.putExtra("email", email);
//                    intent.putExtra("name", name);
//                    intent.putExtra("password", password);
//                    startActivity(intent);
//
//                }
//
//            }
//        });


    }
}
