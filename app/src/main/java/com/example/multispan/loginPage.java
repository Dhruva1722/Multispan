package com.example.multispan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginPage extends AppCompatActivity {

    private   EditText email,pass;
    private  Button loginbtn;
    private  TextView signup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.loginEmailID);
        pass = findViewById(R.id.loginPasswordID);

        loginbtn = findViewById(R.id.loginBtnID);
        signup = findViewById(R.id.loginSignUpID);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String emailCheck = email.getText().toString();
            String passCheck = pass.getText().toString();

                // validations for input email and password
                if (TextUtils.isEmpty(emailCheck)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter email!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                // Validation for correct email format
                if (!isValidEmail(emailCheck)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(passCheck)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter password!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                // Validation for password length
                if (TextUtils.isEmpty(passCheck) || passCheck.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password should be at least 6 characters!", Toast.LENGTH_LONG).show();
                    return;
                }


                // signin existing user
                mAuth.signInWithEmailAndPassword(emailCheck, passCheck)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task)
                                    {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                            "Login successful!!",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                            // if sign-in is successful
                                            // intent to home activity
                                            Intent intent
                                                    = new Intent(loginPage.this,
                                                    MainActivity.class);
                                            startActivity(intent);
                                        }

                                        else {

                                            // sign-in failed
                                            Toast.makeText(getApplicationContext(),
                                                            "Login failed!!",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                        }
                                    }
                                });
        }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationForm.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is already authenticated and bypass login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(loginPage.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the login activity
        }
    }

}