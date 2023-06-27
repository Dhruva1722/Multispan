package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class ACMFM1<arg0> extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

        // create array of Strings
        // and store name of courses
        String[] parameters = { "KKIH", "Voltage",
        "Current", "Walt",
        "Sh Value" };


    Spinner spinner;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acmfm1);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        // Create an instance of the PagerAdapter
        AboutUsPagerAdapter pagerAdapter = new AboutUsPagerAdapter(getSupportFragmentManager());

        // Add fragments to the PagerAdapter
        pagerAdapter.addFragment(new Scince1986Fragment(), "KKIH");
        pagerAdapter.addFragment(new LogoPhilosophyFragment(), "Voltage");
        pagerAdapter.addFragment(new ResearchDevelopmentFragment(), "Current");
        pagerAdapter.addFragment(new ManufacturingQuilityFragment(), "Walt");
        pagerAdapter.addFragment(new CertificationFragment(), "Sh Value");

        // Set the adapter for the ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        spinner = findViewById(R.id.spinnerID);

            spinner.setOnItemSelectedListener(this);


            ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, parameters);


            ad.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);


            spinner.setAdapter(ad);
        }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),
                        parameters[position],
                        Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
