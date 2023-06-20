package com.example.multispan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegistrationForm extends AppCompatActivity {


    EditText name, email, password;
    Button registerbtn;
    FirebaseAuth mAuth;
    TextView loginTxt;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.registerNameID);
        email = findViewById(R.id.registerEmailID);
        password = findViewById(R.id.registerPasswordID);

        loginTxt = findViewById(R.id.registerLogInID);
        registerbtn = findViewById(R.id.registerBtnID);

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationForm.this, loginPage.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = password.getText().toString();

                // Validations for input email and password
                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(RegistrationForm.this,
                                    "Please enter name!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(RegistrationForm.this,
                                    "Please enter email!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                // Validation for correct email format
                if (!isValidEmail(email1)) {
                    Toast.makeText(RegistrationForm.this, "Please enter a valid email address!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass1)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter password!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                // Validation for password length
                if (TextUtils.isEmpty(pass1) || pass1.length() < 6) {
                    Toast.makeText(RegistrationForm.this, "Password should be at least 6 characters!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass1) || pass1.length() > 10) {
                    Toast.makeText(RegistrationForm.this, "Password should not be more then 10 character!", Toast.LENGTH_LONG).show();
                    return;
                }
                // Check if email ID already exists
                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email1)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()) {
                                    SignInMethodQueryResult result = task.getResult();
                                    if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                                        // Email ID already exists
                                        Toast.makeText(RegistrationForm.this, "Email ID already exists", Toast.LENGTH_SHORT).show();
                                    } else {

                                        mAuth.createUserWithEmailAndPassword(email1, pass1)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(RegistrationForm.this,
                                                                            "Registration successful!",
                                                                            Toast.LENGTH_LONG)
                                                                    .show();

                                                            Intent intent = new Intent(RegistrationForm.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish(); // Optional: Prevents going back to the registration form
                                                        } else {
                                                            Toast.makeText(
                                                                    RegistrationForm.this,
                                                                    "Registration failed!! Please try again later",
                                                                    Toast.LENGTH_LONG
                                                            ).show();
                                                        }
                                                    }
                                                });
                                    }


                                } else {
                                    // Failed to check email existence
                                    Toast.makeText(RegistrationForm.this, "Failed to check email existence", Toast.LENGTH_SHORT).show();
                                }

                            }


                        });


            }
        });

    }

    private boolean isValidEmail(String email1) {
        return !TextUtils.isEmpty(email1) && Patterns.EMAIL_ADDRESS.matcher(email1).matches();
    }


}



