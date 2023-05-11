package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LiveModeling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_modeling);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.live_modeling_containerID, new LiveModelingFragment())
                .commit();
    }
}