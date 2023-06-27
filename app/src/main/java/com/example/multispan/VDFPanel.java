package com.example.multispan;

import static com.example.multispan.R.id.lyslave1ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class VDFPanel extends AppCompatActivity {

    LinearLayout acmfm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdfpanel);
        acmfm1 = findViewById(R.id.lyslave1ID);


        acmfm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VDFPanel.this, ACMFM1.class);
                startActivity(intent);
            }
        });


    }
}