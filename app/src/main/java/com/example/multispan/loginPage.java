package com.example.multispan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multispan.SQL.DBHelper;

public class loginPage extends AppCompatActivity {

        EditText email,pass;
        Button loginbtn;
        TextView signup;
        DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = findViewById(R.id.loginEmailID);
        pass = findViewById(R.id.loginPasswordID);
        dbHelper = new DBHelper(this);
        loginbtn = findViewById(R.id.loginBtnID);
        signup = findViewById(R.id.loginSignUpID);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String emailCheck = email.getText().toString();
            String passCheck = pass.getText().toString();
            Cursor cursor = dbHelper.getData();
                if(cursor.getCount() == 0){
                Toast.makeText(loginPage.this,"No entries Exists",Toast.LENGTH_LONG).show();
            }
                if (loginCheck(cursor,emailCheck,passCheck)) {
                Intent intent = new Intent(loginPage.this,MainActivity.class);
                intent.putExtra("email",emailCheck);
                email.setText("");
                pass.setText("");
                startActivity(intent);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(loginPage.this);
                builder.setCancelable(true);
                builder.setTitle("Wrong Credential");
                builder.setMessage("Wrong Credential");
                builder.show();
            }
                dbHelper.close();
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

    private boolean loginCheck(Cursor cursor, String emailCheck, String passCheck) {
        while (cursor.moveToNext()){
            Log.i("SQLite Data: ",cursor.getString(0));
            if (cursor.getString(0).equals(emailCheck)) {
                if (cursor.getString(2).equals(passCheck)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}