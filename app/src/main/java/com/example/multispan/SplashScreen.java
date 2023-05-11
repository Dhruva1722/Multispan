package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView img;
    TextView text;
    Animation top , bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        img = findViewById(R.id.splash_img);
        text = findViewById(R.id.splashtxt);

        top = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        bottom = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.text);

        img.setAnimation(top);
        text.setAnimation(bottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), loginPage.class));
            }
        },3000);


    }
}