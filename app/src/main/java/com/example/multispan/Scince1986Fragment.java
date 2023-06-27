package com.example.multispan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;


public class Scince1986Fragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView retrieveTV;
    public Scince1986Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scince1986, container, false);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // below line is used to get
//        // reference for our database.
//        databaseReference = firebaseDatabase.getReference("Data");
//
//        // initializing our object class variable.
//         retrieveTV = view.findViewById(R.id.idTVRetrieveData);
//
//        // calling method
//        // for getting data.
//        getdata();
      return view;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        getdata();
//    }
//    private void getdata() {
//
//        // calling add value event listener method
//        // for getting the values from database.
//        databaseReference.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                retrieveTV.setText(value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // calling on cancelled method when we receive
//                // any error or we are not able to get the data.
//                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}