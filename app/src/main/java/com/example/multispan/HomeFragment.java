package com.example.multispan;

import android.content.Intent;
import android.os.Bundle;
import com.example.multispan.LiveModeling;
import com.google.firebase.auth.FirebaseAuth;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {


    private FirebaseAuth mAuth;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        // if user logout then redirect to login page
        mAuth = FirebaseAuth.getInstance();
        // Check if the user is already logged in
        if (mAuth.getCurrentUser() == null) {
            // User is not logged in, redirect to the login screen
            Intent intent = new Intent(getActivity(), loginPage.class);
            startActivity(intent);
            getActivity().finish(); // Close the current activity
            return null; // Return null as the view since we're redirecting
        }




        CardView liveModelingCardView = view.findViewById(R.id.liveModelingCardViewID);
        liveModelingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LiveModeling.class);
                startActivity(intent);
            }
        });

        CardView humidityCardView = view.findViewById(R.id.humidityCardViewID);
        humidityCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LiveModeling.class);
                startActivity(intent);
            }
        });
        return view;
    }
}