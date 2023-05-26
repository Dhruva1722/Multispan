package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multispan.SQL.DBHelper;

public class RegistrationForm extends AppCompatActivity {

    EditText name ,email ,password;
    DBHelper dbHelper;
    Button registerbtn;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        name = findViewById(R.id.registerNameID);
        email = findViewById(R.id.registerEmailID);
        password = findViewById(R.id.registerPasswordID);
        dbHelper = new DBHelper(this);
        login = findViewById(R.id.registerLogInID);
        registerbtn = findViewById(R.id.registerBtnID);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), loginPage.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = password.getText().toString();
                boolean b = dbHelper.insetUserData(name1, email1, pass1);
                if (b) {
                    Toast.makeText(RegistrationForm.this, "Data inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationForm.this, "Failed to insert Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
