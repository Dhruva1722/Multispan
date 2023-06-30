package com.example.multispan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class VDFPanel extends AppCompatActivity {

    LinearLayout acmfm1;
    TextView dateTimeInLongTextView, format1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdfpanel);
        acmfm1 = findViewById(R.id.lyslave1ID);


        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        format1 = (TextView) findViewById(R.id.format1);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        format1.setText(dateTime);

//
//        SpeedometerView Speed = (SpeedometerView) findViewById(R.id.speedometer);
//        Speed.setLabelConverter(new SpeedometerView.LabelConverter() {
//            @Override
//            public String getLabelFor(double progress, double maxProgress) {
//                return String.valueOf((int) Math.round(progress));
//            }
//        });
//
//// configure value range and ticks
//        Speed.setMaxSpeed(100);
//        Speed.setMajorTickStep(25);
//        Speed.setMinorTicks(0);
//
//// Configure value range colors
//        Speed.addColoredRange(0, 50, Color.GREEN);
//        Speed.addColoredRange(50, 75, Color.YELLOW);
//        Speed.addColoredRange(75, 100, Color.RED);
//        Speed.setSpeed(25, 2000, 500);

        acmfm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VDFPanel.this, ACMFM1.class);
                startActivity(intent);
            }
        });


    }
}