package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

public class ContactUs extends AppCompatActivity {

    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        relativeLayout = findViewById(R.id.rlContactImgBG);
        relativeLayout.setAlpha(0.5f);
    }
}