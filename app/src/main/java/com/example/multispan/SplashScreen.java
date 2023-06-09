package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // Check if it's the first app launch
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) {
            // Show the splash screen
            // You can use a separate activity or a dialog fragment to display the splash screen
            // Once the splash screen is dismissed, set isFirstLaunch to false
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
        } else {
            navigateToScreen();
            return;
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), loginPage.class));
            }
        },2000);


    }
    private void navigateToScreen() {
        // Check if the user is already logged in
        if (isUserLoggedIn()) {
            // User is logged in, navigate to the home screen
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        } else {
            // User is not logged in, navigate to the registration/login screen
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        }

        finish(); // Finish the splash activity to prevent navigating back to it
    }
    private boolean isUserLoggedIn() {
        // TODO: Implement your logic to check if the user is logged in
        // You can use SharedPreferences, a flag in the database, or any other method
        // Return true if the user is logged in, false otherwise
        return false; // Placeholder value, update with your actual implementation
    }
}