package com.example.multispan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {


    private FirebaseAuth mAuth;

    public AccountFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();


        View view = inflater.inflate(R.layout.fragment_account, container, false);
        LinearLayout profile = view.findViewById(R.id.lyProfileID);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
            }
        });

        LinearLayout aboutus = view.findViewById(R.id.lyAboutUsID);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
            }
        });

        LinearLayout contactus = view.findViewById(R.id.lyContactUsID);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUs.class);
                startActivity(intent);
            }
        });

         LinearLayout logoutbtn = view.findViewById(R.id.lyLogoutID);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void logoutUser() {
        mAuth.signOut();

        // Redirect to login screen
        Intent intent = new Intent(getActivity(), loginPage.class);
        startActivity(intent);
        getActivity().finish(); // Close the current activity
    }

}